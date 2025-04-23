package edu.austral.ingsis.clifford.commands;

public class PwdCreator implements CommandCreator {
    @Override
    public Command create(String[] commandParts) {
        return new Pwd();
    }
}
