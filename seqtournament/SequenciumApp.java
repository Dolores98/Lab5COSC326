package seqtournament;

import sequencium.*;

public class SequenciumApp {
	public static void main(String[] args) {

		for (int i = 0; i < 10; i++) {
			Game game = new Game(new TeamThreeStar(3), new RandomPlayer());
			game.run();
			// System.out.println(game.displayBoard());
		}
	}

}
