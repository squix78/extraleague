package ch.squix.extraleague.model.ranking.badge;

import java.util.HashMap;
import java.util.Map;

public enum BadgeEnum {
	
	// Find more icon classes on http://fortawesome.github.io/Font-Awesome/icons/
	Emperor(1, 0, BadgeType.Honorary, "fa-sun-o", null, "Best player by the end of last week"), 
	King(1, 15, BadgeType.Ranking, "fa-trophy", null, "1st"), 
	Queen(2, 10, BadgeType.Ranking, "fa-female", null, "2nd"), 
	Pawn(3, -5, BadgeType.Shamelet, "fa-ambulance", null, "Last"),
	TopShot(4, 10, BadgeType.Silver, "fa-plane", null, "Highest Average of Goals Per Match (GPM)"),
	Shutout(4, 3, BadgeType.Bronze, "fa-star-o", " Shutout ", "5:0"), 
	BackToBackShutout(5, 5, BadgeType.Silver, "fa-star-half-o", null, "2x5:0"), 
	IAmLegend(6, 10, BadgeType.Silver, "fa-star", null, "3x5:0"), 
	NoMercy(7, 20, BadgeType.Gold, "fa-fighter-jet", null, "4x5:0"), 
	BruceWillis(8, 5, BadgeType.Silver, "fa-mail-reply", null, "Came back and won the game after 0:4"),
	ChuckNorris(8, 10, BadgeType.Gold, "fa-mail-reply-all", null, "Came back and won the game after 0:4, twice in a game! Only Chuck can do it!"),
	PL(8, -5, BadgeType.Shamelet, "fa-phone", null, "Project Leader: Won a match without scoring a goal while being in offensive position"),
	TC(8, 5, BadgeType.Bronze, "fa-futbol-o", null, "Technical Coordinator: Won a match by scoring all the goals from defensive position"),
	x5Slam(8, 5, BadgeType.Bronze, "", null, "won 5-9 games in a row"), 
	x10Slam(9, 10, BadgeType.Silver, "", null, "won 10-14 games in a row"), 
	x15Slam(10, 15, BadgeType.Gold, "", null, "won 15-19 games in a row"), 
	x20Slam(11, 20, BadgeType.Gold, "", null, "won 20 or more games in a row"), 
	Strike(12, 2, BadgeType.Bronze, "fa-bolt", null, "won all matches in a round"), 
	IceMan(10, 5, BadgeType.Gold, "fa-android", null, "won all matches 5:4 in a round"),
	JohnWayne(10, 10, BadgeType.Gold, "fa-magic", null, "Most goals in a game (4 matches)"),
	BundleOfNerves(10, -3, BadgeType.Shamelet, "fa-leaf", null, "Highest rate of 4:5 lost matches"),
	SteelRopeNerves(10, 5, BadgeType.Ranking, "fa-wrench", null, "Highest rate of 5:4 won matches"),
	BattleAxe(11, 5, BadgeType.Silver, "fa-gavel", null, "highest offensive rate"),
	TheWall(11, 5, BadgeType.Silver, "fa-shield", null, "highest defensive rate"),
	ZenMonk(11, 5, BadgeType.Silver, "fa-circle-o", null, "most balanced skills"),
	GrayEminence(11, 5, BadgeType.Silver, "fa-users", null, "played with most different players"),
	LoneWolf(11, -5, BadgeType.Shamelet, "fa-user", null, "played with fewest players"),
	PurpleHeart(11, 3, BadgeType.Honorary, "fa-heart", null, "Contributed code or issues to Extraleague"), 
	SilverStar(12, 5, BadgeType.Honorary, "fa-asterisk", null, "Contributed extraordinary amounts of ideas, code or blood to Extraleague"),
	Incestuous(13, -5, BadgeType.Shamelet, "fa-stethoscope", null, "Played last 2 rounds with the same people"),
	Rookie(20, 0, BadgeType.Honorary, "fa-tag", null, "Canon Foder"),
	Private(20, 0, BadgeType.Honorary, "fa-angle-up", null, "Proofed"),
	Corporal(21, 0, BadgeType.Honorary, "fa-angle-double-up", null, "Seasoned"),
	Seargant(22, 0, BadgeType.Honorary, "fa-chevron-up", null, "Experienced"),
	FirstLeutenant(23, 0, BadgeType.Honorary, "fa-minus", null, "Leader"),
	Captain(24, 0, BadgeType.Honorary, "fa-pause", null, "Field Proofed Leader");
	
	private BadgeType type;
	private String faClass;
	private Integer index;
	private String description;
	private String jsRegex;
	private Integer achievementPoints;
	private static Map<String, BadgeEnum> badgeMap = new HashMap<>();
	
	static {
		for (BadgeEnum badge : BadgeEnum.values()) {
			badgeMap.put(badge.name(), badge);
		}
	}

	BadgeEnum(Integer index, Integer achievementPoints, BadgeType type, String faClass, String jsRegex, String description) {
		this.index = index;
		this.achievementPoints = achievementPoints;
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
	
	public Integer getAchievementPoints() {
		return achievementPoints;
	}
	
	public static BadgeEnum getBadgeByName(String badgeName) {
		return badgeMap.get(badgeName);
	}

}
