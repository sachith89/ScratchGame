package dev.sachith.scratchgame.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.sachith.scratchgame.domain.vo.probability.BonusSymbolProbability;
import dev.sachith.scratchgame.domain.vo.probability.StandardSymbolProbability;

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
