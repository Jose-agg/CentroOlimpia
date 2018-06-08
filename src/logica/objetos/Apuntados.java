package logica.objetos;

public class Apuntados {

	private String codActividad;
	private String codInstalacion;
	private String codUsuario;
	private String fecha;
	private String horaInicio;
	private String codUsuarioR;
	private String apuntado;
	
	public Apuntados(String codActividad, String codInstalacion, String codUsuario, String fecha, String horaInicio,String codUsuarioR,String apuntado) {
		this.codActividad = codActividad;
		this.codInstalacion = codInstalacion;
		this.codUsuario = codUsuario;
		this.fecha = fecha;
		this.horaInicio = horaInicio;
		this.codUsuarioR = codUsuarioR;
		this.setApuntado(apuntado);
	}
	
	
	public Apuntados(String codActividad, String codInstalacion, String codUsuario, String fecha, String horaInicio,String codUsuarioR) {
		this.codActividad = codActividad;
		this.codInstalacion = codInstalacion;
		this.codUsuario = codUsuario;
		this.fecha = fecha;
		this.horaInicio = horaInicio;
		this.codUsuarioR = codUsuarioR;
	}
	
	public String getCodActividad() {
		return codActividad;
	}
	
	public void setCodActividad(String codActividad) {
		this.codActividad = codActividad;
	}
	
	public String getCodInstalacion() {
		return codInstalacion;
	}
	
	public void setCodInstalacion(String codInstalacion) {
		this.codInstalacion = codInstalacion;
	}
	
	public String getCodUsuario() {
		return codUsuario;
	}
	
	public void setCodUsuario(String codUsuario) {
		this.codUsuario = codUsuario;
	}
	
	public String getFecha() {
		return fecha;
	}
	
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
	public String getHoraInicio() {
		return horaInicio;
	}
	
	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}
	public String getCodUsuarioR() {
		return codUsuarioR;
	}
	
	public void setCodUsuarioR(String codUsuarioR) {
		this.codUsuarioR = codUsuarioR;
	}


	public String getApuntado() {
		return apuntado;
	}


	public void setApuntado(String apuntado) {
		this.apuntado = apuntado;
	}


	@Override
	public String toString() {
		return "Apuntados [codActividad=" + codActividad + ", codInstalacion="
				+ codInstalacion + ", codUsuario=" + codUsuario + ", fecha="
				+ fecha + ", horaInicio=" + horaInicio + ", codUsuarioR="
				+ codUsuarioR + ", apuntado=" + apuntado + "]";
	}
	
}
