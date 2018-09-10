package modelo;

public class Autor implements Comparable<Autor>{
	private int idAutor;
	private String nomeAutor;
	private String codAutor;
	
	
	
	public Autor(String nomeAutor, String codAutor) {
		this.nomeAutor = nomeAutor;
		this.codAutor = codAutor;
	}
	
	public Autor(int idAutor, String nomeAutor, String codAutor) {
		this.idAutor = idAutor;
		this.nomeAutor = nomeAutor;
		this.codAutor = codAutor;
	}

	public Autor(String nomeAutor) {
		this.nomeAutor = nomeAutor;
	}
	
	public Autor() {
		
	}
	
	public int getIdAutor() {
		return idAutor;
	}

	public void setIdAutor(int idAutor) {
		this.idAutor = idAutor;
	}

	public String getNomeAutor() {
		return nomeAutor;
	}

	public void setNomeAutor(String nomeAutor) {
		this.nomeAutor = nomeAutor;
	}

	public String getCodAutor() {
		return codAutor;
	}

	public void setCodAutor(String codAutor) {
		this.codAutor = codAutor;
	}
	
	public String toString() {
		return nomeAutor;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nomeAutor == null) ? 0 : nomeAutor.hashCode());
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
		Autor other = (Autor) obj;
		if (nomeAutor == null) {
			if (other.nomeAutor != null)
				return false;
		} else if (!nomeAutor.equals(other.nomeAutor))
			return false;
		return true;
	}

	@Override
	public int compareTo(Autor a) {
		return this.getNomeAutor().compareTo(a.getNomeAutor());
	}
}
