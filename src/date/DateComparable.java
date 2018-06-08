package date;

import java.util.Calendar;
import java.util.Date;


public class DateComparable extends Date{

	private int year;
	private int month;
	private int day;
	
	public DateComparable(int day, int month) {
		setDay(day);
		setMes(month);
		setAnio(2017);
	}
	
	public DateComparable(int day, int month, int year) {
		setDay(day);
		setMes(month);
		setAnio(year);
	}
	
	public DateComparable() {
		Calendar cal = Calendar.getInstance();
		setDay(cal.get(Calendar.DAY_OF_MONTH));
		setMes(cal.get(Calendar.MONTH)+1);
		setAnio(cal.get(Calendar.YEAR));
	}
	
	private void setDay(int day) {
		if (day>0 && day<=31) {
			this.day = day;
		}
	}
	
	private void setMes(int month) {
		if (month>0 && month<=12) {
			this.month = month;
		}
	}
	
	private void setAnio(int year) {
		if (year>1990) {
			this.year = year;
		}
	}
	
	public int getYear() {
		return year;
	}

	public int getMonth() {
		return month;
	}

	public int getDay() {
		return day;
	}
	
	/**
	 * Método que comprueba si una fecha pasada por parámatero es mayor, menor o igual que el día actual
	 * @param date la fecha a comparar
	 * @return Devuelve 0 si son la misma fecha, -1 si es una fecha menor, y 1 si es mayor
	 */
	public int compareDates(int year, int month, int day) {
		if (getYear()==year) {
			if (getMonth()==month) {
				if (getDay()==day) {
					return 0;
				} else if (getDay()>day) {
					return -1;
				} else {
					return 1;
				}
			} else if (getMonth()>month) {
				return -1;
			} else {
				return 1;
			}
		} else if (getYear()>year) {
			return -1;
		} else {
			return 1;
		}
	}
	
	/**
	 * Método que comprueba si la fecha actual se encuentra entre un periodo establecido
	 * @param firstDay el  día del primer mes desde el que comienza el intervalo a comprobar
	 * @param previousMonth el primer mes entre el que se debe encontrar la fecha
	 * @param secondDay el día del segundo mes donde finaliza el intervalo
	 * @param actualMonth el segundo mes donde finaliza el intervalo
	 * @return true si pertenece al intervalo, false en caso contrario
	 */
	public boolean isBetween(int firstDay, int previousMonth, int secondDay, int actualMonth) {
		if (month==previousMonth && day>=firstDay) {
			return true;
		} else if (month==actualMonth && day<=secondDay) {
			return true;
		} else
			return false;
	}
	
	
	/**
	 * Obtiene el més anterior a uno que se le pase como parámetro
	 * @param month el mes del que obtener el mes anterior
	 * @return int el mes anterior a uno que se le pase por parámetro
	 */
	public int getPreviousMonth(int month) {
		if (month==1)
			return 12;
		return month-1;
	}
	
	
	public static int getActualDay() {
		return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
	}
	
	public static int getActualMonth() {
		return Calendar.getInstance().get(Calendar.MONTH)+1;
	}
	
	public static int getActualYear() {
		return Calendar.getInstance().get(Calendar.YEAR);
	}
	
	public static String getActualDate() {
		int actualDay = getActualDay();
		if (actualDay < 10)
			return "0"+actualDay+"/"+getActualMonth()+"/"+getActualYear();
		else
			return actualDay+"/"+getActualMonth()+"/"+getActualYear();
	}
	
	public static int getActualHour() {
		return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
	}

	private static final long serialVersionUID = 1L;

	public static Calendar getFechaSeleccionada(String fecha) {
		int day= Integer.valueOf(fecha.split("/")[0]);
		int month= Integer.valueOf(fecha.split("/")[1])-1;
		int year= Integer.valueOf(fecha.split("/")[2]);
		Calendar c = Calendar.getInstance();
		c.set(year, month, day);
		return c;
	}

	@Override
	public String toString() {
		return "DateComparable [year=" + year + ", month=" + month + ", day=" + day + "]";
	}
	
	
	
}
