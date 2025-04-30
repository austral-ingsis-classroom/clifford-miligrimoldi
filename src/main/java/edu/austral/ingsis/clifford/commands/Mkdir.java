package edu.austral.ingsis.clifford.commands;

import edu.austral.ingsis.clifford.Directory;
import edu.austral.ingsis.clifford.Result;
import edu.austral.ingsis.clifford.Session;
import edu.austral.ingsis.clifford.TreeUpdater;
import java.util.List;

public record Mkdir(String dirName) implements Command {
  @Override
  public Result execute(Session session) throws Exception {
    Directory currentDir = getCurrentDirectory(session);
    if (isInvalidName() || alreadyExists(currentDir)) return errorResult(currentDir);
    return createDirectory(session, currentDir);
  }

  private boolean isInvalidName() {
    return dirName.contains("/") || dirName.contains(" ");
  }

  private boolean alreadyExists(Directory dir) {
    return dir.findChildByName(dirName).isPresent();
  }

  private Result errorResult(Directory dir) {
    if (isInvalidName()) return new Result("Invalid directory name: '" + dirName + "'", null);
    return new Result("name '" + dirName + "' already exists", null);
  }

  private Directory getCurrentDirectory(Session session) {
    try {
      return session.getCurrentDirectory();
    } catch (Exception e) {
      return new Directory("error", List.of());
    }
  }

  private Result createDirectory(Session session, Directory currentDir) throws Exception {
    Directory newDir = new Directory(dirName, List.of());
    Directory updatedCurrent = currentDir.addChild(newDir);
    Directory newRoot = TreeUpdater.updateTree(session.root(), session.path(), updatedCurrent);
    return new Result("'" + dirName + "' directory created", session.updateRoot(newRoot));
  }
}
