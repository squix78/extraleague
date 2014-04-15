package ch.squix.extraleague.model.ranking.badge;

public enum BadgeEnum {
	
	// Find more icon classes on http://fortawesome.github.io/Font-Awesome/icons/
	King(1, BadgeType.Ranking, "fa-trophy", null, "1st"), 
	Queen(2, BadgeType.Ranking, "fa-female", null, "2nd"), 
	Pawn(3, BadgeType.Shamelet, "fa-ambulance", null, "Last"),
	TopShot(4, BadgeType.Silver, "fa-plane", null, "Highest Average of Goals Per Match (GPM)"),
	Shutout(4, BadgeType.Bronze, "fa-star-o", " Shutout ", "5:0"), 
	BackToBackShutout(5, BadgeType.Silver, "fa-star-half-o", null, "2x5:0"), 
	IAmLegend(6, BadgeType.Silver, "fa-star", null, "3x5:0"), 
	NoMercy(7, BadgeType.Gold, "fa-fighter-jet", null, "4x5:0"), 
	BruceWillis(8, BadgeType.Silver, "fa-mail-reply", null, "Came back and won the game after 0:4"),
	ChuckNorris(8, BadgeType.Gold, "fa-mail-reply-all", null, "Came back and won the game after 0:4, twice in a game! Only Chuck can do it!"),
	NxSlam(8, BadgeType.Bronze, "", "[0-9]{1,2}xSlam", "won N games in a row"), 
	Strike(9, BadgeType.Bronze, "fa-bolt", null, "won all matches in a round"), 
	IceMan(10, BadgeType.Gold, "fa-android", null, "won all matches 5:4 in a round"),
	JohnWayne(10, BadgeType.Gold, "fa-magic", null, "Most goals in a game (4 matches)"),
	BundleOfNerves(10, BadgeType.Shamelet, "fa-leaf", null, "Highest rate of 4:5 lost matches"),
	SteelRopeNerves(10, BadgeType.Ranking, "fa-wrench", null, "Highest rate of 5:4 won matches"),
	BattleAxe(11, BadgeType.Silver, "fa-gavel", null, "highest offensive rate"),
	TheWall(11, BadgeType.Silver, "fa-shield", null, "highest defensive rate"),
	ZenMonk(11, BadgeType.Silver, "fa-circle-o", null, "most balanced skills"),
	GrayEminence(11, BadgeType.Silver, "fa-users", null, "played with most different players"),
	LoneWolf(11, BadgeType.Shamelet, "fa-user", null, "played with fewest players"),
	PurpleHeart(11, BadgeType.Honorary, "fa-heart", null, "Contributed code or issues to Extraleague"), 
	SilverStar(12, BadgeType.Honorary, "fa-asterisk", null, "Contributed extraordinary amounts of ideas, code or blood to Extraleague"),
	Incestuous(13, BadgeType.Shamelet, "fa-stethoscope", null, "Played last 2 rounds with the same people");
	
	private BadgeType type;
	private String faClass;
	private Integer index;
	private String description;
	private String jsRegex;

	BadgeEnum(Integer index, BadgeType type, String faClass, String jsRegex, String description) {
		this.index = index;
		this.type = type;
		this.faClass = faClass;
		this.jsRegex = jsRegex;
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
	
	public String getJsRegex() {
		if (jsRegex != null) {
			return jsRegex;
		}
		return name();
	}

}
