package editor.filesystem;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;

import editor.Result;

public class FolderImpl implements Folder {
	final private Map<String, File> files;
	final private Map<String, Folder> folders;
	final private String name;
	final private Path path;

	public FolderImpl(final String path) {
		this.path = Paths.get(path);
		name = this.path.getFileName().toString();
		files = new TreeMap<String, File>();
		folders = new TreeMap<String, Folder>();
		for (java.io.File resource : this.path.toFile().listFiles((current, name) -> true)) {
			if (resource.isFile()) {
				files.put(resource.getName(), new FileImpl(resource.toString()));
			} else if (resource.isDirectory()) {
				folders.put(resource.getName(), new FolderImpl(resource.toString()));
			}
		}
	}

	public Result createFile(final String path) {
		final SplitPath split = new SplitPath(path);
		if (split.isUnrolled()) {
			final Path filePath = this.path.resolve(split.getPath());
			if (files.containsKey(split.getPath())) {
				return new Result(String.format("File (%s) already exists", filePath));
			}
			try {
				if (filePath.toFile().createNewFile()) {
					files.put(split.getPath(), new FileImpl(filePath.toString()));
					return new Result();
				}
			} catch (IOException e) {
			}
			return new Result(String.format("Cannot create file (%s)", filePath));
		}
		if (!folders.containsKey(split.getDir()) && !createFolder(split.getDir()).isOk()) {
			return new Result(String.format("Cannot create folder (%s) along the destination file path", this.path.resolve(split.getDir())));
		}
		return folders.get(split.getDir()).createFile(split.getPath());
	}

	public Result removeFile(final String path) {
		final SplitPath split = new SplitPath(path);
		if (split.isUnrolled()) {
			File file = files.getOrDefault(split.getPath(), null);
			if (file == null) {
				return new Result(String.format("File (%s) to remove does not exist", this.path.resolve(split.getPath())));
			}
			if (!new java.io.File(file.getPath()).delete()) {
				return new Result(String.format("Cannot remove file (%s)", file.getPath()));
			}
			files.remove(file.getFilename());
			return new Result();
		}
		final Folder folder = folders.getOrDefault(split.getDir(), null);
		if (folder == null) {
			return new Result(String.format("Folder (%s) that should contain file to remove does not exist", this.path.resolve(split.getDir())));
		}
		return folder.removeFile(split.getPath());
	}

	public Result createFolder(final String path) {
		final SplitPath split = new SplitPath(path);
		if (split.isUnrolled()) {
			final Path folderPath = this.path.resolve(split.getPath());
			if (folders.containsKey(split.getPath())) {
				return new Result(String.format("Folder (%s) already exist", folderPath));
			}
			if (folderPath.toFile().mkdir()) {
				folders.put(split.getPath(), new FolderImpl(folderPath.toString()));
				return new Result();
			}
			return new Result(String.format("Cannot create folder (%s)", folderPath));
		}
		if (!folders.containsKey(split.getDir()) && !createFolder(split.getDir()).isOk()) {
			return new Result(String.format("Cannot create folder (%s) along the destination folder path", this.path.resolve(split.getDir())));
		}
		return folders.get(split.getDir()).createFolder(split.getPath());
	}

	public Result removeFolder(final String path) {
		final SplitPath split = new SplitPath(path);
		if (split.isUnrolled()) {
			Folder folder = folders.getOrDefault(split.getPath(), null);
			if (folder == null) {
				return new Result(String.format("Folder (%s) to remove does not exist", this.path.resolve(split.getPath())));
			}
			if (!new java.io.File(folder.getPath()).delete()) {
				return new Result(String.format("Cannot remove folder (%s)", folder.getPath()));
			}
			folders.remove(folder.getName());
			return new Result();
		}
		final Folder folder = folders.getOrDefault(split.getDir(), null);
		if (folder == null) {
			return new Result(String.format("Folder (%s) that should contain another folder to remove does not exist", this.path.resolve(split.getDir())));
		}
		return folder.removeFolder(split.getPath());
	}

	public Optional<File> getFile(final String path) {
		final SplitPath split = new SplitPath(path);
		if (split.isUnrolled()) {
			return Optional.ofNullable(files.getOrDefault(split.getPath(), null));
		}
		final Folder folder = folders.getOrDefault(split.getDir(), null);
		if (folder == null) {
			return Optional.empty();
		}
		return folder.getFile(split.getPath());
	}

	public Set<File> getFiles() { return new HashSet<File>(files.values()); }

	public Set<Folder> getFolders() { return new HashSet<Folder>(folders.values()); }

	public String getName() { return name; }

	public String getPath() { return path.toString(); }
}
