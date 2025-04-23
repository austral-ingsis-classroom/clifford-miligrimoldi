package edu.austral.ingsis.clifford.commands;

public class MkdirCreator implements CommandCreator {
    @Override
    public Command create(String[] commandParts) {
        return new Mkdir(commandParts[1]);
    }
}

