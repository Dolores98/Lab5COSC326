package seqtournament;

import sequencium.*;
import java.util.*;

public class SequenciumApp implements Player {
	public static void main(String []args)
	{
		Game newGame = new Game(new RandomPlayer(), new SequenciumApp());
	}
	public String getName()
	{
		return "Team Three Star";
	}
	
}





























