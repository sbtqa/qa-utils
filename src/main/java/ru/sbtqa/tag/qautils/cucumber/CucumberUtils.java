package ru.sbtqa.tag.qautils.cucumber;

import cucumber.api.Scenario;
import cucumber.runtime.io.MultiLoader;
import cucumber.runtime.model.CucumberFeature;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CucumberUtils {

    public static Locale getLocale(Scenario scenario) {
        List<String> scenarioPaths = new ArrayList<>();
        scenarioPaths.add(scenario.getUri());

        CucumberFeature load = CucumberFeature.load(new MultiLoader(ClassLoader.getSystemClassLoader()), scenarioPaths).get(0);
        String language = load.getGherkinFeature().getFeature().getLanguage();

        return new Locale(language);
    }
}
