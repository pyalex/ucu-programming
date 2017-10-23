package edu.ucu.assigmentone.heroes;

import java.util.Random;

class King extends ManCharacter{
    King(int power, int hp) {
        super(power, hp);
    }

    static King buildRandom() {
        Random d = new Random();
        return new King(d.nextInt(10) + 5, d.nextInt(10) + 5);
    }
}
