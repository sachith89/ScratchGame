package dev.sachith.scratchgame;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Sachith Lakmal
 * @since 1.0.0
 */
class MainTest {

    @ParameterizedTest
    @CsvSource({
            "config.json, 100",
            "config.json, 200",
            "config.json, 300"
    })
    void testGameSimulation(String configPath, int bettingAmount) {

        try (InputStream input = new FileInputStream("src/test/resources/" + configPath)) {

            ObjectMapper objectMapper = new ObjectMapper();

            GameConfig config = objectMapper.readValue(input, GameConfig.class);
            GameEngine gameEngine = new GameEngine(config, bettingAmount);
            Result result = gameEngine.play();

            assertNotNull(result, "Result should not be null");
            assertTrue(result.reward() >= 0, "Winning amount should be non-negative");

            objectMapper.writeValue(new File("output_" + configPath), result);
            System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result));

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading configuration file.");
        }
    }
}