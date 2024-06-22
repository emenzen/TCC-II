package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import model.bo.Mensagem;

public class MensagemDao {
	Connection con;

	public MensagemDao() {
		con = Conexao.conectaBanco();
	}

	public boolean insert(Mensagem mensagem) {
		try {
			String sql = "INSERT INTO mensagens (nome_fila, mensagem, status_envio, data_criacao) VALUES (?,?,?,?)";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, mensagem.getQueuename());
			stmt.setString(2, mensagem.getMessage());
			stmt.setInt(3, mensagem.getStatus());
			stmt.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
			stmt.execute();
			
			Conexao.desconectaBanco(con);
			return true;
		} catch (SQLException eSQL) {
			eSQL.printStackTrace();
			return false;
		}
	}
	
	public boolean update(Mensagem mensagem) {
		try {
			String sql = "UPDATE mensagens SET status_envio = ?, data_reprocessamento = ?" + "WHERE id = " + mensagem.getId();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, mensagem.getStatus());
			stmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
			stmt.execute();
			
			//Conexao.desconectaBanco(con);
			return true;
		} catch (SQLException eSQL) {
			eSQL.printStackTrace();
			return false;
		}
	}
	
	public ArrayList<Mensagem> getNaoEnviadas() {
		Statement sentenca;
		ResultSet registros;
		ArrayList<Mensagem> mensagemList = new ArrayList<Mensagem>();
		try {
			sentenca = con.createStatement();

			// faz a consulta
			registros = sentenca.executeQuery(
					"SELECT id, mensagem "
					+ "FROM mensagens "
					+ "WHERE status_envio = 0");
			
			if (registros.next()) {
				
				do {
					Mensagem mensagem = new Mensagem();
					mensagem.setId(Integer.parseInt(registros.getString("id")));
					mensagem.setMessage(registros.getString("mensagem"));
					mensagemList.add(mensagem);
				}while(registros.next());
				sentenca.close();				
			}		
		} catch (SQLException eSQL) {
			eSQL.printStackTrace();
		}
		return mensagemList;
	}
	
	public int getQtdMensagensCriadas() {
		int qtd;
		Statement sentenca;
		ResultSet registros;
		try {
			sentenca = con.createStatement();

			// faz a consulta
			registros = sentenca.executeQuery(
					"SELECT count(1) qtd "
					+ "FROM mensagens;");
			
			if (registros.next()) {
				qtd = Integer.parseInt(registros.getString("qtd"));
				sentenca.close();
				return qtd;
			}		
		} catch (SQLException eSQL) {
			eSQL.printStackTrace();
		}
		return 0;
	}
	
	
	public int getQtdMensagensEnviadas() {
		int qtd;
		Statement sentenca;
		ResultSet registros;
		try {
			sentenca = con.createStatement();

			// faz a consulta
			registros = sentenca.executeQuery(
					"SELECT count(1) qtd "
					+ "FROM mensagens "
					+ "WHERE status_envio = 1;");
			
			if (registros.next()) {
				qtd = Integer.parseInt(registros.getString("qtd"));
				sentenca.close();
				return qtd;
			}		
		} catch (SQLException eSQL) {
			eSQL.printStackTrace();
		}
		return 0;
	}
	
	public int getQtdMensagensReprocessadas() {
		int qtd;
		Statement sentenca;
		ResultSet registros;
		try {
			sentenca = con.createStatement();

			// faz a consulta
			registros = sentenca.executeQuery(
					"SELECT count(1) qtd "
					+ "FROM mensagens "
					+ "WHERE data_reprocessamento is not null "
					+ "and status_envio = 1;");
			
			if (registros.next()) {
				qtd = Integer.parseInt(registros.getString("qtd"));
				sentenca.close();
				return qtd;
			}		
		} catch (SQLException eSQL) {
			eSQL.printStackTrace();
		}
		return 0;
	}
	
	public int getQtdMensagensPendentes() {
		int qtd;
		Statement sentenca;
		ResultSet registros;
		try {
			sentenca = con.createStatement();

			// faz a consulta
			registros = sentenca.executeQuery(
					"SELECT count(1) qtd "
					+ "FROM mensagens "
					+ "WHERE status_envio = 0;");
			
			if (registros.next()) {
				qtd = Integer.parseInt(registros.getString("qtd"));
				sentenca.close();
				return qtd;
			}		
		} catch (SQLException eSQL) {
			eSQL.printStackTrace();
		}
		return 0;
	}
}
