package aeadvent.code2021.day21.istvan;

import java.util.List;

public class Day21 {

    private static final List<RollAndFrequency> ROLLS_AND_FREQUENCIES = List.of(
            new RollAndFrequency(3, 1),
            new RollAndFrequency(4, 3),
            new RollAndFrequency(5, 6),
            new RollAndFrequency(6, 7),
            new RollAndFrequency(7, 6),
            new RollAndFrequency(8, 3),
            new RollAndFrequency(9, 1));


    public static void main(String[] args) {
        System.out.println("Part A: " + partA(9, 6));
        System.out.println("Part B: " + partB(9, 6));

    }

    public static int partA(int player1StartPosition, int player2StartPosition) {
        DeterministicDie die = new DeterministicDie();
        DiracDice game = new DiracDice(player1StartPosition, player2StartPosition, die);
        while (!game.isFinished())
            game.nextTurn();

        return Math.min(game.getPlayerOneScore(), game.getPlayerTwoScore()) * die.getNumberOfRolls();
    }

    public static long partB(int player1StartPosition, int player2StartPosition) {
        long[] playerWins = play(new Player(player1StartPosition), new Player(player2StartPosition));
        return Math.max(playerWins[0], playerWins[1]);
    }

    private static long[] play(Player currentPlayer, Player otherPlayer) {
        if (otherPlayer.score() >= 21)
            return new long[]{0L, 1L};

        long[] result = new long[2];
        for (RollAndFrequency rollAndFrequency : ROLLS_AND_FREQUENCIES) {
            int newPos = (currentPlayer.position() - 1 + rollAndFrequency.roll()) % 10 + 1;
            Player newCurrentPlayerState = new Player(newPos, currentPlayer.score + newPos);
            var newPlayerWins = play(otherPlayer, newCurrentPlayerState);
            result[0] += newPlayerWins[1] * rollAndFrequency.frequency();
            result[1] += newPlayerWins[0] * rollAndFrequency.frequency();
        }
        return result;
    }

    record RollAndFrequency(int roll, int frequency) {
    }

    record Player(int position, int score) {
        Player(int position) {
            this(position, 0);
        }
    }
}
