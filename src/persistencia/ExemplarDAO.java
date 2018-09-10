package persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import modelo.Autor;
import modelo.Exemplar;
import modelo.Titulo;

public class ExemplarDAO {
	private Conexao con = new Conexao();
	
	private final String INSERTEXEMPLAR = "INSERT INTO EXEMPLAR (COD_EXEMPLAR, ID_TITULO) VALUES (?,?)";
	private final String DELETEEXEMPLAR = "DELETE FROM EXEMPLAR WHERE COD_EXEMPLAR = ?";
	private final String LISTEXEMPLAR = "SELECT COD_EXEMPLAR, NOME_TITULO, NOME_AUTOR FROM EXEMPLAR, TITULO, AUTOR WHERE EXEMPLAR.ID_TITULO = TITULO.ID_TITULO AND TITULO.ID_AUTOR = AUTOR.ID_AUTOR";
	private final String LISTEXEMPLARAUTOR = "SELECT COD_EXEMPLAR, NOME_TITULO, NOME_AUTOR FROM EXEMPLAR, TITULO, AUTOR WHERE EXEMPLAR.ID_TITULO = TITULO.ID_TITULO AND TITULO.ID_AUTOR = AUTOR.ID_AUTOR AND NOME_AUTOR = ?";
	private final String LISTEXEMPLARTITULO = "SELECT COD_EXEMPLAR, NOME_TITULO, NOME_AUTOR FROM EXEMPLAR, TITULO, AUTOR WHERE EXEMPLAR.ID_TITULO = TITULO.ID_TITULO AND TITULO.ID_AUTOR = AUTOR.ID_AUTOR AND NOME_TITULO =?";
	private final String CODEXEMPLAR = "SELECT COD_EXEMPLAR FROM EXEMPLAR WHERE ID_EXEMPLAR = (SELECT MAX(ID_EXEMPLAR) FROM EXEMPLAR WHERE ID_TITULO = ?)";
	private final String IDEXEMPLAR = "SELECT ID_EXEMPLAR FROM EXEMPLAR WHERE COD_EXEMPLAR = ?";

	public boolean insertExemplar(String codExemplar, int idTitulo) {
		try {
			// CONECTA
			con.conecta();
			PreparedStatement preparaInstrucao;
			preparaInstrucao = con.getConexao().prepareStatement(INSERTEXEMPLAR);

			// SETA OS VALORES DA INSTRUCAO
			// OBS: PASSA OS PARAMETROS NA ORDEM DAS ? DA INSTRUCAO
			preparaInstrucao.setString(1, codExemplar.toUpperCase());
			preparaInstrucao.setInt(2, idTitulo);

			// EXECUTA A INSTRUCAO
			preparaInstrucao.execute();

			// DESCONECTA
			con.desconecta();
			
			return true;

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			return false;

		}
	}
	
	public boolean deleteExemplar(Exemplar e) {
		try {
			// CONECTA
			con.conecta();
			PreparedStatement preparaInstrucao;
			preparaInstrucao = con.getConexao().prepareStatement(DELETEEXEMPLAR);

			// SETA OS VALORES DA INSTRUCAO
			// OBS: PASSA OS PARAMETROS NA ORDEM DAS ? DA INSTRUCAO
			preparaInstrucao.setString(1, e.getCodExemplar());

			// EXECUTA A INSTRUCAO
			preparaInstrucao.execute();

			// DESCONECTA
			con.desconecta();
			
			return true;

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			return false;

		}
	}
	
	public ArrayList<Exemplar> listExemplar(Autor autor, Titulo titulo) {
		ArrayList<Exemplar> lista = new ArrayList<>(); 
		
		String instrucao, parametro = null;
		if(autor == null && titulo == null)
			instrucao = LISTEXEMPLAR;
		else if(titulo != null) {
			instrucao = LISTEXEMPLARTITULO;
			parametro = titulo.getNomeTitulo();
		}
		else {
			instrucao = LISTEXEMPLARAUTOR;
			parametro = autor.getNomeAutor();
		}

		try {
			// CONECTA
			con.conecta();
			PreparedStatement preparaInstrucao;
			preparaInstrucao = con.getConexao().prepareStatement(instrucao); 
			
			// SETA OS VALORES DA INSTRUCAO
			// OBS: PASSA OS PARAMETROS NA ORDEM DAS ? DA INSTRUCAO
			if(autor != null || titulo != null)
				preparaInstrucao.setString(1, parametro);
			
			// EXECUTA A INSTRUCAO
			ResultSet rs = preparaInstrucao.executeQuery(); 
			
			//TRATA O RETORNO DA CONSULTA
			while (rs.next()) { //enquanto houver registro
				Exemplar e = new Exemplar(rs.getString("COD_EXEMPLAR"), rs.getString("NOME_TITULO"), rs.getString("NOME_AUTOR"));
				lista.add(e); 
			}
			// DESCONECTA
			con.desconecta();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		Collections.sort(lista);
		return lista;
	}
	
	public String codExemplar(int idTitulo) {
		String cod = null;
		try {
			// CONECTA
			con.conecta();
			PreparedStatement preparaInstrucao;
			preparaInstrucao = con.getConexao().prepareStatement(CODEXEMPLAR); 
			
			//SETA OS VALORES DA INSTRUCAO
			//OBS: PASSA OS PARAMETROS NA ORDEM DAS ? DA INSTRUCAO
			preparaInstrucao.setInt(1, idTitulo);
			
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
	
	public PdfPTable geraTabela() {
		PdfPTable table = new PdfPTable(3);
		table.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.setComplete(true);
		try {
			table.setWidths(new int[]{1, 3, 2});
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		table.addCell(new PdfPCell(new Phrase("CÓDIGO")));
		table.addCell(new PdfPCell(new Phrase("TÍTULO")));
		table.addCell(new PdfPCell(new Phrase("AUTOR")));
		
		ArrayList<Exemplar> lista = listExemplar(null, null);
		Collections.sort(lista);
		
		for(Exemplar e: lista) {
			table.addCell(new PdfPCell(new Phrase(e.getCodExemplar())));
			table.addCell(new PdfPCell(new Phrase(e.getNomeTitulo())));
			table.addCell(new PdfPCell(new Phrase(e.getNomeAutor())));
		}
		
		return table;
	}
	
	public int idExemplar(String codExemplar) {
		int id = 0;
		try {
			// CONECTA
			con.conecta();
			PreparedStatement preparaInstrucao;
			preparaInstrucao = con.getConexao().prepareStatement(IDEXEMPLAR); 
			
			//SETA OS VALORES DA INSTRUCAO
			//OBS: PASSA OS PARAMETROS NA ORDEM DAS ? DA INSTRUCAO
			preparaInstrucao.setString(1, codExemplar.toUpperCase());
			
			// EXECUTA A INSTRUCAO
			ResultSet rs = preparaInstrucao.executeQuery(); 
			
			//TRATA O RETORNO DA CONSULTA
			if(rs.next())
				id = rs.getInt("ID_EXEMPLAR");
			
			// DESCONECTA
			con.desconecta();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}
	
}
