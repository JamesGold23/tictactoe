package jg.tictactoe.jdbi;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import jg.tictactoe.core.Game;

@RegisterMapper(GameMapper.class)
public interface GameDAO {
	
	@SqlUpdate("create table if not exists GAMES (ID integer, WHOSE_TURN varchar(10), NUM_MOVES integer, WINNER varchar(10), BOARD char(1)[][], MESSAGE varchar(100))")
	void createTableIfNotExists();
	
	@SqlQuery("select * from GAMES")
	List<Game> getAll();
	
	@SqlQuery("select * from GAMES where ID = :id")
	Game getById(@Bind("id") int id);
	
	@SqlUpdate("delete from GAMES where ID = :id")
	void deleteById(@Bind("id") int id);
	
	@SqlUpdate("update GAMES set WHOSE_TURN = :whoseTurn, NUM_MOVES = :numMoves, WINNER = :winner, BOARD = :board , MESSAGE = :message where ID = :id")
	void update(@BindBean Game game);
	
	@SqlUpdate("insert into GAMES (ID, WHOSE_TURN, NUM_MOVES, WINNER, BOARD, MESSAGE) values (:id, :whoseTurn, :numMoves, :winner, :board, :message)")
	void insert(@BindBean Game game);
}
