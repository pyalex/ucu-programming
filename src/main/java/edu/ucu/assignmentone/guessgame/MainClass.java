package edu.ucu.assigmentone.guessgame;

import edu.ucu.assigmentone.guessgame.GuessGame;

public class MainClass {
    public static void main(String... args ) {
        GuessGame g = new GuessGame();
        g.play(10);
        g.printBestScores();

        g.play(10);
        g.printBestScores();
    }
}
