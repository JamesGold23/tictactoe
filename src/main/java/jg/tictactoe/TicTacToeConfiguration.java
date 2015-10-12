package jg.tictactoe;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

public class TicTacToeConfiguration extends Configuration {
	private String temp;
	
	@Valid
	@NotNull
	@JsonProperty("database")
	private DataSourceFactory database = new DataSourceFactory();
	
	
	public DataSourceFactory getDataSourceFactory() {
		return database;
	}
	
//	public void setDataBase(DataSourceFactory database) {
//		this.database = database;
//	}
	
	public String getTemp() {
		return temp;
	}
	
	public void setTemp(String temp) {
		this.temp = temp;
	}
}
