package seqtournament;

import java.util.ArrayList;

import sequencium.Player;
import sequencium.Utilities;

/**
 * A player for Sequencium, designed to win.
 * 
 * @author Elbert Alcantara
 * @author Christopher Groenewegen
 * @author John KJ Kim
 */
public class TeamThreeStar implements Player {
	// The initial loop takes our player diagonally towards the center not opposed.
	private boolean initialLoop = true;
	// Determines if TeamThreStar is player one(true) or two(false).
	private boolean player1 = true;
	private int heuristicSwitch;

	// Initial positions of the board based on player one or not.
	private int initialPosX;
	private int initialPosY;

	private static final boolean VERBOSE = true;

	/**
	 * Replaces default constructor & sets the heuristicSwitch to default state.
	 */
	public TeamThreeStar() {
		this.heuristicSwitch = 5;
	}

	/**
	 * Sets heuristicSwitch to target value.
	 * 
	 * @param heuristicSwitch Determines which heuristic to use for player.
	 */
	public TeamThreeStar(int heuristicSwitch) {
		this.heuristicSwitch = heuristicSwitch;
	}

	/**
	 * @return String returns our team name
	 */
	public String getName() {
		return "Team Three Star";
	}

	/**
	 * This function simply determines if we continue to move diagonally until an
	 * invalid move.
	 * 
	 * @param board Takes the current board
	 * 
	 * @return True if we can move diagonally, else false
	 */
	public boolean diagonalCheck(int[][] board) {

		if (player1 && board[initialPosX + 1][initialPosY + 1] == 0) {

			return true;
		} else if (!player1 && board[initialPosX - 1][initialPosY - 1] == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Prints the board state.
	 * 
	 * @param Board current state of the board.
	 */
	public void printBoardState(int[][] board) {
		for (int[] is : board) {
			for (int iss : is) {
				System.out.print(iss + "  ");
			}
			System.out.println();
		}
	}

	/**
	 * This function chooses a move for player TeamThreeStar depending on the
	 * current state of the board. It will use the diagonal check function to
	 * continue to move diagonally until it no longer can.
	 * Then it will use our heuristic function to determine the next move
	 * for the rest of the game.
	 * 
	 * @param board The current state of the board.
	 * 
	 * @return Returns the move chosen.
	 */
	public int[] makeMove(int[][] board) {
		if (board[0][0] == 1 && initialLoop == true) {
			initialPosX = 0;
			initialPosY = 0;
			player1 = true;
		} else if (initialLoop == true) {
			initialPosX = board.length;
			initialPosY = board[0].length;
			player1 = false;
			if(VERBOSE)System.out.println("x" + initialPosX);
			if(VERBOSE)System.out.println("y" + initialPosY);
		}
		initialLoop = false;
		 if(VERBOSE)printBoardState(board);
		 if(VERBOSE)System.out.println();

		if (diagonalCheck(board)) {
			if (player1) {
				int[] initialMoves = new int[3];
				initialMoves[0] = initialPosX + 1;
				initialMoves[1] = initialPosY + 1;
				initialMoves[2] = board[initialPosX][initialPosY] + 1;
				return initialMoves;
			} else {
				int[] initialMoves = new int[3];
				initialMoves[0] = initialPosX - 1;
				initialMoves[1] = initialPosY - 1;
				initialMoves[2] = board[initialPosX][initialPosY] - 1;
				return initialMoves;
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

	/**
	 * This function finds all possible moves for player TeamThreeStar in the
	 * current state of the board and at the same time calculates values which could
	 * be used in the heuristic function to determine which move is considered the
	 * most optimal.
	 * 
	 * @param baord Takes in the current state of the board
	 * 
	 * @return Returns a list of possible moves, the list also contains useful
	 *         information for the heuristic function, these are listed below.
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

	/**
	 * Calculates the optimum move for the player agent to make based on the current state of the board.
	 * 
	 * @param possibleMoves Takes in a list of all possible moves in the current
	 *                      state of the board.
	 * 
	 * @return Returns the optimal move based on its facts.
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

	/**
	 * Calculates the optimum move for the player agent to make based on the current state of the board.
	 * 
	 * @param possibleMoves Takes in a list of all possible moves in the current
	 *                      state of the board.
	 * 
	 * @return Returns the optimal move based on its facts.
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
					if(VERBOSE)System.out.println(randomNumber);
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

	/**
	 * Calculates the optimum move for the player agent to make based on the current state of the board.
	 * 
	 * @param possibleMoves Takes in a list of all possible moves in the current
	 *                      state of the board.
	 * 
	 * @return Returns the optimal move based on its facts.
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

	/**
	 * Calculates the optimum move for the player agent to make based on the current state of the board.
	 * 
	 * @param possibleMoves Takes in a list of all possible moves in the current
	 *                      state of the board.
	 * 
	 * @return Returns the optimal move based on its facts.
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
			} else if (moveScore == bestMoveScore) {
				double randomNumber = Math.random();
				if (randomNumber < 0.50) {
					if(VERBOSE)System.out.println(randomNumber);
					bestMove = move;
					bestMoveScore = moveScore;
				}
			}
		}
		int[] move = new int[3];
		for (int i = 0; i < 3; i++) {
			move[i] = bestMove[i];
		}
		move[2] += 1;
		return move;
	}

	/**
	 * Calculates the optimum move for the player agent to make based on the current state of the board.
	 * 
	 * @param possibleMoves Takes in a list of all possible moves in the current
	 *                      state of the board.
	 * 
	 * @return Returns the optimal move based on its facts.
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
