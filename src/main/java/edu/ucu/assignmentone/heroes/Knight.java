package edu.ucu.assignmentone.heroes;


import java.util.Random;

class Knight extends ManCharacter {
    Knight(int power, int hp) {
        super(power, hp);
    }

    static Knight buildRandom() {
        Random d = new Random();
        return new Knight(d.nextInt(13) + 2, d.nextInt(13) + 2);
    }
}
