package dev.sachith.scratchgame;

import java.util.List;
import java.util.Map;

/**
 * @author Sachith Lakmal
 * @since 0.0.1
 */
public record Result(
        List<List<String>> matrix,
        int reward,
        Map<String, List<String>> appliedWinningCombinations,
        String appliedBonusSymbol
) {
}