package editor.parser;

import java.util.List;

interface Checker {
	public List<ProblemView> check(final String line);
}
