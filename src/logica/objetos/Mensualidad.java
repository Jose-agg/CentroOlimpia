package logica.objetos;

public class Mensualidad {
	
	private String codUsuario;
	private double mCantidad;
	private int mes;
	private int year;
	
	public Mensualidad(String codUsuario, double mCantidad, int mes, int year) {
		this.codUsuario = codUsuario;
		this.mCantidad = mCantidad;
		this.mes = mes;
		this.year = year;
	}

	public String getCodUsuario() {
		return codUsuario;
	}

	public void setCodUsuario(String codUsuario) {
		this.codUsuario = codUsuario;
	}

	public double getmCantidad() {
		return mCantidad;
	}

	public void setmCantidad(double mCantidad) {
		this.mCantidad = mCantidad;
	}

	public int getMes() {
		return mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

}
