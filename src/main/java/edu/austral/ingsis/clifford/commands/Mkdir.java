package edu.austral.ingsis.clifford.commands;

import edu.austral.ingsis.clifford.Directory;
import edu.austral.ingsis.clifford.Result;
import edu.austral.ingsis.clifford.Session;
import edu.austral.ingsis.clifford.TreeManager;
import java.util.List;

public record Mkdir(String dirName) implements Command {
  @Override
  public Result execute(Session session) throws Exception {
    Directory currentDir;
    try {
      currentDir = session.getCurrentDirectory();
    } catch (Exception e) {
      return new Result("Error accessing current directory", session);
    }
    if (dirName.contains("/") || dirName.contains(" ")) {
      return new Result("Invalid directory name: '" + dirName + "'", session);
    }
    // Chequeo que no exista un directorio con ese nombre
    if (currentDir.findChildByName(dirName).isPresent()) {
      return new Result("name '" + dirName + "' already exists", session);
    }
    Directory newDirec = new Directory(dirName, List.of());
    Directory newCurrent = currentDir.addChild(newDirec);
    Directory newRoot = TreeManager.updateTree(session.root(), session.path(), newCurrent);

    return new Result("'" + dirName + "' directory created", session.updateRoot(newRoot));
  }
}
