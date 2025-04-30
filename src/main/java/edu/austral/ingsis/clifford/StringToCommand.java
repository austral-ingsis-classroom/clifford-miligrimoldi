package edu.austral.ingsis.clifford;

import edu.austral.ingsis.clifford.commands.Command;
import edu.austral.ingsis.clifford.commands.CommandCreator;
import java.util.Map;

public class StringToCommand {
  public static Command interpret(String input, Map<String, CommandCreator> creators) {
    String[] commandParts = input.trim().split("\\s+");
    String commandName = commandParts[0];

    CommandCreator creator = creators.get(commandName);
    if (creator == null) throw new IllegalArgumentException("Unknown command: " + commandName);

    return creator.create(commandParts);
  }
}
