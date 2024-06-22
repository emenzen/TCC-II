package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class Conexao {

	public static Connection conectaBanco() {
		Connection conexao = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conexao = DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "root",
					"M@mig@23");
		} catch (SQLException eSQL) {
			// exce��es de SQL
			eSQL.printStackTrace();
			JOptionPane.showMessageDialog(null, "Falha na conex�o com o banco!\n" + "Mensagem: " + eSQL.getMessage(),
					"Erro", JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			// demais exce��es
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Falha na conex�o com o banco!\n" + "Mensagem: " + e.getMessage(),
					"Erro", JOptionPane.ERROR_MESSAGE);
		}
		return conexao;
	}

	public static void desconectaBanco(Connection c) {
		try {
			c.close();
		} catch (SQLException eSQL) {
			// exce��es de SQL
			eSQL.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"N�o foi poss�vel desconectar o banco!\n" + "Mensagem: " + eSQL.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}