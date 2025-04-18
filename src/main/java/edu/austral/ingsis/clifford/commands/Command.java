package edu.austral.ingsis.clifford.commands;

import edu.austral.ingsis.clifford.Result;
import edu.austral.ingsis.clifford.Session;

public sealed interface Command permits Mkdir, Cd, Touch, Ls, Rm, Pwd {
  Result execute(Session session) throws Exception;
}
