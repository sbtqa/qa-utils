package ru.sbtqa.tag.qautils.i18n;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class I18NTest {

    @Test
    public void getI18nDefaultTest() {
        Locale.setDefault(Locale.ENGLISH);
        I18N i18n = I18N.getI18n(I18NTest.class);
        Assert.assertEquals("Test String", i18n.get("test.string"));
    }

    @Test
    public void getI18nNoTranslationTest() {
        I18N i18n = I18N.getI18n(I18NTest.class);
        Assert.assertEquals("test.string.not.exist", i18n.get("test.string.not.exist"));
    }

    @Test
    public void getI18nLocaleTest() {
        I18N i18n = I18N.getI18n(I18NTest.class, new Locale("ru", "RU"));
        Assert.assertEquals("Тестовая строка", i18n.get("test.string"));
    }

    @Test
    public void getI18nCustomRootTest() {
        Locale.setDefault(Locale.ENGLISH);
        I18N i18n = I18N.getI18n(I18NTest.class, "i18ns");
        Assert.assertEquals("Test Strings", i18n.get("test.string.s"));
    }

    @Test
    public void getI18nFullTest() {
        Locale.setDefault(Locale.ENGLISH);
        I18N i18n = I18N.getI18n(I18NTest.class, new Locale("ru", "RU"), "i18ns");
        Assert.assertEquals("Тестовые строки", i18n.get("test.string.s"));
    }

    @Test
    public void getI18nReverseTest() {
        Map<String, String> i18nReverse = new HashMap<>();
        i18nReverse.put("Тестовая строка", "test.string");
        i18nReverse.put("Тестовая строка 1", "test.string.1");
        i18nReverse.put("Тестовая строка 2", "test.string.2");
        i18nReverse.put("Тестовая строка 3", "test.string.3");

        I18N i18n = I18N.getI18n(I18NTest.class, new Locale("ru", "RU"));
        Assert.assertEquals(i18nReverse, i18n.reverse().toMap());
    }
}
