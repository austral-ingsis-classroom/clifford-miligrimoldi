package edu.austral.ingsis.clifford.commands;

import edu.austral.ingsis.clifford.Directory;
import edu.austral.ingsis.clifford.FileSystem;
import edu.austral.ingsis.clifford.Result;
import edu.austral.ingsis.clifford.Session;
import edu.austral.ingsis.clifford.TreeManager;
import java.util.Optional;

public record Rm(String name, boolean recursive) implements Command {
  @Override
  public Result execute(Session session) throws Exception {
    Directory currentDirec;
    try {
      currentDirec = session.getCurrentDirectory();
    } catch (Exception e) {
      return new Result("Could not access current directory", session);
    }
    Optional<FileSystem> wantRemove = currentDirec.findChildByName(name);
    if (wantRemove.isEmpty()) {
      return new Result("File or directory '" + name + "' does not exist", session);
    }
    if (wantRemove.get().isDirectory() && !recursive) {
      return new Result("cannot remove '" + name + "', is a directory", session);
    }
    Directory updatedDirec = currentDirec.removeChild(name);
    Directory newRoot = TreeManager.updateTree(session.root(), session.path(), updatedDirec);

    return new Result("'" + name + "' removed", session.updateRoot(newRoot));
  }
}
