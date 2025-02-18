# Scratch Game

[![Java CI with Maven](https://github.com/sachith89/ScratchGame/actions/workflows/maven.yml/badge.svg)](https://github.com/sachith89/ScratchGame/actions/workflows/maven.yml)

## Overview

Scratch Game is a Java-based game engine that simulates a scratch card game. The game generates a matrix of symbols and
checks for winning combinations based on predefined rules.

## Features

- Generate random matrices of symbols
- Check for winning combinations
- Calculate rewards based on winning combinations
- Apply bonus symbols to modify rewards

## Requirements

- Java 11 or higher
- Maven 3.6.0 or higher

## Installation

1. Clone the repository:
    ```sh
    git clone https://github.com/sachith89/ScratchGame.git
    cd ScratchGame
    ```

2. Build the project using Maven:
    ```sh
    mvn clean install
    ```

## Usage

To run the game engine, you can execute the below command in the terminal:

```sh
  java -jar <your-jar-file> --config config.json --betting-amount 100
```

### Running Tests

To run the tests, use the following Maven command:

```sh
    mvn test
```