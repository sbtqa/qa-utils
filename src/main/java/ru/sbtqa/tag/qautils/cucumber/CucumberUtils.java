package ru.sbtqa.tag.qautils.cucumber;

import cucumber.api.Scenario;
import cucumber.runtime.io.MultiLoader;
import cucumber.runtime.model.CucumberFeature;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CucumberUtils {

    private static final Logger LOG = LoggerFactory.getLogger(CucumberUtils.class);
    private static final String DEFAULT_LANGUAGE = "en";

    private CucumberUtils() {
    }

    public static Locale getLocale(Scenario scenario) {
        List<String> scenarioPaths = new ArrayList<>();
        scenarioPaths.add(scenario.getUri());

        MultiLoader multiLoader = new MultiLoader(ClassLoader.getSystemClassLoader());
        String language = DEFAULT_LANGUAGE;
        try {
            CucumberFeature cucumberFeature = CucumberFeature.load(multiLoader, scenarioPaths).get(0);
            language = cucumberFeature.getGherkinFeature().getFeature().getLanguage();
        } catch (Exception e) {
            LOG.warn("Error while reading feature with uri {}. Using default language {} as fallback", scenario.getUri(), DEFAULT_LANGUAGE, e);
        }

        return new Locale(language);
    }
}
