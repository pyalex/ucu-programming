package edu.ucu.assignmentone.guessgame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import lombok.Value;

import javax.swing.*;

@Value
class GameScore implements Comparable<GameScore> {
    String name;
    double score;

    public int compareTo(GameScore o) {
        return (int) (o.score - score);
    }
}

public class GuessGame {
    private ArrayList<GameScore> scores = new ArrayList<GameScore>();

    private int getRandomInRange(int min, int max) {
        return new Random().nextInt(max - min + 1) + min;
    }

    public void play(int max) {
        int tries = 0;
        int guess = -1;
        int number = getRandomInRange(1, max);

        System.out.println(number);

        while (guess != number) {
            guess = Integer.parseInt(JOptionPane.showInputDialog("Guess?"));
            if (guess < number) {
                System.out.println("Try Bigger");
            }else if(guess > number){
                System.out.println("Try Less");
            }
            tries ++;
        }

        String name = JOptionPane.showInputDialog("You win. Enter your name");
        scores.add(new GameScore(name, (double) max / tries));

    }

    public void printBestScores() {
        Collections.sort(scores);

        for (int pos = 0; pos < scores.size(); pos++) {
            GameScore curr = scores.get(pos);
            System.out.printf("%s place: %s with score %.2f\n", pos + 1, curr.getName(), curr.getScore());
        }
    }
}
