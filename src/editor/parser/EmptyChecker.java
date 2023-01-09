package editor.parser;

import java.util.List;
import java.util.LinkedList;

class EmptyChecker implements Checker {
	private static final List<ProblemView> empty = new LinkedList<ProblemView>();

	public List<ProblemView> check(final String line) { return empty; }
}
