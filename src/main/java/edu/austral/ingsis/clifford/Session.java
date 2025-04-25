package edu.austral.ingsis.clifford;

import java.util.List;
import java.util.Optional;

public record Session(Directory root, List<String> path) {
  public Session(Directory root, List<String> path) {
    this.root = root;
    this.path = List.copyOf(path);
  }

  // recorro desde el root, segun la path
  public Directory getCurrentDirectory() throws Exception {
    Directory actual = root;
    for (String nombreDirec : path) {
      Optional<FileSystem> childOpt = actual.findChildByName(nombreDirec);

      if (childOpt.isEmpty()) {
        throw new Exception("Directory: " + nombreDirec + " not found");
      }
      FileSystem child = childOpt.get();
      if (!child.isDirectory()) {
        throw new Exception(nombreDirec + " is not a directory");
      }
      actual = (Directory) child;
    }
    return actual;
  }

  public Session updatePath(List<String> newPath) {
    return new Session(this.root, newPath);
  }

  public Session updateRoot(Directory newRoot) {
    return new Session(newRoot, this.path);
  }
}
