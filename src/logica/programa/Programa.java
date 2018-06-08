package logica.programa;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

import dataBase.DataBase;
import igu.admin.reservas.PanelMostrarReservasAdministrador;
import logica.objetos.Actividad;
import logica.objetos.Apuntados;
import logica.objetos.EventoActividad;
import logica.objetos.Instalacion;
import logica.objetos.Pago;
import logica.objetos.Reserva;
import logica.objetos.Usuario;

public class Programa {

	private Usuario usuarioActual;
	private ArrayList<Reserva> reservas;
	private ArrayList<Usuario> usuarios;
	private ArrayList<EventoActividad> eventos;
	private ArrayList<Actividad> actividades;
	private ArrayList<Apuntados> apuntados;
	private ArrayList<Instalacion> instalaciones;

	public Programa() throws SQLException {
		this.usuarioActual = null;
		this.reservas = DataBase.cargaReservas();
		this.usuarios = DataBase.cargaUsuarios();
		this.eventos = DataBase.cargarEventos();
		this.actividades = DataBase.cargarActividades();
		this.apuntados = DataBase.cargarApuntados();
		this.instalaciones = DataBase.cargarInstalaciones();
		for (Reserva r : reservas) {
			Pago p = DataBase.getPago(r.getCodInstalacion(), r.getCodUsuario(), r.getFechaReserva(), r.getDesde());
			if (p != null)
				r.setPago(p);
		}

	}

	public ArrayList<Reserva> getReservas() {
		return reservas;
	}

	public void cerrarSesion() {
		this.usuarioActual = null;
	}

	private boolean horasEntre(String desde, String hasta, String hora) {
		char[] d = desde.toCharArray();
		char[] h = hasta.toCharArray();
		char[] ho = hora.toCharArray();
		int id = Integer.parseInt("" + d[0] + d[1]);// desde
		int ih = Integer.parseInt("" + h[0] + h[1]);// hasta
		int iho = Integer.parseInt("" + ho[0] + ho[1]);// entre
		if (id <= iho && iho <= ih) {
			return true;
		} else {

			return false;
		}
	}

	/**
	 * Método que busca una determinada reserva de instalaciones en la aplicacion
	 * 
	 * @param codUs
	 *            El codigo de usuario
	 * @param codInstalacion
	 *            el codigo de la instalacion
	 * @param fecha
	 *            la fecha a buscar
	 * @param hora
	 *            la hora en la que se hace la reserva
	 * @return "Mi reserva" si es una reserva realizada por el usuario actual,
	 *         "Reservado" si fue reservado por cualquier otro usuario "Libre" si no
	 *         ha sido reservado.
	 */
	/**
	 * Método que busca una determinada reserva de instalaciones en la aplicacion
	 * 
	 * @param codUs
	 *            El codigo de usuario
	 * @param codInstalacion
	 *            el codigo de la instalacion
	 * @param fecha
	 *            la fecha a buscar
	 * @param hora
	 *            la hora en la que se hace la reserva
	 * @return "Mi reserva" si es una reserva realizada por el usuario actual,
	 *         "Reservado" si fue reservado por cualquier otro usuario "Libre" si no
	 *         ha sido reservado.
	 * @throws SQLException 
	 */
	public String buscarReservas(String codUs, String codInstalacion, String fecha, String hora) throws SQLException {
		for (Reserva res : DataBase.cargaReservas()) {

			if (res.getCancelada().equals("false")) {
				if (res.getCodUsuario().equals(PanelMostrarReservasAdministrador.ADMINISTRADOR)
						&& res.getFechaReserva().equals(fecha) && res.getCodInstalacion().equals(codInstalacion)
						&& res.isEstado().equals("0") && horasEntre(res.getDesde(), res.getHasta(), hora)
						&& !res.getHasta().equals(hora)) {
					return "Administración";

				} else if ((!res.getCodUsuario().equals(codUs) && res.getFechaReserva().equals(fecha)
						&& res.getCodInstalacion().equals(codInstalacion) && res.isEstado().equals("0")
						&& horasEntre(res.getDesde(), res.getHasta(), hora)) && !res.getHasta().equals(hora)) {
					return "Reservado";
				}

			}
		}
		return "Libre";
	}

	/**
	 * Metodo que comprueba si las horas de la reserva es una mayor que la otra
	 * 
	 * @param inicio
	 *            hora de inicio
	 * @param finale
	 *            hora de finalizacion
	 * @return
	 */
	public boolean comprobarHoras(String inicio, String finale) {
		if (finale.length() == 4) {
			finale = 0 + finale;
		}
		char[] i = inicio.toCharArray();
		char[] f = finale.toCharArray();
		int si = Integer.parseInt("" + i[0] + i[1]);
		int sf = Integer.parseInt("" + f[0] + f[1]);
		if (si < sf)
			return true;
		return false;
	}

	// -------------------------------------PANEL RESERVA SOCIO
	// -----------------------------------------------------------------------------------------------------------------

	public Usuario buscarUsuario(String codUsuario) {
		for (Usuario usuario : usuarios) {
			if (usuario.getCodUsuario().equals(codUsuario)) {
				return usuario;

			}
		}
		return null;

	}

	public Reserva buscarReservaSocio(String codUs, String codInstalacion, String fecha, String hora) {
		for (Reserva res : reservas) {
			if (res.getCodUsuario().equals(codUs) && res.getFechaReserva().equals(fecha)
					&& res.getCodInstalacion().equals(codInstalacion)
					&& horasEntre(res.getDesde(), res.getHasta(), hora) && !res.getHasta().equals(hora)) {
				// System.out.println(res.toString());
				return res;
			}

		}
		return null;
	}

	/**
	 * Método que busca una determinada reserva de instalaciones en la aplicacion
	 * 
	 * @param codUs
	 *            El codigo de usuario
	 * @param codInstalacion
	 *            el codigo de la instalacion
	 * @param fecha
	 *            la fecha a buscar
	 * @param hora
	 *            la hora en la que se hace la reserva
	 * @return "Mi reserva" si es una reserva realizada por el usuario actual,
	 *         "Reservado" si fue reservado por cualquier otro usuario "Libre" si no
	 *         ha sido reservado.
	 * @throws SQLException 
	 */
	public String buscarReservasSocios(String codUs, String codInstalacion, String fecha, String hora) throws SQLException {

		for (Reserva res : DataBase.cargaReservas()) {
			/*
			 * System.out.println(res.getCodUsuario()); System.out.println(codUs);
			 * System.out.println(codInstalacion); System.out.println(hora);
			 * System.out.println(fecha); System.out.println(res.getCodUsuario());
			 * System.out.println(res.getFechaReserva());
			 * System.out.println(res.getCodInstalacion());
			 * System.out.println(res.getDesde());
			 */
			if (res.getCancelada().equals("false")) {
				if (res.getCodUsuario().equals(codUs) && res.getFechaReserva().equals(fecha)
						&& res.getCodInstalacion().equals(codInstalacion)
						&& horasEntre(res.getDesde(), res.getHasta(), hora) && !res.getHasta().equals(hora)) {

					return "Mi Reserva";

				} else if (!res.getCodUsuario().equals(codUs) && res.getFechaReserva().equals(fecha)
						&& res.getCodInstalacion().equals(codInstalacion)
						&& horasEntre(res.getDesde(), res.getHasta(), hora) && !res.getHasta().equals(hora)) {
					return "Reservado";
					// horasEntre(res.getDesde(),res.getHasta(),hora)
				}
			}
		}
		return "Libre";
	}

	// Comprobar Que no pueda hacer mas de 3 reservas
	public String comprobarTodosDatos(String horaInicial, String horaFinal, String numSocio, String codInstalacion,
			String fecha) {

		for (Reserva reserva : reservas) {

			if (reserva.getCancelada().equals("false")) {
				// Caso 1 que haya una reserva para ese socio con esas horas, ese día, esa
				// instalación
				if (reserva.getCodUsuario().equals(numSocio) && reserva.getDesde().equals(horaInicial)
						&& reserva.getHasta().equals(horaFinal) && reserva.getCodInstalacion().equals(codInstalacion)
						&& reserva.getFechaReserva().equals(fecha)) {
					// System.out.println("ya reservo para esa hora");
					return "Ya reservo para esa hora.";
					// Caso 2 que no haya una reserva para ese socio pero si con esas horas, ese
					// día, esa instalación
				} else if (!reserva.getCodUsuario().equals(numSocio) && reserva.getDesde().equals(horaInicial)
						&& reserva.getHasta().equals(horaFinal) && reserva.getCodInstalacion().equals(codInstalacion)
						&& reserva.getFechaReserva().equals(fecha)) {
					// System.out.println("ya hay una reserva de otra persona");
					return "Hay una reserva de otra persona.";
					// Caso 3 que haya una reserva para ese socio con esas horas, ese día, pero no
					// esa instalación
				} else if (reserva.getCodUsuario().equals(numSocio) && reserva.getDesde().equals(horaInicial)
						&& reserva.getHasta().equals(horaFinal) && !reserva.getCodInstalacion().equals(codInstalacion)
						&& reserva.getFechaReserva().equals(fecha)) {
					// System.out.println("Reserva Simultánea en otra isntalacion misma hora");
					return "Reserva Simultánea en otra instalación misma hora.";

				} else if (!reserva.getCodUsuario().equals(numSocio) && reserva.getDesde().equals(horaInicial)
						&& reserva.getCodInstalacion().equals(codInstalacion)
						&& reserva.getFechaReserva().equals(fecha)) {
					// System.out.println("ya hay una reserva de otra persona");
					return "Hay una reserva de otra persona.";
				} else if (reserva.getCodUsuario().equals(numSocio) && reserva.getDesde().equals(horaInicial)
						&& !reserva.getCodInstalacion().equals(codInstalacion)
						&& reserva.getFechaReserva().equals(fecha)) {
					// System.out.println("Reserva Simultánea en otra isntalacion misma hora");
					return "Reserva Simultánea en otra instalación misma hora.";
				} else if (reserva.getCodUsuario().equals(numSocio) && reserva.getDesde().equals(horaInicial)
						&& reserva.getCodInstalacion().equals(codInstalacion)
						&& reserva.getFechaReserva().equals(fecha)) {
					// System.out.println("ya reservo para esa hora");
					return "Ya reservo para esa hora.";

				} else if (!validarHora(reserva.getDesde(), reserva.getHasta(), horaInicial, horaFinal)
						&& reserva.getCodInstalacion().equals(codInstalacion)
						&& reserva.getFechaReserva().equals(fecha)) {

					return "Hay una reserva de otra persona.";
				}

			}
		}

		return "Reservar";
	}

	public boolean validarHora(String horaInicial, String horaFinal, String horaInicialIntroducidaPorUsuario,
			String horaFinalIntroducidaPorUsuario) {
		if (horaInicialIntroducidaPorUsuario.compareTo(horaInicial) == 0
				|| horaFinalIntroducidaPorUsuario.compareTo(horaInicial) == 0
				|| horaInicialIntroducidaPorUsuario.compareTo(horaFinal) == 0
				|| horaFinalIntroducidaPorUsuario.compareTo(horaFinal) == 0
				|| (horaInicial.compareTo(horaInicialIntroducidaPorUsuario) > 0
						&& horaInicial.compareTo(horaFinalIntroducidaPorUsuario) < 0)
				|| (horaFinal.compareTo(horaInicialIntroducidaPorUsuario) > 0
						&& horaFinal.compareTo(horaFinalIntroducidaPorUsuario) < 0)
				|| (horaInicial.compareTo(horaInicialIntroducidaPorUsuario) < 0
						&& horaFinal.compareTo(horaFinalIntroducidaPorUsuario) > 0))
			return false;
		return true;
	}

	private boolean ComprobarActividadReserva(String horaInicial, String horaFinal, String numSocio, String fecha) {
		if (apuntadoSocioDia(numSocio, fecha) != null) {
			for (EventoActividad ea : eventoDiaSocio(eventoDia(fecha), apuntadoSocioDia(numSocio, fecha))) {

				return validarHora(ea.getHoraInicio(), ea.getHoraFinal(), horaInicial, horaFinal);

			}
		}
		return true;
	}

	private ArrayList<EventoActividad> eventoDia(String fecha) {
		ArrayList<EventoActividad> as = new ArrayList<EventoActividad>();
		for (EventoActividad eventoActividad : eventos) {
			if (eventoActividad.getFecha().equals(fecha)) {
				as.add(eventoActividad);

			}
		}
		return as;
	}

	private ArrayList<EventoActividad> eventoDiaSocio(ArrayList<EventoActividad> e, ArrayList<Apuntados> a) {
		ArrayList<EventoActividad> as = new ArrayList<EventoActividad>();
		for (EventoActividad eventoActividad : e) {
			for (Apuntados apd : a) {
				if (eventoActividad.getHoraInicio().equals(apd.getHoraInicio())) {
					as.add(eventoActividad);
				}

			}
		}
		return as;

	}

	private ArrayList<Apuntados> apuntadoSocioDia(String codU, String fecha) {
		ArrayList<Apuntados> as = new ArrayList<Apuntados>();
		for (Apuntados apuntados : apuntados) {

			if (apuntados.getApuntado().equals("true") && apuntados.getCodUsuario().equals(codU)
					&& apuntados.getFecha().equals(fecha)) {
				as.add(apuntados);

			}
		}
		return as;
	}

	public String ReservarParaSocio(String horaInicial, String horaFinal, String numSocio, String codInstalacion,
			String fecha, String string) {

		if (comprobarTodosDatos(horaInicial, horaFinal, numSocio, codInstalacion, fecha).equals("Reservar")) {

			// System.out.println(comprobarTodosDatos(horaInicial,horaFinal,numSocio,codInstalacion,fecha));

			if (ComprobarActividadReserva(horaInicial, horaFinal, numSocio, fecha)) {

				// Comprueba que no pueda hacer más de 2 reservas el socio

				// if(numeroDeReservasSocio(numSocio,fecha,codInstalacion,horaInicial,horaFinal)<2){
				if (buscarReservaSocio(numSocio, codInstalacion, fecha, horaInicial) == null) {
					// System.out.println("ENTRA 1");
					Reserva r = new Reserva(numSocio, codInstalacion, horaInicial, horaFinal, fecha, "0", "false");
					reservas.add(r);
					DataBase.hacerReservaSocio(numSocio, codInstalacion, horaInicial, horaFinal, fecha, "0", string);
				} else {
					// System.out.println("ENTRA 2");
					Reserva rs = buscarReservaSocio(numSocio, codInstalacion, fecha, horaInicial);
					rs.setCancelada("false");
					DataBase.pasarReservaANoCancelada(numSocio, codInstalacion, horaInicial, horaFinal, fecha, "0",
							string, "false");
				}

				// Reserva r = new Reserva(numSocio, codInstalacion, horaInicial, horaFinal,
				// fecha, "0","false");
				// System.out.println(r.toString());
				// reservas.add(r);
				// DataBase.hacerReservaSocio(numSocio, codInstalacion, horaInicial, horaFinal,
				// fecha, "0",string);
				return "Reserva";
			} else {
				return "Ya tiene una actividad para esa hora y esa fecha.";
			}
		} else {
			return comprobarTodosDatos(horaInicial, horaFinal, numSocio, codInstalacion, fecha);
		}

	}

	private boolean RangoReserva(String fecha, String codString, String horaInicial, String horaFinal, Reserva r) {
		char[] i = r.getDesde().toCharArray();
		char[] f = r.getHasta().toCharArray();
		int si = Integer.parseInt("" + i[0] + i[1]);// hora inicial reserva
		int sf = Integer.parseInt("" + f[0] + f[1]);// hora final reserva
		char[] in = horaInicial.toCharArray();
		int inicial = Integer.parseInt("" + in[0] + in[1]);// hora introducida
		char[] fin = horaFinal.toCharArray();
		int finall = Integer.parseInt("" + fin[0] + fin[1]);// hora introducida
		if (si < inicial && sf > finall) {
			return false;
		}

		return true;
	}

	private int numeroDeReservasSocio(String numS, String fecha, String codString, String horaInicial,
			String horaFinal) {
		int cont = 0;

		for (Reserva reserva : reservas) {
			char[] i = reserva.getDesde().toCharArray();
			char[] f = reserva.getHasta().toCharArray();
			int si = Integer.parseInt("" + i[0] + i[1]);
			int sf = Integer.parseInt("" + f[0] + f[1]);
			if (reserva.getCodUsuario().equals(numS) && reserva.getFechaReserva().equals(fecha)
					&& reserva.getCodInstalacion().equals(codString)) {
				/*
				 * System.out.println("ENTRA"); System.out.println(si+1);
				 * System.out.println(si+2); System.out.println("Hora inicial"+ si);
				 * System.out.println("Hora final"+ sf);
				 */
				if (si + 1 == sf) {
					cont++;
				} else if (si + 2 == sf) {
					cont = cont + 2;
				}

			}

		}
		// System.out.println(cont);
		return cont;
	}

	public boolean hayReserva(String codU, String codI, String fecha) {
		for (Reserva reserva : reservas) {
			if (reserva.getCodUsuario().equals(codU) && reserva.getCodInstalacion().equals(codI)
					&& reserva.getFechaReserva().equals(fecha)) {
				return true;
			}
		}
		return false;
	}

	public boolean cancelarReserva(ArrayList<Reserva> reservasDelDia, String string) {
		for (Reserva reserva : reservasDelDia) {
			if (reserva.getDesde().equals(string)) {
				// System.out.println(reserva);
				reserva.setCancelada("true");
				DataBase.pasarReservaACancelada(reserva.getCodUsuario(), reserva.getCodInstalacion(),
						reserva.getDesde(), reserva.getHasta(), reserva.getFechaReserva(), "0", reserva.getCancelada());
				return true;

			}
		}
		return false;
	}

	// -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Método que devuelve solo aquellas reservas realzidas por socios
	 * 
	 * @return
	 */
	public ArrayList<Reserva> getReservasSocios() {
		ArrayList<Reserva> reservasSocios = new ArrayList<Reserva>();
		for (Reserva reserva : reservas) {
			if (reserva.getCodUsuario().split("-")[0].equals("S"))
				reservasSocios.add(reserva);
		}
		return reservasSocios;
	}

	// Metodos de Jose que implementan el tema de las reserva para Socios y No
	// Socios

	public String comprobarIncompatibilidadHorario(String horaInicial, String horaFinal, String numSocio,
			String codInstalacion, String fecha) {
		for (Reserva reserva : reservas) {
			if (reserva.getCancelada().equals("false")) {
				// Caso 1 que haya una reserva para ese socio con esas horas, ese día, esa
				// instalación
				if (reserva.getCodUsuario().equals(numSocio) && reserva.getDesde().equals(horaInicial)
						&& reserva.getHasta().equals(horaFinal) && reserva.getCodInstalacion().equals(codInstalacion)
						&& reserva.getFechaReserva().equals(fecha)) {
					return "reservaRepetida";
					// Caso 2 que no haya una reserva para ese socio pero si con esas horas, ese
					// día, esa instalación
				} else if (!reserva.getCodUsuario().equals(numSocio) && reserva.getDesde().equals(horaInicial)
						&& reserva.getHasta().equals(horaFinal) && reserva.getCodInstalacion().equals(codInstalacion)
						&& reserva.getFechaReserva().equals(fecha)) {
					return "ocupada";
					// Caso 3 que haya una reserva para ese socio con esas horas, ese día, pero no
					// esa instalación
				} else if (reserva.getCodUsuario().equals(numSocio) && reserva.getDesde().equals(horaInicial)
						&& reserva.getHasta().equals(horaFinal) && !reserva.getCodInstalacion().equals(codInstalacion)
						&& reserva.getFechaReserva().equals(fecha)) {
					return "simultaneo";

				} else if (!reserva.getCodUsuario().equals(numSocio) && reserva.getDesde().equals(horaInicial)
						&& reserva.getCodInstalacion().equals(codInstalacion)
						&& reserva.getFechaReserva().equals(fecha)) {
					return "ocupada";
				} else if (reserva.getCodUsuario().equals(numSocio) && reserva.getDesde().equals(horaInicial)
						&& !reserva.getCodInstalacion().equals(codInstalacion)
						&& reserva.getFechaReserva().equals(fecha)) {
					return "simultaneo";
				} else if (reserva.getCodUsuario().equals(numSocio) && reserva.getDesde().equals(horaInicial)
						&& reserva.getCodInstalacion().equals(codInstalacion)
						&& reserva.getFechaReserva().equals(fecha)) {
					return "reservaRepetida";
				} else if (!validarHora(reserva.getDesde(), reserva.getHasta(), horaInicial, horaFinal)
						&& reserva.getCodInstalacion().equals(codInstalacion)
						&& reserva.getFechaReserva().equals(fecha)) {

					return "error";
				}
			}
		}
		return "continuar";
	}

	// MODIFICADO PARA INSERTAR RESERVAS CON CANCELADO A FALSE
	public String comprobarReservaSociosAdmin(String codUser, String codInsta, String fechaSeleccionada,
			String horaInicio, String horaFinal, boolean noSocio, String tipoPago) throws SQLException {
		String salida = comprobarIncompatibilidadHorario(horaInicio, horaFinal, codUser, codInsta, fechaSeleccionada);
		if (!salida.equals("continuar")) {
			return salida;
		}
		// if(!noSocio &&
		// ComprobarActividadReserva(horaInicio,horaFinal,codUser,fechaSeleccionada)) {
		Reserva reserva1 = null;
		boolean reabierta = false;
		if (buscarReservaSocio(codUser, codInsta, fechaSeleccionada, horaInicio) == null) {
			reserva1 = new Reserva(codUser, codInsta, horaInicio, horaFinal, fechaSeleccionada, "0", "false");
		} else {
			reserva1 = buscarReservaSocio(codUser, codInsta, fechaSeleccionada, horaInicio);
			reserva1.setCancelada("false");
			DataBase.pasarReservaANoCancelada(codUser, codInsta, horaInicio, horaFinal, fechaSeleccionada, "0",
					tipoPago, "false");
			reabierta = true;
		}

		salida = "userNoEncontrado";
		for (Usuario user : usuarios) {
			if (user.getCodUsuario().equals(codUser)) {
				salida = "valido";
			}
		}
		if (salida.equals("valido") && !reabierta) {
			if (noSocio) {
				reserva1.crearPago();
			}
			reservas.add(reserva1);
			DataBase.setReservaSociosAdmin(reserva1.getCodUsuario(), reserva1.getCodInstalacion(), reserva1.getDesde(),
					reserva1.getHasta(), reserva1.getFechaReserva(), tipoPago);
			if (noSocio) {
				DataBase.setPago(reserva1.getCodInstalacion(), reserva1.getCodUsuario(), reserva1.getFechaReserva(),
						reserva1.getDesde(), reserva1.getPago().getCodPago(), reserva1.getPago().getTotalIva());

			}
			if (noSocio) {
				return "AddNS";
			}
			salida = "añadidos";
			// }
			if (reabierta) {
				salida = "añadidos";
			}
			return salida;
		} else {
			return "actividadSolapada";
		}

	}

	public String agregarNuevoNoSocio(String dni, String nombre, String apellido, String telefono, String correo)
			throws SQLException {
		int num = 0;
		for (Usuario user : usuarios) {
			if (user.getCodUsuario().contains("NS-")) {
				String var = user.getCodUsuario();
				String[] array = var.split("-");
				num = Integer.parseInt(array[1]) + 1;

			}
		}
		String codUsuario = "NS-" + num;
		Usuario nuevoNoSocio = new Usuario(codUsuario, nombre, apellido, Integer.parseInt(telefono), dni, correo);
		usuarios.add(nuevoNoSocio);
		DataBase.insertarUnNoSocio(nuevoNoSocio);
		return codUsuario;
	}

	public ArrayList<Reserva> getReservasParaUnaInstalacionEnUnDia(String codInsta, String fecha) throws SQLException {
		reservas = DataBase.cargaReservas();
		ArrayList<Reserva> reservasLocal = new ArrayList<Reserva>();
		for (Reserva reserva : reservas) {
			if (reserva.getCodInstalacion().equals(codInsta) && reserva.getFechaReserva().equals(fecha)) {
				reservasLocal.add(reserva);
			}
		}
		return reservasLocal;
	}

	public void modificarHorasLlaves(String horaRecogida, String horaDevolucion, Reserva res) throws SQLException {
		res.setRecogidaLlaves(horaRecogida);
		res.setDevolucionLlaves(horaDevolucion);
		DataBase.actulizarUsoLlaves(horaRecogida, horaDevolucion, res);
	}
	// MODIFICADO PARA INSERTAR RESERVAS CON CANCELADO A FALSE
	// MIRAR!!!!!!!!!!!!!!!!!!!!!!!

	public void crearReserva(String codUsuario, String codInstalacion, String desde, String hasta, String estado,
			String fecha) throws SQLException {
		Reserva r = new Reserva(codUsuario, codInstalacion, desde, hasta, fecha, estado, "false");
		reservas.add(r);
		DataBase.setReserva(codUsuario, codInstalacion, desde, hasta, fecha, estado);
	}

	public Pago buscarPago(String usuario, String instalacion, String fecha, String desde, String hasta)
			throws SQLException {
		String[] help = usuario.split("-");
		if (!help[0].equals("NS"))
			usuario = DataBase.getCodigoUsuarioNoSocio(usuario);
		for (Reserva res : reservas) {
			if (res.getCodUsuario().equals(usuario) && res.getCodInstalacion().equals(instalacion)
					&& res.getFechaReserva().equals(fecha) && res.getDesde().equals(desde)
					&& res.getHasta().equals(hasta)) {
				return res.getPago();
			}
		}
		return null;
	}

	public void cancelarReservaSocioAdmin(String fecha, String codInstalacion, String codUsuario, String horaInicio,
			String horaFin, String motivo1) throws SQLException {
		DataBase.cancelarReserva(fecha, codInstalacion, codUsuario, horaInicio, horaFin, motivo1);
		int index = 0;
		for (Reserva r : reservas) {
			if (r.getCodInstalacion().equals(codInstalacion) && r.getFechaReserva().equals(fecha)
					&& r.getCodUsuario().equals(codUsuario) && r.getDesde().equals(horaInicio)
					&& r.getHasta().equals(horaFin)) {

				reservas.get(index).setCancelada("true");
				reservas.get(index).setMotivo(motivo1);
				break;
			}
			index++;
		}
	}

	public void cancelarReservaNoSocioAdministracion(String fecha, String codInstalacion, String DNI, String horaInicio,
			String horaFin, String motivo1) throws SQLException {
		DataBase.cancelarReservaNoSocioAdministracion(fecha, codInstalacion, DNI, horaInicio, horaFin, motivo1);
		String codUsuario = DataBase.getCodigoUsuarioNoSocio(DNI);
		int index = 0;
		for (Reserva r : reservas) {
			if (r.getCodInstalacion().equals(codInstalacion) && r.getFechaReserva().equals(fecha)
					&& r.getCodUsuario().equals(codUsuario) && r.getDesde().equals(horaInicio)
					&& r.getHasta().equals(horaFin)) {
				reservas.get(index).setCancelada("true");
				reservas.get(index).setMotivo(motivo1);
				break;
			}
			index++;
		}

	}

	public String cancelarReservaAdmin(String fecha, String codInstalacion, String horaInicio, String horaFin,
			String motivo1) throws SQLException {
		String usuario = DataBase.cancelarReservaAdmin(fecha, codInstalacion, horaInicio, horaFin, motivo1);
		int index = 0;
		for (Reserva r : reservas) {
			if (r.getCodInstalacion().equals(codInstalacion) && r.getFechaReserva().equals(fecha)
					&& r.getDesde().equals(horaInicio) && r.getHasta().equals(horaFin)) {
				reservas.get(index).setCancelada("true");
				reservas.get(index).setMotivo(motivo1);
				return usuario;
			}
			index++;
		}
		return null;
	}

	public boolean coincideFecha(String fecha) {
		String[] aux = fecha.split("/");
		Calendar c1 = Calendar.getInstance();
		if (Integer.toString(c1.get(Calendar.DATE)).equals(aux[0])
				&& Integer.toString(c1.get(Calendar.MONTH) + 1).equals(aux[1])
				&& Integer.toString(c1.get(Calendar.YEAR)).equals(aux[2])) {
			return true;
		}
		return false;
	}

	public String buscarNombreEventoActividades(String codActividad, String fecha, String hora) throws SQLException {
		hora = hora.split(" ")[0];
		String aux = "";
		int i = 0;
		boolean isDos = false;
		for (EventoActividad evento : eventos) {
			if (evento.getCodActividad().equals(codActividad) && evento.getFecha().equals(fecha)
					&& horasEntre(evento.getHoraInicio(), evento.getHoraFinal(), hora)
					&& !evento.getHoraFinal().equals(hora)) {
				if (!isDos) {
					try {
						if (plazasTotales(codActividad, evento.getCodInstalacion(), fecha,
								evento.getHoraInicio()) == -1) {
							aux = "<html><p>" + DataBase.getNombreInstalacion(evento.getCodInstalacion())
									+ "<br>Plazas ilimitadas" + "<br>";
						} else {
							aux = "<html><p>" + DataBase.getNombreInstalacion(evento.getCodInstalacion())
									+ "<br>Plazas Totales: "
									+ plazasTotales(codActividad, evento.getCodInstalacion(), fecha,
											evento.getHoraInicio())
									+ "<br>Plazas Disponibles: " + plazasDisponibles2(codActividad,
											evento.getCodInstalacion(), fecha, evento.getHoraInicio())
									+ "<br>";
							isDos = true;
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					try {
						if (plazasTotales(codActividad, evento.getCodInstalacion(), fecha,
								evento.getHoraInicio()) == -1) {
							aux = aux + "<br>-----------<br><br>"
									+ DataBase.getNombreInstalacion(evento.getCodInstalacion())
									+ "<br>Plazas ilimitadas<br>";
						} else {
							aux = aux + "<br>-----------<br><br>"
									+ DataBase.getNombreInstalacion(evento.getCodInstalacion()) + "<br>Plazas Totales: "
									+ plazasTotales(codActividad, evento.getCodInstalacion(), fecha,
											evento.getHoraInicio())
									+ "<br>Plazas Disponibles: " + plazasDisponibles2(codActividad,
											evento.getCodInstalacion(), fecha, evento.getHoraInicio())
									+ "<br>";
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
		}

		if (!aux.equals("")) {
			aux = aux + "</p></html>";
		}
		return aux;
	}

	public String buscarNombreEventoActividadesSocios(String codActividad, String fecha, String hora, String codSocio)
			throws SQLException {
		hora = hora.split(" ")[0];
		String aux = "";
		int i = 0;
		boolean isDos = false;
		boolean socioApuntado = false;
		for (EventoActividad evento : eventos) {
			socioApuntado = false;
			socioApuntado = comprobarSiSocioApuntado(evento, codSocio);
			if (evento.getCodActividad().equals(codActividad) && evento.getFecha().equals(fecha)
					&& horasEntre(evento.getHoraInicio(), evento.getHoraFinal(), hora)
					&& !evento.getHoraFinal().equals(hora)) {
				if (!isDos) {
					try {
						if (plazasTotales(codActividad, evento.getCodInstalacion(), fecha,
								evento.getHoraInicio()) == -1) {
							aux = "<html><p>" + DataBase.getNombreInstalacion(evento.getCodInstalacion())
									+ "<br>Plazas ilimitadas" + "<br>";
						} else {
							aux = "<html><p>" + DataBase.getNombreInstalacion(evento.getCodInstalacion())
									+ "<br>Plazas Totales: "
									+ plazasTotales(codActividad, evento.getCodInstalacion(), fecha,
											evento.getHoraInicio())
									+ "<br>Plazas Disponibles: " + plazasDisponibles2(codActividad,
											evento.getCodInstalacion(), fecha, evento.getHoraInicio())
									+ "<br>";
						}
						if (socioApuntado) {
							aux += "Ya apuntado<br>";
						}
						isDos = true;
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					try {
						if (plazasTotales(codActividad, evento.getCodInstalacion(), fecha,
								evento.getHoraInicio()) == -1) {
							aux = aux + "<br>-----------<br><br>"
									+ DataBase.getNombreInstalacion(evento.getCodInstalacion())
									+ "<br>Plazas ilimitadas<br>";
						} else {
							aux = aux + "<br>-----------<br><br>"
									+ DataBase.getNombreInstalacion(evento.getCodInstalacion()) + "<br>Plazas Totales: "
									+ plazasTotales(codActividad, evento.getCodInstalacion(), fecha,
											evento.getHoraInicio())
									+ "<br>Plazas Disponibles: " + plazasDisponibles2(codActividad,
											evento.getCodInstalacion(), fecha, evento.getHoraInicio())
									+ "<br>";
						}
						if (socioApuntado) {
							aux += "Ya apuntado<br>";
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}

		if (!aux.equals("")) {
			aux = aux + "</p></html>";
		}
		return aux;
	}

	private boolean comprobarSiSocioApuntado(EventoActividad evento, String codSocio) {

		for (Apuntados apuntados : apuntados) {
			if (apuntados.getCodUsuario().equals(codSocio)) {
				if (apuntados.getApuntado().equals("true")) {
					if (apuntados.getCodActividad().equals(evento.getCodActividad())
							&& apuntados.getCodInstalacion().equals(evento.getCodInstalacion())
							&& apuntados.getFecha().equals(evento.getFecha())
							&& apuntados.getHoraInicio().equals(evento.getHoraInicio())) {
						return true;
					}
				}
			}
		}
		return false;

	}

	public EventoActividad buscarEventoActividades(String codActividad, String fecha, String hora) throws SQLException {
		hora = hora.split(" ")[0];
		this.eventos =DataBase.cargarEventos();
		for (EventoActividad evento : eventos) {
			if (evento.getCodActividad().equals(codActividad) && evento.getFecha().equals(fecha)
					&& evento.getHoraInicio().equals(hora)) {
				return evento;
			}
		}
		return null;
	}

	public int plazasTotales(String codActividad, String codInstalacion, String fecha, String horaInicio) {
		try {
			for (EventoActividad e : DataBase.cargarEventos()) {
				if (e.getCodActividad().equals(codActividad) && e.getCodInstalacion().equals(codInstalacion)
						&& e.getFecha().equals(fecha) && e.getHoraInicio().equals(horaInicio)) {
					return e.getPlazas();
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	public int plazasDisponibles2(String codActividad, String codInstalacion, String fecha, String horaInicio)
			throws SQLException {
		int aux = 0;
		for (Apuntados a : DataBase.cargarApuntados()) {
			if (a.getApuntado().equals("true")) {
				if (a.getCodActividad().equals(codActividad) && a.getCodInstalacion().equals(codInstalacion)
						&& a.getFecha().equals(fecha) && a.getHoraInicio().equals(horaInicio)) {

					aux++;
				}
			}
		}
		return plazasTotales(codActividad, codInstalacion, fecha, horaInicio) - aux;
	}

	public int plazasDisponibles(String codActividad, String codInstalacion, String fecha, String horaInicio) {
		int aux = 0;
		for (Apuntados a : apuntados) {
			if (a.getApuntado().equals("true")) {
				if (a.getCodActividad().equals(codActividad) && a.getCodInstalacion().equals(codInstalacion)
						&& a.getFecha().equals(fecha) && a.getHoraInicio().equals(horaInicio)) {

					aux++;
				}
			}
		}
		return plazasTotales(codActividad, codInstalacion, fecha, horaInicio) - aux;
	}

	public ArrayList<Actividad> getActividades() {
		try {
			this.actividades = DataBase.cargarActividades();
			return actividades;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public int getAlturaFila(String[] values) {
		int suma = 0;
		for (int i = 0; i < values.length; i++) {
			String[] a = values[i].split("<br>");
			if (suma < a.length) {
				suma = a.length;
			}
		}
		return suma * 20;
	}

	public String apuntarSocioActividadAdmin(String codU, String codActividad, String h, String obtenerFecha,
			int plazasA, EventoActividad ev, String nombreA, String i) throws SQLException {
		System.out.println( plazasDisponibles(codActividad, i, ev.getFecha(), h));
		if (plazasDisponibles(codActividad, i, ev.getFecha(), h)!=0) {
			if (comprobarDatosApuntarse(codU, codActividad, h, obtenerFecha, i, ev).equals("Apuntado")) {
				if (comprobarActividadReserva(codU, codActividad, h, obtenerFecha).equals("Apuntado")) {
					if (buscarApuntado(codActividad, i, codU, obtenerFecha, h, ev.getCodUsuario(), "false") == null) {
						Apuntados a = new Apuntados(codActividad, i, codU, obtenerFecha, h, ev.getCodUsuario(), "true");
						apuntados.add(a);
						DataBase.apuntarSocioActividadAdmin(codU, codActividad, i, ev.getCodUsuario(), obtenerFecha, h);
						this.apuntados = DataBase.cargarApuntados();
						return "Apuntado";
					} else {
						Apuntados a = buscarApuntado(codActividad, i, codU, obtenerFecha, h, ev.getCodUsuario(),
								"false");
						a.setApuntado("true");
						DataBase.pasarApuntadoTrue(a.getCodUsuario(), a.getCodActividad(), a.getCodInstalacion(),
								a.getCodUsuarioR(), a.getFecha(), a.getHoraInicio());
						this.apuntados = DataBase.cargarApuntados();
						return "Apuntado";
					}

				} else {
					return comprobarActividadReserva(codU, codActividad, h, obtenerFecha);
				}

			} else {
				return comprobarDatosApuntarse(codU, codActividad, h, obtenerFecha, i, ev);
			}

		}
		return "No hay plazas para esta actividad.";
	}

	private String comprobarActividadReserva(String codU, String codActividad, String h, String obtenerFecha) throws SQLException {
		EventoActividad ev = buscarEventoActividades(codActividad, obtenerFecha, h);
		for (Reserva res : reservas) {
			// Caso 1 que para ese usuario ya haya una reserva a esa misma hora para esa
			// misma fecha
			if (res.getCodUsuario().equals(codU) && res.getCancelada().equals("false") && res.getDesde().equals(h)
					&& res.getFechaReserva().equals(obtenerFecha)) {
				return "Tiene una reserva de una instalación a esa misma hora.";
			} else if (res.getCodUsuario().equals(codU) && res.getCancelada().equals("false")
					&& res.getFechaReserva().equals(obtenerFecha)
					&& !validarHora(res.getDesde(), res.getHasta(), ev.getHoraInicio(), ev.getHoraFinal())) {
				return "Tiene una reserva de una instalación a esa misma hora.";
			}
		}
		return "Apuntado";

	}

	private boolean rangoActividadReserva(String horaI, String horaF, String horaA) {
		char[] i = horaI.toCharArray();
		char[] f = horaF.toCharArray();
		int si = Integer.parseInt("" + i[0] + i[1]);// hora inicial reserva
		int sf = Integer.parseInt("" + f[0] + f[1]);// hora final reserva
		char[] in = horaA.toCharArray();
		int inicial = Integer.parseInt("" + in[0] + in[1]);// hora introducida
		if (si < inicial && inicial < sf) {
			return false;
		}

		return true;

	}

	private boolean rangoActividad(String horaI, String horaF, String horaA) {
		char[] i = horaI.toCharArray();
		char[] f = horaF.toCharArray();
		int si = Integer.parseInt("" + i[0] + i[1]);// hora inicial reserva
		int sf = Integer.parseInt("" + f[0] + f[1]);// hora final reserva
		char[] in = horaA.toCharArray();
		int inicial = Integer.parseInt("" + in[0] + in[1]);// hora introducida

		if (si <= inicial && inicial <= sf) {

			return false;

		}

		return true;

	}

	private String comprobarDatosApuntarse(String codU, String codActividad, String h, String obtenerFecha, String i,
			EventoActividad ev) throws SQLException {
		for (Apuntados ap : apuntados) {
			// Caso 1 que ya se haya apuntado a la misma actividad, a la misma
			// hora y en la misma fecha.
			if (ap.getApuntado().equals("true")) {

				if (ap.getCodUsuario().equals(codU) && ap.getCodActividad().equals(codActividad)
						&& ap.getFecha().equals(obtenerFecha) && ap.getHoraInicio().equals(h)
						&& ap.getCodInstalacion().equals(i)) {
					return "Ya se ha apuntado a esa actividad.";
				} else if (ap.getCodUsuario().equals(codU) && !ap.getCodActividad().equals(codActividad)
						&& ap.getFecha().equals(obtenerFecha) && ap.getHoraInicio().equals(h)) {
					return "Ya se ha apuntado a una actividad a esa hora.";
				} else if (ap.getCodUsuario().equals(codU) && ap.getCodActividad().equals(codActividad)
						&& ap.getFecha().equals(obtenerFecha) && ap.getHoraInicio().equals(h)
						&& !ap.getCodInstalacion().equals(i)) {
					return "Actividad Simultánea en otra instalación misma hora.";

				} else if (buscarEventoActividades(ap.getCodActividad(), ap.getFecha(), ap.getHoraInicio()) != null) {
					EventoActividad e = buscarEventoActividades(ap.getCodActividad(), ap.getFecha(),
							ap.getHoraInicio());
					EventoActividad evs = buscarEventoActividades(codActividad, obtenerFecha, h);

					if (ap.getCodUsuario().equals(codU) && !ap.getCodActividad().equals(codActividad)
							&& ap.getFecha().equals(obtenerFecha) && ap.getCodInstalacion().equals(i)
							&& !validarHora(e.getHoraInicio(), e.getHoraFinal(), evs.getHoraInicio(),
									evs.getHoraFinal())) {

						return "Ya se ha apuntado a una actividad a esa hora.";
					} else if (ap.getCodUsuario().equals(codU) && ap.getCodActividad().equals(codActividad)
							&& ap.getFecha().equals(obtenerFecha) && !ap.getCodInstalacion().equals(i)
							&& !validarHora(e.getHoraInicio(), e.getHoraFinal(), evs.getHoraInicio(),
									evs.getHoraFinal())) {

						return "Actividad Simultánea en otra instalación misma hora.";
					} else if (ap.getCodUsuario().equals(codU) && ap.getCodActividad().equals(codActividad)
							&& ap.getFecha().equals(obtenerFecha) && ap.getCodInstalacion().equals(i)
							&& !validarHora(e.getHoraInicio(), e.getHoraFinal(), evs.getHoraInicio(),
									evs.getHoraFinal())) {

						return "Ya se ha apuntado a una actividad a esa hora.";
					} else if (ap.getCodUsuario().equals(codU) && !ap.getCodActividad().equals(codActividad)
							&& ap.getFecha().equals(obtenerFecha) && !ap.getCodInstalacion().equals(i)
							&& !validarHora(e.getHoraInicio(), e.getHoraFinal(), evs.getHoraInicio(),
									evs.getHoraFinal())) {

						return "Ya se ha apuntado a una actividad a esa hora.";
					}

				}

			}
		}
		return "Apuntado";

	}

	public boolean buscarUsuarioS(String cod) {
		for (Usuario usuario : usuarios) {
			if (usuario.getCodUsuario().equals(cod)) {
				return true;
			}
		}
		return false;

	}

	public ArrayList<String> buscarInstalacionActividad(String hora, String dia, Actividad a) throws SQLException {
		ArrayList<String> insta = new ArrayList<String>();
		// System.out.println(hora.trim());
		// System.out.println(dia);
		for (EventoActividad ev : eventos) {
			// System.out.println(ev.getHoraInicio().equals(hora.trim()));

			if (ev.getHoraInicio().equals(hora.trim()) && ev.getFecha().equals(dia)
					&& ev.getCodActividad().equals(a.getCodActividad())) {

				insta.add(DataBase.getInstalacion(ev.getCodInstalacion()));
			}
		}
		return insta;
	}

	public String codInsta(String nom) throws SQLException {
		for (Instalacion in : DataBase.cargaInstalaciones()) {
			// System.out.println("Entra");
			// System.out.println(nom);
			if (in.getInombre().equals(nom)) {
				return in.getCodInstalacion();
			}
		}
		return "";
	}

	private Apuntados buscarApuntado(String codActividad, String i, String codU, String obtenerFecha, String h,
			String codUR, String t) {
		for (Apuntados apuntados : apuntados) {
			if (apuntados.getCodActividad().equals(codActividad) && apuntados.getCodInstalacion().equals(i)
					&& apuntados.getCodUsuario().equals(codU) && apuntados.getFecha().equals(obtenerFecha)
					&& apuntados.getHoraInicio().equals(h) && apuntados.getCodUsuarioR().equals(codUR)
					&& apuntados.getApuntado().equals(t)) {

				return apuntados;

			}
		}
		return null;
	}

	public boolean buscarApuntadoB(String text, String codActividad, String obtenerFecha, String horaInicio, String i)
			throws SQLException {
		this.apuntados = DataBase.cargarApuntados();
		for (Apuntados ap : apuntados) {
			// System.out.println(ap);
			if (ap.getCodActividad().equals(codActividad) && ap.getFecha().equals(obtenerFecha)
					&& ap.getHoraInicio().equals(horaInicio) && ap.getCodUsuario().equals(text)
					&& ap.getApuntado().equals("true") && ap.getCodInstalacion().equals(i)) {
				return true;
			}
		}
		return false;
	}

	public Apuntados buscarApuntado(String text, String codActividad, String obtenerFecha, String horaInicio,
			String i) {
		for (Apuntados ap : apuntados) {
			if (ap.getCodActividad().equals(codActividad) && ap.getFecha().equals(obtenerFecha)
					&& ap.getHoraInicio().equals(horaInicio) && ap.getCodUsuario().equals(text)
					&& ap.getApuntado().equals("true") && ap.getCodInstalacion().equals(i)) {

				return ap;
			}
		}
		return null;
	}

	public String cancelarPlazaSocio(Apuntados buscarApuntado) throws SQLException {
		Apuntados a = buscarApuntado;
		a.setApuntado("false");
		DataBase.pasarApuntadoFalse(buscarApuntado.getCodUsuario(), buscarApuntado.getCodActividad(),
				buscarApuntado.getCodInstalacion(), buscarApuntado.getCodUsuarioR(), buscarApuntado.getFecha(),
				buscarApuntado.getHoraInicio(), buscarApuntado.getApuntado());
		this.apuntados = DataBase.cargarApuntados();
		return "Cancelada";
	}

	public void refrescarArray() throws SQLException {
		this.apuntados = DataBase.cargarApuntados();

	}

	public String[] getSociosNoApuntadosActividad(String nomActiv, String nomInsta, String fecha, String horaInicio) {
		Actividad act = getActividadPorNombre(nomActiv);
		Instalacion insta = getInstalacionPorNombre(nomInsta);

		ArrayList<String> solucion = null;
		solucion = new ArrayList<String>();
		ArrayList<String> var = null;
		var = new ArrayList<String>();

		String[] s = null;

		for (Apuntados apuntado : apuntados) {
			if (apuntado.getCodActividad().equals(act.getCodActividad())
					&& apuntado.getCodInstalacion().equals(insta.getCodInstalacion())
					&& apuntado.getFecha().equals(fecha) && apuntado.getHoraInicio().equals(horaInicio)) {
				var.add(apuntado.getCodUsuario());
			}
		}
		for (Usuario user : usuarios) {
			if (user.getCodUsuario().contains("S-") && !user.getCodUsuario().contains("NS-")) {
				solucion.add(user.getCodUsuario());
			}
		}

		solucion = comprobarListaYaApuntados(var, solucion);

		s = null;
		s = new String[solucion.size()];
		int cont = 0;
		for (String codigos : solucion) {
			s[cont] = codigos;
			cont++;
		}
		return s;
	}

	private ArrayList<String> comprobarListaYaApuntados(ArrayList<String> var, ArrayList<String> solucion) {
		ArrayList<String> ultimo = null;
		ultimo = new ArrayList<String>();
		String variable = "";
		for (String v : solucion) {
			variable = v;
			for (String s : var) {
				if (v.equals(s)) {
					variable = "";
				}
			}
			if (!variable.equals("")) {
				ultimo.add(variable);
			}
		}
		return ultimo;
	}

	private Actividad getActividadPorNombre(String nomActividad) {
		Actividad act = null;
		for (Actividad actividad : actividades) {
			if (actividad.getNombreA().equals(nomActividad)) {
				act = actividad;
				break;
			}
		}
		return act;
	}

	private Instalacion getInstalacionPorNombre(String nomInsta) {
		Instalacion insta = null;
		for (Instalacion instalacion : instalaciones) {
			if (instalacion.getInombre().equals(nomInsta)) {
				insta = instalacion;
				break;
			}
		}
		return insta;
	}

	public void apuntarNuevoSocioMonitor(String codUsuario, String nombreActividad, String nomInstalacion, String fecha,
			String hora) throws SQLException {
		Actividad act = getActividadPorNombre(nombreActividad);
		Instalacion insta = getInstalacionPorNombre(nomInstalacion);
		DataBase.apuntarNuevoSocioMonitor(codUsuario, act.getCodActividad(), insta.getCodInstalacion(), fecha, hora);
	}

	public String getEstadoSocioActividad(String codUsuario, String nomActividad, String nomInstal, String fecha,
			String hora) throws SQLException {
		Actividad act = getActividadPorNombre(nomActividad);
		Instalacion insta = getInstalacionPorNombre(nomInstal);
		return DataBase.getEstadoSocioActividad(codUsuario, act.getCodActividad(), insta.getCodInstalacion(), fecha,
				hora);
	}

	public void marcarSociosComoAsistidos(ArrayList<String> codUsuarios, String nomActividad, String nomInstal,
			String fecha, String hora) throws SQLException {
		Actividad act = getActividadPorNombre(nomActividad);
		Instalacion insta = getInstalacionPorNombre(nomInstal);
		for (String string : codUsuarios) {
			DataBase.marcarSociosComoAsistidos(string, act.getCodActividad(), insta.getCodInstalacion(), fecha, hora);
		}
	}

	public void marcarSociosComoFalto(ArrayList<String> codUsuarios, String nomActividad, String nomInstal,
			String fecha, String hora) throws SQLException {
		Actividad act = getActividadPorNombre(nomActividad);
		Instalacion insta = getInstalacionPorNombre(nomInstal);
		for (String string : codUsuarios) {
			DataBase.marcarSociosComoFalto(string, act.getCodActividad(), insta.getCodInstalacion(), fecha, hora);
		}
	}

	public boolean comprobarNombreActividad(String string) {
		for (Actividad actividad : actividades) {
			if (actividad.getNombreA().equals(string)) {
				return false;
			}
		}
		return true;

	}

	public String comprobarActividad(String nombre, String fecha) throws SQLException {
		for (Actividad a : DataBase.getActividades()) {
			if (a.getNombreA().equals(nombre)) {
				// System.out.println("-----------------------------------------ENTRA----------------------------------------");
				// System.out.println(comprobarNombreActividad(nombre, fecha));
				return comprobarNombreActividad2(nombre, fecha);
			}
		}
		return "-2";
	}

	private String codigoA;
	private int conta = 0;

	public void crearActividadPeriodicaVariosDias(String fechaInicial, String fechaFinal, String nombre, int plazas,
			String instalacion, String horaFinal, String horaInicial, String[] dias, String codigo, int cont) {
		// System.out.println("FILAS" +cont);
		if (codigo == null && cont < 1) {
			String[] cod = DataBase.maxActividad().split("-");
			int codigos = Integer.parseInt(cod[1]) + 1;
			String codActividad = "";
			if (codigos < 10) {
				codActividad = cod[0] + "-00" + codigos;
			} else if (codigos < 100) {
				codActividad = cod[0] + "-0" + codigos;
			}
			DataBase.setActividad(nombre, codActividad);
			codigo = codActividad;
			codigoA = codActividad;
			conta++;
		} else if (conta > 0) {
			codigo = codigoA;
		}

		// System.out.println("CODIGO:"+codigo);
		String[] inicio = fechaInicial.split("/");
		String[] finale = fechaFinal.split("/");
		int diaI = Integer.parseInt(inicio[0]);
		int mesI = Integer.parseInt(inicio[1]); // <--
		int yearI = Integer.parseInt(inicio[2]);
		int diaF = Integer.parseInt(finale[0]);
		int mesF = Integer.parseInt(finale[1]);
		int yearF = Integer.parseInt(finale[2]);
		for (int k = 0; k < dias.length; k++) {
			if (yearI == yearF && yearI == yearF) {
				meses(diaI, mesI, yearI, diaF, mesF, nombre, plazas, instalacion, horaFinal, horaInicial,
						diasTabla(dias[k]), codigo);
			} else {
				int diferemcia = yearF - yearI;
				int aux = diferemcia;
				for (int i = yearI; i <= yearF; i++) {
					if (diferemcia == aux) {
						meses(diaI, mesI, i, 31, 12, nombre, plazas, instalacion, horaFinal, horaInicial,
								diasTabla(dias[k]), codigo);
					} else if (aux == 0) {
						meses(1, 1, i, diaF, mesF, nombre, plazas, instalacion, horaFinal, horaInicial,
								diasTabla(dias[k]), codigo);
					} else {
						meses(1, 1, i, 31, 12, nombre, plazas, instalacion, horaFinal, horaInicial, diasTabla(dias[k]),
								codigo);
					}
					aux--;
				}
			}
		}

	}

	public void setCodA(String cod) {
		this.codigoA = cod;
	}

	public void setConta(int cn) {
		this.conta = cn;
	}

	private int diasTabla(String s) {
		if (s.equals("D")) {
			// System.out.println("Domingo");
			return 1;
		} else if (s.equals("L")) {
			// System.out.println("Lunes");
			return 2;
		} else if (s.equals("M")) {
			// System.out.println("Martes");
			return 3;
		} else if (s.equals("X")) {
			// System.out.println("Miercoles");
			return 4;
		} else if (s.equals("V")) {
			// System.out.println("Viernes");
			return 6;
		} else if (s.equals("S")) {
			// System.out.println("Sabado");
			return 7;
		}
		// System.out.println("Jueves");
		return 5;
	}

	private void meses(int diaI, int mesI, int yearActual, int diaF, int mesF, String nombre, int plazas,
			String codInstalacion, String horasTerminar, String horas, int semana, String codActividad) {

		try {
			if (mesI == mesF) {
				for (int k = diaI; k <= diaF; k++) {
					if (diasemana(mesI, k, yearActual, semana))
						crearActividadFinal(mesI, yearActual, plazas, codInstalacion, horas, horasTerminar, k,
								codActividad);
				}
			} else {
				for (int j = mesI; j <= mesF; j++) {
					if (j == mesF) {
						for (int k = 1; k <= diaF; k++) {
							if (diasemana(j, k, yearActual, semana))
								crearActividadFinal(j, yearActual, plazas, codInstalacion, horas, horasTerminar, k,
										codActividad);
						}
					} else {
						if ((j == 1 || j == 3 || j == 5 || j == 7 || j == 8 || j == 10 || j == 12) && mesI == j) {
							for (int k = diaI; k <= 31; k++) {
								if (diasemana(j, k, yearActual, semana))
									crearActividadFinal(j, yearActual, plazas, codInstalacion, horas, horasTerminar, k,
											codActividad);
							}
						} else if (j == 1 || j == 3 || j == 5 || j == 7 || j == 8 || j == 10 || j == 12) {
							for (int k = 1; k <= 31; k++) {
								if (diasemana(j, k, yearActual, semana))
									crearActividadFinal(j, yearActual, plazas, codInstalacion, horas, horasTerminar, k,
											codActividad);
							}
						} else if ((j == 4 || j == 6 || j == 9 || j == 11) && mesI == j) {
							for (int k = diaI; k <= 30; k++) {
								if (diasemana(j, k, yearActual, semana))
									crearActividadFinal(j, yearActual, plazas, codInstalacion, horas, horasTerminar, k,
											codActividad);
							}
						} else if (j == 4 || j == 6 || j == 9 || j == 11) {
							for (int k = 1; k <= 30; k++) {
								if (diasemana(j, k, yearActual, semana))
									crearActividadFinal(j, yearActual, plazas, codInstalacion, horas, horasTerminar, k,
											codActividad);
							}
						} else if ((j == 2 && anoBisiesto(yearActual)) && mesI == j) {
							for (int k = diaI; k <= 29; k++) {
								if (diasemana(j, k, yearActual, semana))
									crearActividadFinal(j, yearActual, plazas, codInstalacion, horas, horasTerminar, k,
											codActividad);
							}
						} else if ((j == 2 && !anoBisiesto(yearActual)) && mesI == j) {
							for (int k = diaI; k <= 28; k++) {
								if (diasemana(j, k, yearActual, semana))
									crearActividadFinal(j, yearActual, plazas, codInstalacion, horas, horasTerminar, k,
											codActividad);
							}
						} else if (j == 2 && anoBisiesto(yearActual)) {
							for (int k = 1; k <= 29; k++) {
								if (diasemana(j, k, yearActual, semana))
									crearActividadFinal(j, yearActual, plazas, codInstalacion, horas, horasTerminar, k,
											codActividad);
							}
						} else if (j == 2 && !anoBisiesto(yearActual)) {
							for (int k = 1; k <= 28; k++) {
								if (diasemana(j, k, yearActual, semana))
									crearActividadFinal(j, yearActual, plazas, codInstalacion, horas, horasTerminar, k,
											codActividad);
							}
						}
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private boolean diasemana(int j, int k, int yearActual, int semana) {
		Calendar c = Calendar.getInstance(Locale.UK);
		c.set(yearActual, j - 1, k);
		int dia = c.get(Calendar.DAY_OF_WEEK);
		if (semana == dia)
			return true;
		return false;
	}

	private void crearActividadFinal(int mesI, int yearActual, int plazas, String codInstalacion, String horas,
			String horasTerminar, int k, String codActividad) throws SQLException {
		String fecha = "";
		if (k < 10) {
			fecha = fecha + "0" + k + "/";
		} else {
			fecha = fecha + k + "/";
		}
		if (mesI < 10) {
			fecha = fecha + "0" + mesI + "/" + yearActual;
		} else {
			fecha = fecha + mesI + "/" + yearActual;
		}
		DataBase.crearEventoActividad(PanelMostrarReservasAdministrador.ADMINISTRADOR, plazas, codInstalacion, horas,
				horasTerminar, fecha, "0", codActividad);

	}

	private boolean anoBisiesto(int anio) {
		if ((anio % 4 == 0) && ((anio % 100 != 0) || (anio % 400 == 0)))
			return true;
		else
			return false;
	}

	public String comprobarNombreActividad2(String nombre, String hoy) throws SQLException {
		ArrayList<EventoActividad> lista = DataBase.getEventosActividadesPorNombre(nombre);
		int[] separadaActual = getFechaSeparada(hoy);
		EventoActividad mayor = getMayorFecha(lista);
		int[] separadaMayor = getFechaSeparada(mayor.getFecha());
		if (separadaMayor[2] < separadaActual[2]) {
			return mayor.getCodActividad();
		} else if (separadaMayor[2] == separadaActual[2]) {
			if (separadaMayor[1] < separadaActual[1]) {
				return mayor.getCodActividad();
			} else if (separadaMayor[1] == separadaActual[1]) {
				if (separadaMayor[0] < separadaActual[0]) {
					return mayor.getCodActividad();
				}
			}
		}
		return "-1";
	}

	private EventoActividad getMayorFecha(ArrayList<EventoActividad> lista) {
		EventoActividad mayor = null;
		for (EventoActividad e : lista) {
			if (mayor == null) {
				mayor = e;
			} else {
				int[] separadaActual = getFechaSeparada(e.getFecha());
				int[] separadaMayor = getFechaSeparada(mayor.getFecha());
				if (separadaMayor[2] < separadaActual[2]) {
					mayor = e;
				} else if (separadaMayor[2] == separadaActual[2]) {
					if (separadaMayor[1] < separadaActual[1]) {
						mayor = e;
					} else if (separadaMayor[1] == separadaActual[1]) {
						if (separadaMayor[0] < separadaActual[0]) {
							mayor = e;
						}
					}
				}
			}

		}
		return mayor;

	}

	public int comprobarNombreActividad(String nombre, String hoy) throws SQLException {
		ArrayList<String> lista = DataBase.getActividadesPorNombre(nombre);
		int[] separadaHoy = getFechaSeparada(hoy);
		for (String string : lista) {
			int[] var = getFechaSeparada(string);
			if (var[2] == separadaHoy[2]) {
				if (var[1] == separadaHoy[1]) {
					if (var[0] == separadaHoy[0])
						;
					else if (var[0] < separadaHoy[0])
						;
					else
						return -1;
				} else if (var[1] < separadaHoy[1])
					;
				else
					return -1;
			} else if (var[2] < separadaHoy[2])
				;
			else
				return -1;
		}
		return 0;
	}

	private int[] getFechaSeparada(String fecha) {
		String[] var = fecha.split("/");
		int[] separada = new int[] { Integer.valueOf(var[0]), Integer.valueOf(var[1]), Integer.valueOf(var[2]) };
		return separada;
	}


	public int crearActividadPuntual(String nombreActividad, Instalacion instalacion, String fecha, String horaInicio,String horaFinal, String numPlazas) {
		String codActividad = cogerCodActividad(nombreActividad);
		int var = -10;
		try {
			Reserva reserva = DataBase.getReserva(fecha, horaInicio, instalacion.getCodInstalacion());
			if (reserva!=null) {
				if (reserva.getCancelada().equals("true") && reserva.getCodUsuario().equals("A-001")) {
					DataBase.desCancelarReserva(fecha, instalacion.getCodInstalacion(), "A-001", horaInicio, horaFinal);
					var = DataBase.crearEventoActividad(codActividad, instalacion.getCodInstalacion(),"A-001",fecha,horaInicio,numPlazas);
				} else if (reserva.getCancelada().equals("true") && !reserva.getCodUsuario().equals("A-001")){
					DataBase.setReserva("A-001", instalacion.getCodInstalacion(), horaInicio, horaFinal, fecha, "0");
					var = DataBase.crearEventoActividad(codActividad, instalacion.getCodInstalacion(),"A-001",fecha,horaInicio,numPlazas);
				}
			} else {
				DataBase.setReserva("A-001", instalacion.getCodInstalacion(), horaInicio, horaFinal, fecha, "0");

				var = DataBase.crearEventoActividad(codActividad, instalacion.getCodInstalacion(),"A-001",fecha,horaInicio,numPlazas);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return var;
	}

	public String cogerCodActividad(String nombre) { //Modificado
		int cont=0;
		Actividad actividad = null;
		try {
			actividad = DataBase.getActividad(nombre);
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		if (actividad == null) {
			try {
				cont = DataBase.countActividades();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			cont += 1;
			String codActividad = "AC-";
			if (cont < 10)
				codActividad += "00" + cont;
			else if (cont >= 10 && cont < 100)
				codActividad += "0" + cont;
			else if (cont >= 100)
				codActividad += cont;
			try {
				DataBase.crearNuevaActividad(codActividad, nombre);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return codActividad;
		} else
			return actividad.getCodActividad();
	}

	/**
	 * Comprueba todo
	 * 
	 * @param codActividad
	 * @param fecha
	 * @param horaInicio
	 * @param codInstalacion
	 * @param codUsuario
	 * @param nombreA
	 * @return
	 * @throws SQLException
	 */
	public boolean buscarActividadPuntual(String codActividad, String fecha, String horaInicio, String codInstalacion,
			String codUsuario, String nombreA) throws SQLException {
		boolean t = false;
		if (!buscarApuntadosActividad(codActividad, codInstalacion, codUsuario, fecha, horaInicio).isEmpty()) {
			for (Apuntados ap : buscarApuntadosActividad(codActividad, codInstalacion, codUsuario, fecha, horaInicio)) {
				// ap.setApuntado("false");
				// DataBase.pasarApuntadoFalse(ap.getCodUsuario(), ap.getCodActividad(),
				// ap.getCodInstalacion(), ap.getCodUsuarioR(), ap.getFecha(),
				// ap.getHoraInicio(), ap.getApuntado());
				// System.out.println(ap.toString());
				t = true;
			}
		}
		if (buscarEventoActividades(codActividad, fecha, horaInicio) != null) {
			// borar evento
			// System.out.println(buscarEventoAdminActividad(codActividad,fecha,horaInicio,codInstalacion,codUsuario).imprimir());
			EventoActividad e = buscarEventoAdminActividad(codActividad, fecha, horaInicio, codInstalacion, codUsuario);
			// DataBase.borrarEventoActividad(e.getCodInstalacion(), e.getFecha(),
			// e.getHoraInicio(), e.getCodActividad(), e.getCodUsuario());
			t = true;
			if (buscarReservaAdminActividad(codUsuario, codInstalacion, fecha, horaInicio) != null) {
				// borrar reserva
				// System.out.println(buscarReservaAdminActividad(codUsuario, codInstalacion,
				// fecha,horaInicio).toString());
				Reserva r = buscarReservaAdminActividad(codUsuario, codInstalacion, fecha, horaInicio);
				// r.setCancelada("true");
				// DataBase.pasarReservaACancelada(r.getCodUsuario(), r.getCodInstalacion(),
				// r.getDesde(), r.getHasta(), r.getFechaReserva(), "0", r.getCancelada());
				t = true;
			}

		} else {
			// no hay eventos actividad return false;
		}

		return t;
	}

	/**
	 * Borra todo
	 * 
	 * @param codActividad
	 * @param fecha
	 * @param horaInicio
	 * @param codInstalacion
	 * @param codUsuario
	 * @param nombreA
	 * @return
	 * @throws SQLException
	 */
	public boolean borrarActividadPuntual(String codActividad, String fecha, String horaInicio, String codInstalacion,
			String codUsuario, String nombreA) throws SQLException {
		boolean t = false;
		if (!buscarApuntadosActividad(codActividad, codInstalacion, codUsuario, fecha, horaInicio).isEmpty()) {
			// -----------------------------¡¡¡¡¡¡BORRA
			// APUNTADOS!!!!!!!!----------------------------------------
			for (Apuntados ap : buscarApuntadosActividad(codActividad, codInstalacion, codUsuario, fecha, horaInicio)) {
				ap.setApuntado("false");
				DataBase.pasarApuntadoFalse(ap.getCodUsuario(), ap.getCodActividad(), ap.getCodInstalacion(),
						ap.getCodUsuarioR(), ap.getFecha(), ap.getHoraInicio(), ap.getApuntado());

				// System.out.println(ap.toString());
				t = true;
			}
			this.apuntados = DataBase.cargarApuntados();
		}
		if (buscarEventoActividades(codActividad, fecha, horaInicio) != null) {
			// borar evento
			// System.out.println(buscarEventoAdminActividad(codActividad,fecha,horaInicio,codInstalacion,codUsuario).imprimir());
			EventoActividad e = buscarEventoAdminActividad(codActividad, fecha, horaInicio, codInstalacion, codUsuario);
			DataBase.borrarEventoActividad(e.getCodInstalacion(), e.getFecha(), e.getHoraInicio(), e.getCodActividad(),
					e.getCodUsuario());
			t = true;
			this.eventos = DataBase.cargarEventos();
			if (buscarReservaAdminActividad(codUsuario, codInstalacion, fecha, horaInicio) != null) {
				// borrar reserva
				// System.out.println(buscarReservaAdminActividad(codUsuario, codInstalacion,
				// fecha,horaInicio).toString());
				Reserva r = buscarReservaAdminActividad(codUsuario, codInstalacion, fecha, horaInicio);
				r.setCancelada("true");
				DataBase.pasarReservaACancelada(r.getCodUsuario(), r.getCodInstalacion(), r.getDesde(), r.getHasta(),
						r.getFechaReserva(), "0", r.getCancelada());
				t = true;
				this.actividades = DataBase.cargarActividades();
			}

		} else {
			t = false;
			// no hay eventos actividad return false;
		}

		return t;
	}

	private EventoActividad buscarEventoAdminActividad(String codActividad, String fecha, String horaInicio,
			String codInstalacion, String codUsuario) {
		for (EventoActividad eventoActividad : eventos) {
			if (eventoActividad.getCodActividad().equals(codActividad) && eventoActividad.getFecha().equals(fecha)
					&& eventoActividad.getHoraInicio().equals(horaInicio)
					&& eventoActividad.getCodInstalacion().equals(codInstalacion)
					&& eventoActividad.getCodUsuario().equals(codUsuario)) {
				return eventoActividad;
			}

		}
		return null;
	}

	private Reserva buscarReservaAdminActividad(String codUR, String codIns, String fecha, String horaI) {
		for (Reserva res : reservas) {
			if (res.getCancelada().equals("false")) {

				if (res.getCodUsuario().equals(codUR) && res.getCodInstalacion().equals(codIns)
						&& res.getFechaReserva().equals(fecha) && res.getDesde().equals(horaI)) {
					return res;
				}

			}
		}
		return null;

	}

	private ArrayList<Apuntados> buscarApuntadosActividad(String codA, String codIn, String codUR, String fecha,
			String horaI) {
		ArrayList<Apuntados> aux = new ArrayList<Apuntados>();
		for (Apuntados apuntados : apuntados) {
			if (apuntados.getApuntado().equals("true")) {
				if (apuntados.getCodActividad().equals(codA) && apuntados.getCodInstalacion().equals(codIn)
						&& apuntados.getCodUsuarioR().equals(codUR) && apuntados.getFecha().equals(fecha)
						&& apuntados.getHoraInicio().equals(horaI)) {
					aux.add(apuntados);
				}

			}
		}

		return aux;
	}

	// Metodos Jose borrar actividades periodicas

	public CancelarActividadPeriodica borrarActividadPeriodica(Actividad actividadParaBorrarP,
			String fechaParaBorrarP) {
		CancelarActividadPeriodica cap = new CancelarActividadPeriodica(actividadParaBorrarP, fechaParaBorrarP, this);
		cap.getActividadesCancelar();
		return cap;
	}

	public String devolverNombreInstalacion(String cod) {
		for (Instalacion ins : instalaciones) {
			if (ins.getCodInstalacion().equals(cod))
				return ins.getInombre();
		}
		return null;
	}

	public String devolverNombreActividad(String cod) {
		for (Actividad a : actividades) {
			if (a.getCodActividad().equals(cod))
				return a.getNombreA();
		}
		return null;
	}

	public ArrayList<Apuntados> getListaApuntados() {
		return apuntados;
	}

	public String borrarPlazaSocio(Apuntados buscarApuntado) throws SQLException {
		Apuntados a = buscarApuntado;
		DataBase.borrarApuntado(buscarApuntado.getCodUsuario(), buscarApuntado.getCodActividad(),
				buscarApuntado.getCodInstalacion(), buscarApuntado.getCodUsuarioR(), buscarApuntado.getFecha(),
				buscarApuntado.getHoraInicio());
		this.apuntados = DataBase.cargarApuntados();
		return "Cancelada";
	}

	public void borrarEventoPeriodico(String codActividad, String codInstalacion, String fecha, String horaInicio)
			throws SQLException {
		DataBase.borrarEventoActividadPeriodico(codActividad, codInstalacion, fecha, horaInicio);
	}

	public void actualizarListaEventos() {
		try {
			this.eventos = DataBase.cargarEventos();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void cancelarReservaPorActividadPeriodica(String codInstalacion, String fecha, String horaInicio)
			throws SQLException {
		DataBase.cancelarReservaPorActividadPeriodica(codInstalacion, fecha, horaInicio, "Cancelacion de Actividad");
	}

}
