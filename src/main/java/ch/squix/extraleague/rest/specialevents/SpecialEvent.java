package ch.squix.extraleague.rest.specialevents;

public enum SpecialEvent {
	
	GoalOfTheDay(EventGroup.TrickShot, "A undescribable wonderful goal", "fa fa-graduation-cap"),
	TrickShotAir(EventGroup.TrickShot, "A wonderful goal with air time", "fa fa-paper-plane"),
	DirectSelfie(EventGroup.Selfie, "Direct score to own goal", "fa fa-dot-circle-o"),
	FullLengthSelfie(EventGroup.Selfie, "Score to own goal via opposite baseline", "fa fa-reply"),
	SelfieAir(EventGroup.Selfie, "Airborne score to own goal", "fa fa-paper-plane"),
	Skirty(EventGroup.Dirty, "Score due to uncontrolled rolling of the players", "fa fa-recycle");
	
	private EventGroup eventGroup;
	private String description;
	private String iconClass;
	
	SpecialEvent(EventGroup eventGroup, String description, String iconClass) {
		this.eventGroup = eventGroup;
		this.description = description;
		this.iconClass = iconClass;
	}
	
	public EventGroup getEventGroup() {
		return eventGroup;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getIconClass() {
		return iconClass;
	}

}
