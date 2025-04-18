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
  @Override
  public Result execute(Session session) {
    List<String> newPath;
    if (pathCd.equals("..")) {
      if (session.path().isEmpty()) {
        newPath = List.of();
      } else {
        newPath = session.path().subList(0, session.path().size() - 1);
      }
    } else if (pathCd.equals(".")) {
      newPath = session.path();
    } else {
      List<String> pathPartsList = Arrays.asList(pathCd.split("/"));
      if (pathCd.startsWith("/")) {
        // absolute route -> empieza desde root
        newPath = new ArrayList<>();
      } else {
        // relative route -> empieza desde donde estas
        newPath = new ArrayList<>(session.path());
      }
      for (String part : pathPartsList) {
        if (part.isEmpty() || part.equals(".")) continue;
        if (part.equals("..")) {
          if (!newPath.isEmpty()) {
            newPath.remove(newPath.size() - 1);
          }
        } else {
          newPath.add(part);
        }
      }
    }
    try {
      Directory actual = session.root();
      for (String dirName : newPath) {
        Optional<FileSystem> child = actual.findChildByName(dirName);
        if (child.isEmpty()) {
          return new Result("'" + dirName + "' directory does not exist", session);
        }
        if (!child.get().isDirectory()) {
          return new Result("'" + dirName + "' is not a directory", session);
        }
        actual = (Directory) child.get();
      }
    } catch (Exception e) {
      return new Result("Error navigating: " + pathCd, session);
    }

    return new Result(
        "moved to directory '" + (newPath.isEmpty() ? "/" : newPath.get(newPath.size() - 1)) + "'",
        session.updatePath(newPath));
  }
}
