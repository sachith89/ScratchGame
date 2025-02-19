package dev.sachith.scratchgame.engine;

import dev.sachith.scratchgame.domain.vo.config.GameConfigData;

import java.util.List;
import java.util.Map;

/**
 * @author Sachith Lakmal
 * @since 1.0.0
 */
public class RewardService {

    private final GameConfigData config;

    public RewardService(GameConfigData config) {
        this.config = config;
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

}
