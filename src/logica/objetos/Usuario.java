package logica.objetos;

public class Usuario {
	
	private String codUsuario;
	private String uNombre;
	private String apellido;
	private int telefono;
	private String dni;
	private String email;
	
	
	public Usuario(String codUsuario, String uNombre, String apellido,
			int telefono, String dni, String email) {
		this.codUsuario = codUsuario;
		this.uNombre = uNombre;
		this.apellido = apellido;
		this.telefono = telefono;
		this.dni = dni;
		this.email = email;
	}


	public String getCodUsuario() {
		return codUsuario;
	}


	public void setCodUsuario(String codUsuario) {
		this.codUsuario = codUsuario;
	}


	public String getuNombre() {
		return uNombre;
	}


	public void setuNombre(String uNombre) {
		this.uNombre = uNombre;
	}


	public String getApellido() {
		return apellido;
	}


	public void setApellido(String apellido) {
		this.apellido = apellido;
	}


	public int getTelefono() {
		return telefono;
	}


	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}


	public String getDni() {
		return dni;
	}


	public void setDni(String dni) {
		this.dni = dni;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}
	
	public String toString() {
		return this.uNombre;
	}
	
	
	
	
	

}
