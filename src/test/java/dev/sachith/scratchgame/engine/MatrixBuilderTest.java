package dev.sachith.scratchgame.engine;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.sachith.scratchgame.domain.vo.Matrix;
import dev.sachith.scratchgame.domain.vo.config.GameConfigData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.provider.Arguments;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Sachith Lakmal
 * @since 1.0.0
 */
class MatrixBuilderTest {

    static GameConfigData config;

    @BeforeAll
    static void setUp() {
        try (InputStream input = new FileInputStream("src/test/resources/config.json")) {
            ObjectMapper objectMapper = new ObjectMapper();
            config = objectMapper.readValue(input, GameConfigData.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static Stream<Arguments> testArguments() {
        return Stream.of();
    }

    @RepeatedTest(5)
    void testGenerateMatrix() {
        Matrix matrix = new MatrixBuilder(config).generateMatrix();

        assertNotNull(matrix);
        assertEquals(3, matrix.value().size());

        for (List<String> row : matrix.value()) {
            assertEquals(3, row.size());
        }
    }
}
