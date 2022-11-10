package edu.prog2.models;

import org.json.JSONObject;

public class SillaEjecutiva extends Silla {

	private Menu menu;
	private Licor licor;

	public SillaEjecutiva(int fila, char columna,
			Menu menu, Licor licor, Avion avion) {
		super(fila, columna, avion);
		ubicacion = (columna == 'B' || columna == 'C') ? Ubicacion.PASILLO : Ubicacion.VENTANA;
		this.menu = menu;
		this.licor = licor;
	}

	public SillaEjecutiva(JSONObject json) {
		super(json);
		this.menu = json.getEnum(Menu.class, "menu");
		this.licor = json.getEnum(Licor.class, "licor");
	}

	public SillaEjecutiva(SillaEjecutiva silla) {
	}

	/**
	 * Este metodo permite obtener el menu que el cliente pidió
	 * 
	 * @return Devuelve el menu
	 */
	public Menu getMenu() {
		return this.menu;
	}

	/**
	 * Permita cambiar el menu que se pidió
	 * 
	 * @param menu Menu por el cual se va a cambiar
	 */
	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	/**
	 * Este metodo obtiene el licor o la bebida que el pasajero pide
	 * 
	 * @return Devuelve la bebida escogida
	 */
	public Licor getLicor() {
		return this.licor;
	}

	/**
	 * Este metodo permite cambiar el tipo de bebida del pasajero
	 * 
	 * @param licor Bebida por la que se va a cambiar
	 */
	public void setLicor(Licor licor) {
		this.licor = licor;
	}

	@Override
	public String toString() {
		String menu = this.menu.toString().replaceAll("_", " ");
		String licor = this.licor.toString().replaceAll("_", " ");
		return String.format("%-16s%-19s%s", super.toString(), menu, licor);
	}

	@Override
	public String toCSV() {
		return String.format("%s;%s;%s%n", super.toCSV().substring(0, super.toCSV().length() - 2), menu, licor);
	}
}