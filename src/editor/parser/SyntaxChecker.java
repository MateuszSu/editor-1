package editor.parser;

import java.util.List;

public interface SyntaxChecker {
	public List<ProblemView> check(final String line);
}
