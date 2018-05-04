package es.ucm.fdi.control;

public enum Command {
	
	Exit("Exit"),
	Save("Save"),
	Reset("Reset"),
	Open("Open"),
	Events("Events"),
	Clear("Clear"),
	Report("Report"),
	Save_Report("Save_Report"),
	Delete_Report("Delete_Report"),
	Play("Play"),
	Filter("Filter Reports")
	;
	private String text;
	Command(String text) {
		this.text = text;
	}
	@Override
	public String toString() {
		return text;
	}
}
