package configuration;

import jakarta.inject.Singleton;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.util.Properties;

@Singleton
@Log4j2
public class DataPaths {

    private Properties p;

    public DataPaths() {
        try {
            p = new Properties();
            p.load(DataPaths.class.getClassLoader().getResourceAsStream("configs/paths.properties"));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    public String getProperty(String key) {
        return p.getProperty(key);
    }
}
