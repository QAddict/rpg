package foundation.fluent.jast.processor;

import foundation.fluent.jast.StartSymbol;
import foundation.fluent.jast.generator.LrParser;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import java.util.Set;
import java.util.function.Consumer;

import static foundation.fluent.jast.generator.Generator.generateParser;

@SupportedAnnotationTypes("foundation.fluent.jast.StartSymbol")
public class JastProcessor extends AbstractProcessor implements Consumer<ExecutableElement> {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        roundEnv.getElementsAnnotatedWith(StartSymbol.class).stream().map(ExecutableElement.class::cast).forEach(this);
        return true;
    }

    @Override
    public void accept(ExecutableElement element) {
        try {
            ClassToGrammarContext context = new ClassToGrammarContext(element);
            LrParser parser = generateParser(context.getGrammar());
            new JastGenerator(processingEnv.getFiler(), context).generateSources(parser);
        } catch (RuntimeException | Error e) {
            e.printStackTrace();
        }
    }

}
