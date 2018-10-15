package modelo;

public class Titulo implements Comparable<Titulo> {
	private int idTitulo;
	private String nomeTitulo;
	private String codTitulo;
	private Autor autor;
	
	public Titulo(String nomeTitulo, String codTitulo, Autor autor) {
		this.nomeTitulo = nomeTitulo;
		this.codTitulo = codTitulo;
		this.autor = autor;
	}
	
	public Titulo(int idTitulo, String nomeTitulo, String codTitulo, int idAutor) {
		this.idTitulo = idTitulo;
		this.nomeTitulo = nomeTitulo;
		this.codTitulo = codTitulo;
		autor = new Autor();
		autor.setIdAutor(idAutor);
	}

	public Titulo(String nomeTitulo, Autor autor) {
		this.nomeTitulo = nomeTitulo;
		this.autor = autor;
	}
	
	public int getIdTitulo() {
		return idTitulo;
	}

	public void setIdTitulo(int idTitulo) {
		this.idTitulo = idTitulo;
	}

	public String getNomeTitulo() {
		return nomeTitulo;
	}

	public void setNomeTitulo(String nomeTitulo) {
		this.nomeTitulo = nomeTitulo;
	}

	public String getCodTitulo() {
		return codTitulo;
	}

	public void setCodTitulo(String codTitulo) {
		this.codTitulo = codTitulo;
	}

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}
	
        @Override
	public String toString() {
		return nomeTitulo;
	}

	@Override
	public int compareTo(Titulo t) {
		return this.getNomeTitulo().compareTo(t.getNomeTitulo());
	}
}
