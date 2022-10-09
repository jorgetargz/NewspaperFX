package configuration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import jakarta.inject.Singleton;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;


@Getter
@Log4j2
@Singleton
public class Credentials {

    private String username;
    private String password;

    public Credentials() {

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();

        try {
            JsonNode node = mapper.readTree(
                    getClass().getClassLoader().getResourceAsStream("configs/credentials.yaml"));
            this.username = node.get("username").asText();
            this.password = node.get("password").asText();

        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}

