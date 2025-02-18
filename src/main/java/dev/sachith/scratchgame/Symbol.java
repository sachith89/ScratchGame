package dev.sachith.scratchgame;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Sachith Lakmal
 * @since 0.0.1
 */
public record Symbol(
        @JsonProperty("reward_multiplier")
        double rewardMultiplier,
        String type,
        String impact,
        Integer extra
) {
}