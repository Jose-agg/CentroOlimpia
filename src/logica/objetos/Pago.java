package logica.objetos;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;

import javax.swing.JOptionPane;

import dataBase.DataBase;

public class Pago {
	private String codInstalacion;
	private String codUsuario;
	private String fecha;
	private String horaInicio;
	private String codPago;
	private double cantidad;
	private double precio;
	private int horas;

	public Pago(String codInstalacion, String codUsuario, String fecha, String horaInicio,
			String codPago, double cantidad) {
		this.codInstalacion = codInstalacion;
		this.codUsuario = codUsuario;
		this.fecha = fecha;
		this.horaInicio = horaInicio;
		this.codPago = codPago;
		this.cantidad = cantidad;
	}

	public Pago(String codUsuario, String codInstalacion, String fecha, String horaInicio, String horaTerminar)
			throws SQLException {

		crearCodPago(fecha, horaInicio, codUsuario);
		this.codUsuario = codUsuario;
		this.codInstalacion = codInstalacion;
		this.precio = DataBase.getPrecioInstalacionCod(codInstalacion);
		this.fecha = fecha;
		this.horas = calcularHoras(horaInicio, horaTerminar);
	}

	private int calcularHoras(String horaInicio, String horaTerminar) {
		if (horaTerminar.length() == 4) {
			horaTerminar = "0" + horaTerminar;
		}
		char[] d = horaInicio.toCharArray();
		char[] h = horaTerminar.toCharArray();
		int id = Integer.parseInt("" + d[0] + d[1]);// desde
		int ih = Integer.parseInt("" + h[0] + h[1]);// hasta
		return ih - id;
	}

	public void crearCodPago(String fecha, String desde, String usuario) {
		String[] f = fecha.split("/");
		String[] d = desde.split(":");
		String[] u = usuario.split("-");
		this.codPago = f[0] + f[1] + f[2] + d[0] + u[0] + u[1] + "";

	}

	public String getCodPago() {
		return codPago;
	}

	public void setCodPago(String codPago) {
		this.codPago = codPago;
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

	public double getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public String toString(boolean devolucion) {
		String factura = generarFactura(devolucion);
		try {
			BufferedWriter fichero = new BufferedWriter(new FileWriter("facturas/FacturaEII-" + getCodPago()));
			fichero.write(factura);
			fichero.close();
		} catch (FileNotFoundException fnfe) {
			JOptionPane.showMessageDialog(null, "El archivo no se ha encontrado");
		} catch (IOException ioe) {
			new RuntimeException("Error de entrada/salida.");
		}
		return factura;
	}

	private String generarFactura(boolean devolucion) {
		Calendar c1 = Calendar.getInstance();
		StringBuilder b = new StringBuilder();
		b.append("RECIBO\n");
		b.append("SPORTline\n");
		b.append("NIF:XX-54968756-Y\n");
		b.append(
				"--------------------------------------------------------------------------------------------------------------------\n");
		b.append("Nº: " + getCodPago() + "\n");
		b.append(Integer.toString(c1.get(Calendar.DATE)) + "/" + Integer.toString(c1.get(Calendar.MONTH) + 1) + "/"
				+ Integer.toString(c1.get(Calendar.YEAR)) + "\n");
		b.append(
				"--------------------------------------------------------------------------------------------------------------------\n");
		b.append("Instalación/horas/Precio\n");
		if (devolucion)
			b.append(getCodInstalacion() + "/" + getHoras() + "/-" + getPrecio() + "\n");
		else
			b.append(getCodInstalacion() + "/" + getHoras() + "/" + getPrecio() + "\n");
		if (devolucion)
			b.append("Total: -" + getTotal() + "€\n");
		else
			b.append("Total: " + getTotal() + "€\n");
		if (devolucion)
			b.append("IVA: -" + getIva() + "\n");
		else
			b.append("IVA: " + getIva() + "\n");
		if (devolucion)
			b.append("Total+IVA: -" + getTotalIva() + "\n");
		else
			b.append("Total+IVA: " + getTotalIva() + "\n");
		// System.out.println(b.toString());
		return b.toString();
	}

	public double getTotalIva() {
		return getTotal() + getIva();
	}

	private double getTotal() {
		return horas * precio;
	}

	private double getIva() {
		return precio * 0.21;
	}

	private double getPrecio() {
		return precio;
	}

	public int getHoras() {
		return horas;
	}
}
