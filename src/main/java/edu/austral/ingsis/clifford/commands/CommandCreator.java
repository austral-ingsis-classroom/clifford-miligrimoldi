package edu.austral.ingsis.clifford.commands;

public interface CommandCreator {
  Command create(String[] commandParts);
}
