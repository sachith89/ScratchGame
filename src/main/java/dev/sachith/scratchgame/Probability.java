package dev.sachith.scratchgame;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @author Sachith Lakmal
 * @since 0.0.1
 */
public record Probability(
        @JsonProperty("standard_symbols")
        List<StandardSymbolProbability> standardSymbols,
        @JsonProperty("bonus_symbols")
        BonusSymbolProbability bonusSymbols
) {
}
