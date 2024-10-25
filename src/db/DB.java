package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DB {

	private static Connection conn = null;

	// aqui ele vai connectar
	public static Connection getConnection() {
		if (conn == null) {
			try {
				Properties props = loadProperties();//aqui ele vai pegar o nome do usario e senha 
				String url = props.getProperty("dburl");//aqui ele vai pegar o valor da url do arquivo db.properties
				conn = DriverManager.getConnection(url, props);//aqui ele vai conetar no mysql
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
		return conn;
	}

	// aqui ele vai fecha a conecao
	public static void closeConnection() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}

	// aqui ele vai pegar os dados do arquivo db.properties vai passar os dados para
	// o method getConnection()
	private static Properties loadProperties() {
		try (FileInputStream fs = new FileInputStream("db.properties")) {
			Properties props = new Properties();
			props.load(fs);
			return props;
		} catch (IOException e) {
			throw new DbException(e.getMessage());
		}
	}
	
	public static void closeStatement(Statement st) {
		if(st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				throw new DbException(e.getMessage());
			}
		}
	}
	public static void closeResulSet(ResultSet rs) {
		if(rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
	public static void closePreparedStatement(PreparedStatement rs) {
		if(rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
}
