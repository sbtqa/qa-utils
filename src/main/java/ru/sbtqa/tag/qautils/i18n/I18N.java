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
    private static final Map<String, I18N> bundleStorage = new HashMap<>();
    private Properties properties = new Properties();
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
    public static synchronized final I18N getI18n(Class callerClass, Locale locale, String bundlePath) {
        String className = callerClass.getSimpleName();
        String classPath = callerClass.getPackage().getName().replaceAll("\\.", File.separator);
        String s = File.separator;
        String resourceFile = bundlePath + s + classPath + s + className + s +
                locale.getLanguage() + ".properties";
        LOG.debug("Loading i18n bundle from {}", resourceFile);
        if (bundleStorage.get(resourceFile) == null) {
            I18N bundle = new I18N();
            bundle.bundleFile = resourceFile;
            try (InputStream streamFromResources = I18N.class.getClassLoader().getResourceAsStream(resourceFile)) {
                InputStreamReader isr = new InputStreamReader(streamFromResources, "UTF-8");
                bundle.properties.load(isr);
            } catch (IOException e) {
                throw new I18NRuntimeException("Failed to access bundle properties file", e);
            }
            bundleStorage.put(resourceFile, bundle);
        }
        return bundleStorage.get(resourceFile);
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
     * @return
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
     * @return
     */
    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<>();
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            map.put((String) entry.getKey(), (String) entry.getValue());
        }
        return map;
    }
}
