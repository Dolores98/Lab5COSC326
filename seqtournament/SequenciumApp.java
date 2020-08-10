package seqtournament;

import sequencium.*;

public class SequenciumApp {
	public static void main(String[] args) {

		for (int i = 0; i < 1; i++) {
			// Game game = new Game(new TeamThreeStar(3), new TeamThreeStar(2));
			// Game game = new Game(new TeamThreeStar(2), new TeamThreeStar(3));
			Game game = new Game(new TeamThreeStar(4), new TeamThreeStar(2));


			game.run();
			System.out.println(game.displayBoard());
		}
	}

}
