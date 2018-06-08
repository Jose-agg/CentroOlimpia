package logica.objetos;

public class Instalacion {
	
	private String codInstalacion;
	private String Inombre;
	
	
	public Instalacion(String codInstalacion, String inombre) {
		this.codInstalacion = codInstalacion;
		Inombre = inombre;
	}
	
	
	public String getCodInstalacion() {
		return codInstalacion;
	}
	public void setCodInstalacion(String codInstalacion) {
		this.codInstalacion = codInstalacion;
	}
	public String getInombre() {
		return Inombre;
	}
	public void setInombre(String inombre) {
		Inombre = inombre;
	}
	
	public String toString() {
		return Inombre;
	}
	
	
	
	
}
