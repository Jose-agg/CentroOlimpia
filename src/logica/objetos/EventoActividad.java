package logica.objetos;

import java.sql.SQLException;

import dataBase.DataBase;

public class EventoActividad {

	private String codActividad;
	private String codInstalacion;
	private String codUsuario;
	private String fecha;
	private String horaInicio;
	private int plazas;
	private String horaFinal;
	
	public EventoActividad(String codActividad, String codInstalacion, String codUsuario, String fecha, String horaInicio, int plazas) {
		this.codActividad = codActividad;
		this.codInstalacion = codInstalacion;
		this.codUsuario = codUsuario;
		this.fecha = fecha;
		this.horaInicio = horaInicio;
		this.setPlazas(plazas);
		try {
			this.horaFinal=DataBase.getHoraFinalEvento(codInstalacion,codUsuario,fecha,horaInicio);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public EventoActividad(String fecha, String codActividad) {
		this.codActividad = codActividad;
		this.fecha = fecha;
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

	public int getPlazas() {
		return plazas;
	}

	public void setPlazas(int plazas) {
		this.plazas = plazas;
	}
	
	public String toString() {
		try {
			return ""+DataBase.getNombreInstalacion(codInstalacion);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return codActividad;
	}

	public String getHoraFinal() {
		return horaFinal;
	}

	public void setHoraFinal(String horaFinal) {
		this.horaFinal = horaFinal;
	}
	
	public String imprimir(){
		return "EventoActividad [codActividad=" + codActividad + ", codInstalacion="
				+ codInstalacion + ", codUsuario=" + codUsuario + ", fecha="
				+ fecha + ", horaInicio=" + horaInicio+ ", horaFinal="
						+ horaFinal + ", plazas="
								+ plazas + "]";
		
		
	}
	
}
