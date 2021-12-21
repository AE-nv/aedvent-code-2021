package aeadvent.code2021.day21.istvan;

public class DiracDice {

    private Player player1;
    private Player player2;

    private Player nextPlayer;

    private DeterministicDie die;

    public DiracDice(int player1Position, int player2Position, DeterministicDie die) {
        this.player1 = new Player(player1Position-1);
        this.player2 = new Player(player2Position-1);
        this.die = die;
        this.nextPlayer = this.player1;
    }

    public int getPlayerOnePosition() {
        return player1.getPosition()+1;
    }

    public int getPlayerTwoPosition() {
        return player2.getPosition()+1;
    }

    public boolean nextTurn() {
        int steps = die.roll()+die.roll()+die.roll();

        int newPosition = nextPlayer.getPosition() + steps;
        newPosition %= 10;

        nextPlayer.changePositionTo(newPosition);

        if (nextPlayer == player1)
            nextPlayer = player2;
        else
            nextPlayer = player1;

        return isFinished();
    }

    public boolean isFinished() {
        return player1.getScore() >= 1000 || player2.getScore() >= 1000;
    }


    public int getPlayerOneScore() {
        return player1.getScore();
    }

    public int getPlayerTwoScore() {
        return player2.getScore();
    }


    private class Player {
        int position;
        int score=0;

        public Player(int position) {
            this.position = position;
        }

       void changePositionTo(int newPosition) {
            score += (newPosition+1);
            position = newPosition;
       }

        public int getPosition() {
            return position;
        }

        public int getScore() {
            return score;
        }
    }
}
