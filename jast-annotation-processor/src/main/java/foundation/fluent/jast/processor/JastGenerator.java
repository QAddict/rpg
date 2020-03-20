package foundation.fluent.jast.processor;

import foundation.fluent.jast.generator.LrParser;

import javax.annotation.processing.Filer;

public class JastGenerator {

    private final Filer filer;

    public JastGenerator(Filer filer) {
        this.filer = filer;
    }

    public void generateSources(LrParser lrParser) {

    }

}
