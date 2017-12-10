package edu.ucu.assignmentone.notswitch;

import java.util.function.Supplier;

interface EmailStrategy {
    void apply(String recipient);
}

class WelcomeEmailStrategy implements EmailStrategy {
    public void apply(String recipient) {
        System.out.printf("Sending welcome email to %s\n", recipient);
    }
}

class SuperDealEmailStrategy implements EmailStrategy {
    public void apply(String recipient) {
        System.out.printf("Sending super deal email to %s\n", recipient);
    }
}

class HanukaEmailStrategy implements EmailStrategy {
    public void apply(String recipient) {
        System.out.printf("Sending happy hanuka to %s\n", recipient);
    }
}

enum EmailStrategyFactory {
    WelcomeEmail(WelcomeEmailStrategy::new),
    SuperDealEmail(SuperDealEmailStrategy::new),
    HappyHanukaEmail(HanukaEmailStrategy::new);

    EmailStrategy strategy;

    EmailStrategyFactory(Supplier<EmailStrategy> strategy) {
        this.strategy = strategy.get();
    }

    public void apply(String recipient) {
        this.strategy.apply(recipient);
    }
}

public class EnumFactory {
    public static void main(String... args) {
        EmailStrategyFactory.valueOf("WelcomeEmail").apply("oleksiimo@wix.com");
        EmailStrategyFactory.valueOf("SuperDealEmail").apply("oleksiimo@wix.com");
        EmailStrategyFactory.valueOf("HappyHanukaEmail").apply("oleksiimo@wix.com");
    }
}
