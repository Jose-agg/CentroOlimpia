package logica.programa;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dataBase.DataBase;
import logica.objetos.Actividad;
import logica.objetos.Apuntados;

public class CancelarActividadPeriodica {

	private Actividad actividad;
	private String fecha;
	private List<Map<String,String>> listaFinal;
	private Programa programa;
	
	public CancelarActividadPeriodica(Actividad actividadParaBorrarP, String fechaParaBorrarP, Programa programa) {
		this.actividad=actividadParaBorrarP;
		this.fecha=fechaParaBorrarP;
		this.programa=programa;
	}
	
	public List<Map<String, String>> getEstadoLista() {
		return listaFinal;
	}
	
	public void getActividadesCancelar() {
		List<Map<String,String>> l = new ArrayList<Map<String,String>>();
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		ArrayList<String> listaInstalaciones = new ArrayList<String>();
		try {
			l = DataBase.getPaqueteActividadesPeriodicas(actividad.getCodActividad());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (Map<String, String> map : l) {
			if(comprobarSiFechaPosterior(map.get("fecha"))) {
				list.add(map);
				String s=map.get("codInstalacion");
				for (String string : listaInstalaciones) {
					if(s.equals(string)) {
						s="";
					}
				}
				if(!s.equals("")) listaInstalaciones.add(s);
			}
		}
		listaFinal=ordenarPorFecha(list);
		listaFinal=ordenarPorInstalaciones(listaFinal,listaInstalaciones);
	}

	public boolean comprobarSiFechaPosterior(String f) {
		String[] original = fecha.split("/");
		String[] variable = f.split("/");
		int[] ori = new int[]{Integer.valueOf(original[0]),Integer.valueOf(original[1]),Integer.valueOf(original[2])};
		int[] var = new int[]{Integer.valueOf(variable[0]),Integer.valueOf(variable[1]),Integer.valueOf(variable[2])};
		if(ori[2]==var[2]) {
			if(ori[1]==var[1]) {
				if(ori[0]==var[0]) {
					return true;
				}
				else if(ori[0]<var[0]) return true;
				else return false;
			}
			else if(ori[1]<var[1]) return true;
			else return false;
		}
		else if(ori[2]<var[2]) return true;
		else return false;
	}
	
	private List<Map<String, String>> ordenarPorFecha(List<Map<String, String>> list) {
		ArrayList<Date> fechas = new ArrayList<Date>();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dateInString = "";
		for (Map<String, String> map : list) {
			dateInString = map.get("fecha");
			try {
	            Date date = formatter.parse(dateInString);
	            fechas.add(date);

	        } catch (ParseException e) {
	            e.printStackTrace();
	        }
		}
		Collections.sort(fechas);
		ArrayList<String> fechasString = new ArrayList<String>();
		for (Date date : fechas) {
			fechasString.add(formatter.format(date));
		}
		List<Map<String,String>> l = new ArrayList<Map<String,String>>();
		for (String string : fechasString) {
			for (Map<String, String> m : list) {
				if(m.get("fecha").equals(string)) {
					l.add(m);
					break;
				}
			}
		}
		return l;
	}
	
	private List<Map<String, String>> ordenarPorInstalaciones(List<Map<String, String>> listaFinal2, ArrayList<String> listaInstalaciones) {
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		for (String inst : listaInstalaciones) {
			for (Map<String, String> map : listaFinal2) {
				if(map.get("codInstalacion").equals(inst)) {
					list.add(map);
				}
			}
		}
		return list;
	}

	public void borrarDatos() {
		try {
			borrarGenteApuntada();
			borrarEventoDeActividad();
			cancelarReserva();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// Key: "codActividad", Value: el codigo de la actividad
	// Key: "codInstalacion", Value: el codigo de la instalacion
	// Key: "fecha", Value: la fecha de esa actividad
	// Key: "horaInicio", Value: la hora de inicio de esa actividad
	
	public void borrarGenteApuntada() throws SQLException {
		ArrayList<Apuntados> apuntados = programa.getListaApuntados();
		int cont = 0;
		
		for (Map<String, String> map : listaFinal) {
			for (Apuntados ap : apuntados) {
				if(ap.getCodActividad().equals(map.get("codActividad")) &&
						ap.getCodInstalacion().equals(map.get("codInstalacion")) &&
						ap.getFecha().equals(map.get("fecha")) &&
						ap.getHoraInicio().equals(map.get("horaInicio"))) {
					programa.borrarPlazaSocio(ap);
					cont++;
				}
			}
		}
		System.out.println("Se han borrado "+cont+" usuarios");
	}
	
	private void borrarEventoDeActividad() throws SQLException {
		for (Map<String, String> map : listaFinal) {
			programa.borrarEventoPeriodico(map.get("codActividad"),map.get("codInstalacion"), map.get("fecha"), map.get("horaInicio"));
		}
		programa.actualizarListaEventos();
	}

	private void cancelarReserva() throws SQLException {
		for (Map<String, String> map : listaFinal) {
			programa.cancelarReservaPorActividadPeriodica(map.get("codInstalacion"), map.get("fecha"), map.get("horaInicio"));
		}
		programa.actualizarListaEventos();
	}

	public List<HashMap<String, String>> getApuntados() {
		try {
			ArrayList<Apuntados> apuntados = DataBase.cargarApuntados();
			List<HashMap<String, String>> aux = new ArrayList<HashMap<String, String>>();
			for (Map<String, String> map : listaFinal) {
				for (Apuntados ap : apuntados) {
					if(ap.getCodActividad().equals(map.get("codActividad")) &&
							ap.getCodInstalacion().equals(map.get("codInstalacion")) &&
							ap.getFecha().equals(map.get("fecha")) &&
							ap.getHoraInicio().equals(map.get("horaInicio"))) {
						HashMap<String, String> mapa = new HashMap<String, String>();
						mapa.put("codigo", ap.getCodUsuario());
						mapa.put("instalacion", ap.getCodInstalacion());
						mapa.put("hora", ap.getHoraInicio());
						mapa.put("fecha", ap.getFecha());
						mapa.put("motivo", "Se ha eliminado la actividad "+ DataBase.getNombreActividad(ap.getCodActividad()));
						aux.add(mapa);
					}
				}
			}
			return aux;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

}
