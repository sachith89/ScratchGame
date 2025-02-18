package dev.sachith.scratchgame;

import java.util.Map;

/**
 * @author Sachith Lakmal
 * @since 0.0.1
 */
public record StandardSymbolProbability(
        int column,
        int row,
        Map<String, Integer> symbols
) {
}