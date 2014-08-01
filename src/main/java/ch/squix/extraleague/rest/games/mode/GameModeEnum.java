package ch.squix.extraleague.rest.games.mode;

public enum GameModeEnum {
	
	FourMatchesToFive(new FourMatchesToFiveMode()),
	OneMatchToSeven(new OneMatchToSevenMode());
	
	private GameMode mode;

	GameModeEnum(GameMode mode) {
		this.mode = mode;
	}
	
	public GameMode getGameMode() {
		return mode;
	}
	
	

}
