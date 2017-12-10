package edu.ucu.assignmentone.heroes;

import java.util.ArrayList;
import java.util.Random;
import java.util.function.Supplier;

public class CharacterFactory {
    private ArrayList<Supplier<Character>> characterBuilders = new ArrayList<>();
    private Random randomGenerator;

    public CharacterFactory() {
        characterBuilders.add(Elf::buildRandom);
        characterBuilders.add(Hobbit::buildRandom);
        characterBuilders.add(King::buildRandom);
        characterBuilders.add(Knight::buildRandom);
        randomGenerator = new Random();
    }

    Character createCharacter() {
        Supplier<Character> builder = characterBuilders.get(randomGenerator.nextInt(characterBuilders.size()));
        return builder.get();
    }
}
