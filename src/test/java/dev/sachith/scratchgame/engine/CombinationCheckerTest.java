package dev.sachith.scratchgame.engine;

import dev.sachith.scratchgame.BaseTest;
import dev.sachith.scratchgame.domain.vo.Matrix;
import dev.sachith.scratchgame.domain.vo.Result;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

/**
 * @author Sachith Lakmal
 * @since 1.0.0
 */
@ExtendWith(MockitoExtension.class)
class CombinationCheckerTest extends BaseTest {

    @Mock
    MatrixBuilder matrixBuilder;

    @Test
    void testCheckWinningCombinations() {

        CombinationChecker combinationChecker = new CombinationChecker(config);

        Matrix matrix = new Matrix
                (List.of(
                        List.of("A", "A", "A"),
                        List.of("B", "B", "C"),
                        List.of("D", "E", "F"),
                        List.of("D", "E", "F")
                )
                );

        lenient().when(matrixBuilder.generateMatrix()).thenReturn(matrix);

        Map<String, List<String>> winningCombinations = combinationChecker.checkWinningCombinations(matrix);

        assertNotNull(winningCombinations);
        assertTrue(winningCombinations.containsKey("A"));
        assertEquals(List.of("same_symbol_3_times", "same_symbols_horizontally"), winningCombinations.get("A"));
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