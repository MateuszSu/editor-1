package editor.parser;

public interface ProblemView {
	public String getCorrection();
	public String getDescription();
	public int getIndexBegin();
	public int getIndexEnd();
}
