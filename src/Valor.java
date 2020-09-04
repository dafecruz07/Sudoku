import java.util.Vector;

/**
 * @author Miguel �ngel Askar Rodr�guez - 201355842
 * @author Danny Fernando Cruz Arango - 201449949
 * Clase encargada de almacenar el valor asignado a una casilla y sus respectivas posibilidades
 */
public class Valor {
	private boolean fijo;
	private int dato;
	private Vector<Integer> posibilidades;

	/**
	 * M�todo constructor
	 * @param fijo Variable que determina si un valor es inalterable
	 * @param dato Valor que se sera asignado a la casilla
	 * @param posibilidades Arreglo de posibilidades de la casilla
	 */
	public Valor(boolean fijo, int dato, Vector<Integer> posibilidades) 
	{
		this.fijo= fijo;
		this.dato= dato;
		this.posibilidades= posibilidades;
	}
	
	/**
	 * M�todo constructor
	 * @param fijo Variable que determina si un valor es inalterable
	 * @param dato Valor que se sera asignado a la casilla
	 */
	public Valor(boolean fijo, int dato) 
	{
		this.fijo= fijo;
		this.dato= dato;
		posibilidades = new Vector<>(0, 1);
	}

	/**
	 * M�todo Get de la variable fijo
	 * @return El valor de fijo
	 */
	public boolean isFijo() {
		return fijo;
	}

	/**
	 * M�todo set de la variable fijo
	 * @param fijo El valor de fijo a modificar
	 */
	public void setFijo(boolean fijo) {
		this.fijo = fijo;
	}

	/**
	 * M�todo Get de la variable dato
	 * @return El valor de dato
	 */
	public int getDato() {
		return dato;
	}

	/**
	 * M�todo set de la variable dato
	 * @param dato El valor de dato a modificar
	 */
	public void setDato(int dato) {
		this.dato = dato;
	}

	/**
	 * M�todo Get de la variable posibilidades
	 * @return El valor de posibilidades
	 */
	public Vector<Integer> getPosibilidades() {
		return posibilidades;
	}

	/**
	 * M�todo set de la variable posibilidades
	 * @param posibilidades El valor de posibilidades a modificar
	 */
	public void setPosibilidades(Vector<Integer> posibilidades) {
		this.posibilidades = posibilidades;
	}
}