package jg.tictactoe;

import org.skife.jdbi.v2.DBI;

import io.dropwizard.Application;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import jg.tictactoe.jdbi.CharArrayArgument;
import jg.tictactoe.jdbi.GameDAO;
import jg.tictactoe.resources.GameResource;

public class TicTacToeApplication extends Application<TicTacToeConfiguration> {
    public static void main(String[] args) throws Exception {
        new TicTacToeApplication().run(args);
    }

    @Override
    public String getName() {
        return "tic-tac-toe";
    }

    @Override
    public void initialize(Bootstrap<TicTacToeConfiguration> bootstrap) {
        // nothing to do yet
    }

    @Override
    public void run(TicTacToeConfiguration config,
                    Environment environment) throws ClassNotFoundException {
    	final DBIFactory factory = new DBIFactory();
    	final DBI jdbi = factory.build(environment, config.getDataSourceFactory(), "postgresql");
    	jdbi.registerArgumentFactory(new CharArrayArgument());
    	
    	
    	
    	final GameDAO gameDAO = jdbi.onDemand(GameDAO.class);

        environment.jersey().register(new GameResource(gameDAO));
    }

}