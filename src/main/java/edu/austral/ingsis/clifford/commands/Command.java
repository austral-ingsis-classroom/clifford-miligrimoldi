package edu.austral.ingsis.clifford.commands;

import edu.austral.ingsis.clifford.Result;
import edu.austral.ingsis.clifford.Session;

public interface Command {
  Result execute(Session session) throws Exception;
}
