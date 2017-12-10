package edu.ucu.assignmentone.heroes;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
abstract class Character {
    protected int power;
    protected int hp;

    abstract void kick(Character c);

    boolean isAlive() {
        return hp > 0;
    }

    public String toString() {
        return getClass().getSimpleName() + "(power=" + Integer.toString(power) +
                ", hp=" + Integer.toString(hp) + ")";
    }
}

