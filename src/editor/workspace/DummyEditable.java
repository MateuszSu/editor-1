package editor.workspace;

import java.util.ArrayList;
import java.util.List;

import editor.parser.ProblemView;

class DummyEditable implements Editable {
	private final static List<ProblemView> emptyList = new ArrayList<ProblemView>();

	public void addLine(int idx) {}
	public void removeLine(int idx) {}
	public String getLine(int idx) { return ""; }
	public List<ProblemView> modifyLine(int idx, final String line) { return emptyList; }
	public int getLinesCount() { return -1; }
}
