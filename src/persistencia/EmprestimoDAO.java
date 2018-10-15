package persistencia;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.Emprestimo;

public class EmprestimoDAO {
	private final Conexao con = new Conexao();
	
	private final String INSERTEMPRESTIMO = "INSERT INTO EMPRESTIMO (ID_USUARIO, ID_EXEMPLAR, DATA_EMPRESTIMO, DATA_DEVOLUCAO) VALUES (?,?, ?, ?)";
	private final String DELETEEMPRESTIMO = "DELETE FROM EMPRESTIMO WHERE ID_EMPRESTIMO = ?";
	private final String LISTEMPRESTIMO = "SELECT ID_EMPRESTIMO, NOME_USUARIO, COD_EXEMPLAR, DATA_EMPRESTIMO, DATA_DEVOLUCAO FROM EMPRESTIMO, USUARIO, EXEMPLAR WHERE EMPRESTIMO.ID_USUARIO = USUARIO.ID_USUARIO AND EMPRESTIMO.ID_EXEMPLAR = EXEMPLAR.ID_EXEMPLAR";
	private final String LISTEMPRESTIMOUSUARIO = "SELECT ID_EMPRESTIMO, NOME_USUARIO, COD_EXEMPLAR, DATA_EMPRESTIMO, DATA_DEVOLUCAO FROM EMPRESTIMO, USUARIO, EXEMPLAR WHERE EMPRESTIMO.ID_USUARIO = USUARIO.ID_USUARIO AND EMPRESTIMO.ID_EXEMPLAR = EXEMPLAR.ID_EXEMPLAR AND EMPRESTIMO.ID_USUARIO = ?";
	private final String LISTEMPRESTIMOEXEMPLAR = "SELECT ID_EMPRESTIMO, NOME_USUARIO, COD_EXEMPLAR, DATA_EMPRESTIMO, DATA_DEVOLUCAO FROM EMPRESTIMO, USUARIO, EXEMPLAR WHERE EMPRESTIMO.ID_USUARIO = USUARIO.ID_USUARIO AND EMPRESTIMO.ID_EXEMPLAR = EXEMPLAR.ID_EXEMPLAR AND EMPRESTIMO.ID_EXEMPLAR = ?";
	private final String LISTEMPRESTIMODATAEMP = "SELECT ID_EMPRESTIMO, NOME_USUARIO, COD_EXEMPLAR, DATA_EMPRESTIMO, DATA_DEVOLUCAO FROM EMPRESTIMO, USUARIO, EXEMPLAR WHERE EMPRESTIMO.ID_USUARIO = USUARIO.ID_USUARIO AND EMPRESTIMO.ID_EXEMPLAR = EXEMPLAR.ID_EXEMPLAR AND EMPRESTIMO.DATA_EMPRESTIMO = ?";
	private final String LISTEMPRESTIMODATADEV = "SELECT ID_EMPRESTIMO, NOME_USUARIO, COD_EXEMPLAR, DATA_EMPRESTIMO, DATA_DEVOLUCAO FROM EMPRESTIMO, USUARIO, EXEMPLAR WHERE EMPRESTIMO.ID_USUARIO = USUARIO.ID_USUARIO AND EMPRESTIMO.ID_EXEMPLAR = EXEMPLAR.ID_EXEMPLAR AND EMPRESTIMO.DATA_DEVOLUCAO = ?";
	
	public boolean insertEmprestimo(Emprestimo e) {
		try {
			// CONECTA
			con.conecta();
			PreparedStatement preparaInstrucao;
			preparaInstrucao = con.getConexao().prepareStatement(INSERTEMPRESTIMO);

			// SETA OS VALORES DA INSTRUCAO
			// OBS: PASSA OS PARAMETROS NA ORDEM DAS ? DA INSTRUCAO
			preparaInstrucao.setInt(1, e.getIdUsuario());
			preparaInstrucao.setInt(2, e.getIdExemplar());
			preparaInstrucao.setDate(3, e.getDataEmprestimo());
			preparaInstrucao.setDate(4, e.getDataDevolucao());

			// EXECUTA A INSTRUCAO
			preparaInstrucao.execute();

			// DESCONECTA
			con.desconecta();
			
			return true;

		} catch (SQLException sqle) {
			return false;

		}
	}
	
	public boolean deleteEmprestimo(Emprestimo e) {
		try {
			// CONECTA
			con.conecta();
			PreparedStatement preparaInstrucao;
			preparaInstrucao = con.getConexao().prepareStatement(DELETEEMPRESTIMO);

			// SETA OS VALORES DA INSTRUCAO
			// OBS: PASSA OS PARAMETROS NA ORDEM DAS ? DA INSTRUCAO
			preparaInstrucao.setInt(1, e.getIdEmprestimo());

			// EXECUTA A INSTRUCAO
			preparaInstrucao.execute();

			// DESCONECTA
			con.desconecta();
			
			return true;

		} catch (SQLException sqle) {
			return false;

		}
	}
	
	public ArrayList<Emprestimo> listEmprestimo() {
		ArrayList<Emprestimo> lista = new ArrayList<>(); 
		
		try {
			// CONECTA
			con.conecta();
			PreparedStatement preparaInstrucao;
			preparaInstrucao = con.getConexao().prepareStatement(LISTEMPRESTIMO); 
			
			
			// EXECUTA A INSTRUCAO
			ResultSet rs = preparaInstrucao.executeQuery(); 
			
			//TRATA O RETORNO DA CONSULTA
			while (rs.next()) { //enquanto houver registro
				Emprestimo e = new Emprestimo(rs.getInt("ID_EMPRESTIMO"), rs.getString("NOME_USUARIO"), rs.getString("COD_EXEMPLAR"), rs.getDate("DATA_EMPRESTIMO"), rs.getDate("DATA_DEVOLUCAO"));
				lista.add(e); 
			}
			// DESCONECTA
			con.desconecta();
		} catch (SQLException sqle) {
                    System.out.println(sqle.getMessage());
		}
		
		return lista;
	}
	
	public ArrayList<Emprestimo> listEmprestimoUsuario(int idUsuario) {
		ArrayList<Emprestimo> lista = new ArrayList<>();

		try {
			// CONECTA
			con.conecta();
			PreparedStatement preparaInstrucao;
			preparaInstrucao = con.getConexao().prepareStatement(LISTEMPRESTIMOUSUARIO);

			// SETA OS VALORES DA INSTRUCAO
			// OBS: PASSA OS PARAMETROS NA ORDEM DAS ? DA INSTRUCAO
			preparaInstrucao.setInt(1, idUsuario);

			// EXECUTA A INSTRUCAO
			ResultSet rs = preparaInstrucao.executeQuery();

			// TRATA O RETORNO DA CONSULTA
			while (rs.next()) { // enquanto houver registro
				Emprestimo e = new Emprestimo(rs.getInt("ID_EMPRESTIMO"), rs.getString("NOME_USUARIO"),
						rs.getString("COD_EXEMPLAR"), rs.getDate("DATA_EMPRESTIMO"), rs.getDate("DATA_DEVOLUCAO"));
				lista.add(e);
			}
			// DESCONECTA
			con.desconecta();
		} catch (SQLException sqle) {
                     System.out.println(sqle.getMessage());
		}

		return lista;
	}
	
	public ArrayList<Emprestimo> listEmprestimoExemplar(int idExemplar) {
		ArrayList<Emprestimo> lista = new ArrayList<>();

		try {
			// CONECTA
			con.conecta();
			PreparedStatement preparaInstrucao;
			preparaInstrucao = con.getConexao().prepareStatement(LISTEMPRESTIMOEXEMPLAR);

			// SETA OS VALORES DA INSTRUCAO
			// OBS: PASSA OS PARAMETROS NA ORDEM DAS ? DA INSTRUCAO
			preparaInstrucao.setInt(1, idExemplar);

			// EXECUTA A INSTRUCAO
			ResultSet rs = preparaInstrucao.executeQuery();

			// TRATA O RETORNO DA CONSULTA
			while (rs.next()) { // enquanto houver registro
				Emprestimo e = new Emprestimo(rs.getInt("ID_EMPRESTIMO"), rs.getString("NOME_USUARIO"),
						rs.getString("COD_EXEMPLAR"), rs.getDate("DATA_EMPRESTIMO"), rs.getDate("DATA_DEVOLUCAO"));
				lista.add(e);
			}
			// DESCONECTA
			con.desconecta();
		} catch (SQLException sqle) {
                     System.out.println(sqle.getMessage());
		}

		return lista;
	}
	
	public ArrayList<Emprestimo> listEmprestimoDataEmp(Date dtEmp) {
		ArrayList<Emprestimo> lista = new ArrayList<>();

		try {
			// CONECTA
			con.conecta();
			PreparedStatement preparaInstrucao;
			preparaInstrucao = con.getConexao().prepareStatement(LISTEMPRESTIMODATAEMP);

			// SETA OS VALORES DA INSTRUCAO
			// OBS: PASSA OS PARAMETROS NA ORDEM DAS ? DA INSTRUCAO
			preparaInstrucao.setDate(1, dtEmp);

			// EXECUTA A INSTRUCAO
			ResultSet rs = preparaInstrucao.executeQuery();

			// TRATA O RETORNO DA CONSULTA
			while (rs.next()) { // enquanto houver registro
				Emprestimo e = new Emprestimo(rs.getInt("ID_EMPRESTIMO"), rs.getString("NOME_USUARIO"),
						rs.getString("COD_EXEMPLAR"), rs.getDate("DATA_EMPRESTIMO"), rs.getDate("DATA_DEVOLUCAO"));
				lista.add(e);
			}
			// DESCONECTA
			con.desconecta();
		} catch (SQLException sqle) {
                     System.out.println(sqle.getMessage());
		}

		return lista;
	}
	
	public ArrayList<Emprestimo> listEmprestimoDataDev(Date dtDev) {
		ArrayList<Emprestimo> lista = new ArrayList<>();

		try {
			// CONECTA
			con.conecta();
			PreparedStatement preparaInstrucao;
			preparaInstrucao = con.getConexao().prepareStatement(LISTEMPRESTIMODATADEV);

			// SETA OS VALORES DA INSTRUCAO
			// OBS: PASSA OS PARAMETROS NA ORDEM DAS ? DA INSTRUCAO
			preparaInstrucao.setDate(1, dtDev);

			// EXECUTA A INSTRUCAO
			ResultSet rs = preparaInstrucao.executeQuery();

			// TRATA O RETORNO DA CONSULTA
			while (rs.next()) { // enquanto houver registro
				Emprestimo e = new Emprestimo(rs.getInt("ID_EMPRESTIMO"), rs.getString("NOME_USUARIO"),
						rs.getString("COD_EXEMPLAR"), rs.getDate("DATA_EMPRESTIMO"), rs.getDate("DATA_DEVOLUCAO"));
				lista.add(e);
			}
			// DESCONECTA
			con.desconecta();
		} catch (SQLException sqle) {
                     System.out.println(sqle.getMessage());
		}

		return lista;
	}
	
}
