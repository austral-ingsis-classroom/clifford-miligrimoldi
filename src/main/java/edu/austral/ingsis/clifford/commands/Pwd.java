package edu.austral.ingsis.clifford.commands;

import edu.austral.ingsis.clifford.Result;
import edu.austral.ingsis.clifford.Session;

public record Pwd() implements Command {
  @Override
  public Result execute(Session session) {
    return new Result(session.path().toString(), session);
  }
}
