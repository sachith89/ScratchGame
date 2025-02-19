package dev.sachith.scratchgame.engine;

import dev.sachith.scratchgame.BaseTest;
import dev.sachith.scratchgame.domain.vo.Matrix;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Sachith Lakmal
 * @since 0.0.1
 */
class GameEngineTest extends BaseTest {

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

}