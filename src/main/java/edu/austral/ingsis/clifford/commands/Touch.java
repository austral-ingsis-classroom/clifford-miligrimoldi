package edu.austral.ingsis.clifford.commands;

import edu.austral.ingsis.clifford.Directory;
import edu.austral.ingsis.clifford.File;
import edu.austral.ingsis.clifford.Result;
import edu.austral.ingsis.clifford.Session;
import edu.austral.ingsis.clifford.TreeManager;

public record Touch(String fileName) implements Command {
  @Override
  public Result execute(Session session) throws Exception {
    if (fileName.contains("/") || fileName.contains(" ")) {
      return new Result("Invalid file name: '" + fileName + "'", session);
    }
    Directory currentDir;
    try {
      currentDir = session.getCurrentDirectory();
    } catch (Exception e) {
      return new Result("Could not access current directory", session);
    }

    if (currentDir.findChildByName(fileName).isPresent()) {
      return new Result("File or directory '" + fileName + "' already exists", session);
    }

    File newFile = new File(fileName);
    Directory direcUpdated = currentDir.addChild(newFile);

    Directory rootUpdated = TreeManager.updateTree(session.root(), session.path(), direcUpdated);
    return new Result("'" + fileName + "' file created", session.updateRoot(rootUpdated));
  }
}
