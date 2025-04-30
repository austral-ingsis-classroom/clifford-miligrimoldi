package edu.austral.ingsis.clifford.commands;

import edu.austral.ingsis.clifford.Directory;
import edu.austral.ingsis.clifford.FileSystem;
import edu.austral.ingsis.clifford.Path;
import edu.austral.ingsis.clifford.Result;
import edu.austral.ingsis.clifford.Session;
import java.util.Optional;

public record Cd(String pathCd) implements Command {

  @Override
  public Result execute(Session session) {
    Path newPath = Path.normalize(pathCd, session.path());
    return tryNavigate(session, newPath);
  }

  private Result tryNavigate(Session session, Path path) {
    Directory current = session.root();

    for (String dir : path.segments()) {
      Optional<FileSystem> child = current.findChildByName(dir);
      if (child.isEmpty()) return new Result("'" + dir + "' directory does not exist", session);
      if (!child.get().isDirectory())
        return new Result("'" + dir + "' is not a directory", session);
      current = (Directory) child.get();
    }

    return new Result("moved to directory '" + path.lastSegment() + "'", session.updatePath(path));
  }
}
