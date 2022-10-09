package configuration;

import jakarta.inject.Singleton;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.util.Properties;

@Singleton
@Log4j2
@Getter
public class DataPathsXML {

    private String readersPath;

    public DataPathsXML() {
        try {
            Properties p = new Properties();
            p.loadFromXML(DataPathsXML.class.getClassLoader().getResourceAsStream("configs/paths.xml"));
            this.readersPath = p.getProperty("readersPath");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
