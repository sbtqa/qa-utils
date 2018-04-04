package ru.sbtqa.tag.qautils.cucumber;

import cucumber.api.Scenario;
import cucumber.runtime.ScenarioImpl;
import java.util.Locale;
import org.junit.Assert;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CucumberUtilsTest {

    @Test
    public void returnCorrectLanguageLocaleTest() {
        Scenario scenario = mock(ScenarioImpl.class);
        when(scenario.getUri()).thenReturn("src/test/resources/features/Correct.feature");
        Locale expectedLocale = new Locale("ru");

        Locale actualLocale = CucumberUtils.getLocale(scenario);

        Assert.assertEquals(expectedLocale, actualLocale);
    }

    @Test
    public void fallbackToDefaultLanguageTest() {
        Scenario scenario = mock(ScenarioImpl.class);
        when(scenario.getUri()).thenReturn("src/test/resources/features/Uncorrect.feature");
        Locale expectedLocale = new Locale("en");

        Locale actualLocale = CucumberUtils.getLocale(scenario);

        Assert.assertEquals(expectedLocale, actualLocale);
    }
}
