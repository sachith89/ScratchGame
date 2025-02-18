package dev.sachith.scratchgame.engine;

import dev.sachith.scratchgame.domain.vo.Matrix;
import dev.sachith.scratchgame.domain.vo.Result;
import dev.sachith.scratchgame.domain.vo.WinningCombination;
import dev.sachith.scratchgame.domain.vo.config.GameConfigData;

import java.util.*;

/**
 * @author Sachith Lakmal
 * @since 0.0.1
 */
public class GameEngine {

    private final MatrixBuilder matrixBuilder;
    private final GameConfigData config;

    public GameEngine(GameConfigData config, MatrixBuilder matrixBuilder) {
        this.config = config;
        this.matrixBuilder = matrixBuilder;
    }

    public Result play(final double betAmount) {

        Matrix matrix = matrixBuilder.generateMatrix();

        Map<String, List<String>> winningCombinations = checkWinningCombinations(matrix);
        double reward = calculateReward(betAmount, winningCombinations);
        Result result = new Result(matrix, reward, winningCombinations);

        return applyBonus(result);
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

    public double calculateReward(double betAmount, Map<String, List<String>> winningCombinations) {

        double reward = 0;

        for (Map.Entry<String, List<String>> entry : winningCombinations.entrySet()) {

            String symbol = entry.getKey();
            double symbolReward = betAmount * config.symbols().get(symbol).rewardMultiplier();

            for (String winCondition : entry.getValue()) {
                symbolReward *= config.winCombinations().get(winCondition).rewardMultiplier();
            }

            reward += symbolReward;
        }

        return reward;
    }

    private Result applyBonus(Result result) {

        String appliedBonusSymbol = "MISS";
        double rewardWithBonus = result.getReward();

        for (List<String> row : result.getMatrix().value()) {
            for (String symbol : row) {

                if (config.symbols().containsKey(symbol) && "bonus".equals(config.symbols().get(symbol).type())) {

                    appliedBonusSymbol = switch (config.symbols().get(symbol).impact()) {
                        case "multiply_reward" -> {
                            rewardWithBonus *= (int) config.symbols().get(symbol).rewardMultiplier();
                            yield symbol;
                        }
                        case "extra_bonus" -> {
                            rewardWithBonus += config.symbols().get(symbol).extra();
                            yield symbol;
                        }
                        case "miss" -> {
                            rewardWithBonus = 0;
                            yield "MISS";
                        }
                        default -> appliedBonusSymbol;
                    };
                }
            }
        }
        result.setReward(rewardWithBonus);
        result.setAppliedBonusSymbol(appliedBonusSymbol);

        return result;
    }

}
