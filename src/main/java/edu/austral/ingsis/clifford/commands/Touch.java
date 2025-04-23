package edu.austral.ingsis.clifford.commands;

import edu.austral.ingsis.clifford.Directory;
import edu.austral.ingsis.clifford.File;
import edu.austral.ingsis.clifford.Result;
import edu.austral.ingsis.clifford.Session;
import edu.austral.ingsis.clifford.TreeUpdater;

public record Touch(String fileName) implements Command {
  @Override
  public Result execute(Session session) throws Exception {
    if (isInvalidName()) return invalidNameResult(session);

    Directory currentDir = getCurrentDirectoryOrError(session);
    if (currentDir == null) return new Result("Could not access current directory", session);

    if (currentDir.findChildByName(fileName).isPresent()) {
      return new Result("File or directory '" + fileName + "' already exists", session);
    }

    return createFileInDir(currentDir, session);
  }

  private boolean isInvalidName() {
    return fileName.contains("/") || fileName.contains(" ");
  }

  private Result invalidNameResult(Session session) {
    return new Result("Invalid file name: '" + fileName + "'", session);
  }

  private Directory getCurrentDirectoryOrError(Session session) {
    try {
      return session.getCurrentDirectory();
    } catch (Exception e) {
      return null;
    }
  }

  private Result createFileInDir(Directory currentDir, Session session) throws Exception {
    File newFile = new File(fileName);
    Directory updatedDir = currentDir.addChild(newFile);
    Directory updatedRoot = TreeUpdater.updateTree(session.root(), session.path(), updatedDir);
    return new Result("'" + fileName + "' file created", session.updateRoot(updatedRoot));
  }
}
