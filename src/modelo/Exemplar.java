package modelo;

public class Exemplar implements Comparable<Exemplar>{
	private int idExemplar;
	private String codExemplar;
	private String nomeTitulo;
	private String nomeAutor;
	
	public Exemplar(String codExemplar, String nomeTitulo, String nomeAutor) {
		this.codExemplar = codExemplar;
		this.nomeTitulo = nomeTitulo;
		this.nomeAutor = nomeAutor;
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

	public String getNomeTitulo() {
		return nomeTitulo;
	}

	public void setNomeTitulo(String nomeTitulo) {
		this.nomeTitulo = nomeTitulo;
	}

	public String getNomeAutor() {
		return nomeAutor;
	}

	public void setNomeAutor(String nomeAutor) {
		this.nomeAutor = nomeAutor;
	}
	
	@Override
	public String toString() {
		return codExemplar;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codExemplar == null) ? 0 : codExemplar.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Exemplar other = (Exemplar) obj;
		if (codExemplar == null) {
			if (other.codExemplar != null)
				return false;
		} else if (!codExemplar.equals(other.codExemplar))
			return false;
		return true;
	}

	@Override
	public int compareTo(Exemplar e) {
		return this.getCodExemplar().compareTo(e.getCodExemplar());
	}
}
