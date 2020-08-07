package seqtournament;

import sequencium.*;

public class SequenciumApp {
	public static void main(String[] args) {

		// System.out.println(game.displayBoard());

		for (int i = 0; i < 10; i++) {
			Game game = new Game(new TeamThreeStar(3), new TeamThreeStar());
			game.run();
			//game.reportOutcome();
			//System.out.println(game.getLog());
		}
	}

}
