package persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AnotacaoDAO {
	private final Conexao con = new Conexao();
	
	private final String INSERTANOTACAO = "INSERT INTO ANOTACAO (TEXTO) VALUES (?)";
	private final String DELETEANOTACAO = "DELETE FROM ANOTACAO";
	private final String LISTANOTACAO = "SELECT * FROM ANOTACAO";
	
	public boolean insertAnotacao(String t) {
		try {
			// CONECTA
			con.conecta();
			PreparedStatement preparaInstrucao;
			
			preparaInstrucao = con.getConexao().prepareStatement(DELETEANOTACAO);
			preparaInstrucao.execute();

			preparaInstrucao = con.getConexao().prepareStatement(INSERTANOTACAO);

			// SETA OS VALORES DA INSTRUCAO
			// OBS: PASSA OS PARAMETROS NA ORDEM DAS ? DA INSTRUCAO
			preparaInstrucao.setString(1, t);

			// EXECUTA A INSTRUCAO
			preparaInstrucao.execute();

			// DESCONECTA
			con.desconecta();
			
			return true;

		} catch (SQLException e) {
			return false;

		}
	}
	
	
	
	public boolean deleteAnotacao() {
		try {
			// CONECTA
			con.conecta();
			PreparedStatement preparaInstrucao;
			preparaInstrucao = con.getConexao().prepareStatement(DELETEANOTACAO);

			// SETA OS VALORES DA INSTRUCAO
			// OBS: PASSA OS PARAMETROS NA ORDEM DAS ? DA INSTRUCAO

			// EXECUTA A INSTRUCAO
			preparaInstrucao.execute();

			// DESCONECTA
			con.desconecta();
			
			return true;

		} catch (SQLException e) {
			return false;

		}
	}
	
	public String listAnotacao() {
		String lista = ""; 

		try {
			// CONECTA
			con.conecta();
			PreparedStatement preparaInstrucao;
			preparaInstrucao = con.getConexao().prepareStatement(LISTANOTACAO); 
			
			// EXECUTA A INSTRUCAO
			ResultSet rs = preparaInstrucao.executeQuery(); 
			
			//TRATA O RETORNO DA CONSULTA
			if(rs.next())
				lista = rs.getString("TEXTO");
	
			// DESCONECTA
			con.desconecta();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return lista;
	}
}
