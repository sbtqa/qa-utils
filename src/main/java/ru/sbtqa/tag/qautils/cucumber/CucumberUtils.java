package ru.sbtqa.tag.qautils.cucumber;

import cucumber.runtime.io.MultiLoader;
import cucumber.runtime.model.CucumberFeature;
import cucumber.runtime.model.FeatureLoader;
import io.cucumber.core.api.Scenario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CucumberUtils {

    private static final Logger LOG = LoggerFactory.getLogger(CucumberUtils.class);
    private static final String DEFAULT_LANGUAGE = "en";

    private CucumberUtils() {
    }

    public static Locale getLocale(Scenario scenario) {
        List<URI> scenarioPaths = new ArrayList<>();
        scenarioPaths.add(URI.create(scenario.getUri()));

        MultiLoader multiLoader = new MultiLoader(ClassLoader.getSystemClassLoader());
        String language = DEFAULT_LANGUAGE;
        try {
            CucumberFeature cucumberFeature = new FeatureLoader(multiLoader).load(scenarioPaths).get(0);
            language = cucumberFeature.getGherkinFeature().getFeature().getLanguage();
        } catch (Exception e) {
            LOG.warn("Error while reading feature with uri {}. Using default language {} as fallback", scenario.getUri(), DEFAULT_LANGUAGE, e);
        }

        return new Locale(language);
    }
}
