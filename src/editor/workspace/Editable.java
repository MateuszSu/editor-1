package editor.workspace;

import java.util.List;

import editor.parser.ProblemView;

public interface Editable {
	public void addLine(int idx);
	public void removeLine(int idx);
	public String getLine(int idx);
	public List<ProblemView> modifyLine(int idx, final String line);
	public int getLinesCount();
}
