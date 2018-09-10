package persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

import modelo.Autor;
import modelo.Titulo;

public class TituloDAO {
	private Conexao con = new Conexao();
	
	private final String INSERTTITULO = "INSERT INTO TITULO (NOME_TITULO, COD_TITULO, ID_AUTOR) VALUES (?,?,?)";
	private final String UPDATETITULO = "UPDATE TITULO SET NOME_TITULO = ? WHERE NOME_TITULO = ?";
	private final String DELETETITULO = "DELETE FROM TITULO WHERE NOME_TITULO = ?";
	private final String LISTTITULO = "SELECT * FROM TITULO";
	private final String LISTTITULOAUTOR = "SELECT * FROM TITULO, AUTOR WHERE NOME_AUTOR = ? AND TITULO.ID_AUTOR = AUTOR.ID_AUTOR";
	private final String VALIDANOMETITULO = "SELECT COUNT(NOME_TITULO) FROM TITULO WHERE UPPER(NOME_TITULO) = ? AND TITULO.ID_AUTOR = ?";
	private final String CODTITULO = "SELECT COD_TITULO FROM TITULO WHERE ID_TITULO = (SELECT MAX(ID_TITULO) FROM TITULO, AUTOR WHERE UPPER(NOME_AUTOR) = ? AND TITULO.ID_AUTOR = AUTOR.ID_AUTOR)";

	public boolean insertTitulo(Titulo t) {
		try {
			// CONECTA
			con.conecta();
			PreparedStatement preparaInstrucao;
			preparaInstrucao = con.getConexao().prepareStatement(INSERTTITULO);

			// SETA OS VALORES DA INSTRUCAO
			// OBS: PASSA OS PARAMETROS NA ORDEM DAS ? DA INSTRUCAO
			preparaInstrucao.setString(1, t.getNomeTitulo().toUpperCase());
			preparaInstrucao.setString(2, t.getCodTitulo().toUpperCase());
			preparaInstrucao.setInt(3, t.getAutor().getIdAutor());

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
	
	public boolean updateTitulo(String nome, Titulo t) {
		try {
			// CONECTA
			con.conecta();
			PreparedStatement preparaInstrucao;
			preparaInstrucao = con.getConexao().prepareStatement(UPDATETITULO);

			// SETA OS VALORES DA INSTRUCAO
			// OBS: PASSA OS PARAMETROS NA ORDEM DAS ? DA INSTRUCAO
			preparaInstrucao.setString(1, nome.toUpperCase());
			preparaInstrucao.setString(2, t.getNomeTitulo().toUpperCase());

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
	
	public boolean deleteTitulo(Titulo t) {
		try {
			// CONECTA
			con.conecta();
			PreparedStatement preparaInstrucao;
			preparaInstrucao = con.getConexao().prepareStatement(DELETETITULO);

			// SETA OS VALORES DA INSTRUCAO
			// OBS: PASSA OS PARAMETROS NA ORDEM DAS ? DA INSTRUCAO
			preparaInstrucao.setString(1, t.getNomeTitulo());

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
	
	public ArrayList<Titulo> listTitulo(Autor a) {
		ArrayList<Titulo> lista = new ArrayList<>(); 
		
		String instrucao;
		if(a == null) {
			instrucao = LISTTITULO;
		}
		else {
			instrucao = LISTTITULOAUTOR;
		}

		try {
			// CONECTA
			con.conecta();
			PreparedStatement preparaInstrucao;
			preparaInstrucao = con.getConexao().prepareStatement(instrucao); 
			
			if(a != null)
				preparaInstrucao.setString(1, a.getNomeAutor());
			
			// EXECUTA A INSTRUCAO
			ResultSet rs = preparaInstrucao.executeQuery(); 
			
			//TRATA O RETORNO DA CONSULTA
			while (rs.next()) { //enquanto houver registro
				Titulo t = new Titulo(rs.getInt("ID_TITULO"), rs.getString("NOME_TITULO"), rs.getString("COD_TITULO"), rs.getInt("ID_AUTOR"));
				lista.add(t); 
			}
			// DESCONECTA
			con.desconecta();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		Collections.sort(lista);
		return lista;
	}
	
	public boolean validaNomeTitulo(String nomeTitulo, int idAutor) {
		int qtd=0;
		try {
			// CONECTA
			con.conecta();
			PreparedStatement preparaInstrucao;
			preparaInstrucao = con.getConexao().prepareStatement(VALIDANOMETITULO); 
			
			//SETA OS VALORES DA INSTRUCAO
			//OBS: PASSA OS PARAMETROS NA ORDEM DAS ? DA INSTRUCAO
			preparaInstrucao.setString(1, nomeTitulo.toUpperCase());
			preparaInstrucao.setInt(2, idAutor);
			
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
	
	public String codTitulo(String nomeAutor) {
		String cod = null;
		try {
			// CONECTA
			con.conecta();
			PreparedStatement preparaInstrucao;
			preparaInstrucao = con.getConexao().prepareStatement(CODTITULO); 
			
			//SETA OS VALORES DA INSTRUCAO
			//OBS: PASSA OS PARAMETROS NA ORDEM DAS ? DA INSTRUCAO
			preparaInstrucao.setString(1, nomeAutor.toUpperCase());
			
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
}
