package editor.parser;

class Problem implements ProblemView {
	private String correction = null;
	private String description = null;
	private int idxBegin;
	private int idxEnd;

	Problem(int idxBegin, int idxEnd) {
		this.idxBegin = idxBegin;
		this.idxEnd = idxEnd;
	}

	public String getCorrection() { return correction; }

	void setCorrection(final String correction) { this.correction = correction; }

	public String getDescription() { return description; }

	void setDescription(final String description) { this.description = description; }

	public int getIndexBegin() { return idxBegin; }

	public int getIndexEnd() { return idxEnd; }
}
