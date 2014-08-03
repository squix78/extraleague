package ch.squix.extraleague.rest.games.mode;

public enum GameModeEnum {
	
	FourMatchesToFive("4 matches to 5", "Four matches are played in fix order according ELO ranking"),
	ThreeMatchesToFive("3 matches to 5", "Three matches are played in random start formation"),
	OneMatchToSeven("1 match to 7", "One match is played to 7 goals following initial player list");
	
	private String label;
	private String description;

	GameModeEnum(String label, String description) {
		this.label = label;
		this.description = description;
	}
	
	public String getLabel() {
		return label;
	}
	
	public String getDescription() {
		return description;
	}
	
	

}
