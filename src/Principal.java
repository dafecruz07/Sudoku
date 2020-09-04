   import java.util.Vector;

/**
 * @author Miguel Ángel Askar Rodríguez - 201355842
 * @author Danny Fernando Cruz Arango - 201449949
 * Clase encargada de gestionar la funcionalidad del sudoku, la cual hace uso de las diferentes clases del proyecto
 */
public class Principal {

	private Vector<Estado> camino;
	private Estado estadoActual;
	private boolean estaResuelto;
	private ThreadGroup t1;
	
	/**
	 * Método constructor
	 * @param camino
	 * @param estadoActual
	 */
	public Principal(Vector<Estado> camino, Estado estadoActual) 
	{
		this.camino = camino;
		this.estadoActual = estadoActual;
		this.estaResuelto= false;
		
	}
	
	/**
	 * Método constructor
	 */
	public Principal() 
	{
		camino= new Vector<Estado>(0,1);
		this.estaResuelto= false;
	}

	/**
	 * Metodo usado para leer la plantilla base en txt
	 * @param nombreArchivo Nombre del archivo que contiene el sudoku
	 * @return Estado El estado inicial de la partida
	 */
	public Estado leerPlantilla(String nombreArchivo)
	{
		Archivos lector= new Archivos();
		lector.leer(nombreArchivo);
		String sudoku= lector.getLeido();
		return new Estado(sudoku);
	}
	
	/**
	 * Metodo encargado de verificar los multiples estados del sudoku
	 * e iterar hasta obtener un resultado positivo, si se da un estado de error se cambia
	 * a una diferente posibilidad en la casilla donde se presento el error
	 * @param actual El estado actual de la partida
	 */
	public void resolverSudoku(Estado actual)
	{
		boolean continuar= true; //Variable que determina si la partida ha finalizado o no
		while(continuar) // Mientras que la solucion no se haya encontrado continua la partida
		{
			//Verifica si hay o no un error
			if(estadoActual.contarCasillasConPosibilidades()!=estadoActual.contarCasillasVacias()) 
			{
				if(estadoActual.getPosibilidadesCasilla().size()>0)//Pregunta si queda al menos una posibilidad para la casilla
				{
					estadoActual.asignarHijo(); //Asigna los datos para la siguiente posibilidad
				}
				else
				{
					if(estadoActual.getCasillas().size()>1) //Verifica si quedaban hermanos
					{
						estadoActual.cambiarCasilla(); //Se cambia al siguiente hermano
						if(estadoActual.getPosibilidadesCasilla().size()>0) //Se verifica que queden posibilidades en la casilla
						{
							//Se guarda el estado utilizado
							camino.add(new Estado(estadoActual.getTablero(), estadoActual.getCasillas(), estadoActual.getPosibilidadesCasilla())); 
							estadoActual.asignarHijo(); //Se asignan los datos a la casilla a cambiar
						}											
					}
					else //En caso de que deba buscar más posibilidades en sus padres.
					{
						boolean estaArreglado= false;
						while(!estaArreglado)
						{
							Estado estado= camino.remove(camino.size()-1); //Se remueve el estado más reciente y se almacena
							//Se guarda este estado para su comprobacion
							estadoActual= new Estado(estado.getTablero(), estado.getCasillas(), estado.getPosibilidadesCasilla()); 
							if(estadoActual.getPosibilidadesCasilla().size()>0)//Pregunta si queda al menos una posibilidad para la casilla
							{
								estadoActual.asignarHijo(); //Cambia a la siguiente posibilidad
								estaArreglado= true; //Indica que se encontró una solución
							}
							else //Si no quedan posibilidades
							{
								if(estadoActual.getCasillas().size()>1) //Se pregunta si quedan hermanos
								{
									estadoActual.cambiarCasilla(); //Se pasa al siguiente hermano
									if(estadoActual.getPosibilidadesCasilla().size()>0) //Pregunta si quedan posibilidades en este hermano
									{
										//Se agrega el estado sin modificar al camino (reemplazando al eliminado)
										camino.add(new Estado(estadoActual.getTablero(), estadoActual.getCasillas(), estadoActual.getPosibilidadesCasilla())); //Se guarda el estado utilizado
										estadoActual.asignarHijo(); //Se cambia a la siguiente posibilidad
									}
									estaArreglado= true; //Indica que se arregló el estado
								}
								
							}
							
						}	
					}						
				}				
			}
			else
			{
				//Se agrega el estado a camino
				camino.add(new Estado(estadoActual.getTablero(), estadoActual.getCasillas(), estadoActual.getPosibilidadesCasilla())); //Se guarda el estado utilizado
				//Se crea el siguiente estado
				estadoActual= new Estado(estadoActual.getTablero(), new Vector<int[]>(0,1), new Vector<Integer>(0,1));
				estadoActual.terminarEstadoInicial(); //Se asignan las posibilidades y casillas nuevas
				estadoActual.asignarHijo(); //se cambia a la siguiente posibilidad
				
				//A continuación se imprime el tablero para conocer el recorrido, en caso de no quererlo,...
				//... comentar las siguientes 3 líneas
				System.out.println("");
				estadoActual.imprimirTablero();
				System.out.println("");
			}
			if(estadoActual.contarCasillasVacias()==0)//verifica si ya resolvió el sudoku
			{
				continuar= false; //Hace que salga del ciclo while principal
			}
		}
		
		estadoActual.imprimirTablero();//Se imprime el tablero resulto
		System.out.println("¡¡¡Resuelto!!!"); //¡¡¡¡Eureka!!!!	
	}
	
	/**
	 * Metodo que inicializa los datos del sudoku y hace llamado al metodo encargado de la solucion del sudoku
	 * @param ruta Ubicacion/nombre del archivo
	 */
	public void iniciarSudoku(String ruta)
	{
		estadoActual= leerPlantilla(ruta); //Se lee el archivo de texto y con el se crea un estado inicial
		estadoActual.calcularPosibilidades();//Se calculan las primeras posibilidades de la partida
		estadoActual.terminarEstadoInicial();//Se crean los hijos de las casillas con menor cantidad de posibilidades
		resolverSudoku(estadoActual);//Se da inicio al metodo resolver sudoku
	}
	
	/**
	 * Metodo Main
	 * @param args
	 */
	public static void main(String[] args) {
		Principal p= new Principal();
		p.iniciarSudoku("sdk.txt"); //Se carga el archivo de texto (si se quiere cambiar el sudoku, se debe cambiar el contenido del archivo, No cambiar el archivo.
	}	

	/**
	 * Método Get de la variable camino
	 * @return El valor de camino
	 */
	public Vector<Estado> getCamino() {
		return camino;
	}

	/**
	 * Método set de la variable camino
	 * @param camino El valor de camino a modificar
	 */
	public void setCamino(Vector<Estado> camino) {
		this.camino = camino;
	}

	/**
	 * Método Get de la variable estadoActual
	 * @return El valor de estadoActual
	 */
	public Estado getEstadoActual() {
		return estadoActual;
	}

	/**
	 * Método set de la variable estadoActual
	 * @param estadoActual El valor de estadoActual a modificar
	 */
	public void setEstadoActual(Estado estadoActual) 
	{
		this.estadoActual = estadoActual;
	}
	
	/**
	 * Método Get de la variable estaResuelto
	 * @return El valor de estaResuelto
	 */
	public boolean isEstaResuelto() {
		return estaResuelto;
	}

	/**
	 * Método set de la variable estaResuelto
	 * @param estaResuelto El valor de estaResuelto a modificar
	 */
	public void setEstaResuelto(boolean estaResuelto) {
		this.estaResuelto = estaResuelto;
	}
}