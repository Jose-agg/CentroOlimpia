package logica.objetos;

public class SocioR {
	
	private String nSocio;
	private String codUsuario;
	
	public SocioR(String codUSuario, String nSocio){
		this.setCodUsuario(codUSuario);
		this.nSocio=nSocio;
		
		
	}

	public String getnSocio() {
		return nSocio;
	}

	public void setnSocio(String nSocio) {
		this.nSocio = nSocio;
	}

	public String getCodUsuario() {
		return codUsuario;
	}

	public void setCodUsuario(String codUsuario) {
		this.codUsuario = codUsuario;
	}


	

}
