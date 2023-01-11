package editor.workspace;

import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import editor.Result;
import editor.filesystem.File;
import editor.filesystem.Folder;
import editor.parser.SyntaxChecker;

public class Workspace {
	private ActiveTab activeTab;
	private final Folder root;
	private Map<String, Tab> tabs = new HashMap<String, Tab>();

	public Workspace(final Folder root, final SyntaxChecker syntaxChecker) {
		activeTab = new ActiveTab(syntaxChecker);
		this.root = root;
	}

	public Result openFile(final String path) {
		Tab tab = tabs.getOrDefault(path, null);
		if (tab != null) {
			activeTab.set(tab);
			return new Result("File already opened");
		}
		final Optional<File> fileSearch = root.getFile(path);
		if (fileSearch.isEmpty()) {
			return new Result("No such file");
		}
		tab = new Tab(fileSearch.get());
		final Result result = tab.load();
		if (!result.isOk()) {
			return result;
		}
		activeTab.set(tab);
		tabs.put(path, tab);
		return new Result();
	}

	public List<String> getFiles() {
		List<String> files = new LinkedList<String>();
		for (final File file : root.getFiles()) {
			files.add(file.getFilename());
		}
		for (final Folder folder : root.getFolders()) {
			getFilesRecursive(files, folder, folder.getName());
		}
		return files;
	}

	private void getFilesRecursive(List<String> files, Folder folder, String path) {
		for (final File file : folder.getFiles()) {
			files.add(String.join("/", path, file.getFilename()));
		}
		for (final Folder subFolder : folder.getFolders()) {
			getFilesRecursive(files, subFolder, String.join("/", path, subFolder.getName()));
		}
	}

	public List<String> getOpenedFiles() { return new ArrayList<String>(tabs.keySet()); }

	public Result saveActive() {
		if (!activeTab.isSet()) {
			return new Result("No active tab");
		}
		return activeTab.save();
	}

	public Result closeFile(final String path) {
		final Tab tab = tabs.getOrDefault(path, null);
		if (tab == null) {
			return new Result("The file to be closed is not opened");
		}
		final Result result = tab.save();
		tabs.remove(path);
		if (!result.isOk()) {
			return result;
		}
		return new Result();
	}

	public void closeAll() {
		Set<String> openedTabs = new HashSet<String>(tabs.keySet());
		for (final String openedTab : openedTabs) {
			closeFile(openedTab);
		}
	}

	public Editable getActive() { return activeTab.isSet() ? activeTab : ActiveTab.emptyActive ; }

	public Folder getRoot() { return root; }

	public Result createFile(final String path) { return root.createFile(path); }

	public Result removeFile(final String path) {

		closeFile(path);
		return root.removeFile(path);
	}

	public Result createFolder(final String path) { return root.createFolder(path); }

	public Result removeFolder(final String path) {
		for (final String file : getOpenedFiles()) {
			if (file.startsWith(path, 0)) {
				closeFile(file);
			}
		}
		return root.removeFolder(path);
	}
}
