package dev.sachith.scratchgame.engine;

import dev.sachith.scratchgame.domain.vo.Result;
import dev.sachith.scratchgame.domain.vo.config.GameConfigData;

import java.util.List;

/**
 * @author Sachith Lakmal
 * @since 1.0.0
 */
public class BonusService {

    private final GameConfigData config;

    public BonusService(GameConfigData config) {
        this.config = config;
    }

    Result applyBonus(Result result) {

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
