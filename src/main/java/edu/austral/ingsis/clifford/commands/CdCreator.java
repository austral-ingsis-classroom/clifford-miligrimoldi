package edu.austral.ingsis.clifford.commands;

public class CdCreator implements CommandCreator {
  @Override
  public Command create(String[] commandParts) {
    return new Cd(commandParts[1]);
  }
}
