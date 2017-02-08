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
import java.util.HashMap;

/**
 * i18n resource bundle from utf-8 properties
 */
public class I18N {

    private static final Logger LOG = LoggerFactory.getLogger(I18N.class);
    private static final Map<String, I18N> BUNDLE_STORAGE = new HashMap<>();
    private static final String BUNDLE_ENCODING = "UTF-8";
    private final Properties properties = new Properties();
    private String bundleFile;


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
        return getI18n(callerClass, Locale.getDefault(), bundlePath);
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
                locale.getLanguage() + ".properties";
        LOG.debug("Loading i18n bundle from {}", resourceFile);
        if (BUNDLE_STORAGE.get(resourceFile) == null) {
            I18N bundle = new I18N();
            bundle.bundleFile = resourceFile;
            try (InputStream streamFromResources = I18N.class.getClassLoader().getResourceAsStream(resourceFile)) {
                InputStreamReader isr = new InputStreamReader(streamFromResources, BUNDLE_ENCODING);
                bundle.properties.load(isr);
            } catch (IOException e) {
                throw new I18NRuntimeException("Failed to access bundle properties file", e);
            }
            synchronized (BUNDLE_STORAGE) {
                BUNDLE_STORAGE.put(resourceFile, bundle);
            }
        }
        return BUNDLE_STORAGE.get(resourceFile);
    }

    /**
     * Get translation by key
     *
     * @param key translation key
     * @return Translation or key if no translation found
     */
    public String get(String key) {
        String translation = properties.getProperty(key);
        if (translation == null) {
            LOG.warn("There is no \"{}\" key in \"{}\" bundle. Failing back to {}", key, this.bundleFile, key);
            translation = key;
        }
        return translation;
    }

    /**
     * Swap keys and values in bundle
     *
     * @return Reversed bundle
     */
    public I18N reverse() {
        Set<Map.Entry<Object, Object>> entries = properties.entrySet();
        I18N reversed = new I18N();
        for (Map.Entry<Object, Object> entry : entries) {
            reversed.properties.put(entry.getValue(), entry.getKey());
        }
        return reversed;
    }

    /**
     * Represent bundle as map
     *
     * @return Bundle as map
     */
    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<>();
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            map.put((String) entry.getKey(), (String) entry.getValue());
        }
        return map;
    }
}
