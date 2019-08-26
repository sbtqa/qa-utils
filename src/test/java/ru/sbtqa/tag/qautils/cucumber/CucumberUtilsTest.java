package ru.sbtqa.tag.qautils.cucumber;

import io.cucumber.core.api.Scenario;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.Locale;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CucumberUtilsTest {

    @Test
    public void returnCorrectLanguageLocaleTest() {
        Scenario scenario = mock(Scenario.class);
        when(scenario.getUri()).thenReturn(new File("src/test/resources/features/Correct.feature").toURI().toString());
        Locale expectedLocale = new Locale("ru");

        Locale actualLocale = CucumberUtils.getLocale(scenario);

        Assert.assertEquals(expectedLocale, actualLocale);
    }

    @Test
    public void fallbackToDefaultLanguageTest() {
        Scenario scenario = mock(Scenario.class);
        when(scenario.getUri()).thenReturn(new File("src/test/resources/features/Incorrect.feature").toURI().toString());
        Locale expectedLocale = new Locale("en");

        Locale actualLocale = CucumberUtils.getLocale(scenario);

        Assert.assertEquals(expectedLocale, actualLocale);
    }
}
