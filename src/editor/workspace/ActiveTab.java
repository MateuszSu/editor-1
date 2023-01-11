package editor.workspace;

import java.util.List;

import editor.Result;
import editor.parser.ProblemView;
import editor.parser.SyntaxChecker;

class ActiveTab implements Editable {
	private final SyntaxChecker syntaxChecker;
	private Tab tab;
	public final static Editable emptyActive = new DummyEditable();

	ActiveTab(final SyntaxChecker syntaxChecker) { this.syntaxChecker = syntaxChecker; }

	public void addLine(int idx) {
		tab.getLines().add(idx, "");
		tab.setUnsaved();
	}

	public void removeLine(int idx) {
		tab.getLines().remove(idx);
		tab.setUnsaved();
	}

	public String getLine(int idx) { return tab.getLines().get(idx); }

	public List<ProblemView> modifyLine(int idx, String line) {
		tab.getLines().set(idx, line);
		tab.setUnsaved();
		return syntaxChecker.check(line);
	}

	public int getLinesCount() { return tab.getLines().size(); }

	boolean isActive(Tab other) { return tab.equals(other); }

	void set(Tab tab) { this.tab = tab; }

	void unset() { this.tab = null; }

	Result save() { return tab.save(); }

	boolean isSet() { return this.tab != null; }
}
