package seqtournament;

import sequencium.*;
import java.util.*;

public class TeamThreeStar implements Player {

	private boolean initalLoop = true;
	private int count = 0;

	public String getName() {
		return "Team Three Star";
	}

	public int[] initialMove(int[][] board) {
		for (int[] is : board) {
			for (int iss :is){
				System.out.print(iss);
			}
			System.out.println();
		}

		if (count == 0) {
			int initialMove[] = { 1, 1, 2 };
			count++;
			return initialMove;
		} else {
			int initialMove[] = { 2, 2, 3 };
			initalLoop = false;
			return initialMove;
		}
	}

	public int[] makeMove(int[][] board) {
		if (initalLoop) {
			int[] initialMoves = new int[3];
			initialMoves = initialMove(board);
			return initialMoves;
		} else {
			int[] theMove = new int[3];

			ArrayList<int[]> possibleMoves = new ArrayList<int[]>();

			possibleMoves = getPossibleMoves(board);
			theMove = heuristic(possibleMoves);
			return theMove;
		}
	}

	private static ArrayList<int[]> getPossibleMoves(int[][] board)
	// rowIndex, colIndex, Highest Possible Value, Highest Enemy Value, AVG friendly
	// value, AVG enemy value, number of enemy tiles, number of friendly tiles
	// 0, 1, 2, 3, 4, 5, 6, 7,
	{
		ArrayList<int[]> possibleMoves = new ArrayList<int[]>();
		ArrayList<int[]> neighbours = new ArrayList<int[]>();

		int boardRows = board.length;
		int boardCols = board[0].length;

		for (int i = 0; i < boardRows; i++) {
			for (int j = 0; j < boardCols; j++) {
				if (board[i][j] == 0) {
					int highestValue = 0;
					int highestEnemy = 0;
					int avgFriendly = 0;
					int avgEnemy = 0;
					int friendlyCount = 0;
					int enemyCount = 0;

					neighbours = Utilities.neighbours(i, j, boardRows, boardCols);
					for (int k = 0; k < neighbours.size(); k++) {
						int[] coordinate = neighbours.get(k);

						int value = board[coordinate[0]][coordinate[1]];

						if (value > 0) {
							friendlyCount += 1;
							avgFriendly += value;
							if (value > highestValue) {
								highestValue = value;
							}
						} else if (value < 0) {
							enemyCount += 1;
							avgEnemy += value;
							if (value < highestEnemy) {
								highestEnemy = value;
							}
						}
					}

					if (highestValue > 0) {

						avgFriendly = avgFriendly / friendlyCount;
						if (enemyCount > 0) {
							avgEnemy = avgEnemy / enemyCount;
						}
						int[] move = { i, j, highestValue, highestEnemy, avgFriendly, avgEnemy, friendlyCount,
								enemyCount };
						possibleMoves.add(move);
					}
				}
			}
		}
		return possibleMoves;
	}

	private static int[] heuristic(ArrayList<int[]> possibleMoves) {
		int[] bestMove = new int[8];
		int bestMoveScore = 0;

		for (int i = 0; i < possibleMoves.size(); i++) {
			int[] move = possibleMoves.get(i);
			int moveScore = move[3] + move[6];
			if (moveScore >= bestMoveScore) {
				bestMove = move;
				bestMoveScore = moveScore;
			}
		}
		int[] move = new int[3];
		for (int i = 0; i < 3; i++) {
			move[i] = bestMove[i];
		}
		move[2] += 1;
		return move;
	}
}
