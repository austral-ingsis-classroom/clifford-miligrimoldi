package edu.austral.ingsis.clifford.commands;

import edu.austral.ingsis.clifford.Directory;
import edu.austral.ingsis.clifford.File;
import edu.austral.ingsis.clifford.Result;
import edu.austral.ingsis.clifford.Session;
import edu.austral.ingsis.clifford.TreeUpdater;

public record Touch(String fileName) implements Command {
  @Override
  public Result execute(Session session) throws Exception {
    if (isInvalidName()) return errorResult(session, "Invalid file name: '" + fileName + "'");

    Directory currentDir;
    try {
      currentDir = session.getCurrentDirectory();
    } catch (Exception e) {
      return errorResult(session, "Could not access current directory");
    }

    if (currentDir.findChildByName(fileName).isPresent()) {
      return errorResult(session, "File or directory '" + fileName + "' already exists");
    }

    return createFileInDir(currentDir, session);
  }

  private boolean isInvalidName() {
    return fileName.contains("/") || fileName.contains(" ");
  }

  private Result errorResult(Session session, String message) {
    return new Result(message, session);
  }

  private Result createFileInDir(Directory currentDir, Session session) throws Exception {
    File newFile = new File(fileName);
    Directory updatedDir = currentDir.addChild(newFile);
    Directory updatedRoot = TreeUpdater.updateTree(session.root(), session.path(), updatedDir);
    return new Result("'" + fileName + "' file created", session.updateRoot(updatedRoot));
  }
}
