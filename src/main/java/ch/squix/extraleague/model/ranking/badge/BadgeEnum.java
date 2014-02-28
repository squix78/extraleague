package ch.squix.extraleague.model.ranking.badge;

public enum BadgeEnum {
	
	King(1, BadgeType.Achievement, "fa-trophy", "1st"), 
	Queen(2, BadgeType.Achievement, "fa-female", "2nd"), 
	Pawn(3, BadgeType.Shamelet, "fa-ambulance", "Last"), 
	Shutout(4, BadgeType.Achievement, "fa-star-o", "5:0"), 
	BackToBackShutout(5, BadgeType.Achievement, "fa-star-half-o", "2x5:0"), 
	IAmLegend(6, BadgeType.Achievement, "fa-star", "3x5:0"), 
	NoMercy(7, BadgeType.Achievement, "fa-fighter-jet", "4x5:0"), 
	NxSlam(8, BadgeType.Achievement, "", "won N games in a row"), 
	Strike(9, BadgeType.Achievement, "fa-bolt", "won all matches in a round"), 
	IceMan(10, BadgeType.Achievement, "fa-android", "won all matches 5:4 in a round"), 
	PurpleHeart(11, BadgeType.Achievement, "fa-heart", "Contributed code or issues to Extraleague"), 
	SilverStar(12, BadgeType.Achievement, "fa-asterisk", "Contributed extraordinary amounts of ideas, code or blood to Extraleague"),
	Incestuous(13, BadgeType.Shamelet, "fa-stethoscope", "Played last 2 rounds with the same people");
	
	private BadgeType type;
	private String faClass;
	private Integer index;
	private String description;

	BadgeEnum(Integer index, BadgeType type, String faClass, String description) {
		this.index = index;
		this.type = type;
		this.faClass = faClass;
		this.description = description;
	}
	
	public BadgeType getBadgeType() {
		return type;
	}
	
	public String getFaClass() {
		return faClass;
	}
	
	public Integer getIndex() {
		return index;
	}
	
	public String getDescription() {
		return description;
	}

}
