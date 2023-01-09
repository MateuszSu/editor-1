package editor.parser;

import java.util.List;

public class SyntaxCheckerWrapper implements SyntaxChecker, SyntaxCheckerSetter {
	private Checker checker = null;

	public SyntaxCheckerWrapper() {};

	public void enable(final String name) { checker = SyntaxCheckers.getSytnaxChecker(name); }

	public void disable() { checker = SyntaxCheckers.getEmptyChecker(); }

	public List<ProblemView> check(final String line) { return checker.check(line); }
}
