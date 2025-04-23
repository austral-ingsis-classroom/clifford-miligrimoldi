package edu.austral.ingsis.clifford.commands;

import edu.austral.ingsis.clifford.Directory;
import edu.austral.ingsis.clifford.FileSystem;
import edu.austral.ingsis.clifford.Result;
import edu.austral.ingsis.clifford.Session;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public record Cd(String pathCd) implements Command {
  private static String error = "";

  @Override
  public Result execute(Session session) {
    List<String> newPath = resolveNewPath(session);

    Directory current = navigateToNewPath(session.root(), newPath);
    if (current == null) return new Result(error, session);

    return new Result(
        "moved to directory '" + (newPath.isEmpty() ? "/" : newPath.get(newPath.size() - 1)) + "'",
        session.updatePath(newPath));
  }

  private List<String> resolveNewPath(Session session) {
    if (pathCd.equals("..")) return moveUp(session);
    if (pathCd.equals(".")) return session.path();
    return parsePathInput(session);
  }

  private List<String> moveUp(Session session) {
    if (session.path().isEmpty()) return List.of();
    return session.path().subList(0, session.path().size() - 1);
  }

  private List<String> parsePathInput(Session session) {
    List<String> parts = Arrays.asList(pathCd.split("/"));
    List<String> result =
        pathCd.startsWith("/") ? new ArrayList<>() : new ArrayList<>(session.path());

    for (String part : parts) {
      if (part.isEmpty() || part.equals(".")) continue;
      if (part.equals("..")) {
        if (!result.isEmpty()) result.remove(result.size() - 1);
      } else {
        result.add(part);
      }
    }
    return result;
  }

  private Directory navigateToNewPath(Directory root, List<String> path) {
    Directory current = root;
    for (String dir : path) {
      Optional<FileSystem> child = current.findChildByName(dir);
      if (child.isEmpty()) {
        error = "'" + dir + "' directory does not exist";
        return null;
      }
      if (!child.get().isDirectory()) {
        error = "'" + dir + "' is not a directory";
        return null;
      }
      current = (Directory) child.get();
    }
    return current;
  }
}
