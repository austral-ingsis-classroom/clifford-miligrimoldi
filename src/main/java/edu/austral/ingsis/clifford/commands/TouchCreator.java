package edu.austral.ingsis.clifford.commands;

public class TouchCreator implements CommandCreator {
    @Override
    public Command create(String[] commandParts) {
        return new Touch(commandParts[1]);
    }
}