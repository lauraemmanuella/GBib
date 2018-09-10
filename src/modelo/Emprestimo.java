package modelo;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class Emprestimo {
	private int idEmprestimo;
	private int idUsuario;
	private String nomeUsuario;
	private int idExemplar;
	private String codExemplar;
	private Date dataEmprestimo;
	private Date dataDevolucao;
	private String dtEmprestimo;
	private String dtDevolucao;
	
	public Emprestimo(int idUsuario, int idExemplar, Date dataEmprestimo, Date dataDevolucao) {
		this.idUsuario = idUsuario;
		this.idExemplar = idExemplar;
		this.dataEmprestimo = dataEmprestimo;
		this.dataDevolucao = dataDevolucao;
	}

	public Emprestimo(int idEmprestimo, String nomeUsuario, String codExemplar, Date dataEmprestimo, Date dataDevolucao) {
		this.idEmprestimo = idEmprestimo;
		this.nomeUsuario = nomeUsuario;
		this.codExemplar = codExemplar;
		this.dataEmprestimo = dataEmprestimo;
		this.dataDevolucao = dataDevolucao;

		this.dtEmprestimo = formataDataString(dataEmprestimo);
		this.dtDevolucao = formataDataString(dataDevolucao);
	}
	
	public Emprestimo() {
		
	}
	
	public String formataDataString(Date data) {
		if (data == null) {
			return null;
		}
		return new SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date(data.getTime()));
	}

	public int getIdEmprestimo() {
		return idEmprestimo;
	}
	
	public void setIdEmprestimo(int idEmprestimo) {
		this.idEmprestimo = idEmprestimo;
	}
	
	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}
	
	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}
	
	public int getIdExemplar() {
		return idExemplar;
	}

	public void setIdExemplar(int idExemplar) {
		this.idExemplar = idExemplar;
	}
	
	public String getCodExemplar() {
		return codExemplar;
	}
	
	public void setCodExemplar(String codExemplar) {
		this.codExemplar = codExemplar;
	}
	
	public Date getDataEmprestimo() {
		return dataEmprestimo;
	}

	public void setDataEmprestimo(Date dataEmprestimo) {
		this.dataEmprestimo = dataEmprestimo;
	}

	public Date getDataDevolucao() {
		return dataDevolucao;
	}
	
	public void setDataDevolucao(Date dataDevolucao) {
		this.dataDevolucao = dataDevolucao;
	}

	public String getDtEmprestimo() {
		return dtEmprestimo;
	}

	public void setDtEmprestimo(String dtEmprestimo) {
		this.dtEmprestimo = dtEmprestimo;
	}

	public String getDtDevolucao() {
		return dtDevolucao;
	}

	public void setDtDevolucao(String dtDevolucao) {
		this.dtDevolucao = dtDevolucao;
	}
}
