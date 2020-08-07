package seqtournament;

import sequencium.*;

public class SequenciumApp {
	public static void main(String[] args) {

		// System.out.println(game.displayBoard());

		for (int i = 0; i < 1; i++) {
			Game game = new Game(new TeamThreeStar(3), new TeamThreeStar(2));
			game.run();
			System.out.println(game.displayBoard());
			// game.reportOutcome();
			//System.out.println(game.getLog());
		}
	}

}
