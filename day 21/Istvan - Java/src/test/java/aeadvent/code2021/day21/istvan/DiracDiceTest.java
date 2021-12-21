package aeadvent.code2021.day21.istvan;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DiracDiceTest {

    @Test
    void newGame() {
        DeterministicDie die = new DeterministicDie();
        DiracDice game = new DiracDice(4,8, die);
        assertThat(game.getPlayerOnePosition()).isEqualTo(4);
        assertThat(game.getPlayerTwoPosition()).isEqualTo(8);
        assertThat(die.getNumberOfRolls()).isEqualTo(0);
    }

    @Test
    void playTurn() {
        DeterministicDie die = new DeterministicDie();
        DiracDice game = new DiracDice(4,8, die);
        boolean isFinished = game.nextTurn();
        assertThat(isFinished).isFalse();
        assertThat(die.getNumberOfRolls()).isEqualTo(3);
        assertThat(game.getPlayerOnePosition()).isEqualTo(10);
        assertThat(game.getPlayerOneScore()).isEqualTo(10);
        assertThat(game.getPlayerTwoPosition()).isEqualTo(8);
        assertThat(game.getPlayerTwoScore()).isEqualTo(0);
    }

    @Test
    void playTwoTurns() {
        DeterministicDie die = new DeterministicDie();
        DiracDice game = new DiracDice(4,8, die);
        boolean isFinishedAfterFirstTurn = game.nextTurn();
        boolean isFinishedAfterSecondTurn = game.nextTurn();
        assertThat(isFinishedAfterFirstTurn).isFalse();
        assertThat(isFinishedAfterSecondTurn).isFalse();
        assertThat(die.getNumberOfRolls()).isEqualTo(6);
        assertThat(game.getPlayerOnePosition()).isEqualTo(10);
        assertThat(game.getPlayerOneScore()).isEqualTo(10);
        assertThat(game.getPlayerTwoPosition()).isEqualTo(3);
        assertThat(game.getPlayerTwoScore()).isEqualTo(3);
    }

    @Test
    void playEightTurns() {
        DeterministicDie die = new DeterministicDie();
        DiracDice game = new DiracDice(4,8, die);
        for (int i = 0; i < 7; i++) {
            game.nextTurn();
        }
        assertThat(game.nextTurn()).isFalse();

        assertThat(die.getNumberOfRolls()).isEqualTo(24);
        assertThat(game.getPlayerOnePosition()).isEqualTo(6);
        assertThat(game.getPlayerOneScore()).isEqualTo(26);
        assertThat(game.getPlayerTwoPosition()).isEqualTo(6);
        assertThat(game.getPlayerTwoScore()).isEqualTo(22);
    }

}

