package editor.filesystem;

class SplitPath {
	private final String dir;
	private final String path;

	SplitPath(final String path) {
		final String[] split = path.split("/", 2);
		if (split.length == 2) {
			dir = split[0];
			this.path = split[1];
		} else {
			dir = null;
			this.path = split[0];
		}
	}

	public boolean isUnrolled() { return dir == null; }

	public String getPath() { return path; }

	public String getDir() { return dir; }
}
