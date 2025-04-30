package edu.austral.ingsis;

import edu.austral.ingsis.clifford.Directory;
import edu.austral.ingsis.clifford.Path;
import edu.austral.ingsis.clifford.Result;
import edu.austral.ingsis.clifford.Session;
import edu.austral.ingsis.clifford.StringToCommand;
import edu.austral.ingsis.clifford.commands.CdCreator;
import edu.austral.ingsis.clifford.commands.Command;
import edu.austral.ingsis.clifford.commands.CommandCreator;
import edu.austral.ingsis.clifford.commands.LsCreator;
import edu.austral.ingsis.clifford.commands.MkdirCreator;
import edu.austral.ingsis.clifford.commands.PwdCreator;
import edu.austral.ingsis.clifford.commands.RmCreator;
import edu.austral.ingsis.clifford.commands.TouchCreator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileSystemRunnerImpl implements FileSystemRunner {
  private static final Map<String, CommandCreator> creators = new HashMap<>();

  static {
    creators.put("mkdir", new MkdirCreator());
    creators.put("touch", new TouchCreator());
    creators.put("cd", new CdCreator());
    creators.put("pwd", new PwdCreator());
    creators.put("rm", new RmCreator());
    creators.put("ls", new LsCreator());
  }

  @Override
  public List<String> executeCommands(List<String> commands) throws Exception {
    List<String> resultados = new ArrayList<>();
    Session session = new Session(new Directory("/", List.of()), new Path(List.of()));

    for (String linea : commands) {
      Command command = StringToCommand.interpret(linea, creators);
      Result result = command.execute(session);
      session = result.session();
      resultados.add(result.output());
    }

    return resultados;
  }
}
