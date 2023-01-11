package editor.workspace;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;
import java.util.LinkedList;

import editor.Result;
import editor.filesystem.File;

class Tab {
	private File file;
	private boolean fileTouched = false;
	private boolean fileLoaded = false;
	protected List<String> lines = new LinkedList<String>();

	Tab(File file) { this.file = file; }

	Result load() {
		if (fileLoaded) {
			return new Result("File already loaded");
		}
		lines = new LinkedList<String>();
		try (Stream<String> stream  = Files.lines(Paths.get(file.getPath()))) {
			stream.forEach(s -> lines.add(s));
		} catch (IOException e) {
			return new Result("Cannot load file");
		}
		fileLoaded = true;
		fileTouched = false;
		return new Result();
	}

	private Result dump() {
		try {
			FileWriter writer = new FileWriter(file.getPath());
			for (final String line : lines) {
				writer.write(line + System.lineSeparator());
			}
			writer.close();
		} catch (IOException e) {
			return new Result(String.format("Cannot dump file (%s)", file.getPath()));
		}
		fileTouched = false;
		return new Result();
	}

	void setUnsaved() { fileTouched = true; }

	boolean isUnsaved() { return fileTouched; }

	Result save() {
		if (isUnsaved()) {
			return dump();
		}
		return new Result();
	}

	List<String> getLines() { return lines; }
}
