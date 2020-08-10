package seqtournament;

import sequencium.*;
import java.util.*;

public class TeamThreeStar implements Player {

	private boolean initialLoop = true;
	private boolean player1 = true;
	private int count = 0;
	private int heuristicSwitch;

	public TeamThreeStar() {
		this.heuristicSwitch = 0;
	}

	public TeamThreeStar(int heuristicSwitch) {
		this.heuristicSwitch = heuristicSwitch;
	}

	public String getName() {
		return "Team Three Star";
	}

	public int[] initialMove(int[][] board) {
		if (board[0][0] == 1) {
			player1 = true;
			if (count == 0) {
				int initialMove[] = { 1, 1, 2 };
				count++;
				return initialMove;
			} else {
				int initialMove[] = { 2, 2, 3 };
				initialLoop = false;
				return initialMove;
			}
		} else {
			player1 = false;
			if (count == 0) {
				int initialMove[] = { 4, 4, 2 };
				count++;
				return initialMove;
			} else {
				int initialMove[] = { 3, 3, 3 };
				initialLoop = false;
				return initialMove;
			}
		}
	}

	public boolean diagonalCheck(int[][] board) {
		if (board[3][3] == 0 && player1) {

			return true;
		} else if (board[2][2] == 0 && !player1) {

			return true;
		}
		return false;
	}

	public void printBoardState(int[][] board) {
		for (int[] is : board) {
			for (int iss : is) {
				System.out.print(iss);
			}
			System.out.println();
		}
	}

	public int[] makeMove(int[][] board) {

		// printBoardState(board);
		// System.out.println();

		if (initialLoop) {
			int[] initialMoves = new int[3];
			initialMoves = initialMove(board);
			return initialMoves;
		} else if (diagonalCheck(board)) {

			if (player1) {
				int nextMove[] = { 3, 3, 4 };
				return nextMove;
			} else {
				int nextMove[] = { 2, 2, 4 };
				return nextMove;
			}
		} else {
			int[] theMove = new int[3];

			ArrayList<int[]> possibleMoves = new ArrayList<int[]>();

			possibleMoves = getPossibleMoves(board);
			switch (heuristicSwitch) {
				case 2:
					theMove = heuristic2(possibleMoves);
					break;
				case 3:
					theMove = heuristic3(possibleMoves);
					break;
				case 4:
					theMove = heuristic4(possibleMoves);
					break;
				case 5:
					theMove = heuristic5(possibleMoves);
					break;
				default:
					theMove = heuristic(possibleMoves);
					break;
			}

			return theMove;
		}
	}

	private static ArrayList<int[]> getPossibleMoves(int[][] board)
	// 0 rowIndex, 1 colIndex, 2 Highest Possible Value, 3 Highest Enemy Value,
	// 4 AVG friendly value, 5 AVG enemy value, 6 number of enemy tiles,
	// 7 number of friendly tiles, 8 connections(how many connections move has of
	// ally), 9 enemy connections

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
					int allyConnections = 0;
					int enemyConnections = 0;

					neighbours = Utilities.neighbours(i, j, boardRows, boardCols);
					for (int k = 0; k < neighbours.size(); k++) {
						int[] coordinate = neighbours.get(k);

						int value = board[coordinate[0]][coordinate[1]];

						if (value > 0) {
							ArrayList<int[]> allyNeighbours = new ArrayList<int[]>(
									Utilities.neighbours(coordinate[0], coordinate[1], boardRows, boardCols));
							for (int l = 0; l < allyNeighbours.size(); l++) {
								int[] coordinate2 = allyNeighbours.get(l);
								int value2 = board[coordinate2[0]][coordinate2[1]];

								if (value2 > 0) {
									allyConnections += 1;
								}
							}
							friendlyCount += 1;
							avgFriendly += value;
							if (value > highestValue) {
								highestValue = value;
							}
						} else if (value < 0) {
							ArrayList<int[]> enemyNeighbours = new ArrayList<int[]>(
									Utilities.neighbours(coordinate[0], coordinate[1], boardRows, boardCols));
							for (int l = 0; l < enemyNeighbours.size(); l++) {
								int[] coordinate2 = enemyNeighbours.get(l);
								int value2 = board[coordinate2[0]][coordinate2[1]];

								if (value2 < 0) {
									enemyConnections += 1;
								}
							}

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
								enemyCount, allyConnections, enemyConnections };
						possibleMoves.add(move);
					}
				}
			}
		}
		return possibleMoves;
	}

	private static int[] heuristic4(ArrayList<int[]> possibleMoves) {
		int[] bestMove = new int[9];
		int bestMoveScore = 0;
		bestMove = possibleMoves.get(0);
		for (int i = 0; i < possibleMoves.size(); i++) {
			int[] move = possibleMoves.get(i);
			int moveScore = move[3] + move[6] - move[2];
			if (move[6] == 0) {
				moveScore = -move[7];
			}

			if (moveScore <= bestMoveScore && move[6] < 6) {
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

	private static int[] heuristic3(ArrayList<int[]> possibleMoves) {
		int[] bestMove = new int[9];
		int bestMoveScore = 0;
		bestMove = possibleMoves.get(0);

		for (int i = 0; i < possibleMoves.size(); i++) {
			int[] move = possibleMoves.get(i);
			int moveScore = move[3] + move[6] - move[7];
			if (move[6] == 0) {
				moveScore = -move[7];
			}

			if (moveScore <= bestMoveScore && move[6] < 6) {
				bestMove = move;
				bestMoveScore = moveScore;
			} else {
			}
		}
		int[] moved = new int[3];
		for (int i = 0; i < 3; i++) {
			moved[i] = bestMove[i];
		}
		moved[2] += 1;
		return moved;
	}

	private static int[] heuristic2(ArrayList<int[]> possibleMoves) {
		int[] bestMove = new int[9];
		int bestMoveScore = 0;
		bestMove = possibleMoves.get(0);
		for (int i = 0; i < possibleMoves.size(); i++) {
			int[] move = possibleMoves.get(i);
			int moveScore = move[3] * 100 + move[6] - move[2] * 2 + (move[9] - move[8] * 2) / 5;
			if (moveScore <= bestMoveScore) {
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

	private static int[] heuristic5(ArrayList<int[]> possibleMoves) {
		int[] bestMove = new int[9];
		int bestMoveScore = 0;

		bestMove = possibleMoves.get(0);
		for (int i = 0; i < possibleMoves.size(); i++) {
			int[] move = possibleMoves.get(i);
			int moveScore = move[3] * 5 + move[6] - move[2] + (move[9] - move[8]) / 2;
			if (moveScore <= bestMoveScore) {
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

	private static int[] heuristic(ArrayList<int[]> possibleMoves) {
		int[] bestMove = new int[9];
		int bestMoveScore = 0;

		for (int i = 0; i < possibleMoves.size(); i++) {
			int[] move = possibleMoves.get(i);
			int moveScore = move[3] + move[6];
			if (moveScore <= bestMoveScore) {
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
