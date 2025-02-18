package dev.sachith.scratchgame;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * @author Sachith Lakmal
 * @since 0.0.1
 */
public record GameConfig(
        int columns,
        int rows,
        Map<String, Symbol> symbols,
        Probability probabilities,
        @JsonProperty("win_combinations")
        Map<String, WinningCombination> winCombinations
) {
}