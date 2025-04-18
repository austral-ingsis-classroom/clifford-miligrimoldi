package edu.austral.ingsis;

import edu.austral.ingsis.clifford.Directory;
import edu.austral.ingsis.clifford.Result;
import edu.austral.ingsis.clifford.Session;
import edu.austral.ingsis.clifford.StringToCommand;
import edu.austral.ingsis.clifford.commands.Command;
import java.util.ArrayList;
import java.util.List;

public class FileSystemRunnerImpl implements FileSystemRunner {

  @Override
  public List<String> executeCommands(List<String> commands) throws Exception {
    List<String> resultados = new ArrayList<>();
    Session session = new Session(new Directory("/", List.of()), List.of());

    for (String linea : commands) {
      Command command = StringToCommand.interpret(linea);
      Result result = command.execute(session);
      session = result.session();
      resultados.add(result.output());
    }

    return resultados;
  }
}
