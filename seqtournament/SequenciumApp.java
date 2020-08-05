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
	
	
	public int[] makeMove(int[][] board) 
	{
		int[] theMove = new int[3];
		
		ArrayList<int[]> possibleMoves = new ArrayList<int[]>();
		possibleMoves = getPossibleMoves(board);
		return theMove;
	}
	
	public static ArrayList<int[]> getPossibleMoves(int[][] board) // rowIndex, colIndex, Highest Possible Value, Highest Enemy Value, AVG friendly value, AVG enemy value, number of enemy tiles, number of friendly tiles
	{															   // 0,        1,        2,                      3,                   4,                  5,               6,                     7,                          
		Utilities utils = new Utilities();
		ArrayList<int[]> possibleMoves = new ArrayList<int[]>();
		ArrayList<int[]> neighbours = new ArrayList<int[]>();
		
		int boardRows = board.length;
		int boardCols = board[0].length;
		
		for(int i = 0; i < boardRows; i++)
		{
			for(int j = 0; j < boardCols; j++)
			{
				if(board[i][j] == 0)
				{
					int highestValue = 0;
					int highestEnemy = 0;
					int avgFriendly = 0;
					int avgEnemy = 0;
					int friendlyCount = 0;
					int enemyCount = 0;
					
					neighbours = utils.neighbours(i, j, boardRows, boardCols);
					for(int k = 0; k < neighbours.size(); k ++)
					{
						int[] coordinate = neighbours.get(k);
						
						int value = board[coordinate[0]][coordinate[1]];

                                                // This is a comment - John
                                                
						if(value > 0)
						{
							friendlyCount += 1;
							avgFriendly += value;
							if(value > highestValue) {highestValue = value;}
						}
						else if(value < 0)
						{
							enemyCount += 1;
							avgEnemy += value;
							if(value < highestEnemy) {highestEnemy = value;}
						}
					}
					
					if(highestValue > 0)
					{
						avgFriendly = avgFriendly/friendlyCount;
						avgEnemy = avgEnemy/enemyCount;
						int[] move = {i, j, highestValue, highestEnemy, avgFriendly, avgEnemy, friendlyCount, enemyCount};
						possibleMoves.add(move);
					}
				}
			}
		}
		return possibleMoves;
	}
	
	public static int[] heuristic(ArrayList<int[]> possibleMoves)
	{
		return null;
	}
}






























