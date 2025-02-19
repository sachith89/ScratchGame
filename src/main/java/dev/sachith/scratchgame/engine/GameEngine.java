package dev.sachith.scratchgame.engine;

import dev.sachith.scratchgame.domain.vo.Matrix;
import dev.sachith.scratchgame.domain.vo.Result;
import dev.sachith.scratchgame.domain.vo.config.GameConfigData;

import java.util.List;
import java.util.Map;

/**
 * @author Sachith Lakmal
 * @since 0.0.1
 */
public class GameEngine {

    private final MatrixBuilder matrixBuilder;
    private final CombinationChecker combinationChecker;
    private final RewardService rewardService;
    private final GameConfigData config;
    private final BonusService bonusService;


    public GameEngine(GameConfigData config,
                      MatrixBuilder matrixBuilder) {
        this.config = config;
        this.matrixBuilder = matrixBuilder;
        this.combinationChecker = new CombinationChecker(config);
        this.rewardService = new RewardService(config);
        this.bonusService = new BonusService(config);
    }

    public GameEngine(GameConfigData config,
                      MatrixBuilder matrixBuilder,
                      CombinationChecker combinationChecker,
                      RewardService rewardService,
                      BonusService bonusService) {
        this.config = config;
        this.matrixBuilder = matrixBuilder;
        this.combinationChecker = combinationChecker;
        this.rewardService = rewardService;
        this.bonusService = bonusService;
    }

    public Result play(final double betAmount) {

        // STEP 1: Generate matrix
        Matrix matrix = matrixBuilder.generateMatrix();
        // STEP 2: Check winning combinations
        Map<String, List<String>> winningCombinations = combinationChecker.checkWinningCombinations(matrix);
        // STEP 3: Calculate reward
        double reward = rewardService.calculateReward(betAmount, winningCombinations);
        // STEP 4: Apply bonus
        Result result = new Result(matrix, reward, winningCombinations);

        return bonusService.applyBonus(result);
    }


}
