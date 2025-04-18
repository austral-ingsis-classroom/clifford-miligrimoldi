package edu.austral.ingsis.clifford;

import edu.austral.ingsis.clifford.commands.Cd;
import edu.austral.ingsis.clifford.commands.Command;
import edu.austral.ingsis.clifford.commands.Ls;
import edu.austral.ingsis.clifford.commands.Mkdir;
import edu.austral.ingsis.clifford.commands.Pwd;
import edu.austral.ingsis.clifford.commands.Rm;
import edu.austral.ingsis.clifford.commands.Touch;
import java.util.Optional;

public class StringToCommand {
  public static Command interpret(String input) {
    String[] words = input.trim().split("\\s+");
    String commandName = words[0];
    switch (commandName) {
      case "mkdir":
        return new Mkdir(words[1]);
      case "touch":
        return new Touch(words[1]);
      case "cd":
        return new Cd(words[1]);
      case "pwd":
        return new Pwd();
      case "rm":
        boolean recursive = input.contains("--recursive");
        String name = words[1].equals("--recursive") ? words[2] : words[1];
        return new Rm(name, recursive);
      case "ls":
        Optional<String> order = Optional.empty();
        if (words.length > 1 && words[1].startsWith("--ord=")) {
          order = Optional.of(words[1].substring(6));
        }
        return new Ls(order);
      default:
        throw new IllegalArgumentException("Unknown command: " + commandName);
    }
  }
}
