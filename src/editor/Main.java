package editor;

import java.nio.file.Paths;

import editor.filesystem.*;
import editor.parser.SyntaxCheckerWrapper;

public class Main {

  public static void main(String[] args) {
    final String rootPath = "/tmp/java";
    final java.io.File rootFile = Paths.get(rootPath).toAbsolutePath().toFile();
    if (!rootFile.isDirectory() && !rootFile.mkdir()) {
      // cannot create root directory
    }
    Folder root = new FolderImpl(rootFile.getPath().toString());
    System.out.println(root.createFile("foo/bar/baz").getMessage());
    System.out.println(root.createFile("foo/bar").getMessage());
    System.out.println(root.createFile("foo/bar/baz").getMessage());
    System.out.println(root.removeFolder("foo/baz").getMessage());
    System.out.println(root.removeFolder("bar/baz").getMessage());

    SyntaxCheckerWrapper syntaxCheckerWrapper = new SyntaxCheckerWrapper();
    syntaxCheckerWrapper.disable();

    Config config = new Config(syntaxCheckerWrapper);
    config.enableSyntaxChecker("");
    config.dump();

    Editor editor = new Editor();
    System.out.println(editor.open(rootPath).getMessage());
    System.out.println(editor.getFiles());
    System.out.println(editor.getOpenedFiles());
    System.out.println(editor.openFile("foo/bar/baz").getMessage());
    System.out.println(editor.getOpenedFiles());
    editor.edit().addLine(0);
    editor.edit().modifyLine(0, "fooo");
    System.out.println(editor.openFile("foo/foo9").getMessage());
    editor.edit().addLine(0);
    editor.edit().addLine(0);
    editor.edit().modifyLine(0, "baar");
    editor.save();
    editor.close();
  }
}