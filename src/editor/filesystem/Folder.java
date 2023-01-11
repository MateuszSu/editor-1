package editor.filesystem;

import java.util.Optional;
import java.util.Set;

import editor.Result;

public interface Folder {
	public Result createFile(final String path);
	public Result removeFile(final String path);

	public Result createFolder(final String path);
	public Result removeFolder(final String path);

	public Optional<File> getFile(final String path);

	public Set<File> getFiles();
	public Set<Folder> getFolders();

	public String getName();
	public String getPath();
}
