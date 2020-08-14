package seqtournament;

import sequencium.Game;
import sequencium.RandomPlayer;

public class SequenciumApp {
	public static void main(String[] args) {

		for (int i = 0; i < 1; i++) {
			// Game game = new Game(new TeamThreeStar(3), new TeamThreeStar(2));
			// Game game = new Game(new TeamThreeStar(2), new TeamThreeStar(3));
			Game game = new Game(new TeamThreeStar(), new RandomPlayer(), 10, 10);

			// game.makeVerbose();
			game.run();
			// System.out.println(game.getLog());
		}
	}

}
