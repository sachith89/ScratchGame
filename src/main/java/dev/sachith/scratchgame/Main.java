package dev.sachith.scratchgame;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.sachith.scratchgame.domain.vo.Result;
import dev.sachith.scratchgame.domain.vo.config.GameConfigData;
import dev.sachith.scratchgame.engine.GameEngine;
import dev.sachith.scratchgame.engine.MatrixBuilder;

import java.io.File;
import java.io.IOException;

/**
 * @author Sachith Lakmal
 * @since 0.0.1
 */
public class Main {

    public static void main(String[] args) {

        if (args.length < 2) {
            System.out.println("Usage: java -jar <your-jar-file> --config config.json --betting-amount 100");
            return;
        }

        String configPath = null;
        int bettingAmount = 0;

        for (int i = 0; i < args.length; i++) {

            if (args[i].equals("--config")) {

                configPath = args[i + 1];

            } else if (args[i].equals("--betting-amount")) {
                bettingAmount = Integer.parseInt(args[i + 1]);
            }
        }

        if (configPath == null || bettingAmount <= 0) {
            System.out.println("Invalid parameters.");
            return;
        }

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            GameConfigData config = objectMapper.readValue(new File(configPath), GameConfigData.class);
            GameEngine gameEngine = new GameEngine(config, new MatrixBuilder(config));
            Result result = gameEngine.play(bettingAmount);

            objectMapper.writeValue(new File("output.json"), result);
            System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading configuration file.");
        }
    }
}

