package ru.sbtqa.tag.qautils.cucumber;

import cucumber.api.Scenario;
import cucumber.runtime.io.MultiLoader;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Locale;

import gherkin.AstBuilder;
import gherkin.Parser;
import gherkin.ast.GherkinDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CucumberUtils {

    private static final Logger LOG = LoggerFactory.getLogger(CucumberUtils.class);
    private static final String DEFAULT_LANGUAGE = "en";

    private CucumberUtils() {
    }

    public static Locale getLocale(Scenario scenario) {
        MultiLoader multiLoader = new MultiLoader(ClassLoader.getSystemClassLoader());
        String language = DEFAULT_LANGUAGE;
        try {
            String content = new String(Files.readAllBytes(Paths.get(scenario.getUri())));
            GherkinDocument gherkinDocument = new Parser<>(new AstBuilder()).parse(content);
            language = gherkinDocument.getFeature().getLanguage();
        } catch (Exception e) {
            LOG.warn("Error while reading feature with uri {}. Using default language {} as fallback", scenario.getUri(), DEFAULT_LANGUAGE, e);
        }

        return new Locale(language);
    }
}
