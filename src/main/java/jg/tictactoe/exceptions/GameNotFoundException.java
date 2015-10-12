package jg.tictactoe.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class GameNotFoundException extends WebApplicationException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7852370442054195974L;

	public GameNotFoundException(String message) {
		 super(Response.status(Response.Status.NOT_FOUND)
	             .entity(new ErrorMessage(message, 404)).type(MediaType.APPLICATION_JSON).build());
	}

}
