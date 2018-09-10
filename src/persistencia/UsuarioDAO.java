package persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

import modelo.Usuario;

public class UsuarioDAO {
	private Conexao con = new Conexao();
	
	private final String INSERTUSUARIO = "INSERT INTO USUARIO (NOME_USUARIO, TEL_USUARIO, EMAIL_USUARIO) VALUES (?,?,?)";
	private final String UPDATEUSUARIO = "UPDATE USUARIO SET NOME_USUARIO = ?, TEL_USUARIO = ?, EMAIL_USUARIO = ? WHERE ID_USUARIO = ?";
	private final String DELETEUSUARIO = "DELETE FROM USUARIO WHERE ID_USUARIO = ?";
	private final String LISTUSUARIO = "SELECT * FROM USUARIO";
	private final String BUSCARUSUARIO = "SELECT * FROM USUARIO WHERE UPPER(NOME_USUARIO) LIKE ?";
	
	public boolean insertUsuario(Usuario u) {
		try {
			// CONECTA
			con.conecta();
			PreparedStatement preparaInstrucao;
			preparaInstrucao = con.getConexao().prepareStatement(INSERTUSUARIO);

			// SETA OS VALORES DA INSTRUCAO
			// OBS: PASSA OS PARAMETROS NA ORDEM DAS ? DA INSTRUCAO
			preparaInstrucao.setString(1, u.getNomeUsuario().toUpperCase());
			preparaInstrucao.setString(2, u.getTelUsuario());
			preparaInstrucao.setString(3, u.getEmailUsuario().toUpperCase());

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
	
	public boolean updateUsuario(Usuario u) {
		try {
			// CONECTA
			con.conecta();
			PreparedStatement preparaInstrucao;
			preparaInstrucao = con.getConexao().prepareStatement(UPDATEUSUARIO);

			// SETA OS VALORES DA INSTRUCAO
			// OBS: PASSA OS PARAMETROS NA ORDEM DAS ? DA INSTRUCAO
			preparaInstrucao.setString(1, u.getNomeUsuario().toUpperCase());
			preparaInstrucao.setString(2, u.getTelUsuario());
			preparaInstrucao.setString(3, u.getEmailUsuario().toUpperCase());
			preparaInstrucao.setInt(4, u.getIdUsuario());

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
	
	public boolean deleteUsuario(int idUsuario) {
		try {
			// CONECTA
			con.conecta();
			PreparedStatement preparaInstrucao;
			preparaInstrucao = con.getConexao().prepareStatement(DELETEUSUARIO);

			// SETA OS VALORES DA INSTRUCAO
			// OBS: PASSA OS PARAMETROS NA ORDEM DAS ? DA INSTRUCAO
			preparaInstrucao.setInt(1, idUsuario);

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
	
	public ArrayList<Usuario> listUsuario() {
		ArrayList<Usuario> lista = new ArrayList<>(); 

		try {
			// CONECTA
			con.conecta();
			PreparedStatement preparaInstrucao;
			preparaInstrucao = con.getConexao().prepareStatement(LISTUSUARIO); 
			
			// EXECUTA A INSTRUCAO
			ResultSet rs = preparaInstrucao.executeQuery(); 
			
			//TRATA O RETORNO DA CONSULTA
			while (rs.next()) { //enquanto houver registro
				Usuario u = new Usuario(rs.getInt("ID_USUARIO"), rs.getString("NOME_USUARIO"),rs.getString("TEL_USUARIO"), rs.getString("EMAIL_USUARIO"));
				lista.add(u); 
			}
			// DESCONECTA
			con.desconecta();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Collections.sort(lista);
		return lista;
	}
	
	public ArrayList<Usuario> buscarUsuario(String nomeUsuario) {
		ArrayList<Usuario> lista = new ArrayList<>(); 

		try {
			// CONECTA
			con.conecta();
			PreparedStatement preparaInstrucao;
			preparaInstrucao = con.getConexao().prepareStatement(BUSCARUSUARIO); 
			
			//SETA OS VALORES DA INSTRUCAO
			//OBS: PASSA OS PARAMETROS NA ORDEM DAS ? DA INSTRUCAO
			preparaInstrucao.setString(1, "%"+nomeUsuario.toUpperCase()+"%");
			
			// EXECUTA A INSTRUCAO
			ResultSet rs = preparaInstrucao.executeQuery(); 
			
			//TRATA O RETORNO DA CONSULTA
			while (rs.next()) { //enquanto houver registro
				Usuario u = new Usuario(rs.getInt("ID_USUARIO"), rs.getString("NOME_USUARIO"),rs.getString("TEL_USUARIO"), rs.getString("EMAIL_USUARIO"));
				lista.add(u); 
			}
			// DESCONECTA
			con.desconecta();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Collections.sort(lista);
		return lista;
	}
}
