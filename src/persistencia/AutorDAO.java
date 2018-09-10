package persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

import modelo.Autor;

public class AutorDAO {
	private Conexao con = new Conexao();
	
	private final String INSERTAUTOR = "INSERT INTO AUTOR (NOME_AUTOR, COD_AUTOR) VALUES (?,?)";
	private final String UPDATEAUTOR = "UPDATE AUTOR SET NOME_AUTOR = ? WHERE NOME_AUTOR = ?";
	private final String DELETEAUTOR = "DELETE FROM AUTOR WHERE NOME_AUTOR = ?";
	private final String LISTAUTOR = "SELECT * FROM AUTOR";
	private final String VALIDANOMEAUTOR = "SELECT COUNT(NOME_AUTOR) FROM AUTOR WHERE UPPER(NOME_AUTOR) = ?";
	private final String CODAUTOR = "SELECT COD_AUTOR FROM AUTOR WHERE ID_AUTOR = (SELECT MAX(ID_AUTOR) FROM AUTOR WHERE UPPER(NOME_AUTOR) LIKE ?)";
	
	public boolean insertAutor(Autor a) {
		try {
			// CONECTA
			con.conecta();
			PreparedStatement preparaInstrucao;
			preparaInstrucao = con.getConexao().prepareStatement(INSERTAUTOR);

			// SETA OS VALORES DA INSTRUCAO
			// OBS: PASSA OS PARAMETROS NA ORDEM DAS ? DA INSTRUCAO
			preparaInstrucao.setString(1, a.getNomeAutor().toUpperCase());
			preparaInstrucao.setString(2, a.getCodAutor().toUpperCase());

			// EXECUTA A INSTRUCAO
			preparaInstrucao.execute();

			// DESCONECTA
			con.desconecta();
			
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;

		}
	}
	
	public boolean updateAutor(String nome, Autor a) {
		try {
			// CONECTA
			con.conecta();
			PreparedStatement preparaInstrucao;
			preparaInstrucao = con.getConexao().prepareStatement(UPDATEAUTOR);

			// SETA OS VALORES DA INSTRUCAO
			// OBS: PASSA OS PARAMETROS NA ORDEM DAS ? DA INSTRUCAO
			preparaInstrucao.setString(1, nome.toUpperCase());
			preparaInstrucao.setString(2, a.getNomeAutor().toUpperCase());

			// EXECUTA A INSTRUCAO
			preparaInstrucao.execute();

			// DESCONECTA
			con.desconecta();
			
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;

		}
	}
	
	public boolean deleteAutor(Autor a) {
		try {
			// CONECTA
			con.conecta();
			PreparedStatement preparaInstrucao;
			preparaInstrucao = con.getConexao().prepareStatement(DELETEAUTOR);

			// SETA OS VALORES DA INSTRUCAO
			// OBS: PASSA OS PARAMETROS NA ORDEM DAS ? DA INSTRUCAO
			preparaInstrucao.setString(1, a.getNomeAutor().toUpperCase());

			// EXECUTA A INSTRUCAO
			preparaInstrucao.execute();

			// DESCONECTA
			con.desconecta();
			
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;

		}
	}
	
	public ArrayList<Autor> listAutor() {
		ArrayList<Autor> lista = new ArrayList<>(); 

		try {
			// CONECTA
			con.conecta();
			PreparedStatement preparaInstrucao;
			preparaInstrucao = con.getConexao().prepareStatement(LISTAUTOR); 
			
			// EXECUTA A INSTRUCAO
			ResultSet rs = preparaInstrucao.executeQuery(); 
			
			//TRATA O RETORNO DA CONSULTA
			while (rs.next()) { //enquanto houver registro
				Autor a = new Autor(rs.getInt("ID_AUTOR"), rs.getString("NOME_AUTOR"), rs.getString("COD_AUTOR"));
				lista.add(a); 
			}
			// DESCONECTA
			con.desconecta();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Collections.sort(lista);
		return lista;
	}
	
	public String codAutor(char letraAutor) {
		String cod = null;
		try {
			// CONECTA
			con.conecta();
			PreparedStatement preparaInstrucao;
			preparaInstrucao = con.getConexao().prepareStatement(CODAUTOR); 
			
			//SETA OS VALORES DA INSTRUCAO
			//OBS: PASSA OS PARAMETROS NA ORDEM DAS ? DA INSTRUCAO
			preparaInstrucao.setString(1, letraAutor+"%");
			
			// EXECUTA A INSTRUCAO
			ResultSet rs = preparaInstrucao.executeQuery(); 
			
			//TRATA O RETORNO DA CONSULTA
			if(rs.next())
				cod = rs.getString(1);
			
			// DESCONECTA
			con.desconecta();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cod;
	}
	
	public boolean validaNomeAutor(String nomeAutor) {
		int qtd=0;
		try {
			// CONECTA
			con.conecta();
			PreparedStatement preparaInstrucao;
			preparaInstrucao = con.getConexao().prepareStatement(VALIDANOMEAUTOR); 
			
			//SETA OS VALORES DA INSTRUCAO
			//OBS: PASSA OS PARAMETROS NA ORDEM DAS ? DA INSTRUCAO
			preparaInstrucao.setString(1, nomeAutor.toUpperCase());
			
			// EXECUTA A INSTRUCAO
			ResultSet rs = preparaInstrucao.executeQuery(); 
			
			//TRATA O RETORNO DA CONSULTA
			if(rs.next())
				qtd = rs.getInt(1);
			
			// DESCONECTA
			con.desconecta();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(qtd == 0)
			return true;
		else
			return false;
	}
}
