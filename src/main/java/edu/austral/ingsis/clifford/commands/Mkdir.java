package edu.austral.ingsis.clifford.commands;

import edu.austral.ingsis.clifford.Directory;
import edu.austral.ingsis.clifford.Result;
import edu.austral.ingsis.clifford.Session;
import edu.austral.ingsis.clifford.TreeUpdater;

public record Mkdir(String dirName) implements Command {
  @Override
  public Result execute(Session session) throws Exception {
    Directory currentDir;
    try {
      currentDir = session.getCurrentDirectory();
    } catch (Exception e) {
      return new Result("Could not access current directory", session);
    }

    if (isInvalidName()) return errorResult(session, "Invalid directory name: '" + dirName + "'");
    if (alreadyExists(currentDir))
      return errorResult(session, "name '" + dirName + "' already exists");

    return createDirectory(session, currentDir);
  }

  private boolean isInvalidName() {
    return dirName.contains("/") || dirName.contains(" ");
  }

  private boolean alreadyExists(Directory dir) {
    return dir.findChildByName(dirName).isPresent();
  }

  private Result errorResult(Session session, String message) {
    return new Result(message, session);
  }

  private Result createDirectory(Session session, Directory currentDir) throws Exception {
    Directory newDir = new Directory(dirName, java.util.List.of());
    Directory updatedCurrent = currentDir.addChild(newDir);
    Directory newRoot = TreeUpdater.updateTree(session.root(), session.path(), updatedCurrent);
    return new Result("'" + dirName + "' directory created", session.updateRoot(newRoot));
  }
}
