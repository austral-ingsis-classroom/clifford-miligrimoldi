package edu.austral.ingsis.clifford;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public record Path(List<String> segments) {

  public Path {
    segments = List.copyOf(segments);
  }

  public static Path normalize(String input, Path currentPath) {
    List<String> parts = Arrays.asList(input.split("/"));
    List<String> result =
        input.startsWith("/") ? new ArrayList<>() : new ArrayList<>(currentPath.segments());

    for (String part : parts) {
      if (part.isEmpty() || part.equals(".")) continue;
      if (part.equals("..")) {
        if (!result.isEmpty()) result.remove(result.size() - 1);
      } else {
        result.add(part);
      }
    }

    return new Path(result);
  }

  public boolean isRoot() {
    return segments.isEmpty();
  }

  public String lastSegment() {
    return isRoot() ? "/" : segments.get(segments.size() - 1);
  }

  public Path parent() {
    if (isRoot()) return this;
    return new Path(segments.subList(0, segments.size() - 1));
  }

  @Override
  public String toString() {
    return isRoot() ? "/" : "/" + String.join("/", segments);
  }

  public boolean isEmpty() {
    return segments.isEmpty();
  }

  public String head() {
    return segments.get(0);
  }

  public Path tail() {
    return new Path(segments.subList(1, segments.size()));
  }
}
