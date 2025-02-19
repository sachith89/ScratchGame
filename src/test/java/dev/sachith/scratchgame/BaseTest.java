package dev.sachith.scratchgame;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.sachith.scratchgame.domain.vo.config.GameConfigData;
import org.junit.jupiter.api.BeforeAll;

import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @author Sachith Lakmal
 * @since 1.0.0
 */
public class BaseTest {

    protected static GameConfigData config;

    @BeforeAll
    static void setUp() {
        try (InputStream input = new FileInputStream("src/test/resources/config.json")) {
            ObjectMapper objectMapper = new ObjectMapper();
            config = objectMapper.readValue(input, GameConfigData.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
