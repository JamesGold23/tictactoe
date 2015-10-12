package jg.tictactoe.jdbi;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import jg.tictactoe.core.Game;

/* Map a row from the DB to a Game object */
public class GameMapper implements ResultSetMapper<Game>{
	public Game map(int index, ResultSet resultSet, StatementContext statementContext) throws SQLException
	{
		Array arr = resultSet.getArray("BOARD");
		
		String string_board[][] = (String[][])arr.getArray();
		
		// board retrieved from the DB has type String[][]...
		// so convert it back to char[][]
		
		char[][] board = new char[string_board.length][string_board.length];
		
		for (int i = 0; i < string_board.length; i++) {
			for (int j = 0; j < string_board[0].length; j++) {
				System.out.println("ENTRY: " + i + " " + j + ": " + string_board[i][j]);
				board[i][j] = string_board[i][j].charAt(0);
			}
		}		

		return new Game(resultSet.getInt("ID"),
						resultSet.getInt("WHOSE_TURN"),
						resultSet.getInt("NUM_MOVES"),
						resultSet.getInt("WINNER"),
						board,
						resultSet.getString("MESSAGE"));
	}
}
