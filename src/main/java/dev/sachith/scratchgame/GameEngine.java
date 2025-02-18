package dev.sachith.scratchgame;

import java.util.*;

/**
 * @author Sachith Lakmal
 * @since 0.0.1
 */
public class GameEngine {

    private final GameConfig config;
    private final int betAmount;
    private final Random random = new Random();

    public GameEngine(GameConfig config, int betAmount) {
        this.config = config;
        this.betAmount = betAmount;
    }

    public Result play() {

        List<List<String>> matrix = generateMatrix();
        Map<String, List<String>> winningCombinations = checkWinningCombinations(matrix);
        int reward = calculateReward(winningCombinations);
        String bonusSymbol = applyBonus(reward);

        return new Result(matrix, reward, winningCombinations, bonusSymbol);
    }

    private List<List<String>> generateMatrix() {

        List<List<String>> matrix = new ArrayList<>();

        for (int row = 0; row < config.rows(); row++) {

            List<String> rowList = new ArrayList<>();

            for (int col = 0; col < config.columns(); col++) {
                rowList.add(getRandomSymbol(col, row));
            }

            matrix.add(rowList);
        }

        return matrix;
    }

    private String getRandomSymbol(int col, int row) {

        Map<String, Integer> probabilities = config.probabilities().standardSymbols().get(0).symbols();
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

    private Map<String, List<String>> checkWinningCombinations(List<List<String>> matrix) {
        return new HashMap<>();
    }

    private int calculateReward(Map<String, List<String>> winningCombinations) {

        int reward = 0;

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

    private String applyBonus(int reward) {
        return "MISS";
    }

}
