package seqtournament;

import sequencium.*;

public class SequenciumApp {
	public static void main(String []args)
	{
		Game game = new Game(new RandomPlayer(), new TeamThreeStar());
		game.run();
	}
	
}





























