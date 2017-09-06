package com.homepunk.github;

import com.google.auto.service.AutoService;
import com.homepunk.github.visitors.HandleVisitor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

import static javax.lang.model.SourceVersion.RELEASE_7;

@AutoService(Processor.class)
@SupportedAnnotationTypes({"com.homepunk.github.HandleField", "com.homepunk.github.OnFieldHandleResult"})
@SupportedSourceVersion(RELEASE_7)
public class UniversalErrorHandlerProcessor extends AbstractProcessor {
    private Map<TypeElement, HandleVisitor> handleFieldVisitorMap;
    private Set<TypeElement> handleFieldParents;
    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        messager = processingEnvironment.getMessager();
        handleFieldParents = new HashSet<>();
        handleFieldVisitorMap = new HashMap<>();
    }

    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment) {
        if (annotations.isEmpty()) {
            return false;
        }

        processHandleFieldAnnotations(roundEnvironment);
        processOnFieldHandleResultAnnotations(roundEnvironment);

        for (TypeElement parent : handleFieldParents) {
            handleFieldVisitorMap.get(parent).generateJavaSource();
        }

        return true;
    }

    private void processHandleFieldAnnotations(RoundEnvironment roundEnvironment) {
        final Set<? extends Element> annotatedFields = roundEnvironment.getElementsAnnotatedWith(HandleField.class);
        //  Every element is our annotated field
        for (final Element annotatedField : annotatedFields) {
            final TypeElement parent = (TypeElement) annotatedField.getEnclosingElement();
            processHandleFieldVisitors(parent);
            annotatedField.accept(handleFieldVisitorMap.get(parent), null);
        }
    }

    private void processHandleFieldVisitors(TypeElement parent) {
        if (handleFieldParents.add(parent)) {
            handleFieldVisitorMap.put(parent, new HandleVisitor(processingEnv, parent));
        }
    }

    private void processOnFieldHandleResultAnnotations(RoundEnvironment roundEnvironment) {
        final Set<? extends Element> annotatedMethods = roundEnvironment.getElementsAnnotatedWith(OnFieldHandleResult.class);

        for (Element annotatedElement : annotatedMethods) {
            if (annotatedElement.getKind() != ElementKind.METHOD) {
                messager.printMessage(Diagnostic.Kind.ERROR, "@OnFieldHandleResult using for methods only", annotatedElement);
            }

            ExecutableElement method = (ExecutableElement) annotatedElement;

            String handleResultFromClassName = annotatedElement.getAnnotation(OnFieldHandleResult.class).value();

            for (TypeElement handleFieldParent : handleFieldParents) {
                String handleFieldParentName = handleFieldParent.getSimpleName().toString();

                if (handleFieldParentName.equals(handleResultFromClassName)) {
                    if (handleFieldVisitorMap.get(handleFieldParent) != null) {
                        handleFieldVisitorMap.get(handleFieldParent).visitExecutable(method, null);
                    } else {
                        messager.printMessage(Diagnostic.Kind.ERROR, "ERRRO");
                    }
                }
            }
        }
    }

}
