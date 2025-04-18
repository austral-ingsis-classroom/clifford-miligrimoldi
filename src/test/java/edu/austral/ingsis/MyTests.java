package edu.austral.ingsis;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.austral.ingsis.clifford.Directory;
import edu.austral.ingsis.clifford.File;
import edu.austral.ingsis.clifford.Result;
import edu.austral.ingsis.clifford.Session;
import edu.austral.ingsis.clifford.commands.*;
import java.util.List;
import org.junit.jupiter.api.Test;

public class MyTests {

  @Test
  void testTouchAlreadyExists() throws Exception {
    Session session = new Session(new Directory("root", List.of(new File("file1.txt"))), List.of());
    Command touch = new Touch("file1.txt");

    Result result = touch.execute(session);

    assertEquals("File or directory 'file1.txt' already exists", result.output());
  }

  @Test
  void testRmDoesNotExist() throws Exception {
    Session session = new Session(new Directory("root", List.of()), List.of());
    Command rm = new Rm("missing.txt", false);

    Result result = rm.execute(session);

    assertEquals("File or directory 'missing.txt' does not exist", result.output());
  }

  @Test
  void testPwdAtRoot() throws Exception {
    Session session = new Session(new Directory("root", List.of()), List.of());
    Command pwd = new Pwd();

    Result result = pwd.execute(session);

    assertEquals("/", result.output());
  }

  @Test
  void testMkdirAlreadyExists() throws Exception {
    Session session =
        new Session(new Directory("root", List.of(new Directory("docs", List.of()))), List.of());
    Command mkdir = new Mkdir("docs");

    Result result = mkdir.execute(session);

    assertEquals("name 'docs' already exists", result.output());
  }

  @Test
  void testCdNotADirectory() throws Exception {
    Directory root = new Directory("root", List.of(new File("notadir")));
    Session session = new Session(root, List.of());
    Command cd = new Cd("notadir");

    Result result = cd.execute(session);

    assertEquals("'notadir' is not a directory", result.output());
  }

  @Test
  void testCdDot() throws Exception {
    Session session =
        new Session(
            new Directory("root", List.of(new Directory("mydir", List.of()))), List.of("mydir"));

    Command cd = new Cd(".");

    Result result = cd.execute(session);

    assertEquals("moved to directory 'mydir'", result.output());
  }

  @Test
  void testCdDoubleDot() throws Exception {
    Session session =
        new Session(
            new Directory("root", List.of(new Directory("child", List.of()))), List.of("child"));
    Command cd = new Cd("..");

    Result result = cd.execute(session);

    assertEquals("moved to directory '/'", result.output());
  }

  @Test
  void testTouchInvalidName() throws Exception {
    Session session = new Session(new Directory("root", List.of()), List.of());
    Command touch = new Touch("fi le.txt");

    Result result = touch.execute(session);

    assertEquals("Invalid file name: 'fi le.txt'", result.output());
  }
}
