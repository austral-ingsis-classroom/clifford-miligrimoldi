package edu.austral.ingsis.clifford.commands;

import java.util.Optional;

public class LsCreator implements CommandCreator {
    @Override
    public Command create(String[] commandParts) {
        Optional<String> order = Optional.empty();
        if (commandParts.length > 1 && commandParts[1].startsWith("--ord=")) {
            order = Optional.of(commandParts[1].substring(6));
        }
        return new Ls(order);
    }
}
