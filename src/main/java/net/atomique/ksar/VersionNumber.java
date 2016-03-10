package net.atomique.ksar;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class VersionNumber {

    /** slf4j */
    private static final Logger LOGGER= LoggerFactory.getLogger(VersionNumber.class);

   
    public static String getVersionNumber() {
        return getVersion("net.atomique.ksar", "ksar");
    }
    
    
    /**
     * return the version of a component (use the marven archiver)
     * @param group the component group id
     * @param artifact the component artifact id
     * @return the version
     */
    public static String getVersion(String group, String artifact) {
        String version = null;

        String resource = group + "/" + artifact + "/pom.properties";

        Properties props = new Properties();
        InputStream stream = null;
        try {
            stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("META-INF/maven/" + resource);
            if(stream != null) {
                props.load(stream);
                version = props.getProperty("version");
            }
            else {
                LOGGER.warn("Version file not found for " + resource);
            }
        }
        catch (Exception e) {
            // NOOP
            LOGGER.error("Unable to get version for " + resource, e);
        }
        finally {
            if(stream != null) {
                try {stream.close();} catch (IOException e) {;/*NOOP*/}
            }
        }

        if(version != null && version.trim().isEmpty()) {
            return null;
        }

        return version;
    }
}
