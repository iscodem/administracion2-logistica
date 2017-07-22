package pe.gob.sunat.recurso2.administracion.siga.registro.util;

public class ComisionadoUtil {

	private static char comilla_simple = "'".charAt(0);
	private static char espacio = ' ';

	public static final String limpiarCaracteresTrim(String input) {

		if (input == null || input.isEmpty()) return input;

		return input.replace('"', espacio).replace('/', espacio).replace('°', espacio).replace(comilla_simple, espacio).trim();
	}

	public static final String limpiarCaracteres(String input) {

		if (input == null || input.isEmpty()) return input;

		return input.replace('"', espacio).replace('/', espacio).replace('°', espacio).replace(comilla_simple, espacio);
	}
}
