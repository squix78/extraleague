package ch.squix.extraleague.rest.specialevents;

public enum EventGroup {
	
	Selfie("btn-danger"), TrickShot("btn-primary"), Dirty("btn-danger");
	
	private String buttonClass;

	EventGroup(String buttonClass) {
		this.buttonClass = buttonClass;
	}
	
	public String getButtonClass() {
		return buttonClass;
	}

}
