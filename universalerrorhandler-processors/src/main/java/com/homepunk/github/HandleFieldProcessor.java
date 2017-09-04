package com.homepunk.github;

import com.google.auto.service.AutoService;
import com.homepunk.github.visitors.HandleFieldVisitor;

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
import javax.lang.model.element.TypeElement;

import static javax.lang.model.SourceVersion.RELEASE_7;

@AutoService(Processor.class)
@SupportedAnnotationTypes("com.homepunk.github.HandleField")
@SupportedSourceVersion(RELEASE_7)
public class HandleFieldProcessor extends AbstractProcessor {
    private Map<TypeElement, HandleFieldVisitor> handleFieldVisitors;
    private Set<TypeElement> parents;
    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        parents = new HashSet<>();
        handleFieldVisitors = new HashMap<>();
        messager = processingEnvironment.getMessager();
    }

    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment) {
        if (annotations.isEmpty()) {
            return false;
        }

        final Set<? extends Element> annotatedElements = roundEnvironment.getElementsAnnotatedWith(HandleField.class);
        //  Every element is our annotated field
        for (final Element element : annotatedElements) {
            final TypeElement parent = (TypeElement) element.getEnclosingElement();
            processNewVisitors(parent);
            element.accept(handleFieldVisitors.get(parent), null);
        }

        for (TypeElement parent : parents) {
            handleFieldVisitors.get(parent).generateJavaSource();
        }

        return true;
    }

    private void processNewVisitors(TypeElement parent) {
        HandleFieldVisitor handleFieldVisitor;

        if (parents.add(parent)) {
            handleFieldVisitor = new HandleFieldVisitor(processingEnv, parent);
            handleFieldVisitors.put(parent, handleFieldVisitor);
        }
    }
}
