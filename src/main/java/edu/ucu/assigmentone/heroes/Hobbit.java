package edu.ucu.assigmentone.heroes;

class Hobbit extends Character {
    Hobbit() {
        super(0, 3);
    }
    static Hobbit buildRandom() {
        return new Hobbit();
    }

    @Override
    void kick(Character c) {
        toCry();
    }

    private void toCry() {
        System.out.println("don't kill me");
    }
}
