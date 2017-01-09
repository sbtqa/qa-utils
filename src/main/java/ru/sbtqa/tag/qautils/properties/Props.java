package ru.sbtqa.tag.qautils.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Props {

    private static final Logger LOG = LoggerFactory.getLogger(Props.class);

    private static Props instance;
    private static Properties properties;

    /**
     * Constructs Props object. It loads properties from filesystem path set in
     * the <b>BDDConfigFile</b> system properties or from classpath
     * config/application.properties by default.
     *
     * @throws java.io.IOException if there are an error to read properties file
     */
    public Props() throws IOException {
        String sConfigFile = System.getProperty("BDDConfigFile", "config/application.properties");
        properties = new Properties();
        LOG.info("Loading properties from {}", sConfigFile);
        try (InputStream streamFromResources = Props.class.getClassLoader().getResourceAsStream(sConfigFile);) {
            properties.load(streamFromResources);
        }
    }

    /**
     * Creates single instance of Props class or returns already created
     *
     * @return instance of Props
     */
    public static synchronized Props getInstance() {
        if (instance == null) {
            try {
                instance = new Props();
            } catch (IOException e) {
                throw new PropsRuntimeException("Failed to open or close properties file", e);
            }
        }
        return instance;
    }

    /**
     * Returns value of the property 'name' of empty string if the property is
     * not found
     *
     * @param name Name of the property to get value of
     * @return value of the property of empty string
     */
    private String getProp(String name) {
        String val = getProps().getProperty(name, "");
        if (val.isEmpty()) {
            LOG.warn("Property {} was not found in properties file", name);
        }
        return val.trim();
    }

    /**
     * Get property from file
     *
     * @param prop property name.
     * @return property value.
     */
    public static String get(String prop) {
        return getInstance().getProp(prop);
    }

    /**
     * Get property from file
     *
     * @param prop property name.
     * @param defaultValue default value if not set
     * @return property value.
     */
    public static String get(String prop, String defaultValue) {
        String value = getInstance().getProp(prop);

        if (value.isEmpty()) {
            return defaultValue;
        }

        return value;
    }

    /**
     * @return the props
     */
    public static Properties getProps() {
        return properties;
    }
}
