package edu.austral.ingsis.clifford;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public record Directory(String name, List<FileSystem> children) implements FileSystem {
  public Directory(String name, List<FileSystem> children) {
    this.name = name;
    this.children = List.copyOf(children);
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public boolean isDirectory() {
    return true;
  }

  public Directory addChild(FileSystem node) {
    List<FileSystem> newChildren = new ArrayList<>(children);
    newChildren.add(node);
    return new Directory(name, newChildren);
  }

  public Directory removeChild(String nameRemove) {
    List<FileSystem> newChildren = new ArrayList<>();
    for (FileSystem child : children) {
      if (!child.getName().equals(nameRemove)) {
        newChildren.add(child);
      }
    }
    return new Directory(this.name, newChildren);
  }

  public Directory replaceChild(Directory old, Directory newDirec) {
    List<FileSystem> newChildren = new ArrayList<>();
    for (FileSystem child : children) {
      if (child == old) {
        newChildren.add(newDirec);
      } else {
        newChildren.add(child);
      }
    }
    return new Directory(this.name, newChildren);
  }

  public Optional<FileSystem> findChildByName(String name) {
    for (FileSystem child : children) {
      if (child.getName().equals(name)) {
        return Optional.of(child);
      }
    }
    return Optional.empty();
  }
}
