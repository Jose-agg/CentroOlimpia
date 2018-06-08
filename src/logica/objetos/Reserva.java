package logica.objetos;

import java.sql.SQLException;

public class Reserva {
	
	private String codUsuario;
	private String codInstalacion;
	private String desde;
	private String hasta;
	private String estado;
	private String fechaReserva;
	private Pago pago;
	private String recogidaLlaves;
	private String devolucionLlaves;
	private String cancelada;
	private String motivo;

	public Reserva(String codUsuario, String codInstalacion, String desde,
			String hasta,String fechaReserva,String estado,String cancelada) {
		this.codUsuario = codUsuario;
		this.codInstalacion = codInstalacion;
		this.desde = desde;
		this.hasta = hasta;
		this.estado = estado;
		this.fechaReserva = fechaReserva;
		recogidaLlaves="";
		devolucionLlaves="";
		this.cancelada=cancelada;
	}
	
	public int getYear() {
		String[] fecha = fechaReserva.split("/");
		return Integer.valueOf(fecha[2]);
	}
	
	public int getMonth() {
		String[] fecha = fechaReserva.split("/");
		return Integer.valueOf(fecha[1]);
	}
	
	public int getDay() {
		String[] fecha = fechaReserva.split("/");
		return Integer.valueOf(fecha[0]);
	}

	public String getRecogidaLlaves() {
		return recogidaLlaves;
	}

	public void setRecogidaLlaves(String recogidaLlaves) {
		this.recogidaLlaves = recogidaLlaves;
	}

	public String getDevolucionLlaves() {
		return devolucionLlaves;
	}

	public void setDevolucionLlaves(String devolucionLlaves) {
		this.devolucionLlaves = devolucionLlaves;
	}

	public String getCodUsuario() {
		return codUsuario;
	}

	public void setCodUsuario(String codUsuario) {
		this.codUsuario = codUsuario;
	}

	public String getCodInstalacion() {
		return codInstalacion;
	}

	public void setCodInstalacion(String codInstalacion) {
		this.codInstalacion = codInstalacion;
	}

	public String getDesde() {
		return desde;
	}

	public void setDesde(String desde) {
		this.desde = desde;
	}

	public String getHasta() {
		return hasta;
	}

	public void setHasta(String hasta) {
		this.hasta = hasta;
	}


	public String isEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getFechaReserva() {
		return fechaReserva;
	}

	public void setFechaReserva(String fechaReserva) {
		this.fechaReserva = fechaReserva;
	}
	
	

	public Pago getPago(){
		return this.pago;
	}
	
	public void crearPago() throws SQLException{
		pago= new Pago(codUsuario, codInstalacion, fechaReserva, desde, hasta);
	}

	public String getCancelada() {
		return cancelada;
	}

	public void setCancelada(String cancelada) {
		this.cancelada = cancelada;
	}

	@Override
	public String toString() {
		return "Reserva [codUsuario=" + codUsuario + ", codInstalacion="
				+ codInstalacion + ", desde=" + desde + ", hasta=" + hasta
				+ ", estado=" + estado + ", fechaReserva=" + fechaReserva
				+ ", pago=" + pago + ", recogidaLlaves=" + recogidaLlaves
				+ ", devolucionLlaves=" + devolucionLlaves + ", cancelada="
				+ cancelada + "]";
	}

	
	

	public void setMotivo(String motivo1) {
		this.motivo=motivo1;
	}
	public String getMotivo() {
		return motivo;
	}

	public void setPago(Pago p) {
		this.pago=p;
	}

}
