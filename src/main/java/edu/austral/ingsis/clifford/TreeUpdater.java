package edu.austral.ingsis.clifford;

public class TreeUpdater {
  public static Directory updateTree(Directory root, Path path, Directory updated)
      throws Exception {
    if (path.isEmpty()) return updated; // caso base
    String next = path.head();
    Path rest = path.tail();

    FileSystem child =
        root.findChildByName(next).orElseThrow(() -> new Exception("Directory not found: " + next));

    if (!child.isDirectory()) {
      throw new Exception(next + " is not a directory");
    }

    Directory updatedChild = updateTree((Directory) child, rest, updated);
    return root.replaceChild((Directory) child, updatedChild);
  }
}
