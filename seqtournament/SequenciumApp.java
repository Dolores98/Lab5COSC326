package seqtournament;

import sequencium.*;

public class SequenciumApp {
	public static void main(String[] args) {

		for (int i = 0; i < 1; i++) {
			Game game = new Game(new RandomPlayer(), new TeamThreeStar(2));
			game.run();
			// System.out.println(game.displayBoard());
		}
	}

}
