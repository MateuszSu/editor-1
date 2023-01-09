package editor;

import java.nio.file.Paths;

import editor.filesystem.*;

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
  }
}