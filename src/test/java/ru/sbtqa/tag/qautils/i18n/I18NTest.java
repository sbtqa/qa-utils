package ru.sbtqa.tag.qautils.i18n;

import org.junit.Assert;
import org.junit.Test;

import java.util.Locale;

public class I18NTest {

    @Test
    public void getI18nDefaultTest() {
        Locale.setDefault(Locale.ENGLISH);
        I18N i18n = I18N.getI18n(I18NTest.class);
        Assert.assertEquals("Test String", i18n.getProperty("test.string"));
    }

    @Test
    public void getI18nLocaleTest() {
        I18N i18n = I18N.getI18n(I18NTest.class, new Locale("ru", "RU"));
        Assert.assertEquals("Тестовая строка", i18n.getProperty("test.string"));
    }

    @Test
    public void getI18nCustomRootTest() {
        Locale.setDefault(Locale.ENGLISH);
        I18N i18n = I18N.getI18n(I18NTest.class, "i18ns");
        Assert.assertEquals("Test Strings", i18n.getProperty("test.string.s"));
    }

    @Test
    public void getI18nFullTest() {
        Locale.setDefault(Locale.ENGLISH);
        I18N i18n = I18N.getI18n(I18NTest.class, new Locale("ru", "RU"), "i18ns");
        Assert.assertEquals("Тестовые строки", i18n.getProperty("test.string.s"));
    }

    @Test
    public void getI18nReverseTest() {
        I18N i18nReverse;
        i18nReverse = new I18N();
        i18nReverse.put("Тестовая строка", "test.string");
        i18nReverse.put("Тестовая строка 1", "test.string.1");
        i18nReverse.put("Тестовая строка 2", "test.string.2");
        i18nReverse.put("Тестовая строка 3", "test.string.3");

        I18N i18n = I18N.getI18n(I18NTest.class, new Locale("ru", "RU"));
        Assert.assertEquals(i18nReverse, i18n.reverse());
    }
}
