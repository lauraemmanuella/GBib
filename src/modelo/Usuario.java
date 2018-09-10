package modelo;

public class Usuario implements Comparable<Usuario>{
	private int idUsuario;
	private String nomeUsuario;
	private String telUsuario;
	private String emailUsuario;
	
	public Usuario(String nomeUsuario, String telUsuario, String emailUsuario) {
		this.nomeUsuario = nomeUsuario;
		this.telUsuario = telUsuario;
		this.emailUsuario = emailUsuario;
	}
	
	public Usuario(int idUsuario, String nomeUsuario, String telUsuario, String emailUsuario) {
		this.idUsuario = idUsuario;
		this.nomeUsuario = nomeUsuario;
		this.telUsuario = telUsuario;
		this.emailUsuario = emailUsuario;
	}
	
	public Usuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}
	
	public Usuario() {
		
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

	public String getTelUsuario() {
		return telUsuario;
	}

	public void setTelUsuario(String telUsuario) {
		this.telUsuario = telUsuario;
	}

	public String getEmailUsuario() {
		return emailUsuario;
	}

	public void setEmailUsuario(String emailUsuario) {
		this.emailUsuario = emailUsuario;
	}

	public String toString() {
		return nomeUsuario;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nomeUsuario == null) ? 0 : nomeUsuario.hashCode());
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
		Usuario other = (Usuario) obj;
		if (nomeUsuario == null) {
			if (other.nomeUsuario != null)
				return false;
		} else if (!nomeUsuario.equals(other.nomeUsuario))
			return false;
		return true;
	}

	@Override
	public int compareTo(Usuario u) {
		return this.getNomeUsuario().compareTo(u.getNomeUsuario());
	}
}
