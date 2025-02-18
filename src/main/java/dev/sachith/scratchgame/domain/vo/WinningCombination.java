package dev.sachith.scratchgame.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @author Sachith Lakmal
 * @since 0.0.1
 */
public record WinningCombination(

        @JsonProperty("reward_multiplier")
        double rewardMultiplier,
        String when,
        int count,
        String group,
        @JsonProperty("covered_areas")
        List<List<String>> coveredAreas

) {
}