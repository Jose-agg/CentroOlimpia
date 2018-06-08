package logica.objetos;

public class Evento {
	
	private Actividad actividad;
	private String codInstalacion;
	private String codUsuarioR;
	private String fecha;
	private String horaInicio;
	private int plazas;
	
	public Evento(Actividad actividad, String codInstalacion, String fecha, String horaInicio, int plazas) {
		this.actividad=actividad;
		this.codInstalacion=codInstalacion;
		this.codUsuarioR="A-001";
		this.fecha = fecha;
		this.horaInicio = horaInicio;
		this.plazas = plazas;
	}

	public Actividad getActividad() {
		return actividad;
	}

	public void setActividad(Actividad actividad) {
		this.actividad = actividad;
	}

	public String getCodInstalacion() {
		return codInstalacion;
	}

	public void setCodInstalacion(String codInstalacion) {
		this.codInstalacion = codInstalacion;
	}

	public String getCodUsuarioR() {
		return codUsuarioR;
	}

	public void setCodUsuarioR(String codUsuarioR) {
		this.codUsuarioR = codUsuarioR;
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

	public int getPlazas() {
		return plazas;
	}

	public void setPlazas(int plazas) {
		this.plazas = plazas;
	}
	
}
