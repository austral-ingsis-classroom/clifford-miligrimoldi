package edu.austral.ingsis.clifford.commands;

public class RmCreator implements CommandCreator {
    @Override
    public Command create(String[] commandParts) {
        boolean recursive = false;
        String name;

        if (commandParts.length > 1 && commandParts[1].equals("--recursive")) {
            recursive = true;
            name = commandParts[2];
        } else {
            name = commandParts[1];
        }

        return new Rm(name, recursive);
    }
}
