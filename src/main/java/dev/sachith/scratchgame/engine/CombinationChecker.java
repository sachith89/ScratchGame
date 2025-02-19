package dev.sachith.scratchgame.engine;

import dev.sachith.scratchgame.domain.vo.Matrix;
import dev.sachith.scratchgame.domain.vo.WinningCombination;
import dev.sachith.scratchgame.domain.vo.config.GameConfigData;

import java.util.*;

/**
 * @author Sachith Lakmal
 * @since 1.0.0
 */
public class CombinationChecker {

    private final GameConfigData config;

    public CombinationChecker(GameConfigData config) {
        this.config = config;
    }

    public Map<String, List<String>> checkWinningCombinations(Matrix matrix) {

        Map<String, List<String>> winningCombinations = new HashMap<>();

        config.winCombinations().forEach((combinationName, combination) -> {
            if ("same_symbols".equals(combination.when())) {
                checkSameSymbols(matrix.value(), combination, winningCombinations, combinationName);
            } else if ("linear_symbols".equals(combination.when())) {
                checkLinearSymbols(matrix.value(), combination, winningCombinations, combinationName);
            }
        });

        return winningCombinations;
    }

    private void checkSameSymbols(List<List<String>> matrix, WinningCombination combination,
                                  Map<String, List<String>> winningCombinations, String combinationName) {

        Map<String, Integer> symbolCount = new HashMap<>();

        for (List<String> row : matrix) {
            for (String symbol : row) {
                symbolCount.put(symbol, symbolCount.getOrDefault(symbol, 0) + 1);
            }
        }

        for (Map.Entry<String, Integer> entry : symbolCount.entrySet()) {
            if (entry.getValue() == combination.count()) {
                winningCombinations.computeIfAbsent(entry.getKey(), k -> new ArrayList<>()).add(combinationName);
            }
        }
    }

    private void checkLinearSymbols(List<List<String>> matrix, WinningCombination combination,
                                    Map<String, List<String>> winningCombinations, String combinationName) {

        for (List<String> area : combination.coveredAreas()) {
            Set<String> symbols = new HashSet<>();

            for (String pos : area) {
                String[] indices = pos.split(":");
                int row = Integer.parseInt(indices[0]);
                int col = Integer.parseInt(indices[1]);
                symbols.add(matrix.get(row).get(col));
            }

            if (symbols.size() == 1) {
                String symbol = symbols.iterator().next();
                winningCombinations.computeIfAbsent(symbol, k -> new ArrayList<>()).add(combinationName);
            }
        }
    }

}
