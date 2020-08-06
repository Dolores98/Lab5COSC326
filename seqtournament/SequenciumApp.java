package seqtournament;

import sequencium.*;

public class SequenciumApp {
	public static void main(String []args)
	{
		Game game = new Game(new TeamThreeStar(), new RandomPlayer());
		game.makeVerbose();
		game.reportOutcome();
		game.run();
		System.out.println(game.displayBoard());

	}
	
}





























