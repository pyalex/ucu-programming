package edu.ucu.assigmentone.heroes;

import java.util.Random;

class ManCharacter extends Character {
    ManCharacter(int power, int hp) {
        super(power, hp);
    }

    @Override
    void kick(Character c) {
        Random r = new Random();
        c.hp -= r.nextInt(power);
    }
}
