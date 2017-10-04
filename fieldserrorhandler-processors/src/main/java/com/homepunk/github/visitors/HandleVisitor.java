package com.homepunk.github.visitors;

import android.support.annotation.NonNull;

import com.homepunk.github.HandleField;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import com.sun.source.util.Trees;
import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.tree.TreeTranslator;
import com.sun.tools.javac.util.Names;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.ElementScanner7;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

/**
 * Created by Homepunk on 30.08.2017.
 **/

public class HandleVisitor extends ElementScanner7<Void, Void> {
    private static final String ANDROID_APP_PACKAGE = "android.app";
    private static final String ANDROID_UTIL_PACKAGE = "android.util";
    private static final String FIELDS_ERROR_HANDLER_MANAGERS_PACKAGE = "github.homepunk.com.fieldserrorhandler.managers";
    private final CodeBlock.Builder handleFieldMethodBodyBuilder = CodeBlock.builder();
    private final Trees trees;
    // knows where compilation runs, architect folders in builds/intermediate...
    private final Filer filer;
    // output warnings, info etc on compilation time, interrupt compilation process if we send message with ERROR priority
    private final Messager logger;
    // Gives us ability to create, select etc AST objects
    private final TreeMaker treeMaker;
    // converter from our java initial format to format which would be accepted by compiler
    private final Names names;
    private Name targetClassName;
    //  Enclosing element is activity, where our field is located, TypeElement tells us that it's a class
    private TypeElement originElement;
    private List<Name> targetFieldNames;
    private Map<Name, HandleField> annotations;
    private ExecutableElement onHandleResultMethod;
    private Element destinationClass;

    public HandleVisitor(ProcessingEnvironment environment, TypeElement parent) {
        originElement = parent;
        targetClassName = parent.getSimpleName();
        annotations = new HashMap<>();
        targetFieldNames = new ArrayList<>();
        filer = environment.getFiler();
        logger = environment.getMessager();
        trees = Trees.instance(environment);
        final JavacProcessingEnvironment javacEnvironment = (JavacProcessingEnvironment) environment;
        names = Names.instance(javacEnvironment.getContext());
        treeMaker = TreeMaker.instance(javacEnvironment.getContext());
    }

    @Override
    public Void visitVariable(VariableElement field, Void aVoid) {
        targetFieldNames.add(field.getSimpleName());
        annotations.put(field.getSimpleName(), field.getAnnotation(HandleField.class));
        removePrivateModificator(field);
        return super.visitVariable(field, aVoid);
    }

    @Override
    public Void visitExecutable(ExecutableElement executableElement, Void aVoid) {
        this.onHandleResultMethod = executableElement;
        this.destinationClass = onHandleResultMethod.getEnclosingElement();
        return null;
    }


    public void generateJavaSource() {
        final TypeSpec.Builder classBuilder = TypeSpec
                .classBuilder(targetClassName + "_" + "FieldHandler")
                .addModifiers(Modifier.PUBLIC)
                .addOriginatingElement(originElement)
                .addField(getTargetViewField())
                .addField(getLoggerTagField())
                .addMethod(getConstructor())
                .addMethod(getAddHandlerMethod())
                .addMethod(getHandleFieldMethod());

        if (onHandleResultMethod != null) {
            classBuilder.addField(getDestinationField())
                    .addMethod(getSetDestinationMethod());
        }

        writeJavaFile(classBuilder.build());
    }

    private MethodSpec getAddHandlerMethod() {
        MethodSpec.Builder addHandlerBuilder = MethodSpec.methodBuilder("addHandler")
                .addModifiers(Modifier.PUBLIC);
//                .addCode(generateAddHandlerMethodBody());

        return addHandlerBuilder.build();
    }

    private CodeBlock generateAddHandlerMethodBody() {
        // TODO: 28.09.2017
        return null;
    }

    private FieldSpec getTargetViewField() {
        return FieldSpec.builder(ClassName.get(originElement.asType()), "target", Modifier.PRIVATE).build();
    }

    private FieldSpec getLoggerTagField() {
        return FieldSpec
                .builder(String.class, "TAG", Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                .initializer("$S", targetClassName + "_FieldHandler").build();
    }

    private MethodSpec getConstructor() {
        return MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(ClassName.get(originElement.asType()), "target")
                .addStatement("this.target = target")
                .addStatement("handleField()")
                .build();
    }

    private FieldSpec getDestinationField() {
        return FieldSpec.builder(ClassName.get(destinationClass.asType()), "destination", Modifier.PUBLIC).build();
    }

    private MethodSpec getSetDestinationMethod() {
        return MethodSpec.methodBuilder("setDestination")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(ClassName.get(onHandleResultMethod.getEnclosingElement().asType()), "destination")
                .addStatement("this.destination = destination")
                .build();
    }

    private MethodSpec getHandleFieldMethod() {
        return MethodSpec.methodBuilder("handleField")
                .addModifiers(Modifier.PUBLIC)
                .addCode(generateHandleFieldMethodCode())
                .build();
    }

    private CodeBlock generateHandleFieldMethodCode() {
        generateCheckFieldsNotNullCodeBlock();
        generateFieldsHandlingCodeBlock();

        if (onHandleResultMethod != null) {
            generateHandleCallbackCodeBlock();
        }

        return handleFieldMethodBodyBuilder.build();
    }

    private void generateHandleCallbackCodeBlock() {
        if (onHandleResultMethod != null) {
            List<? extends VariableElement> onHandleResultMethodParameters = onHandleResultMethod.getParameters();

            TypeSpec handleResultListener = TypeSpec.anonymousClassBuilder("")
                    .addSuperinterface(getTopLevelClassName("github.homepunk.com.fieldserrorhandler.handlers.listeners", "HandleResultListener"))
                    .addMethod(getAnonymousMethodImpl(onHandleResultMethodParameters.size()))
                    .build();

            handleFieldMethodBodyBuilder.addStatement("$T.getInstance(target).setOnHandleResultListener($L)", getTopLevelClassName(FIELDS_ERROR_HANDLER_MANAGERS_PACKAGE, "FieldsHandleManager"), handleResultListener);

        }
    }

    @NonNull
    private MethodSpec getAnonymousMethodImpl(int destinationParametersSize) {
        MethodSpec.Builder anonymousMethodImplBuilder = MethodSpec.methodBuilder("onFieldHandleResult")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(getTopLevelClassName("github.homepunk.com.fieldserrorhandler.models", "HandleResult"), "handleResult")
                .beginControlFlow("if (destination != null)");

//        if (destinationParametersSize == 1) {
//            anonymousMethodImplBuilder.addStatement("destination.$N(targetType)", onHandleResultMethod.getSimpleName());
//        } else if (destinationParametersSize == 2) {
            anonymousMethodImplBuilder.addStatement("destination.$N(handleResult)", onHandleResultMethod.getSimpleName());
//        }

        return anonymousMethodImplBuilder.endControlFlow().build();
    }

    private void generateCheckFieldsNotNullCodeBlock() {
        final ClassName androidLoggerClassName = getTopLevelClassName(ANDROID_UTIL_PACKAGE, "Log");

        for (Name targetFieldName : targetFieldNames) {
            handleFieldMethodBodyBuilder
                    .beginControlFlow("if (target.$N == null)", targetFieldName)
                    .addStatement("$T.e(TAG, $S)", androidLoggerClassName, targetFieldName + " can't be null")
                    .addStatement("return")
                    .endControlFlow();
        }
    }

    private void generateFieldsHandlingCodeBlock() {
        final ClassName universalHandleManagerClassName = getTopLevelClassName(FIELDS_ERROR_HANDLER_MANAGERS_PACKAGE, "FieldsHandleManager");

        for (Name targetFieldName : targetFieldNames) {
            int universalFieldType = annotations.get(targetFieldName).value();
            int universalFieldActionType = annotations.get(targetFieldName).action();

            handleFieldMethodBodyBuilder.addStatement("$T.getInstance(target).target(target.$N,$L,$L)",
                    universalHandleManagerClassName,
                    targetFieldName,
                    universalFieldType,
                    universalFieldActionType);
        }
    }

    private ClassName getTopLevelClassName(String packageName, String targetClassName) {
        return ClassName.get(packageName, targetClassName).topLevelClassName();
    }

    private void writeJavaFile(TypeSpec typeSpec) {
        final JavaFile javaFile = JavaFile
                .builder(originElement.getEnclosingElement().toString(), typeSpec)
                .addFileComment("Generated by HandleField processor, don't modify")
                .build();

        try {
            final JavaFileObject sourceFile = filer.createSourceFile(javaFile.packageName + "." + typeSpec.name, originElement);

            try (final Writer writer = new BufferedWriter(sourceFile.openWriter())) {
                javaFile.writeTo(writer);
            }

        } catch (IOException e) {
            logger.printMessage(Diagnostic.Kind.ERROR, e.getMessage(), originElement);
        }
    }

    private void removePrivateModificator(VariableElement field) {
        ((JCTree) trees.getTree(field)).accept(new TreeTranslator() {
            @Override
            public void visitVarDef(JCTree.JCVariableDecl jcVariableDecl) {
                super.visitVarDef(jcVariableDecl);
                // remove field modifier
                jcVariableDecl.mods.flags &= ~Flags.PRIVATE;
            }
        });
    }
}
