package dev.sachith.scratchgame.domain.vo;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Sachith Lakmal
 * @since 0.0.1
 */
public class Result {

    private Matrix matrix;
    private double reward;
    private Map<String, List<String>> appliedWinningCombinations;
    private String appliedBonusSymbol;

    public Result() {
    }

    public Result(Matrix matrix, double reward, Map<String, List<String>> appliedWinningCombinations) {
        this.matrix = matrix;
        this.reward = reward;
        this.appliedWinningCombinations = appliedWinningCombinations;
    }

    public Matrix getMatrix() {
        return matrix;
    }

    public void setMatrix(Matrix matrix) {
        this.matrix = matrix;
    }

    public double getReward() {
        return reward;
    }

    public void setReward(double reward) {
        this.reward = reward;
    }

    public Map<String, List<String>> getAppliedWinningCombinations() {
        return appliedWinningCombinations;
    }

    public void setAppliedWinningCombinations(
            Map<String, List<String>> appliedWinningCombinations) {
        this.appliedWinningCombinations = appliedWinningCombinations;
    }

    public String getAppliedBonusSymbol() {
        return appliedBonusSymbol;
    }

    public void setAppliedBonusSymbol(String appliedBonusSymbol) {
        this.appliedBonusSymbol = appliedBonusSymbol;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Result result)) return false;
        return Double.compare(reward, result.reward) == 0 && Objects.equals(matrix,
                result.matrix) && Objects.equals(appliedWinningCombinations,
                result.appliedWinningCombinations) && Objects.equals(appliedBonusSymbol,
                result.appliedBonusSymbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(matrix, reward, appliedWinningCombinations, appliedBonusSymbol);
    }
}


