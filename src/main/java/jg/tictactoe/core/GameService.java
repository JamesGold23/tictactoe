package jg.tictactoe.core;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import jg.tictactoe.exceptions.GameNotFoundException;
import jg.tictactoe.jdbi.GameDAO;

public class GameService {
	GameDAO gameDAO;
	protected static AtomicInteger idCounter = new AtomicInteger();
	
	//private Map<Long, Game> games = GamesDB.getGames();
	
	public GameService(GameDAO dao) {
		this.gameDAO = dao;
		gameDAO.createTableIfNotExists();
	}
	
	public List<Game> getGames() {
		return new ArrayList<Game>(gameDAO.getAll());
	}
	
	public Game getGame(int id) {
		Game g = gameDAO.getById(id);
		if (g == null) {
			throw new GameNotFoundException("No game with id " + id + " was found");
		}
		return g;
	}
	
	
	public Game addGame() {
		int id = idCounter.incrementAndGet();
		Game g = new Game(id);
		gameDAO.insert(g);
		return g;		
	}
	
	
	public Game makeMove(int id, int player, int x, int y) {
		Game g = gameDAO.getById(id);
		if (g == null) {
			throw new GameNotFoundException("No game with id " + id + " was found");
		}
		g.makeMove(player, x, y);
		gameDAO.update(g);
		return g;
	}
	
	public Game resetGame(int id) {
		Game g = gameDAO.getById(id);
		if (g == null) {
			throw new GameNotFoundException("No game with id " + id + " was found");
		}
		g.reset();
		gameDAO.update(g);
		return g;
	}
	
	public void deleteGame(int id) {
		Game g = gameDAO.getById(id);
		if (g == null) {
			throw new GameNotFoundException("No game with id " + id + " was found");
		}
		gameDAO.deleteById(id);
	}
}
