package ch.squix.extraleague.rest.games.mode;

public enum GameModeEnum {
	
	FourMatchesToFive(new FourMatchesToFiveMode(), "4 matches to 5", "Four matches are played in fix order according ELO ranking"),
	ThreeMatchesToFive(new ThreeMatchesToFiveMode(), "3 matches to 5", "Three matches are played in random start formation"),
	OneMatchToSeven(new OneMatchToSevenMode(), "1 match to 7", "One match is played to 7 goals following initial player list");
	
	private GameMode mode;
	private String label;
	private String description;

	GameModeEnum(GameMode mode, String label, String description) {
		this.mode = mode;
		this.label = label;
		this.description = description;
	}
	
	public GameMode getGameMode() {
		return mode;
	}
	
	public String getLabel() {
		return label;
	}
	
	public String getDescription() {
		return description;
	}
	
	

}
