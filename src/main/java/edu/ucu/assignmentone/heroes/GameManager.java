package edu.ucu.assignmentone.heroes;

public class GameManager {
    static void fight(Character c1, Character c2) {
        System.out.printf("Let the fight between %s and %s begin\n", c1, c2);

        while (true) {
            System.out.printf("%s is kicking %s\n", c1, c2);
            c1.kick(c2);
            if (!c2.isAlive()) {
                System.out.printf("%s won. %s is dead", c1, c2);
                break;
            }

            System.out.printf("%s is kicking %s\n", c2, c1);
            c2.kick(c1);
            if (!c1.isAlive()) {
                System.out.printf("%s won. %s is dead", c2, c1);
                break;
            }
        }
    }
}
