package dev.sachith.scratchgame.domain.vo.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.sachith.scratchgame.domain.vo.Probability;
import dev.sachith.scratchgame.domain.vo.Symbol;
import dev.sachith.scratchgame.domain.vo.WinningCombination;

import java.util.Map;

/**
 * @author Sachith Lakmal
 * @since 0.0.1
 */
public record GameConfigData(
        int columns,
        int rows,
        Map<String, Symbol> symbols,
        Probability probabilities,
        @JsonProperty("win_combinations")
        Map<String, WinningCombination> winCombinations
) {
}