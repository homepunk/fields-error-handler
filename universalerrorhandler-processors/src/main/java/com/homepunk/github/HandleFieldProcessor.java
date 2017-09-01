package com.homepunk.github;

import com.google.auto.service.AutoService;
import com.homepunk.github.visitors.HandleFieldCodeGenerator;

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
    private HandleFieldCodeGenerator codeGenerator;
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

        final Set<? extends Element> handleFieldElements = roundEnvironment.getElementsAnnotatedWith(HandleField.class);
        final Set<? extends Element> handleOnActionElements = roundEnvironment.getElementsAnnotatedWith(HandleOnAction.class);

        //  Every element is our annotated field
        for (final Element element : handleFieldElements) {
            //  Enclosing element is activity, where our field is located, TypeElement tells us that it's a class
            final TypeElement object = (TypeElement) element.getEnclosingElement();
            if (handleOnActionElements.contains(element)) {
                processElement(element);
            }
        }


        return true;
    }

    private void processElement(Element element) {
        codeGenerator = new HandleFieldCodeGenerator(processingEnv, element);
        element.accept(codeGenerator, null);
        codeGenerator.generateJavaSource();
    }
}
