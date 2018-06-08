 package dataBase;

import java.security.interfaces.RSAKey;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import logica.objetos.Actividad;
import logica.objetos.Apuntados;
import logica.objetos.Evento;
import logica.objetos.EventoActividad;
import logica.objetos.Instalacion;
import logica.objetos.Mensualidad;
import logica.objetos.Pago;
import logica.objetos.Reserva;
import logica.objetos.SocioR;
import logica.objetos.Usuario;

public class DataBase {
	
	//private static Connection con;
	
	private static Connection getConnection() {
		try {
			Class.forName("org.sqlite.JDBC");
			return DriverManager.getConnection("jdbc:sqlite:DB\\instaDepor.db");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * Método que busca en la base de datos a un determinado miembro de la administracion
	 * @param codUsuario el codigo de usuario a buscar
	 * @return devuelve un array con el codigo, el nombre y la contraseña del usuario.
	 * @throws SQLException
	 */
	public static String[] getAdministrador(String codUsuario) throws SQLException {
		String[] administrador = new String[7];
		Connection con = getConnection();
		PreparedStatement pst = con.prepareStatement("SELECT *"
				+ " FROM usuario"
				+ " WHERE codUsuario = ?");
		pst.setString(1, codUsuario);
		ResultSet rs = pst.executeQuery();
		for (int i=0; i<7; i++)
			administrador[i] = rs.getString(i+1);
		rs.close();
		pst.close();
		con.close();
		return administrador;
	}
	
	
	/**
	 * Método que lee y devuelve las instalaciones de la base de datos
	 * @return
	 * @throws SQLException
	 */
	public static ArrayList<Instalacion> getInstalaciones() throws SQLException{
		ArrayList<Instalacion> instalaciones = new ArrayList<Instalacion>();
		Connection con = getConnection();
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("SELECT * FROM INSTALACIONES");
		while (rs.next()) {
			instalaciones.add(new Instalacion(rs.getString("codInstalacion"), rs.getString("nombreInstalacion")));
		}
		rs.close();
		st.close();
		con.close();
		return instalaciones;
	}

	
	///---------------------------------------------------------ROSA-----------------------------------------------------------------------------
	/**
	 * Método que lee y devuelve las instalaciones de la base de datos
	 * @return
	 * @throws SQLException
	 */
	public static ArrayList<Instalacion> cargaInstalaciones() throws SQLException{
		ArrayList<Instalacion> instalaciones = new ArrayList<Instalacion>();
		Connection con = getConnection();
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("SELECT * FROM INSTALACIONES");
		while (rs.next()) {
			instalaciones.add(new Instalacion(rs.getString("codInstalacion"), rs.getString("nombreInstalacion")));
		}
		rs.close();
		st.close();
		con.close();
		return instalaciones;
	}
	
	public static ArrayList<Reserva> cargaReservas() throws SQLException{
		ArrayList<Reserva> reservas = new ArrayList<Reserva>();
		Connection con = getConnection();
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("SELECT * FROM RESERVAS");
		while (rs.next()) {
			Reserva res = new Reserva(rs.getString("codUsuario"), rs.getString("codInstalacion"), 
					rs.getString("horaInicio"), rs.getString("horaFinal"), rs.getString("fecha"), rs.getString("instalacionOcupada"),rs.getString("cancelada"));
			res.setRecogidaLlaves(rs.getString("recogidaLlaves"));
			res.setDevolucionLlaves(rs.getString("devolucionLlaves"));
			reservas.add(res);
		}
		rs.close();
		st.close();
		con.close();
		return reservas;
	}
	
	public static ArrayList<Usuario> cargaUsuarios() throws SQLException{
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
		Connection con = getConnection();
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("SELECT * FROM USUARIOS");
		while (rs.next()) {
			usuarios.add(new Usuario(rs.getString(1), rs.getString(3), rs.getString(4),Integer.parseInt(rs.getString(5)), rs.getString(6)
					, rs.getString(7)));
					
		}
		rs.close();
		st.close();
		con.close();
		return usuarios;
	}
	


	public static void hacerReservaSocio(String numSocio,String codInstalacion,String horaInicial,String horaFinal,String fecha,String ocupada, String s ) 
	{
		Connection con = getConnection();

		String insertTableSQL = "INSERT INTO RESERVAS"
                + "(codInstalacion, codUsuario, fecha, horaInicio, horaFinal, instalacionOcupada,formaDePago) VALUES"
                + "(?,?,?,?,?,?,?)";
	

		try {
			PreparedStatement pst = con.prepareStatement(insertTableSQL);
			pst.setString(1, codInstalacion);
			pst.setString(2, numSocio);
			pst.setString(3, fecha);
			pst.setString(4, horaInicial);
			pst.setString(5, horaFinal);
			pst.setString(6, ocupada);
			pst.setString(7,s);
			//pst.setString(7, null);
			//pst.setString(8, null);
			int res = pst.executeUpdate();
			if(res!=0){
				System.out.println("Se inserto");
				pst.close();
				con.close();
			}else{
				System.out.println("No se inserto");
				pst.close();
				con.close();
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	
		
	}
	
	
	//NO SE USA
	public static void eliminarReservaSocio(String numSocio,String codInstalacion,String horaInicial,String horaFinal,String fecha,String ocupada, String s ) 
	{
		Connection con = getConnection();
		
		   String Ssql = "DELETE FROM RESERVAS "
                   + "WHERE codInstalacion = ? AND codUsuario= ? AND  fecha= ? AND horaInicio= ? AND horaFinal= ?";
	
		try {   
	        
	     
	        PreparedStatement pst = con.prepareStatement(Ssql);
	        pst.setString(1, codInstalacion);
			pst.setString(2, numSocio);
			pst.setString(3, fecha);
			pst.setString(4, horaInicial);
			pst.setString(5, horaFinal);
	    	int res = pst.executeUpdate();
			if(res!=0){
				System.out.println("Se borro");
			}else{
				System.out.println("No se borro");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void pasarReservaACancelada(String numSocio,String codInstalacion,String horaInicial,String horaFinal,String fecha,String ocupada,String s ) 
	{


		Connection con = getConnection();
		
		   String Ssql = "UPDATE RESERVAS SET cancelada=?"
                   + "WHERE codInstalacion = ? AND codUsuario= ? AND  fecha= ? AND horaInicio= ? AND horaFinal= ?";
	
		try {   
	        
	     
	        PreparedStatement pst = con.prepareStatement(Ssql);
	        pst.setString(1, s);
	        pst.setString(2, codInstalacion);
			pst.setString(3, numSocio);
			pst.setString(4, fecha);
			pst.setString(5, horaInicial);
			pst.setString(6, horaFinal);
	
	    	int res = pst.executeUpdate();
			if(res!=0){
				System.out.println("Se borro");
				pst.close();
				con.close();
			}else{
				System.out.println("No se borro");
				pst.close();
				con.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void pasarReservaANoCancelada(String numSocio,String codInstalacion,String horaInicial,String horaFinal,String fecha,String ocupada,String pago, String s ) 
	{


		Connection con = getConnection();
		
		   String Ssql = "UPDATE RESERVAS SET cancelada=? , formaDePago=?"
                   + "WHERE codInstalacion = ? AND codUsuario= ? AND  fecha= ? AND horaInicio= ? AND horaFinal= ?";
	
		try {   
	        
	     
	        PreparedStatement pst = con.prepareStatement(Ssql);
	        pst.setString(1, s);
	        pst.setString(2, pago);
	        pst.setString(3, codInstalacion);
			pst.setString(4, numSocio);
			pst.setString(5, fecha);
			pst.setString(6, horaInicial);
			pst.setString(7, horaFinal);
			
	    	int res = pst.executeUpdate();
			if(res!=0){
				System.out.println("Se actualizo");
				pst.close();
				con.close();
			}else{
				System.out.println("No se actualizo");
				pst.close();
				con.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	///---------------------------------------------------------PELAYO-----------------------------------------------------------------------------
	
		public static String getInstalacion(String codInstalacion) throws SQLException {
			Connection con = getConnection();
			PreparedStatement pst = con.prepareStatement("SELECT i.nombreInstalacion"
					+ " FROM INSTALACIONES i"
					+ " WHERE i.codInstalacion = ?");
			pst.setString(1, codInstalacion);
			ResultSet rs = pst.executeQuery();
			String resultado = rs.getString(1);
			rs.close();
			pst.close();
			con.close();
			return resultado;
		}
		
		public static String getNombre(String codUsuario) throws SQLException {
			Connection con = getConnection();
			PreparedStatement pst = con.prepareStatement("SELECT s.nombreUsuario, s.apellidoUsuario"
					+ " FROM usuarios s"
					+ " WHERE s.codUsuario = ?");
			pst.setString(1, codUsuario);
			ResultSet rs = pst.executeQuery();
			String nombre = rs.getString(1) + " " + rs.getString(2);
			rs.close();
			pst.close();
			con.close();
			return nombre;
		}
		
		public static String getDNI(String codUsuario) throws SQLException {
			Connection con = getConnection();
			PreparedStatement pst = con.prepareStatement("SELECT s.DNIUsuario"
					+ " FROM usuarios s"
					+ " WHERE s.codUsuario = ?");
			pst.setString(1, codUsuario);
			ResultSet rs = pst.executeQuery();
			String nombre = rs.getString(1);
			rs.close();
			pst.close();
			con.close();
			return nombre;
		}
		
		public static Pago getPago(String codInstalacion, String codUsuario, String fecha, String hora) throws SQLException {
			Connection con = getConnection();
			PreparedStatement pst = con.prepareStatement("SELECT *"
					+ " FROM PAGOS p"
					+ " WHERE p.codUsuario = ? and p.codInstalacion = ? and p.fecha = ? and p.horaInicio = ?");
			pst.setString(1, codUsuario);
			pst.setString(2, codInstalacion);
			pst.setString(3, fecha);
			pst.setString(4, hora);
			ResultSet rs = pst.executeQuery();
			Pago pago = null;
			if (rs.next()) {
				pago = new Pago(rs.getString("codInstalacion"), rs.getString("codUsuario"), 
						rs.getString("fecha"), rs.getString("horaInicio"), rs.getString("codPago"), rs.getDouble("cantidad"));
			}
			rs.close();
			pst.close();
			con.close();
			return pago;
		}
		
		public static double getPrecioInstalacion(String nombreInstalacion) throws SQLException {
			Connection con = getConnection();
			PreparedStatement pst = con.prepareStatement("SELECT i.precio"
					+ " FROM INSTALACIONES i"
					+ " WHERE i.nombreInstalacion = ?");
			pst.setString(1, nombreInstalacion);
			ResultSet rs = pst.executeQuery();
			double precio = rs.getDouble(1);
			rs.close();
			pst.close();
			con.close();
			return precio;
		}
		public static double getPrecioInstalacionCod(String codInstalacion) throws SQLException {
			double horas=0;
			Connection con = getConnection();
			PreparedStatement pst = con.prepareStatement("Select precio from INSTALACIONES where codInstalacion=?");
			pst.setString(1, codInstalacion);
			ResultSet rs=pst.executeQuery();
			horas=rs.getDouble(1);
			rs.close();
			pst.close();
			con.close();
			return horas;
		}
		
		public static void guardarPago(String codInstalacion, String codUsuario, String fecha, String horaInicio, String codPago, double cantidad) throws SQLException {
			Connection con = getConnection();
			PreparedStatement pst = con.prepareStatement("INSERT INTO PAGOS (codInstalacion, codUsuario, fecha, "
					+ "horaInicio, codPago, cantidad)"
					+ " VALUES (?,?,?,?,?,?)");
			pst.setString(1, codInstalacion);
			pst.setString(2, codUsuario);
			pst.setString(3, fecha);
			pst.setString(4, horaInicio);
			pst.setString(5, codPago);
			pst.setDouble(6, cantidad);
			pst.executeUpdate();
			pst.close();
			con.close();
		}
		
		public static double getDeuda(String codUsuario) throws SQLException {
			Connection con = getConnection();
			PreparedStatement pst = con.prepareStatement("SELECT s.adeudado"
					+ " FROM SOCIOS s"
					+ " WHERE s.codUsuario=?");
			pst.setString(1, codUsuario);
			ResultSet rs=pst.executeQuery();
			double deuda = rs.getDouble(1);
			rs.close();
			pst.close();
			con.close();
			return deuda;
		}
		
		public static void guardarDeuda(String codUsuario, double deuda) throws SQLException {
			Connection con = getConnection();
			PreparedStatement pst = con.prepareStatement("UPDATE SOCIOS "
					+ "SET adeudado=?"
					+ " WHERE codUsuario=?");
			pst.setDouble(1, getDeuda(codUsuario)+deuda);
			pst.setString(2, codUsuario);
			pst.executeUpdate();
			pst.close();
			con.close();
		}
		
		public static double getCuota(String codUsuario) throws SQLException {
			Connection con = getConnection();
			PreparedStatement pst = con.prepareStatement("SELECT s.cuota"
					+ " FROM SOCIOS s"
					+ " WHERE s.codUsuario=?");
			pst.setString(1, codUsuario);
			ResultSet rs=pst.executeQuery();
			double cuota = rs.getDouble(1);
			rs.close();
			pst.close();
			con.close();
			return cuota;
		}
		
		public static Mensualidad getMensualidad(String codUsuario) throws SQLException {
			Connection con = getConnection();
			PreparedStatement pst = con.prepareStatement("SELECT *"
					+ " FROM MENSUALIDADES m"
					+ " WHERE m.codUsuario=?");
			pst.setString(1, codUsuario);
			ResultSet rs=pst.executeQuery();
			Mensualidad mensualidad = null;
			if (rs.next())
				mensualidad = new Mensualidad(rs.getString(1), rs.getDouble(2), rs.getInt(3), rs.getInt(4));
			rs.close();
			pst.close();
			con.close();
			return mensualidad;
		}
		
		public static void incrementarMensualidad(String codUsuario, double deuda, int mes, int year) throws SQLException {
			Mensualidad mensualidad = getMensualidad(codUsuario);
			if (mensualidad!=null) {
			Connection con = getConnection();
			PreparedStatement pst = con
					.prepareStatement("UPDATE MENSUALIDADES " + "SET mCantidad=?" + " WHERE codUsuario=?"
							+ "and mes=? and anio=?");

			pst.setDouble(1, mensualidad.getmCantidad() + deuda);
			pst.setString(2, codUsuario);
			pst.setInt(3, mes);
			pst.setInt(4, year);
			pst.executeUpdate();
			pst.close();
			con.close();
			} else {
				crearMensualidad(codUsuario, deuda, mes, year);
			}
		}
		
		public static void crearMensualidad(String codUsuario, double deuda, int mes, int year) throws SQLException {
			Connection con = getConnection();
			PreparedStatement pst = con
					.prepareStatement("INSERT INTO MENSUALIDADES (codUsuario, mCantidad, mes, anio)"
							+ "VALUES (?,?,?,?)");
			pst.setString(1, codUsuario);
			pst.setInt(3, mes);
			pst.setInt(4, year);
			pst.setDouble(2, getCuota(codUsuario)+deuda);
			pst.executeUpdate();
			pst.close();
			con.close();
		}
		
		public static void setPagoCobrado(String codInstalacion, String codUsuario, String fecha, String hora) throws SQLException {
			Connection con = getConnection();
			PreparedStatement pst = con.prepareStatement("UPDATE PAGOS "
					+ "SET cantidad=? "
					+ "WHERE codInstalacion=? and codUsuario=? and fecha=? and horaInicio=?");
			double cantidad = getPago(codInstalacion, codUsuario, fecha, hora).getCantidad()*(-1.0);
			pst.setDouble(1, cantidad);
			pst.setString(2, codInstalacion);
			pst.setString(3, codUsuario);
			pst.setString(4, fecha);
			pst.setString(5, hora);
			pst.executeUpdate();
			pst.close();
			con.close();
		}
		
		public static ArrayList<Actividad> getActividades() throws SQLException {
			ArrayList<Actividad> actividades = new ArrayList<Actividad>();
			Connection con = getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM ACTIVIDADES");
			while (rs.next()) {
				actividades.add(new Actividad(rs.getString(1), rs.getString(2)));
			}
			rs.close();
			st.close();
			con.close();
			return actividades;
		}
		
		public static Actividad getActividad(String nombreActividad) throws SQLException {
			Connection con = getConnection();
			PreparedStatement pst = con.prepareStatement("SELECT a.codActividad"
					+ " FROM ACTIVIDADES a"
					+ " WHERE a.nombreA=?");
			pst.setString(1, nombreActividad);
			ResultSet st = pst.executeQuery();
			Actividad actividad = null;
			if (st.next())
				actividad = new Actividad(st.getString(1), nombreActividad);
			st.close();
			pst.close();
			con.close();
			return actividad;
		}
		
		public static Actividad getActividadPorCodigo(String codActividad) throws SQLException {
			Connection con = getConnection();
			PreparedStatement pst = con.prepareStatement("SELECT a.nombreA"
					+ " FROM ACTIVIDADES a"
					+ " WHERE a.codActividad=?");
			pst.setString(1, codActividad);
			ResultSet st = pst.executeQuery();
			Actividad actividad = null;
			if (st.next())
				actividad = new Actividad(codActividad, st.getString(1));
			st.close();
			pst.close();
			con.close();
			return actividad;
		}
		
		public static ArrayList<Evento> getEventos(String nombreActividad, String fecha) throws SQLException{
			Connection con = getConnection();
			Actividad actividad = getActividad(nombreActividad);
			PreparedStatement pst = con.prepareStatement("SELECT v.codInstalacion, v.fecha, v.horaInicio, v.plazas "
					+ "FROM EVENTOS v "
					+ "WHERE v.codActividad=? and v.fecha=?");
			pst.setString(1, actividad.getCodActividad());
			pst.setString(2, fecha);
			ResultSet rs = pst.executeQuery();
			ArrayList<Evento> eventos = new ArrayList<Evento>();
			while (rs.next()) {
				eventos.add(new Evento(actividad, rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4)));
			}
			rs.close();
			pst.close();
			con.close();
			return eventos;
		}
		
		public static ArrayList<String> getInstalacionEvento(String nombreActividad, String fecha, String horaInicio) throws SQLException {
			Connection con = getConnection();
			Actividad actividad = getActividad(nombreActividad);
			PreparedStatement pst = con.prepareStatement("SELECT v.codInstalacion "
					+ "FROM EVENTOS v "
					+ "WHERE v.codActividad=? and v.fecha=? and v.horaInicio=?");
			pst.setString(1, actividad.getCodActividad());
			pst.setString(2, fecha);
			pst.setString(3, horaInicio);
			ResultSet rs = pst.executeQuery();
			ArrayList<String> eventos = new ArrayList<String>();
			while (rs.next()) {
				eventos.add(getInstalacion(rs.getString(1)));
			}
			rs.close();
			pst.close();
			con.close();
			return eventos;
		}
		
		public static ArrayList<Instalacion> getInstalacionesEventos(String codActividad, String fecha, String horaInicio) throws SQLException {
			Connection con = getConnection();
			PreparedStatement pst = con.prepareStatement("SELECT e.codInstalacion"
					+ " FROM EVENTOS e"
					+ " WHERE e.codActividad=? and e.fecha=? and e.horaInicio=?");
			pst.setString(1, codActividad);
			pst.setString(2, fecha);
			pst.setString(3, horaInicio);
			ResultSet st= pst.executeQuery();
			ArrayList<Instalacion> instalaciones = new ArrayList<Instalacion>();
			while (st.next()) {
				String codInstalacion = st.getString(1);
				instalaciones.add(new Instalacion(codInstalacion, getInstalacion(codInstalacion)));
			}
			st.close();
			pst.close();
			con.close();
			return instalaciones;
		}
		
		public static String getCodInstalacion(String nombreI) throws SQLException {
			Connection con = getConnection();
			PreparedStatement pst = con.prepareStatement("SELECT i.codInstalacion "
					+ "FROM INSTALACIONES i "
					+ "WHERE i.nombreInstalacion=?");
			pst.setString(1, nombreI);
			ResultSet rs = pst.executeQuery();
			String nombre = rs.getString(1);
			rs.close();
			pst.close();
			con.close();
			return nombre;
		}
		
		public static int getPlazasEvento(String codActividad, String codInstalacion, String fecha, String horaInicio) throws SQLException {
			Connection con = getConnection();
			PreparedStatement pst = con.prepareStatement("SELECT v.plazas "
					+ "FROM EVENTOS v "
					+ "WHERE v.codActividad=? and v.codInstalacion=? and v.fecha=? and v.horaInicio=?");
			pst.setString(1, codActividad);
			pst.setString(2, codInstalacion);
			pst.setString(3, fecha);
			pst.setString(4, horaInicio);
			ResultSet rs = pst.executeQuery();
			int numero = rs.getInt(1);
			rs.close();
			pst.close();
			con.close();
			return numero;
		}
		
		public static int countPlazasOcupadas(String codActividad, String fecha, String hora, String codInstalacion) throws SQLException {
			Connection con = getConnection();
			PreparedStatement pst = con.prepareStatement("SELECT count(*) numero "
					+ "FROM APUNTADOS a "
					+ "WHERE a.codActividad=? and a.fecha=? and a.horaInicio=? and "
					+ "a.codInstalacion=? and (a.apuntado='true' or a.apuntado='var1')");
			pst.setString(1, codActividad);
			pst.setString(2, fecha);
			pst.setString(3, hora);
			pst.setString(4, codInstalacion);
			ResultSet rs = pst.executeQuery();
			int numero = rs.getInt(1);
			rs.close();
			pst.close();
			con.close();
			return numero;
		}
		
		public static ArrayList<Usuario> getApuntados(String codActividad, String codInstalacion, String fecha, String horaInicio) throws SQLException{
			Connection con = getConnection();
			ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
			PreparedStatement pst = con.prepareStatement("SELECT a.codUsuario "
					+ "FROM APUNTADOS a "
					+ "WHERE a.codActividad=? and a.codInstalacion=? and a.fecha=? and a.horaInicio=?");
			pst.setString(1, codActividad);
			pst.setString(2, codInstalacion);
			pst.setString(3, fecha);
			pst.setString(4, horaInicio);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				usuarios.add(getUsuario(rs.getString(1)));
			}
			rs.close();
			pst.close();
			con.close();
			return usuarios;
		}
		
		public static Evento getEvento(String codInstalacion, String fecha, String horaInicio) throws SQLException {
			Connection con = getConnection();
			PreparedStatement pst = con.prepareStatement("SELECT *"
					+ "FROM EVENTOS e "
					+ "WHERE e.codInstalacion=? and e.fecha=? and e.horaInicio=?");
			pst.setString(1, codInstalacion);
			pst.setString(2, fecha);
			pst.setString(3, horaInicio);
			ResultSet rs = pst.executeQuery();
			Evento evento = null;
			if (rs.next()) {
				Actividad actividad = DataBase.getActividadPorCodigo(rs.getString("codActividad"));
				evento = new Evento(actividad, rs.getString("codInstalacion"), rs.getString("fecha"), rs.getString("horaInicio"), Integer.valueOf(rs.getString("plazas")));
			}
			rs.close();
			pst.close();
			con.close();
			return evento;
		}
		
		public static void removeApuntados(String codActividad, String codInstalacion, String fecha, String horaInicio) throws SQLException {
			Connection con = getConnection();
			PreparedStatement pst = con.prepareStatement("DELETE from APUNTADOS"
					+ " WHERE codActividad=? and codInstalacion=? and fecha=? and horaInicio=?");
			pst.setString(1, codActividad);
			pst.setString(2, codInstalacion);
			pst.setString(3, fecha);
			pst.setString(4, horaInicio);
			pst.executeUpdate();
			pst.close();
			con.close();			
		}
		
		public static Usuario getUsuario(String codUsuario) throws SQLException {
			Connection con = getConnection();
			PreparedStatement pst = con.prepareStatement("SELECT u.codUsuario, u.nombreUsuario, u.apellidoUsuario, u.telefonoUsuario, u.DNIUsuario, u.emailUsuario "
					+ "FROM USUARIOS u "
					+ "WHERE u.codUsuario=?");
			pst.setString(1, codUsuario);
			ResultSet rs = pst.executeQuery();
			Usuario usuario = null;
			if (rs.next()) {
				usuario = new Usuario(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5), rs.getString(6));
			}
			rs.close();
			pst.close();
			con.close();
			return usuario;
		}
		
		public static void apuntarSocio(String codUsuario, String codActividad, String codInstalacion, String fecha, String hora) throws SQLException {
			Connection con = getConnection();
			PreparedStatement pst = con
					.prepareStatement("INSERT INTO APUNTADOS (codUsuario, codActividad, codInstalacion, codUsuarioR, fecha, horaInicio)"
							+ "VALUES (?,?,?,'A-001',?,?)");
			pst.setString(1, codUsuario);
			pst.setString(2, codActividad);
			pst.setString(3, codInstalacion);
			pst.setString(4, fecha);
			pst.setString(5, hora);
			pst.executeUpdate();
			pst.close();
			con.close();
		}
		
		public static void actualizarSocioApuntado(String apuntado, String codUsuario, String codActividad, String codInstalacion, String fecha, String hora) throws SQLException {
			Connection con = getConnection();
			PreparedStatement pst = con
					.prepareStatement("UPDATE APUNTADOS SET apuntado=?"
							+ " WHERE codUsuario=? and codActividad=? and codInstalacion=? and fecha=? and horaInicio=?");
			pst.setString(1, apuntado);
			pst.setString(2, codUsuario);
			pst.setString(3, codActividad);
			pst.setString(4, codInstalacion);
			pst.setString(5, fecha);
			pst.setString(6, hora);
			pst.executeUpdate();
			pst.close();
			con.close();
		}
		
		public static boolean existsReserva (String codUsuario, String fecha, String hora) throws SQLException {
			Connection con = getConnection();
			PreparedStatement pst = con.prepareStatement("SELECT *"
					+ " FROM RESERVAS r"
					+ " WHERE r.codUsuario=? and r.fecha=? and r.horaInicio=?");
			pst.setString(1, codUsuario);
			pst.setString(2, fecha);
			pst.setString(3, hora);
			ResultSet rs = pst.executeQuery();
			boolean exists = rs.next();
			rs.close();
			pst.close();
			con.close();
			return exists;
		}
		
		public static Reserva getReserva (String fecha, String hora, String codInstalacion, String cancelada) throws SQLException { //puntual
			Connection con = getConnection();
			PreparedStatement pst = con.prepareStatement("SELECT *"
					+ " FROM RESERVAS r"
					+ " WHERE r.fecha=? and r.horaInicio=? and r.codInstalacion=? and r.cancelada=?");
			pst.setString(1, fecha);
			pst.setString(2, hora);
			pst.setString(3, codInstalacion);
			pst.setString(4, cancelada);
			ResultSet rs = pst.executeQuery();
			Reserva reserva = null;
			if (rs.next())
				reserva = new Reserva(rs.getString(2), rs.getString(1), rs.getString(4), rs.getString(5), rs.getString(3), String.valueOf(rs.getInt(6)), rs.getString("cancelada"));
			rs.close();
			pst.close();
			con.close();
			return reserva;
		}
		
		public static Reserva getReserva (String fecha, String hora, String codInstalacion) throws SQLException { //periodicas
			Connection con = getConnection();
			PreparedStatement pst = con.prepareStatement("SELECT *"
					+ " FROM RESERVAS r"
					+ " WHERE r.fecha=? and r.horaInicio=? and r.codInstalacion=?");
			pst.setString(1, fecha);
			pst.setString(2, hora);
			pst.setString(3, codInstalacion);
			ResultSet rs = pst.executeQuery();
			Reserva reserva = null;
			if (rs.next())
				reserva = new Reserva(rs.getString(2), rs.getString(1), rs.getString(4), rs.getString(5), rs.getString(3), String.valueOf(rs.getInt(6)), rs.getString("cancelada"));
			rs.close();
			pst.close();
			con.close();
			return reserva;
		}
		
		public static ArrayList<Reserva> getReservasInterseccion(String fecha, String horaInicial, String horaFinal, String codInstalacion) throws SQLException {
			Connection con = getConnection();
			PreparedStatement pst = con.prepareStatement("select * " + 
					"from reservas r " + 
					"where r.fecha=? and r.codInstalacion=? " + 
					"intersect " + 
					"select * " + 
					"from reservas r1 " + 
					"where (r1.horaInicio<? and r1.horaFinal>=?) or " + 
					"(r1.horaInicio<? and r1.horaFinal<? and r1.horaInicio>=?) or " +
					"(r1.horaInicio<? and r1.horaFinal<? and r1.horaFinal>?)");
			pst.setString(1, fecha);
			pst.setString(2, codInstalacion);
			pst.setString(3, horaFinal);
			pst.setString(4, horaFinal);
			pst.setString(5, horaFinal);
			pst.setString(6, horaFinal);
			pst.setString(7, horaInicial);
			ResultSet rs = pst.executeQuery();
			
			ArrayList<Reserva> reservas = new ArrayList<Reserva>();
			while (rs.next()) {
				reservas.add(new Reserva(rs.getString(2), rs.getString(1), rs.getString(4), rs.getString(5), rs.getString(3), String.valueOf(rs.getInt(6)), rs.getString("cancelada")));
			}
			rs.close();
			pst.close();
			con.close();
			return reservas;
		}
		
		/**
		 * Método que obtiene todas aquellas reservas de la base de datos pertenecientes a eventos no cancelados.
		 * @param codActividad el codigo de la actividad de la que se desea encontrar eventos.
		 * @return ArrayList<Reserva> lista de todas aquellas reservas no canceladas de un determinado evento
		 * @throws SQLException 
		 */
		public static ArrayList<Reserva> getReservasEventosNoCancelados(String codActividad) throws SQLException{
			Connection con = getConnection();
			PreparedStatement pst = con.prepareStatement("Select r.codInstalacion, r.codUsuario, r.fecha, r.horaInicio, r.horaFinal, r.cancelada, r.instalacionOcupada " + 
					"From Eventos e, Reservas r " + 
					"Where e.codActividad=? and r.cancelada='false' and r.codInstalacion=e.codInstalacion and " + 
					"r.codUsuario=e.codUsuarioR and r.fecha=e.fecha and r.horaInicio=e.horaInicio");
			pst.setString(1, codActividad);
			ResultSet rs = pst.executeQuery();
			ArrayList<Reserva> reservas = new ArrayList<Reserva>();
			while (rs.next()) {
				reservas.add(new Reserva(rs.getString("codUsuario"), rs.getString("codInstalacion"), 
						rs.getString("horaInicio"), rs.getString("horaFinal"), rs.getString("fecha"), rs.getString("instalacionOcupada"), rs.getString("cancelada")));
			}
			rs.close();
			pst.close();
			con.close();
			return reservas;
		}
		
		
		public static boolean existsApuntado (String codUsuario, String fecha, String hora) throws SQLException {
			Connection con = getConnection();
			PreparedStatement pst = con.prepareStatement("SELECT *"
					+ " FROM APUNTADOS a"
					+ " WHERE a.codUsuario=? and a.fecha=? and a.horaInicio=? and (a.apuntado='true' or a.apuntado='var1')");
			pst.setString(1, codUsuario);
			pst.setString(2, fecha);
			pst.setString(3, hora);
			ResultSet rs = pst.executeQuery();
			boolean exists = rs.next();
			pst.close();
			pst.close();
			con.close();
			return exists;
		}
		
		public static void removeEvento(String codInstalacion, String fecha, String horaInicio) throws SQLException {
			Connection con = getConnection();
			PreparedStatement pst = con.prepareStatement("DELETE from EVENTOS"
					+ " WHERE codInstalacion=? and fecha=? and horaInicio=?");
			pst.setString(1, codInstalacion);
			pst.setString(2, fecha);
			pst.setString(3, horaInicio);
			pst.executeUpdate();
			pst.close();
			con.close();
		}
		
		///------------------------------------------------------------------------------------------------------------------------------------------
		
		///---------------------------------------------------------JOSE-----------------------------------------------------------------------------
		public static void setReservaSociosAdmin(String codUsuario, String codInstalacion, String horaInicio, String horaFin, String fecha, String tipoPago) throws SQLException{
			Connection con = getConnection();
			PreparedStatement pst = con.prepareStatement("Insert into RESERVAS(codInstalacion,codUsuario,fecha,horaInicio,horaFinal,instalacionOcupada,formaDePago) values (?,?,?,?,?,?,?)");
			pst.setString(1, codInstalacion);
			pst.setString(2, codUsuario);
			pst.setString(3, fecha);
			pst.setString(4, horaInicio);
			pst.setString(5, horaFin);
			pst.setInt(6, 0);
			pst.setString(7, tipoPago);
			int res = pst.executeUpdate();
//			if(res!=0){
//				System.out.println("Se inserto Reserva de Socio por Admin");
//			}else{
//				System.out.println("No se inserto");
//			}
			pst.close();
			con.close();
		}
		public static void setReserva(String codUsuario, String codInstalacion, String horas, String horasTerminar, String fecha, String estado) throws SQLException{
			Connection con = getConnection();
			PreparedStatement pst = con.prepareStatement("Insert into RESERVAS(codInstalacion,codUsuario,fecha,horaInicio,horaFinal,instalacionOcupada) values (?,?,?,?,?,?)");
			pst.setString(1, codInstalacion);
			pst.setString(2, codUsuario);
			pst.setString(3, fecha);
			pst.setString(4, horas);
			pst.setString(5, horasTerminar);
			pst.setString(6, estado);
			pst.executeUpdate();
			pst.close();
			//con.commit();
			con.close();
		}
		public static void setPago(String codInstalacion, String codUsuario, String fechaReserva, String desde,
				String codPago, double d) throws SQLException {
			Connection con = getConnection();
			PreparedStatement pst = con.prepareStatement("Insert into PAGOS(codInstalacion,codUsuario,fecha,horaInicio,codPago,cantidad)values (?,?,?,?,?,?)");
			pst.setString(1, codInstalacion);
			pst.setString(2, codUsuario);
			pst.setString(3, fechaReserva);
			pst.setString(4, desde);
			pst.setString(5, codPago);
			pst.setDouble(6, d);
			pst.executeUpdate();
			pst.close();
			con.close();
		}
		
		public static void insertarUnNoSocio(Usuario noSocio) throws SQLException{
			Connection con = getConnection();
			PreparedStatement pst = con.prepareStatement("Insert into USUARIOS(codUsuario,password,nombreUsuario,apellidoUsuario,telefonoUsuario,DNIUsuario,emailUsuario) values (?,?,?,?,?,?,?)");
			pst.setString(1, noSocio.getCodUsuario());
			pst.setString(2, "X");
			pst.setString(3, noSocio.getuNombre());
			pst.setString(4, noSocio.getApellido());
			pst.setString(5, Integer.toString(noSocio.getTelefono()));
			pst.setString(6, noSocio.getDni());
			pst.setString(7, noSocio.getEmail());
			int res = pst.executeUpdate();
//			if(res!=0){
//				System.out.println("Se inserto un nuevo NoSocio");
//			}else{
//				System.out.println("No se inserto");
//			}
			pst.close();
			con.close();
		}
		
		public static void actulizarUsoLlaves(String horaRecogida, String horaDevolucion, Reserva reserva) throws SQLException {
			Connection con = getConnection();
			PreparedStatement pst = con.prepareStatement("UPDATE RESERVAS SET recogidaLlaves=?, devolucionLlaves=? WHERE codInstalacion=? AND codUsuario=? AND fecha=? AND horaInicio=?");
			pst.setString(1, horaRecogida);
			pst.setString(2, horaDevolucion);
			pst.setString(3, reserva.getCodInstalacion());
			pst.setString(4, reserva.getCodUsuario());
			pst.setString(5, reserva.getFechaReserva());
			pst.setString(6, reserva.getDesde());
			int res = pst.executeUpdate();
//			if(res!=0){
//				System.out.println("Se actualizaron las horas");
//			}else{
//				System.out.println("No se actualizaron las horas");
//			}
			pst.close();
			con.close();
		}
		
		public static ArrayList<EventoActividad> cargarEventos() throws SQLException{
			ArrayList<EventoActividad> eventos = new ArrayList<EventoActividad>();
			Connection con = getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM EVENTOS");
			while (rs.next()) {
				eventos.add(new EventoActividad(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6)));
						
			}
			rs.close();
			st.close();
			con.close();
			return eventos;
		}
		
		public static ArrayList<Actividad> cargarActividades() throws SQLException{
			ArrayList<Actividad> actividades = new ArrayList<Actividad>();
			Connection con = getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM ACTIVIDADES");
			while (rs.next()) {
				actividades.add(new Actividad(rs.getString(1), rs.getString(2)));
						
			}
			rs.close();
			st.close();
			con.close();
			return actividades;
		}
		
		public static ArrayList<Instalacion> cargarInstalaciones() throws SQLException{
			ArrayList<Instalacion> insta = new ArrayList<Instalacion>();
			Connection con = getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM INSTALACIONES");
			while (rs.next()) {
				insta.add(new Instalacion(rs.getString(1), rs.getString(2)));			
			}
			rs.close();
			st.close();
			con.close();
			return insta;
		}
		public static void apuntarNuevoSocioMonitor(String codUsuario, String codActividad, String codInstalacion, String fecha, String hora) throws SQLException {
			Connection con = getConnection();
			PreparedStatement pst = con
					.prepareStatement("INSERT INTO APUNTADOS (codUsuario, codActividad, codInstalacion, codUsuarioR, fecha, horaInicio,apuntado)"
							+ "VALUES (?,?,?,'A-001',?,?,'var1')");
			pst.setString(1, codUsuario);
			pst.setString(2, codActividad);
			pst.setString(3, codInstalacion);
			pst.setString(4, fecha);
			pst.setString(5, hora);
			pst.executeUpdate();
			pst.close();
			con.close();
		}
		public static String getEstadoSocioActividad(String codUsuario, String codActividad, String codInsta,String fecha, String hora) throws SQLException {
			Connection con = getConnection();
			PreparedStatement pst = con.prepareStatement("SELECT apuntado FROM APUNTADOS "
														+ "WHERE codUsuario=? "
														+ "AND codActividad=? "
														+ "AND codInstalacion=? "
														+ "AND fecha=? "
														+ "AND horaInicio=?");
			pst.setString(1, codUsuario);
			pst.setString(2, codActividad);
			pst.setString(3, codInsta);
			pst.setString(4, fecha);
			pst.setString(5, hora);
			ResultSet rs =pst.executeQuery();
			String s="";
			while(rs.next()) {
				s=rs.getString(1);
			}
			rs.close();
			pst.close();
			con.close();
			return s;
		}
		
		public static void marcarSociosComoAsistidos(String string, String codActividad, String codInstalacion,String fecha, String hora) throws SQLException {
			Connection con = getConnection();
			PreparedStatement pst = con.prepareStatement("UPDATE APUNTADOS SET apuntado=? "
														+ "WHERE codUsuario=? "
														+ "AND codActividad=? "
														+ "AND codInstalacion=? "
														+ "AND fecha=? "
														+ "AND horaInicio=?");
			pst.setString(1, "var1");
			pst.setString(2, string);
			pst.setString(3, codActividad);
			pst.setString(4, codInstalacion);
			pst.setString(5, fecha);
			pst.setString(6, hora);
			int res = pst.executeUpdate();
//			if(res!=0){
//				System.out.println("Se actualizo el estado");
//			}else{
//				System.out.println("No actualizo el estado");
//			}
			pst.close();
			con.close();
			
		}
		
		public static void marcarSociosComoFalto(String string, String codActividad, String codInstalacion,String fecha, String hora) throws SQLException {
			Connection con = getConnection();
			PreparedStatement pst = con.prepareStatement("UPDATE APUNTADOS SET apuntado=? "
														+ "WHERE codUsuario=? "
														+ "AND codActividad=? "
														+ "AND codInstalacion=? "
														+ "AND fecha=? "
														+ "AND horaInicio=?");
			pst.setString(1, "var2");
			pst.setString(2, string);
			pst.setString(3, codActividad);
			pst.setString(4, codInstalacion);
			pst.setString(5, fecha);
			pst.setString(6, hora);
			int res = pst.executeUpdate();
//			if(res!=0){
//				System.out.println("Se actualizo el estado");
//			}else{
//				System.out.println("No actualizo el estado");
//			}
			pst.close();
			con.close();
			
		}
		public static ArrayList<String> getActividadesPorNombre(String nombre) throws SQLException {
			Connection con = getConnection();
			PreparedStatement pst = con.prepareStatement("SELECT * FROM EVENTOS WHERE codActividad=(SELECT codActividad FROM ACTIVIDADES where nombreA=?)");
			pst.setString(1, nombre);
			ResultSet rs = pst.executeQuery();
			ArrayList<String> lista = new ArrayList<String>();
			while(rs.next()) {
				lista.add(rs.getString("fecha"));
			}
			rs.close();
			pst.close();
			con.close();
			return lista;
			
		}
		
		public static void crearNuevaActividad(String codActividad, String nombreActividad) throws SQLException{
			Connection con = getConnection();
			PreparedStatement pst = con.prepareStatement("Insert into ACTIVIDADES(codActividad,nombreA) values (?,?)");
			pst.setString(1, codActividad);
			pst.setString(2, nombreActividad);
			pst.executeUpdate();
			pst.close();
			//con.commit();
			con.close();
		}
		public static int crearEventoActividad(String codActividad, String codInstalacion, String codAdmin, String fecha,String horaInicio, String numPlazas) throws SQLException{
			Connection con = getConnection();
			PreparedStatement pst = con.prepareStatement("Insert into EVENTOS(codActividad,codInstalacion,codUsuarioR,fecha,horaInicio,plazas) values (?,?,?,?,?,?)");
			pst.setString(1, codActividad);
			pst.setString(2, codInstalacion);
			pst.setString(3, codAdmin);
			pst.setString(4, fecha);
			pst.setString(5, horaInicio);
			pst.setString(6, numPlazas);
			int res = pst.executeUpdate();
			pst.close();
			//con.commit();
			con.close();
			return res;
		}
		
		public static int countActividades() throws SQLException {
			Connection con = getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select count(*) from actividades");
			int number = rs.getInt(1);
			rs.close();
			st.close();
			con.close();
			return number;
		}
		public static List<Map<String,String>> getPaqueteActividadesPeriodicas(String codActividad) throws SQLException {
			Connection con = getConnection();
			PreparedStatement pst = con.prepareStatement("SELECT * FROM EVENTOS WHERE codActividad=?");
			pst.setString(1, codActividad);
			ResultSet rs =pst.executeQuery();
			List<Map<String,String>> list = new ArrayList<Map<String,String>>();
			while(rs.next()) {
				Map<String,String> actividad = new HashMap<String,String>();
				actividad.put("codActividad", rs.getString(1));
				actividad.put("codInstalacion", rs.getString(2));
				actividad.put("fecha", rs.getString(4));
				actividad.put("horaInicio", rs.getString(5));
				list.add(actividad);
			}
			rs.close();
			pst.close();
			con.close();
			return list;
		}
		
		public static void borrarApuntado(String codUsuario, String codActividad, String codInstalacion,String codUsuarioR, String fecha, String horaInicio) throws SQLException {
			Connection con = getConnection();
			PreparedStatement pst = con.prepareStatement("DELETE FROM APUNTADOS WHERE codActividad=? AND codInstalacion=? AND codUsuario= ? AND  fecha= ? AND horaInicio= ?");
			pst.setString(1, codActividad);
			pst.setString(2, codInstalacion);
			pst.setString(3, codUsuario);
			pst.setString(4, fecha);
			pst.setString(5, horaInicio);
			int res = pst.executeUpdate();
			if(res!=0){
				System.out.println("Se borro a un usuario de la lista de apuntados");
			}else{
				System.out.println("Fallo: no se borro a un usuario");
			}
			pst.close();
			con.close();
			
		}
		public static void borrarEventoActividadPeriodico(String codActividad, String codInstalacion, String fecha, String horaInicio) throws SQLException {
			Connection con = getConnection();
			PreparedStatement pst = con.prepareStatement("DELETE FROM EVENTOS WHERE codActividad=? AND codInstalacion=? AND  fecha= ? AND horaInicio= ?");
			pst.setString(1, codActividad);
			pst.setString(2, codInstalacion);
			pst.setString(3, fecha);
			pst.setString(4, horaInicio);
			int res = pst.executeUpdate();
			if(res!=0){
				System.out.println("Se borro un evento de la lista de eventos");
			}else{
				System.out.println("Fallo: no se borro un evento");
			}
			pst.close();
			con.close();
			
		}
		
		public static void cancelarReservaPorActividadPeriodica(String codInstalacion, String fecha, String horaInicio, String motivo) throws SQLException {
			Connection con = getConnection();
			PreparedStatement pst = con.prepareStatement("UPDATE RESERVAS set motivo=?, cancelada=?  Where fecha=? and codInstalacion=? and codUsuario=? and horaInicio=?");
			pst.setString(1, motivo);
			pst.setString(2, "true");
			pst.setString(3, fecha);
			pst.setString(4, codInstalacion);
			pst.setString(5, "A-001");
			pst.setString(6, horaInicio);
			int res = pst.executeUpdate();
			if(res!=0){
				System.out.println("Se cancelo una reserva de la lista de reservas");
			}else{
				System.out.println("Fallo: no se cancelo una reserva");
			}
			pst.close();
			con.close();
			
		}
		
		///-----------------------------------------------------------------------------------------------------------------------------------------
		public static ArrayList<String[]> getHorasReservaSociosAdmin(String fecha, String codInstalacion,String codUsuario) throws SQLException{
			Connection con = getConnection();
			PreparedStatement pst = con.prepareStatement("Select horaInicio, horaFinal, cancelada from RESERVAS where fecha=? and codInstalacion=? and codUsuario=?");
			pst.setString(1, fecha);
			pst.setString(2, codInstalacion);
			pst.setString(3, codUsuario);
			ResultSet rs =pst.executeQuery();
			ArrayList<String[]> lista = new ArrayList<String[]>();
			while(rs.next()) {
				if(rs.getString(3).equals("false"))
				lista.add(new String[] {rs.getString(1),rs.getString(2)});
			}
			rs.close();
			pst.close();
			con.close();
			return lista;
		}

		public static ArrayList<String[]> getHorasReservaNoSociosAdmin(String fecha, String codInstalacion,
				String DNI) throws SQLException {
			Connection con = getConnection();
			PreparedStatement pst = con.prepareStatement("Select horaInicio, horaFinal,cancelada from RESERVAS,USUARIOS where fecha=? and codInstalacion=? and DNIUsuario=? and RESERVAS.codUsuario=USUARIOS.codUsuario");
			pst.setString(1, fecha);
			pst.setString(2, codInstalacion);
			pst.setString(3, DNI);
			ResultSet rs =pst.executeQuery();
			ArrayList<String[]> lista = new ArrayList<String[]>();
			while(rs.next()) {
				if(rs.getString(3).equals("false"))
				lista.add(new String[] {rs.getString(1),rs.getString(2)});
			}
			rs.close();
			pst.close();
			con.close();
			return lista;
		}

		public static void cancelarReserva(String fecha, String codInstalacion, String codUsuario,
				String horaInicio, String horaFin, String motivo1) throws SQLException {
			Connection con = getConnection();
			PreparedStatement pst = con.prepareStatement("UPDATE RESERVAS set motivo=?, cancelada=?  Where fecha=? and codInstalacion=? and codUsuario=? and horaInicio=? and horaFinal=?");
			pst.setString(1, motivo1);
			pst.setString(2, "true");
			pst.setString(3, fecha);
			pst.setString(4, codInstalacion);
			pst.setString(5, codUsuario );
			pst.setString(6, horaInicio);
			pst.setString(7, horaFin);
			pst.executeUpdate();
			pst.close();
			con.close();
		}
		
		public static void desCancelarReserva(String fecha, String codInstalacion, String codUsuario,
				String horaInicio, String horaFin) throws SQLException {
			Connection con = getConnection();
			PreparedStatement pst = con.prepareStatement("UPDATE RESERVAS set motivo=?, cancelada=?  Where fecha=? and codInstalacion=? and codUsuario=? and horaInicio=? and horaFinal=?");
			pst.setString(1, null);
			pst.setString(2, "false");
			pst.setString(3, fecha);
			pst.setString(4, codInstalacion);
			pst.setString(5, codUsuario );
			pst.setString(6, horaInicio);
			pst.setString(7, horaFin);
			pst.executeUpdate();
			pst.close();
			con.close();
		}

		public static void cancelarReservaNoSocioAdministracion(String fecha, String codInstalacion, String dNI,
				String horaInicio, String horaFin, String motivo1) throws SQLException {
			Connection con = getConnection();
			PreparedStatement pst = con.prepareStatement("UPDATE RESERVAS set motivo=?, cancelada=? Where fecha=? and codInstalacion=? and codUsuario=? and horaInicio=? and horaFinal=?");
			pst.setString(1, motivo1);
			pst.setString(2, "true");
			pst.setString(3, fecha);
			pst.setString(4, codInstalacion);
			pst.setString(5, getCodigoUsuarioNoSocio(dNI) );
			pst.setString(6, horaInicio);
			pst.setString(7, horaFin);
			pst.executeUpdate();
			pst.close();
			con.close();
		}

		public static String getCodigoUsuarioNoSocio(String dNI) throws SQLException {
			Connection con = getConnection();
			PreparedStatement pst = con.prepareStatement("Select codUsuario from USUARIOS where DNIUsuario=?");
			pst.setString(1, dNI);
			ResultSet rs=pst.executeQuery();
			String codUsuario= rs.getString("codUsuario");
			rs.close();
			pst.close();
			con.close();
			return codUsuario;
		}

		public static ArrayList<String[]> getHorasReservaAdmin(String fecha, String codInstalacion) throws SQLException {
			Connection con = getConnection();
			PreparedStatement pst = con.prepareStatement("Select horaInicio, horaFinal, cancelada from RESERVAS where fecha=? and codInstalacion=? order by horaInicio asc");
			pst.setString(1, fecha);
			pst.setString(2, codInstalacion);
			ResultSet rs =pst.executeQuery();
			ArrayList<String[]> lista = new ArrayList<String[]>();
			while(rs.next()) {
				if(rs.getString(3).equals("false"))
				lista.add(new String[] {rs.getString(1),rs.getString(2)});
			}
			rs.close();
			pst.close();
			con.close();
			return lista;
		}

		public static String cancelarReservaAdmin(String fecha,String codInstalacion, String horaInicio, String horaFin,
				String motivo1) throws SQLException {
			Connection con = getConnection();
			PreparedStatement pst = con.prepareStatement("UPDATE RESERVAS set motivo=?, cancelada=?  Where fecha=? and codInstalacion=?  and horaInicio=? and horaFinal=?");
			pst.setString(1, motivo1);
			pst.setString(2, "true");
			pst.setString(3, fecha);
			pst.setString(4, codInstalacion);
			pst.setString(5, horaInicio);
			pst.setString(6, horaFin);
			pst.executeUpdate();
			pst=con.prepareStatement("Select codUsuario from RESERVAS where fecha=? and codInstalacion=?  and horaInicio=? and horaFinal=?");
			pst.setString(1, fecha);
			pst.setString(2, codInstalacion);
			pst.setString(3, horaInicio);
			pst.setString(4, horaFin);
			ResultSet rs= pst.executeQuery();
			String usuario = rs.getString(1);
			rs.close();
			pst.close();
			con.close();
			return usuario;
		}

		public static String getNombreActividad(String codActividad) throws SQLException {
			Connection con = getConnection();
			PreparedStatement pst = con.prepareStatement("Select nombreA from ACTIVIDADES where codActividad=?");
			pst.setString(1, codActividad);
			ResultSet rs= pst.executeQuery();
			String nombre = rs.getString(1);
			rs.close();
			pst.close();
			con.close();
			return nombre;
		}

	

		public static String getNombreInstalacion(String codInstalacion) throws SQLException {
			Connection con = getConnection();
			PreparedStatement pst = con.prepareStatement("Select nombreInstalacion from INSTALACIONES where codInstalacion=?");
			pst.setString(1, codInstalacion);
			ResultSet rs= pst.executeQuery();
			String nombre = rs.getString(1);
			rs.close();
			pst.close();
			con.close();
			return nombre;
		}

		public static String getHoraFinalEvento(String codInstalacion, String codUsuario, String fecha, String horaInicio) throws SQLException {
			Connection con = getConnection();
			PreparedStatement pst = con.prepareStatement("Select horaFinal from RESERVAS where codInstalacion=? and codUsuario =? and fecha=? and horaInicio=?");
			pst.setString(1, codInstalacion);
			pst.setString(2, codUsuario);
			pst.setString(3, fecha);
			pst.setString(4, horaInicio);
			ResultSet rs= pst.executeQuery();
			String hora = rs.getString(1);
			rs.close();
			pst.close();
			con.close();
			return hora;
		}
		public static void pasarApuntadoFalse(String string, String string2, String string3, String string4, String string5, String string6, String string7) {

			Connection con = getConnection();

			String Ssql = "UPDATE APUNTADOS SET apuntado=?"
					+ "WHERE  codUsuario = ? AND codActividad = ? AND codInstalacion= ? AND codUsuarioR = ? AND  fecha= ? AND horaInicio= ? ";

			try {

				PreparedStatement pst = con.prepareStatement(Ssql);
				pst.setString(1, "false");
				pst.setString(2, string);
				pst.setString(3, string2);
				pst.setString(4, string3);
				pst.setString(5, string4);
				pst.setString(6, string5);
				pst.setString(7, string6);
				
				int res = pst.executeUpdate();
				if (res != 0) {
					System.out.println("Se borro plaza");
					pst.close();
					con.close();
				} else {
					System.out.println("No se borro plaza");
					pst.close();
					con.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
		public static void pasarApuntadoTrue(String string, String string2, String string3, String string4, String string5, String string6) {

			Connection con = getConnection();

			String Ssql = "UPDATE APUNTADOS SET apuntado=?"
					+ "WHERE  codUsuario = ? AND codActividad = ? AND codInstalacion= ? AND codUsuarioR = ? AND  fecha= ? AND horaInicio= ? ";

			try {

				PreparedStatement pst = con.prepareStatement(Ssql);
				pst.setString(1, "true");
				pst.setString(2, string);
				pst.setString(3, string2);
				pst.setString(4, string3);
				pst.setString(5, string4);
				pst.setString(6, string5);
				pst.setString(7, string6);
				
				int res = pst.executeUpdate();
				if (res != 0) {
					System.out.println("Se borro plaza");
					pst.close();
					con.close();
				} else {
					System.out.println("No se borro plaza");
					pst.close();
					con.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		public static ArrayList<Apuntados> cargarApuntados() throws SQLException {
			ArrayList<Apuntados> apuntados = new ArrayList<Apuntados>();
			Connection con = getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM APUNTADOS");
			while (rs.next()) {
				apuntados.add(new Apuntados(rs.getString(2), rs.getString(3), rs.getString(1), rs.getString(5), rs.getString(6),rs.getString(4),rs.getString(7)));
						
			}
			rs.close();
			st.close();
			con.close();
			return apuntados;
		}

		public static void apuntarSocioActividadAdmin(String codU,
				String codActividad, String codInstalacion, String codUsuario,
				String obtenerFecha, String h) {
			Connection con = getConnection();

			String insertTableSQL = "INSERT INTO APUNTADOS"
	                + "(codUsuario, codActividad, codInstalacion, codUsuarioR, fecha, horaInicio) VALUES"
	                + "(?,?,?,?,?,?)";
		

			try {
				PreparedStatement pst = con.prepareStatement(insertTableSQL);
				pst.setString(1, codU);
				pst.setString(2, codActividad);
				pst.setString(3, codInstalacion);
				pst.setString(4, codUsuario);
				pst.setString(5, obtenerFecha);
				pst.setString(6, h);
				int res = pst.executeUpdate();
				if(res!=0){
					System.out.println("Se apunto");
					pst.close();
					con.close();
				}else{
					System.out.println("No se apunto");
					pst.close();
					con.close();
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

	public static void crearEventoActividad(String administrador, int plazas, String codInstalacion,
				String horas, String horasTerminar, String fecha, String estado,String codActividad) {
			
			try {
				Reserva reserva = getReserva(fecha, horas, codInstalacion);
				if (reserva!=null) {
					if (reserva.getCancelada().equals("true") && reserva.getCodUsuario().equals(administrador)) {
						desCancelarReserva(fecha, codInstalacion, administrador, horas, horasTerminar);
						setEventos(codActividad,codInstalacion,administrador,fecha,horas,plazas);
					}
					else if (reserva.getCancelada().equals("true") && !reserva.getCodUsuario().equals(administrador)){
						setReserva(administrador, codInstalacion, horas, horasTerminar, fecha, estado);
						setEventos(codActividad,codInstalacion,administrador,fecha,horas,plazas);
					}
				}
				else {
					setReserva(administrador, codInstalacion, horas, horasTerminar, fecha, estado);
					setEventos(codActividad,codInstalacion,administrador,fecha,horas,plazas);
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		private static void setEventos(String codActividad, String codInstalacion, String administrador, String fecha,
				String horas, int plazas) throws SQLException {
				Connection con = getConnection();
				PreparedStatement pst= con.prepareStatement("INSERT into EVENTOS (codActividad,codInstalacion,codUsuarioR,fecha,horaInicio,plazas)values(?,?,?,?,?,?)");
				pst.setString(1, codActividad);
				pst.setString(2, codInstalacion);
				pst.setString(3, administrador);
				pst.setString(4, fecha);
				pst.setString(5, horas);
				pst.setInt(6, plazas);
				pst.executeUpdate();
				pst.close();
				con.close();
		}

		public static void setActividad(String nombre, String codActividad)  {
			try {	
			Connection con = getConnection();
				PreparedStatement pst = con.prepareStatement("INSERT into ACTIVIDADES(codActividad,nombreA) values (?,?)");
				pst.setString(1, codActividad);
				pst.setString(2, nombre );
				pst.executeUpdate();
				pst.close();
				con.close();
			}catch (SQLException e) {
				e.getSQLState();
			}
		}

		public static String maxActividad() {
			try {
			Connection con = getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("Select max(codActividad) from ACTIVIDADES");
			rs.next();
			String codActividad=rs.getString(1);
			rs.close();
			st.close();
			con.close();
			return codActividad;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
		public static ArrayList<EventoActividad> getEventosActividadesPorNombre(String nombre) throws SQLException {
						Connection con = getConnection();
						PreparedStatement pst = con.prepareStatement("SELECT fecha,codActividad FROM EVENTOS WHERE codActividad=(SELECT codActividad FROM ACTIVIDADES where nombreA=?)");
						pst.setString(1, nombre);
						ResultSet rs = pst.executeQuery();
						ArrayList<EventoActividad> lista = new ArrayList<EventoActividad>();
						while(rs.next()) {
							lista.add(new EventoActividad(rs.getString(1), rs.getString(2)));
						}
						rs.close();
						pst.close();
						con.close();
						return lista;
						
					}
		
		

		public static void borrarEventoActividad(String codInstalacion,
				String fecha, String horaInicio, String codActividad,
				String codUsuario) throws SQLException {
			Connection con = getConnection();
			PreparedStatement pst = con.prepareStatement("DELETE from EVENTOS"
					+ " WHERE codInstalacion=? and fecha=? and horaInicio=? and codActividad=? and codUsuarioR=?");
			pst.setString(1, codInstalacion);
			pst.setString(2, fecha);
			pst.setString(3, horaInicio);
			pst.setString(4, codActividad);
			pst.setString(5, codUsuario);
			pst.executeUpdate();
			pst.close();
			con.close();
			
		}

		public static EventoActividad _getEvento(String codInstalacion, String fechaReserva, String desde) {
			Connection con = getConnection();
			try {
			PreparedStatement pst = con.prepareStatement("Select * from EVENTOS"
					+ " WHERE codInstalacion=? and fecha=? and horaInicio=?");
			pst.setString(1, codInstalacion);
			pst.setString(2, fechaReserva);
			pst.setString(3, desde);
			ResultSet st =pst.executeQuery();
			EventoActividad ea= new EventoActividad(st.getString("codActividad"), st.getString("codInstalacion"), st.getString("codUsuarioR"), st.getString("fecha"), st.getString("horaInicio"),st.getInt("plazas") );
			st.close();
			pst.close();
			con.close();
			return ea;
			}catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		}
		
	
		
		
		
}


