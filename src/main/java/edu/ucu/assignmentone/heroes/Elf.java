package edu.ucu.assigmentone.heroes;

class Elf extends Character {
    Elf() {
        super(10, 10);
    }

    static Elf buildRandom() {
        return new Elf();
    }

    @Override
    void kick(Character c) {
        if (c.power < power) {
            c.hp = 0;
        }else{
            c.power --;
        }
    }
}
