package seqtournament;

import java.util.ArrayList;

import sequencium.Player;
import sequencium.Utilities;

/*
 * A player for Sequencium, designed to win.
 * 
 * @author Elbert Alcantara, Chris Groenewegen, John KJ Kim
 */
public class TeamThreeStar implements Player {
	// The initial loop takes our player diagonally towards the center not opposed.
	private boolean initialLoop = true;
	// Determines if TeamThreStar is player one(true) or two(false).
	private boolean player1 = true;
	// Keeping track of diagonal position.
	private int count = 0;
	private int heuristicSwitch;

	public TeamThreeStar() {
		this.heuristicSwitch = 0;
	}

	public TeamThreeStar(int heuristicSwitch) {
		this.heuristicSwitch = heuristicSwitch;
	}

	/*
	 * @return String: returns our team name
	 */
	public String getName() {
		return "Team Three Star";
	}

	/*
	 * This is a function that takes the player to the center of the board every
	 * game in the start to gain more space.
	 * 
	 * @param int[][]: takes in the current game board
	 * 
	 * @return int[]: returns an initial diagonal move to take in more space
	 */
	public int[] initialMove(int[][] board) {
		// We also determine whether we are player one or two in this function.
		// If we start at coordinates 0,0, then we are player one, else two.
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

	/*
	 * This function simply determines if we can keep moving diagonally one more
	 * time once we reach the center.
	 * 
	 * @param int[][]: takes the current board
	 * 
	 * @return boolean: true if we can move diagonally after we reach the center,
	 * else false
	 */
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

	/*
	 * This function chooses a move for player TeamThreeStar depending on the
	 * current state of the board. It will use initialMove function initially until
	 * it reaches the center at which point it will check if it can move diagonally
	 * one more time (if enemy hasn't occupied the opposing diagonal square at the
	 * center). Then it will use our heuristic function to determine the next move
	 * for the rest of the game.
	 * 
	 * @param int[][]:takes in the current state of the board
	 * 
	 * @return int[]:returns the move chosen
	 */
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

	/*
	 * This function finds all possible moves for player TeamThreeStar in the
	 * current state of the board and at the same time calculates values which could
	 * be used in the heuristic function to determine which move is considered the
	 * most optimal.
	 * 
	 * @param int[][]: takes in the current state of the board
	 * 
	 * @return ArrayList<int[]>: returns a list of possible moves, the list also
	 * contains useful information for the heuristic function, these are listed
	 * below.
	 */
	private static ArrayList<int[]> getPossibleMoves(int[][] board)
	// The following list are the information stored in each possible move array.
	// 0 rowIndex, 1 colIndex, 2 Highest Possible Value, 3 Highest Enemy Value,
	// 4 AVG friendly value, 5 AVG enemy value, 6 number of enemy tiles,
	// 7 number of friendly tiles, 8 connections(how many connections move has of
	// ally), 9 enemy connections

	{
		ArrayList<int[]> possibleMoves = new ArrayList<int[]>();
		ArrayList<int[]> neighbours = new ArrayList<int[]>();

		int boardRows = board.length;
		int boardCols = board[0].length;

		// Iterates through all tiles of the board.
		for (int i = 0; i < boardRows; i++) {
			for (int j = 0; j < boardCols; j++) {
				// If tiles has a value of 0, it has not been used yet.
				if (board[i][j] == 0) {
					// Values for use in heuristic function.
					int highestValue = 0;
					int highestEnemy = 0;
					int avgFriendly = 0;
					int avgEnemy = 0;
					int friendlyCount = 0;
					int enemyCount = 0;
					int allyConnections = 0;
					int enemyConnections = 0;

					// Contains the neighbors of the candidate tile.
					neighbours = Utilities.neighbours(i, j, boardRows, boardCols);
					// Iterates through all the tiles neighbors.
					for (int k = 0; k < neighbours.size(); k++) {
						int[] coordinate = neighbours.get(k);

						int value = board[coordinate[0]][coordinate[1]];

						// If neighbor tile has a value and has been used, we iterate through its
						// neighbors
						// to check how many connections it has with its friendly tiles.
						if (value > 0) { // For friendly tiles.
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
						} else if (value < 0) { // For enemy tiles.
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

					// If highestValue is greater than 0, then candidate tile is connected to a
					// friendly tile
					// hence it is indeed a possible move. The move array is then declared and
					// returned.
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

	/*
	 * @param int[]: takes in all possible moves in the current state of the board
	 * 
	 * @return int[]: returns what it believes is the optimal move.
	 */
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

	/*
	 * @param int[]: takes in all possible moves in the current state of the board
	 * 
	 * @return int[]: returns what it believes is the optimal move.
	 */
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

			if (moveScore < bestMoveScore) {
				bestMove = move;
				bestMoveScore = moveScore;
			} else if (moveScore == bestMoveScore) {
				double randomNumber = Math.random();
				if (randomNumber < 0.50) {
					System.out.println(randomNumber);
					bestMove = move;
					bestMoveScore = moveScore;
				}
			}
		}
		int[] moved = new int[3];
		for (int i = 0; i < 3; i++) {
			moved[i] = bestMove[i];
		}
		moved[2] += 1;
		return moved;
	}

	/*
	 * @param int[]: takes in all possible moves in the current state of the board
	 * 
	 * @return int[]: returns what it believes is the optimal move.
	 */
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

	/*
	 * @param int[]: takes in all possible moves in the current state of the board
	 * 
	 * @return int[]: returns what it believes is the optimal move.
	 */
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

	/*
	 * @param int[]: takes in all possible moves in the current state of the board
	 * 
	 * @return int[]: returns what it believes is the optimal move.
	 */
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
