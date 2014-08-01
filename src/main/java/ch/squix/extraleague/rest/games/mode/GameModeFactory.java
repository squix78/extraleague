package ch.squix.extraleague.rest.games.mode;


public class GameModeFactory {
	
	public static GameMode createGameMode(GameModeEnum gameModeEnum) {
		
		switch(gameModeEnum) {
		case FourMatchesToFive:
				return new FourMatchesToFiveMode();
		case OneMatchToSeven:
				return new OneMatchToSevenMode();
		}
		return new FourMatchesToFiveMode();
	}

}
