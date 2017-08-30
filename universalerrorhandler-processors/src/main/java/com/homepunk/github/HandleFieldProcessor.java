package com.homepunk.github;

import com.google.auto.service.AutoService;
import com.homepunk.github.visitors.HandleFieldVisitor;

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

import static javax.lang.model.SourceVersion.RELEASE_6;

@AutoService(Processor.class)
@SupportedAnnotationTypes("com.homepunk.github.HandleField")
@SupportedSourceVersion(RELEASE_6)
public class HandleFieldProcessor extends AbstractProcessor {
    private HandleFieldVisitor visitor;
    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        messager = processingEnvironment.getMessager();
    }

    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment) {
        if (annotations.isEmpty()) {
            return false;
        }

        final Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(HandleField.class);
        //  Every element is our annotated field
        for (final Element element : elements) {
            processElement(element);
        }


        return true;
    }

    private void processElement(Element element) {
        //  Enclosing element is activity, where our field is located, TypeElement tells us that it's a class
        final TypeElement object = (TypeElement) element.getEnclosingElement();

        visitor = new HandleFieldVisitor(processingEnv, element);
        element.accept(visitor, null);
        visitor.brewJava();
    }
}
