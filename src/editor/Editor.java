package editor;

import java.nio.file.Paths;
import java.util.List;

import editor.filesystem.FolderImpl;
import editor.parser.SyntaxCheckerWrapper;
import editor.workspace.Editable;
import editor.workspace.Workspace;

class Editor {
	private SyntaxCheckerWrapper syntaxCheckerWrapper = new SyntaxCheckerWrapper();
	private Config config = new Config(syntaxCheckerWrapper);
	private Workspace workspace;

	Editor() {};

	public Result open(final String path) {
		if (workspace != null && Paths.get(path).toString().equals(workspace.getRoot().getPath())) {
			return new Result(String.format("Directory (%s) is already opened", path));
		}
		workspace = new Workspace(new FolderImpl(path), syntaxCheckerWrapper);
		return new Result();
	}

	public Result close() {
		if (workspace == null) {
			return new Result("Nothing opened");
		}
		config.dump();
		workspace.closeAll();
		workspace = null;
		return new Result();
	}

	public Config getConfig() { return config; }

	public Editable edit() { return workspace.getActive(); }

	public List<String> getFiles() { return workspace.getFiles(); }

	public Result createFile(final String path) { return workspace.createFile(path); }

	public Result removeFile(final String path) { return workspace.removeFile(path); }

	public Result createFolder(final String path) { return workspace.createFolder(path); }

	public Result removeFolder(final String path) { return workspace.removeFolder(path); }

	public List<String> getOpenedFiles() { return workspace.getOpenedFiles(); }

	public Result openFile(final String path) { return workspace.openFile(path); }

	public Result closeFile(final String path) { return workspace.closeFile(path); }

	public void save() { workspace.saveActive(); }
}
