package edu.austral.ingsis.clifford.commands;

import edu.austral.ingsis.clifford.Directory;
import edu.austral.ingsis.clifford.FileSystem;
import edu.austral.ingsis.clifford.Result;
import edu.austral.ingsis.clifford.Session;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public record Ls(Optional<String> order) implements Command {
  @Override
  public Result execute(Session session) {
    Directory currentDirec;
    try {
      currentDirec = session.getCurrentDirectory();
    } catch (Exception e) {
      return new Result("Could not access current directory", session);
    }

    List<FileSystem> children = currentDirec.children();
    if (order.isPresent()) { // me aseguro de que el Optional tenga algo
      String ord = order.get();
      Comparator<FileSystem> comparator =
          Comparator.comparing(FileSystem::getName); // ordena alfabeticamente por nombre
      if (ord.equalsIgnoreCase("desc")) {
        comparator = comparator.reversed();
      }
      children = children.stream().sorted(comparator).toList();
    }

    String output = children.stream().map(FileSystem::getName).collect(Collectors.joining(" "));

    return new Result(output, session);
  }
}
