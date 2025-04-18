package edu.austral.ingsis.clifford.commands;

import edu.austral.ingsis.clifford.Result;
import edu.austral.ingsis.clifford.Session;
import java.util.List;

public record Pwd() implements Command {
  @Override
  public Result execute(Session session) {
    List<String> path = session.path();
    String pathOutput = "";
    if (path.isEmpty()) {
      pathOutput += "/";
    } else {
      for (String pathPart : path) {
        pathOutput += "/" + pathPart;
      }
    }
    return new Result(pathOutput, session);
  }
}
