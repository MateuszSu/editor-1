package editor.filesystem;

import java.nio.file.Path;
import java.nio.file.Paths;

public class FileImpl implements File {
	private final String filename;
	private final Path path;

	FileImpl(final String path) {
		this.path = Paths.get(path);
		filename = this.path.getFileName().toString();
	}

	public String getFilename() { return filename; }

	public String getPath() { return path.toString(); }
}