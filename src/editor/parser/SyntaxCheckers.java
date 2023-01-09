package editor.parser;

class SyntaxCheckers {
	private static final Checker emptyChecker = new EmptyChecker();

	static Checker getSytnaxChecker(final String name) {
		switch (name) {
			case "":
			default:
				return emptyChecker;
		}
	}

	static Checker getEmptyChecker() { return emptyChecker; }
}
