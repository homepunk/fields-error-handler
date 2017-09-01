package com.homepunk.github.visitors;

import android.support.annotation.NonNull;

import com.homepunk.github.HandleField;
import com.homepunk.github.HandleOnAction;
import com.homepunk.github.models.UniversalFieldAction;
import com.homepunk.github.models.UniversalFieldType;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
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

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
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

public class HandleFieldCodeGenerator extends ElementScanner7<Void, Void> {
    private static final String ANDROID_APP_PACKAGE = "android.app";
    private static final String ANDROID_UTIL_PACKAGE = "android.util";
    private static final String UNIVERSAL_ERROR_HANDLER_MANAGERS_PACKAGE = "github.homepunk.com.universalerrorhandler.managers";
    private static final String UNIVERSAL_ERROR_HANDLER_HANDLERS_PACKAGE = "github.homepunk.com.universalerrorhandler.handlers";
    private final Trees trees;
    // knows where compilation runs, architect folders in builds/intermediate...
    private final Filer filer;
    // output warnings, info etc on compilation time, interrupt compilation process if we send message with ERROR priority
    private final Messager logger;
    // Gives us ability to create, select etc AST objects
    private final TreeMaker treeMaker;
    // It's our activity
    private final TypeElement originElement;
    // converter from our java initial format to format which would be accepted by compiler
    private final Names names;
    private final Element element;
    private final String universalFieldType;
    private final String universalFieldActionType;
    private final Name targetClassName;
    private Name targetFieldName;

    public HandleFieldCodeGenerator(ProcessingEnvironment environment, Element element) {
        super();
        this.element = element;
        originElement = (TypeElement) element.getEnclosingElement();
        targetClassName = originElement.getSimpleName();
        filer = environment.getFiler();
        logger = environment.getMessager();
        trees = Trees.instance(environment);
        final JavacProcessingEnvironment javacEnvironment = (JavacProcessingEnvironment) environment;
        names = Names.instance(javacEnvironment.getContext());
        treeMaker = TreeMaker.instance(javacEnvironment.getContext());
        universalFieldType = element.getAnnotation(HandleField.class).value();
        universalFieldActionType = element.getAnnotation(HandleOnAction.class).value();

    }

    @Override
    public Void visitVariable(VariableElement field, Void aVoid) {
        this.targetFieldName = field.getSimpleName();

        ((JCTree) trees.getTree(field)).accept(new TreeTranslator() {
            @Override
            public void visitVarDef(JCTree.JCVariableDecl jcVariableDecl) {
                super.visitVarDef(jcVariableDecl);
                // remove field modifier
                jcVariableDecl.mods.flags &= ~Flags.PRIVATE;
            }
        });

        return super.visitVariable(field, aVoid);
    }


    public void generateJavaSource() {
        final String generatedClassName = targetClassName + "_" + universalFieldType + "FieldHandler";

        final TypeSpec generatedClass = TypeSpec
                .classBuilder(generatedClassName)
                .superclass(getGenerifiedBaseHandlerSuperclass())
                .addModifiers(Modifier.PUBLIC)
                .addOriginatingElement(originElement)
                .addField(getLoggerTagField())
                .addMethod(getConstructor())
                .addMethod(getHandleFieldMethod())
                .build();

        writeJavaFile(generatedClass);
    }

    @NonNull
    private ParameterizedTypeName getGenerifiedBaseHandlerSuperclass() {
        final ClassName baseFieldHandlerClassName = getTopLevelClassName(UNIVERSAL_ERROR_HANDLER_HANDLERS_PACKAGE, "BaseFieldHandler");
        return ParameterizedTypeName.get(baseFieldHandlerClassName, ClassName.get(originElement.asType()));
    }

    @NonNull
    private FieldSpec getLoggerTagField() {
        return FieldSpec
                .builder(String.class, "TAG", Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                .initializer("$S", targetClassName + "_TAG").build();
    }

    @NonNull
    private MethodSpec getConstructor() {
        return MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(ClassName.get(originElement.asType()), "target")
                .addStatement("super(target)")
                .addStatement("handleField()")
                .build();
    }

    @NonNull
    private MethodSpec getHandleFieldMethod() {
        return MethodSpec.methodBuilder("handleField")
                .addModifiers(Modifier.PUBLIC)
                .addCode(generateHandleFieldMethodBody())
                .build();
    }

    private CodeBlock generateHandleFieldMethodBody() {
        final ClassName androidLoggerClassName = getTopLevelClassName(ANDROID_UTIL_PACKAGE, "Log");
        final ClassName universalHandleManagerClassName = getTopLevelClassName(UNIVERSAL_ERROR_HANDLER_MANAGERS_PACKAGE, "UniversalHandleManager");

        return CodeBlock.builder()
                .beginControlFlow("if (targetActivity.$N == null)", targetFieldName)
                .addStatement("$T.i(TAG, $S)", androidLoggerClassName, targetClassName + " EditText can't be null")
                .addStatement("return")
                .endControlFlow()
                .addStatement("$T.target(targetActivity.$N, $T.$N).handleOnAction($T.$N)",
                        universalHandleManagerClassName,
                        targetFieldName,
                        UniversalFieldType.class,
                        universalFieldType.toUpperCase(),
                        UniversalFieldAction.class,
                        universalFieldActionType.toUpperCase())
                .addStatement("$T.i(TAG, $S)", androidLoggerClassName, targetClassName + " handleField method called")
                .build();
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
}
