package edu.austral.ingsis.clifford.commands;

import edu.austral.ingsis.clifford.Path;
import edu.austral.ingsis.clifford.Result;
import edu.austral.ingsis.clifford.Session;

public record Pwd() implements Command {
  @Override
  public Result execute(Session session) {
    Path path = session.path();
    String pathOutput = "";
    if (path.isEmpty()) {
      pathOutput += "/";
    } else {
      for (String pathPart : path.segments()) {
        pathOutput += "/" + pathPart;
      }
    }
    return new Result(pathOutput, session);
  }
}
