package jg.tictactoe.jdbi;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.Argument;
import org.skife.jdbi.v2.tweak.ArgumentFactory;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/* Need to instruct JDBI how to store a char[][] in the postgres DB */
public class CharArrayArgument implements ArgumentFactory<char[][]> {    
    public boolean accepts(Class<?> expectedType, Object value, StatementContext ctx) {
        return value != null && char[][].class.isAssignableFrom(value.getClass());
    }
  
    public Argument build(Class<?> expectedType, final char[][] value, StatementContext ctx) {
        return new Argument() {
            public void apply(int position, PreparedStatement statement, StatementContext ctx) throws SQLException {
                Array values = statement.getConnection().createArrayOf("char", value);
                statement.setArray(position, values);
            }
        };
    }
}
