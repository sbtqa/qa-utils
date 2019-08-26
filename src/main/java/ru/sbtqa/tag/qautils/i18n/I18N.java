package ru.sbtqa.tag.qautils.i18n;

import io.cucumber.core.api.Scenario;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.sbtqa.tag.qautils.cucumber.CucumberUtils;

/**
 * i18n resource bundle from UTF-8 properties
 */
public class I18N {

    private static final Logger LOG = LoggerFactory.getLogger(I18N.class);

    private static final Map<String, I18N> BUNDLE_STORAGE = new HashMap<>();
    private static final String BUNDLE_ENCODING = "UTF-8";
    public static final String DEFAULT_BUNDLE_PATH = "i18n";
    public static final String SECRET_DELIMITER = "°\u0000\u0000\u0000 ";
    private static final String DELIMITER = "/";
    private final Properties properties = new Properties();
    private String bundleFile;

    /**
     * Gets bundle with default system locale and i18n as parent path
     *
     * @param callerClass Class resource for
     * @return Resources for given class
     */
    public static final I18N getI18n(Class callerClass) {
        return getI18n(callerClass, Locale.getDefault(), DEFAULT_BUNDLE_PATH);
    }

    /**
     * Gets bundle with given locale and i18n as parent path
     *
     * @param callerClass Class resource for
     * @param locale Resources locale
     * @return Resources for given class
     */
    public static final I18N getI18n(Class callerClass, Locale locale) {
        return getI18n(callerClass, locale, DEFAULT_BUNDLE_PATH);
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
     * Gets bundle with default bundlePath and given executable scenario
     *
     * @param callerClass Class resource for
     * @param scenario Current executable scenario
     * @return Resources for given class
     */
    public static final I18N getI18n(Class callerClass, Scenario scenario) {
        Locale locale = CucumberUtils.getLocale(scenario);
        return I18N.getI18n(callerClass, locale);
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
        String className = callerClass.getSimpleName().toLowerCase();
        String resourceFile = bundlePath + DELIMITER + className + DELIMITER + locale.getLanguage() + ".properties";
        LOG.debug("Loading i18n bundle from {}", resourceFile);
        if (BUNDLE_STORAGE.get(resourceFile) == null) {
            I18N bundle = new I18N();
            bundle.bundleFile = resourceFile;
            try (InputStream streamFromResources = I18N.class.getClassLoader().getResourceAsStream(resourceFile)) {
                InputStreamReader isr = new InputStreamReader(streamFromResources, BUNDLE_ENCODING);
                bundle.properties.load(isr);
            } catch (IOException | NullPointerException e) {
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
            LOG.debug("There is no \"{}\" key in \"{}\" bundle. Failing back to {}", key, this.bundleFile, key);
            translation = key;
        }
        return translation;
    }

    /**
     * Get key by translation
     *
     * @param value translation value
     * @return translation
     */
    public String getKey(String value) {
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            if (value.equals(entry.getValue())) {
                return entry.getKey().toString();
            }
        }
        throw new I18NRuntimeException("None of the keys belongs to the value \"" + value + "\" in \"" + this.bundleFile + "\" bundle");
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
