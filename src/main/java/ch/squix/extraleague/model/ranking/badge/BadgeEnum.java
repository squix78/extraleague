package ch.squix.extraleague.model.ranking.badge;

public enum BadgeEnum {
	
	// Find more icon classes on http://fortawesome.github.io/Font-Awesome/icons/
	King(1, BadgeType.Ranking, "fa-trophy", "1st"), 
	Queen(2, BadgeType.Ranking, "fa-female", "2nd"), 
	Pawn(3, BadgeType.Shamelet, "fa-ambulance", "Last"),
	TopShot(4, BadgeType.Silver, "fa-plane", "Highest Average of Goals Per Match (GPM)"),
	Shutout(4, BadgeType.Bronze, "fa-star-o", "5:0"), 
	BackToBackShutout(5, BadgeType.Silver, "fa-star-half-o", "2x5:0"), 
	IAmLegend(6, BadgeType.Silver, "fa-star", "3x5:0"), 
	NoMercy(7, BadgeType.Gold, "fa-fighter-jet", "4x5:0"), 
	NxSlam(8, BadgeType.Bronze, "", "won N games in a row"), 
	Strike(9, BadgeType.Bronze, "fa-bolt", "won all matches in a round"), 
	IceMan(10, BadgeType.Gold, "fa-android", "won all matches 5:4 in a round"),
	BundleOfNerves(10, BadgeType.Shamelet, "fa-leaf", "Highest rate of 4:5 lost matches"),
	SteelRopeNerves(10, BadgeType.Ranking, "fa-wrench", "Highest rate of 5:4 won matches"),
	BattleAxe(11, BadgeType.Silver, "fa-gavel", "highest offensive rate"),
	TheWall(11, BadgeType.Silver, "fa-shield", "highest defensive rate"),
	ZenMonk(11, BadgeType.Silver, "fa-circle-o", "most balanced skills"),
	GrayEminence(11, BadgeType.Silver, "fa-users", "played with most different players"),
	LoneWolf(11, BadgeType.Shamelet, "fa-user", "played with fewest players"),
	PurpleHeart(11, BadgeType.Honorary, "fa-heart", "Contributed code or issues to Extraleague"), 
	SilverStar(12, BadgeType.Honorary, "fa-asterisk", "Contributed extraordinary amounts of ideas, code or blood to Extraleague"),
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
