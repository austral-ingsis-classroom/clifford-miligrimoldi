package edu.austral.ingsis.clifford;

import java.util.List;

public class TreeUpdater {
  public static Directory updateTree(Directory root, List<String> path, Directory updated)
      throws Exception {
    if (path.isEmpty()) return updated; // caso base
    String next = path.get(0);
    List<String> restPath = path.subList(1, path.size());

    FileSystem child =
        root.findChildByName(next).orElseThrow(() -> new Exception("Directory not found: " + next));

    if (!child.isDirectory()) {
      throw new Exception(next + " is not a directory");
    }

    Directory updatedChild = updateTree((Directory) child, restPath, updated);
    Directory newRoot = root.replaceChild((Directory) child, updatedChild);
    return newRoot;
  }
}
