package ru.sbtqa.tag.qautils.properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class Props {

    private static final Logger LOG = LoggerFactory.getLogger(Props.class);

    private static Props instance;
    private static Properties properties;

    private Props() {
        initProperties();
    }

    private static void initProperties() {
        String sConfigFile = "application.properties";
        String sConfigFileFolder = System.getProperty("TagConfigFile", "config");
        properties = new Properties();
        if (LOG.isDebugEnabled()) {
            LOG.debug("Loading properties from {}/{}", sConfigFileFolder, sConfigFile);
        }
        InputStream streamFromResources = Props.class.getClassLoader().getResourceAsStream(sConfigFileFolder + "/" + sConfigFile);
        if (streamFromResources == null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Loading properties from {}", sConfigFile);
            }
            streamFromResources = Props.class.getClassLoader().getResourceAsStream(sConfigFile);
        }
        if (streamFromResources == null) {
            throw new PropsRuntimeException("File with properties not found");
        }
        try {
            InputStreamReader isr = new InputStreamReader(streamFromResources, StandardCharsets.UTF_8);
            properties.load(isr);
        } catch (IOException e) {
            throw new PropsRuntimeException("Failed to access properties file", e);
        } finally {
            try {
                streamFromResources.close();
            } catch (IOException e) {
                throw new PropsRuntimeException("Failed to close properties file", e);
            }
        }
    }

    /**
     * Creates single instance of Props class or returns already created It
     * loads properties from filesystem path set in the <b>TagConfigFile</b>
     * system properties or from classpath config/application.properties by
     * default.
     *
     * @return instance of Props
     */
    public static synchronized Props getInstance() {
        if (instance == null) {
            instance = new Props();
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
            LOG.debug("Property {} was not found in properties file", name);
        }
        return val.trim();
    }

    /**
     * Get properties as a Properties object

     * @return the Properties object
     */
    public static Properties getProps() {
        initProperties();
        return properties;
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
}
