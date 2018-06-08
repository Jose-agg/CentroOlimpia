package logica.objetos;

public class Actividad {

	private String codActividad;
	private String nombreA;
	private String codInstalacion; 		//hola5
	

	public Actividad(String codActividad, String anombre, String codInstalacion) {
		this.codActividad = codActividad;
		this.nombreA = anombre;
		this.codInstalacion = codInstalacion;
	}
	
	public Actividad(String codActividad, String anombre) {
		this.codActividad = codActividad;
		this.nombreA = anombre;
	}
	
	public String getCodActividad() {
		return codActividad;
	}
	public void setCodActividad(String codActividad) {
		this.codActividad = codActividad;
	}
	public String getNombreA() {
		return nombreA;
	}
	public void setNombreA(String nombreA) {
		this.nombreA = nombreA;
	}
	public String toString() {
		return nombreA;
	}
	
	public String getCodInstalacion() {
		return codInstalacion;
	}
	
	public void setCodInstalacion(String codInstalacion) {
		this.codInstalacion = codInstalacion;
	}	

}
