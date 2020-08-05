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
	
	
	public int[] makeMove(int[][] board) 
	{
		int[] theMove = new int[3];
		
		ArrayList<int[]> possibleMoves = new ArrayList<int[]>();
		possibleMoves = getPossibleMoves(board);
		return theMove;
	}
	
	public static ArrayList<int[]> getPossibleMoves(int[][] board)
	{
		ArrayList<int[]> possibleMoves = new ArrayList<int[]>();
		Utilities utils = new Utilities();
		
		
		 
		return possibleMoves;
	}
	
	public static int[] heuristic(ArrayList<int[]> possibleMoves)
	{
		
	}
}






























