package fr.killax.app.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;

import fr.killax.app.data.mysql.Column;
import fr.killax.app.data.mysql.Tuple;

public class MySQLClient {

	private String host;
	private String username;
	private String password;
	private String dbName;
	
	private Connection connection;
	
	private static String driver = "com.mysql.cj.jdbc.Driver";
	
	public MySQLClient(String host, String username, String password, int port) throws Exception{
		this.host = String.format("jdbc:mysql://%s:%s", host, port);
		Class.forName(driver);
	}
	
	public Connection connect() throws Exception{
		return (connection = DriverManager.getConnection(host, username, password));
	}
	
	public void disconnect() throws Exception {
		connection.close();
	}
	
	public void setDatabase(String dbName) throws SQLException {
		connection.setCatalog((this.dbName = dbName));
	}
	
	public String getDatabase() {
		return dbName;
	}
	
	public void createTable(String tableName, Column... columns) throws Exception {
		String values = "";
		for (int i = 0; i < columns.length; i++) {
			values += (i != 0 ? ", " : "") + columns[i];
		}
		
		PreparedStatement query = connection.prepareStatement(String.format("CREATE TABLE IF NOT EXISTS %s(%s);", tableName, values));
		query.executeUpdate();
	}
	
	public void insert(String tableName, Column[] columns, Tuple[] tuples) throws Exception {
		String column = "";
		for (int i = 0; i < columns.length; i++)
			column += (i != 0 ? ", " : "") + columns[i].getLabel();
		String values = "";
		for (int i = 0; i < tuples.length; i++)
			values += (i != 0 ? ", " : "") + tuples[i];
		
		PreparedStatement query = connection.prepareStatement(String.format("INSERT INTO %s(%s) VALUES %s;", tableName, column, values));
		query.executeUpdate();
	}
	
	public HashMap<String, HashMap<String, String>> selectToMap(String tableName, String primaryKey, Column[] columns) throws Exception {
		String column = "";
		for (int i = 0; i < columns.length; i++)
			column += (i != 0 ? ", " : "") + columns[i].getLabel();
		
		PreparedStatement query = connection.prepareStatement(String.format("SELECT %s FROM %s;", column, tableName));
		ResultSet result = query.executeQuery();
		
		HashMap<String, HashMap<String, String>> resultMapped = new HashMap<String, HashMap<String, String>>();
		ResultSetMetaData resultMeta = result.getMetaData();
		while(result.next()) {
			HashMap<String, String> valuesMapped = new HashMap<String, String>();
			for (int i = 1; i <= resultMeta.getColumnCount(); i++) {
				valuesMapped.put(resultMeta.getColumnName(i), result.getString(i));
			}
			resultMapped.put(result.getString(primaryKey), valuesMapped);
		}
		
		return resultMapped;
	}
	
	public HashMap<String, HashMap<String, String>> selectAllToMap(String tableName, String primaryKey) throws Exception {
		PreparedStatement query = connection.prepareStatement(String.format("SELECT %s FROM %s;", "*", tableName));
		ResultSet result = query.executeQuery();
		
		HashMap<String, HashMap<String, String>> resultMapped = new HashMap<String, HashMap<String, String>>();
		ResultSetMetaData resultMeta = result.getMetaData();
		while(result.next()) {
			HashMap<String, String> valuesMapped = new HashMap<String, String>();
			for (int i = 1; i <= resultMeta.getColumnCount(); i++) {
				valuesMapped.put(resultMeta.getColumnName(i), result.getString(i));
			}
			resultMapped.put(result.getString(primaryKey), valuesMapped);
		}
		
		return resultMapped;
	}
}
