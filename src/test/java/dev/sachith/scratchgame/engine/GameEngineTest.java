package dev.sachith.scratchgame.engine;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.sachith.scratchgame.domain.vo.Result;
import dev.sachith.scratchgame.domain.vo.config.GameConfigData;
import dev.sachith.scratchgame.domain.vo.Matrix;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

/**
 * @author Sachith Lakmal
 * @since 0.0.1
 */
@ExtendWith(MockitoExtension.class)
class GameEngineTest {

    static GameConfigData config;

    @Mock
    MatrixBuilder matrixBuilder;

    @BeforeAll
    static void setUp() {
        try (InputStream input = new FileInputStream("src/test/resources/config.json")) {
            ObjectMapper objectMapper = new ObjectMapper();
            config = objectMapper.readValue(input, GameConfigData.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testCheckWinningCombinations() {

        GameEngine engine = new GameEngine(config, matrixBuilder);

        Matrix matrix = new Matrix
                (List.of(
                        List.of("A", "A", "A"),
                        List.of("B", "B", "C"),
                        List.of("D", "E", "F"),
                        List.of("D", "E", "F")
                )
                );

        lenient().when(matrixBuilder.generateMatrix()).thenReturn(matrix);

        Map<String, List<String>> winningCombinations = engine.checkWinningCombinations(matrix);

        assertNotNull(winningCombinations);
        assertTrue(winningCombinations.containsKey("A"));
        assertEquals(List.of("same_symbol_3_times", "same_symbols_horizontally"), winningCombinations.get("A"));
    }

    @Test
    void testBonusSymbolGeneration() {

        Matrix matrix = new MatrixBuilder(config).generateMatrix();

        boolean bonusSymbolFound = false;
        for (List<String> row : matrix.value()) {
            for (String symbol : row) {
                if (config.symbols().containsKey(symbol) && "bonus".equals(config.symbols().get(symbol).type())) {
                    bonusSymbolFound = true;
                    break;
                }
            }
        }

        assertTrue(bonusSymbolFound, "Bonus symbol should be generated in the matrix");
    }

    @Test
    void testMultipleWinningCombinationsForSingleSymbol() {
        GameEngine engine = new GameEngine(config, matrixBuilder);

        Matrix matrix = new Matrix(List.of(
                List.of("A", "A", "A"),
                List.of("B", "B", "C"),
                List.of("D", "E", "+1000"),
                List.of("D", "E", "F")
        ));

        when(matrixBuilder.generateMatrix()).thenReturn(matrix);

        Result result = engine.play(100);

        assertEquals((100 * 5 * 1 * 2) + 1000, result.getReward(),
                "Reward should be multiplied for multiple winning combinations of the same symbol");
    }
}