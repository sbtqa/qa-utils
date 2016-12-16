package ru.sbtqa.tag.qautils.properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Props {

    private static final Logger log = LoggerFactory.getLogger(Props.class);

    private static Props instance;
    private static Properties props;

    /**
     * Creates single instance of Props class or returns already created
     *
     * @return instance of Props
     */
    public synchronized static Props getInstance() {
        if (instance == null) {
            instance = new Props();
        }
        return instance;
    }

    /**
     * Constructs Props object. It loads properties from filesystem path set in
     * the <b>BDDConfigFile</b> system properties or from classpath
     * config/application.properties by default.
     */
    public Props() {
        try {
            System.setProperty("logback.configurationFile", "config/logback.xml");
            String sConfigFile = System.getProperty("BDDConfigFile", "config/application.properties");
            log.info("Loading properties from: " + sConfigFile);
            //first try to load properties from resources
            InputStream in = Props.class.getClassLoader().getResourceAsStream(sConfigFile);
            //if failed try to load from filesystem
            if (in == null) {
                in = new FileInputStream(sConfigFile);
            }
            props = new Properties();
            props.load(in);
            in.close();
        } catch (IOException e) {
            log.error("Failed to initialize props: " + e);
        }
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
            log.debug("Property {} was not found in Props", name);
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
        return Props.getInstance().getProp(prop);
    }

    /**
     * Get property from file
     *
     * @param prop property name.
     * @param defaultValue default value if not set
     * @return property value.
     */
    public static String get(String prop, String defaultValue) {
        String val = Props.getInstance().getProp(prop);
        if (val.isEmpty()) {
            return defaultValue;
        }
        return val;
    }

    /**
     * @return the props
     */
    public static Properties getProps() {
        return props;
    }
}
