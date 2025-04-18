package edu.austral.ingsis.clifford;

public record File(String name) implements FileSystem {
  @Override
  public String getName() {
    return name;
  }

  @Override
  public boolean isDirectory() {
    return false;
  }
}
