package dev.sachith.scratchgame.engine;

import dev.sachith.scratchgame.domain.vo.Matrix;
import dev.sachith.scratchgame.domain.vo.config.GameConfigData;
import dev.sachith.scratchgame.domain.vo.probability.StandardSymbolProbability;

import java.util.*;

/**
 * @author Sachith Lakmal
 * @since 1.0.0
 */
public class MatrixBuilder {

    private final Random random = new Random();
    private final GameConfigData config;

    public MatrixBuilder(GameConfigData config) {
        this.config = config;
    }

    public Matrix generateMatrix() {

        List<List<String>> matrix = new ArrayList<>();

        for (int row = 0; row < config.rows(); row++) {

            List<String> rowList = new ArrayList<>();

            for (int col = 0; col < config.columns(); col++) {
                rowList.add(getRandomSymbol(col, row, config));
            }

            matrix.add(rowList);
        }

        return new Matrix(matrix);
    }

    protected String getRandomSymbol(int col, int row, GameConfigData config) {
        Map<String, Integer> probabilities = new HashMap<>();

        // Add standard symbol probabilities
        for (StandardSymbolProbability standardSymbol : config.probabilities().standardSymbols()) {
            if (standardSymbol.column() == col && standardSymbol.row() == row) {
                probabilities.putAll(standardSymbol.symbols());
            }
        }

        // Add bonus symbol probabilities
        probabilities.putAll(config.probabilities().bonusSymbols().symbols());

        int total = probabilities.values().stream().mapToInt(Integer::intValue).sum();
        int rand = random.nextInt(total);
        int cumulative = 0;

        for (Map.Entry<String, Integer> entry : probabilities.entrySet()) {
            cumulative += entry.getValue();
            if (rand < cumulative) {
                return entry.getKey();
            }
        }

        return "MISS";
    }
}
