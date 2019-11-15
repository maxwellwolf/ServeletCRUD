package br.com.servletcrud.connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexao {
	
	private static String url = "jdbc:mysql://localhost/db_atividade?useTimezone=true&serverTimezone=UTC";
	private static String password = "root";
	private static String user = "root";
	private static Connection connection = null;
	
	static {
		conectar();

	}

	public Conexao() {
		conectar();
	}
	
	
	private static void conectar() {
		try {
			if (connection == null) {
				Class.forName("com.mysql.cj.jdbc.Driver");
				connection = DriverManager.getConnection(url, user, password);
				connection.setAutoCommit(false);
				//System.out.println("Conectou com sucesso!");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() {
		return connection;
	}
	
	
		
		
	}


