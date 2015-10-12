package jg.tictactoe.resources;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import jg.tictactoe.core.Game;
import jg.tictactoe.core.GameService;
import jg.tictactoe.jdbi.GameDAO;

@Path("/games")
@Produces(MediaType.APPLICATION_JSON)
public class GameResource {

	private GameService service;
	
	public GameResource(GameDAO dao) {
		service = new GameService(dao);
	}
	
	@GET
	public List<Game> getGames() {
		return service.getGames();
	}
	
	@GET
	@Path("/{gameId}")
	public Game getGame(@PathParam("gameId") int id) {
		return service.getGame(id);
	}
	
	@POST
	public Game addGame() {
		return service.addGame();
	}
	
	@PUT
	@Path("/{gameId}")
	public Game resetGame(@PathParam("gameId") int id) {
		return service.resetGame(id);
	}
	
	@DELETE
	@Path("/{gameId}")
	public void deleteGame(@PathParam("gameId") int id) {
		service.deleteGame(id);
	}
	
	@PUT
	@Path("/{gameId}/{player : player[12]}/{x}/{y}")	
	public Game makeMove(@PathParam("gameId") int id,
						 @PathParam("player") String player_string,
						 @PathParam("x") int x,
						 @PathParam("y") int y) 
	{
		int player = Character.getNumericValue(player_string.charAt(player_string.length()-1));
		return service.makeMove(id, player, x, y);
	}
}
