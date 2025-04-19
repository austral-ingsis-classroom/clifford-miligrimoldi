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
    // GIVEN
    Session session = new Session(new Directory("root", List.of(new File("file1.txt"))), List.of());
    Command touch = new Touch("file1.txt");
    // WHEN
    Result result = touch.execute(session);
    // THEN
    assertEquals("File or directory 'file1.txt' already exists", result.output());
  }

  @Test
  void testRmDoesNotExist() throws Exception {
    // GIVEN
    Session session = new Session(new Directory("root", List.of()), List.of());
    Command rm = new Rm("missing.txt", false);
    // WHEN
    Result result = rm.execute(session);
    // THEN
    assertEquals("File or directory 'missing.txt' does not exist", result.output());
  }

  @Test
  void testPwdAtRoot() throws Exception {
    // GIVEN
    Session session = new Session(new Directory("root", List.of()), List.of());
    Command pwd = new Pwd();
    // WHEN
    Result result = pwd.execute(session);
    // THEN
    assertEquals("/", result.output());
  }

  @Test
  void testMkdirAlreadyExists() throws Exception {
    // GIVEN
    Session session =
        new Session(new Directory("root", List.of(new Directory("docs", List.of()))), List.of());
    Command mkdir = new Mkdir("docs");
    // WHEN
    Result result = mkdir.execute(session);
    // THEN
    assertEquals("name 'docs' already exists", result.output());
  }

  @Test
  void testCdNotADirectory() throws Exception {
    // GIVEN
    Directory root = new Directory("root", List.of(new File("notDirec")));
    Session session = new Session(root, List.of());
    Command cd = new Cd("notDirec");
    // WHEN
    Result result = cd.execute(session);
    // THEN
    assertEquals("'notDirec' is not a directory", result.output());
  }

  @Test
  void testCdDot() throws Exception {
    // GIVEN
    Session session =
        new Session(
            new Directory("root", List.of(new Directory("mydir", List.of()))), List.of("mydir"));

    Command cd = new Cd(".");
    // WHEN
    Result result = cd.execute(session);
    // THEN
    assertEquals("moved to directory 'mydir'", result.output());
  }

  @Test
  void testCdDoubleDot() throws Exception {
    // GIVEN
    Session session =
        new Session(
            new Directory("root", List.of(new Directory("child", List.of()))), List.of("child"));
    Command cd = new Cd("..");
    // WHEN
    Result result = cd.execute(session);
    // THEN
    assertEquals("moved to directory '/'", result.output());
  }

  @Test
  void testTouchInvalidName() throws Exception {
    // GIVEN
    Session session = new Session(new Directory("root", List.of()), List.of());
    Command touch = new Touch("fi le.txt");
    // WHEN
    Result result = touch.execute(session);
    // THEN
    assertEquals("Invalid file name: 'fi le.txt'", result.output());
  }
}
