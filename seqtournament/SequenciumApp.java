package seqtournament;

import sequencium.*;

public class SequenciumApp {
	public static void main(String[] args) {

		for (int i = 0; i < 20; i++) {
			Game game = new Game(new TeamThreeStar(4), new RandomPlayer());
			game.run();
			// System.out.println(game.displayBoard());
		}
	}

}
