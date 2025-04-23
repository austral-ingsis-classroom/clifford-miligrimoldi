package edu.austral.ingsis.clifford;

import edu.austral.ingsis.clifford.commands.CdCreator;
import edu.austral.ingsis.clifford.commands.Command;
import edu.austral.ingsis.clifford.commands.CommandCreator;
import edu.austral.ingsis.clifford.commands.LsCreator;
import edu.austral.ingsis.clifford.commands.MkdirCreator;
import edu.austral.ingsis.clifford.commands.PwdCreator;
import edu.austral.ingsis.clifford.commands.RmCreator;
import edu.austral.ingsis.clifford.commands.TouchCreator;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class StringToCommand {
  private static final Map<String, CommandCreator> creators = new HashMap<>();

  static {
    creators.put("mkdir", new MkdirCreator());
    creators.put("touch", new TouchCreator());
    creators.put("cd", new CdCreator());
    creators.put("pwd", new PwdCreator());
    creators.put("rm", new RmCreator());
    creators.put("ls", new LsCreator());
  }

  public static Command interpret(String input) {
    String[] commandParts = input.trim().split("\\s+");
    String commandName = commandParts[0];

    CommandCreator creator = creators.get(commandName);
    if (creator == null) throw new IllegalArgumentException("Unknown command: " + commandName);

    return creator.create(commandParts);
  }
}
