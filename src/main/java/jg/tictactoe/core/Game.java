package jg.tictactoe.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


public class Game {
	public static final int NULL_PLAYER = 0;
	public static final int PLAYER1 = 1;
	public static final int PLAYER2 = 2;
	protected static final int BOARD_DIMENSION = 3;
	
	protected int id; 
	protected int whoseTurn;
	protected int numMoves;
	protected Integer winner;
	protected char[][] board;
	protected String message;
	
	public Game(int id) {
		this.id = id;
		board = new char[BOARD_DIMENSION][BOARD_DIMENSION];
		reset();
	}
	
	public Game(int id, int turn, int numMoves, int winner, char[][] board, String message) {
		this.id = id;
		this.whoseTurn = turn;
		this.numMoves = numMoves;
		this.winner = winner;
		this.board = board;
		this.message = message;
	}
	
	public void reset() {
		whoseTurn = PLAYER1;
		winner = NULL_PLAYER;
		numMoves = 0;
		message = "Player 1's turn";
		for (int i = 0; i < BOARD_DIMENSION; i++) {
			for (int j = 0; j < BOARD_DIMENSION; j++) {
				board[i][j] = ' ';
			}
		}		
	}
	
	public int getId() {
		return id;
	}
	
	@JsonIgnore
	public int getNumMoves() {
		return numMoves;
	}
	
	public int getWhoseTurn() {
		return whoseTurn;
	}
	
	public int getWinner() {
		return winner;
	}
	
	@JsonIgnore
	public char[][] getBoard() {
		return board;
	}
	
	@JsonProperty("board")
	public String getPrettyBoard() {
		System.out.println("GAME BOARD: ");
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				System.out.println("ENTRY: " + i + " " + j + ": " + board[i][j]);
			}
		}	
		
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < BOARD_DIMENSION; i++) {
			for (int j = 0; j < BOARD_DIMENSION; j++) {
				s.append(board[i][j]);
				if (j < BOARD_DIMENSION-1) {
					s.append('|');
				}			
			}
			s.append('\n');
			if (i < BOARD_DIMENSION-1) {
				for (int j = 0; j < BOARD_DIMENSION + BOARD_DIMENSION - 1; j++) {
					s.append('-');
				}
				s.append('\n');
			}			
		}
		
		return s.toString();
	}

	public String getMessage() {
		return message;
	}
	
	public void makeMove(int player, int row, int col) {
		if (isWon() || isDraw()) {
			message = "Game is over! You can reset this game or start a new one.";
			return;
		}
		
		if (player != getWhoseTurn()) {
			message = "It's not your turn!";
			return;
		}
		
		if (row < 0 || row > BOARD_DIMENSION-1 || col < 0 || col > BOARD_DIMENSION-1 || board[row][col] != ' ') {
			message = "Invalid move.";
			return;
		}		
		
		if (player == PLAYER1) {
			board[row][col] = 'X';
			whoseTurn = PLAYER2;
			message = "Player 2's turn.";
		}
		else {
			board[row][col] = 'O';
			whoseTurn = PLAYER1;
			message = "Player 1's turn.";
		}
		
		numMoves++;
		
		setWinnerIfSomebodyWon();
		
		if (isWon()) {
			whoseTurn = NULL_PLAYER;
			if (winner == PLAYER1) {
				message = "Player 1 wins! You can reset this game or create a new one.\n";
			}
			else {
				message = "Player 2 wins! You can reset this game or create a new one.\n";
			}
		}
		else if (isDraw()) {
			whoseTurn = NULL_PLAYER;
			message = "Game ends in a draw! You can reset this game or create a new one.\n";			
		}
	}
	
	protected boolean isWon() {
		return winner != NULL_PLAYER;
	}
	
	protected boolean isDraw() {
		/* Check if all squares are filled in */
		return numMoves == BOARD_DIMENSION * BOARD_DIMENSION;
	}
	
	protected void setWinnerIfSomebodyWon() {
		/* Check verticals */
		for (int r = 0, c = 0; c < BOARD_DIMENSION; c++) {			
			int player = playerHasThreeInARow(r, c, 1, 0);
			if (player == PLAYER1) {
				winner = PLAYER1;
			}
			else if (player == PLAYER2) {
				winner = PLAYER2;
			}
		}
		
		/* Check horizontals */
		for (int r = 0, c = 0; r < BOARD_DIMENSION; r++) {
			int player = playerHasThreeInARow(r, c, 0, 1);
			if (player == PLAYER1) {
				winner = PLAYER1;
			}
			else if (player == PLAYER2) {
				winner = PLAYER2;
			}
		}
		
		/* Check top left to bottom right diagonal */
		int player = playerHasThreeInARow(0, 0, 1, 1);
		if (player == PLAYER1) {
			winner = PLAYER1;
		}
		else if (player == PLAYER2) {
			winner = PLAYER2;
		}
		
		/* Check top right to bottom left diagonal */
		player = playerHasThreeInARow(0, BOARD_DIMENSION-1, 1, -1);
		if (player == PLAYER1) {
			winner = PLAYER1;
		}
		else if (player == PLAYER2) {
			winner = PLAYER2;
		}
	}
	
	protected int playerHasThreeInARow(int startRow, int startCol, int rowDir, int colDir) {
		char startSquare = board[startRow][startCol];
		
		if (startSquare == ' ') {
			return NULL_PLAYER;
		}
		
		for (int r = startRow, c = startCol; r >= 0 && r < BOARD_DIMENSION && c >= 0 && c < BOARD_DIMENSION; r += rowDir, c+= colDir) {
			if (board[r][c] != startSquare) {
				return NULL_PLAYER;
			}
		}
		
		if (startSquare == 'X') {
			return PLAYER1;
		}
		else {
			return PLAYER2;
		}		
	}	
}
