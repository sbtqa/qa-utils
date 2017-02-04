package ru.sbtqa.tag.qautils.i18n;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * i18n resource bundle from utf-8 properties
 */
public class I18N extends Properties {

    private static final Logger LOG = LoggerFactory.getLogger(I18N.class);


    /**
     * Gets bundle with default system locale and i18n as parent path
     *
     * @param callerClass Class resource for
     * @return Resources for given class
     */
    public static final I18N getI18n(Class callerClass) {
        return getI18n(callerClass, Locale.getDefault(), "i18n");
    }

    /**
     * Gets bundle with given locale and i18n as parent path
     *
     * @param callerClass Class resource for
     * @param locale Resources locale
     * @return Resources for given class
     */
    public static final I18N getI18n(Class callerClass, Locale locale) {
        return getI18n(callerClass, locale, "i18n");
    }

    /**
     * Gets bundle with default system locale and given parent path
     *
     * @param callerClass Class resource for
     * @param bundlePath Resources parent path
     * @return Resources for given class
     */
    public static final I18N getI18n(Class callerClass, String bundlePath) {
        return getI18n(callerClass, Locale.getDefault(), "i18n");
    }

    /**
     * Gets bundle with given locale and parent path
     *
     * @param callerClass Class resource for
     * @param locale Resources locale
     * @param bundlePath Resources parent path
     * @return Resources for given class
     */
    public static final I18N getI18n(Class callerClass, Locale locale, String bundlePath) {
        String className = callerClass.getSimpleName();
        String classPath = callerClass.getPackage().getName().replaceAll("\\.", File.separator);
        String s = File.separator;
        String resourceFile = bundlePath + s + classPath + s + className + s +
                locale.getCountry() + ".properties";
        LOG.info("Loading i18n bundle from {}", resourceFile);
        I18N properties = new I18N();
        try (InputStream streamFromResources = I18N.class.getClassLoader().getResourceAsStream(resourceFile)) {
            InputStreamReader isr = new InputStreamReader(streamFromResources, "UTF-8");
            properties.load(isr);
        } catch (IOException e) {
            throw new I18NRuntimeException("Failed to access bundle properties file", e);
        }
        return properties;
    }

    /**
     * Swap keys and values in bundle
     *
     * @return
     */
    public I18N reverse() {
        Set<Map.Entry<Object, Object>> entries = this.entrySet();
        I18N reversed = new I18N();
        for (Map.Entry<Object, Object> entry : entries) {
            reversed.put(entry.getValue(), entry.getKey());
        }
        return reversed;
    }
}
