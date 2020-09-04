import java.util.StringTokenizer;
import java.util.Vector;

/**
 * @author Miguel Ángel Askar Rodríguez - 201355842
 * @author Danny Fernando Cruz Arango - 201449949
 * Clase encargada de calcular posibilidades, almacenar tablero, guardar los elementos de un estado
 * y alterar el estado.
 */

public class Estado 
{
	private Valor[][] tablero; //Arreglo bidimiensional que guarda el tablero del estado
	private Vector<int[]> casillas; //Este vector guarda los estados de un nivel del árbol
	private Vector<Integer> posibilidadesCasilla; //Este vector guarda las posibilidades de la casilla que se decidió alterar estado
	
	/**
	 * Método constructor con parámetros
	 * @param tablero Tablero a asignar al estado
	 * @param casillas Vector de arreglos de enteros que guarda las casillas disponibles (hijos)
	 * @param posibilidadesCasilla Vector de enteros que guarda las posibilidades de una casilla.
	 */
	public Estado(Valor[][] tablero, Vector<int[]> casillas, Vector<Integer> posibilidadesCasilla) 
	{
		this.tablero = tablero;
		this.casillas= casillas;
		this.posibilidadesCasilla= posibilidadesCasilla;		
	}
	
	/**
	 * Método constructor sin parámetros
	 * @param sudoku
	 */
	public Estado(String sudoku) //Lee el archivo y guarda el tablero
	{
		Valor[][] tablero= new Valor[9][9];
		StringTokenizer tokenLinea= new StringTokenizer(sudoku, "\n"); //Separa el string por saltos de línea		
		for (int i = 0; i < 9; i++) 
		{
			String linea= tokenLinea.nextToken();
			StringTokenizer tokenNumero= new StringTokenizer(linea, " "); //Separa el string por espacios
			for (int j = 0; j < 9; j++)
			{
				int siguiente= Integer.parseInt(tokenNumero.nextToken()); //Pide el siguiente número para guardarlo
				if(siguiente!=0)
				{
					tablero[i][j]= (new Valor(true, siguiente)); //Asigna que el valor y que es fijo
				}else
				{
					tablero[i][j]= (new Valor(false, siguiente)); //Asigna que el valor y que no es fijo 
				}
			}			
		}
		this.tablero = tablero;		
		casillas= new Vector<int[]>(0,1); //Inicializa el vector casillas
		posibilidadesCasilla= new Vector<Integer>(0,1); //Inicializa el vector posibilidadesCasilla
	}
	
	/**
	 * Metodo que cuenta la cantidad de casillas con posibilidades
	 * @return int Casillas que tienen una o más posibilides en el tablero
	 */
	public int contarCasillasConPosibilidades()
	{
		calcularPosibilidades(); //Actualiza las posibilidades
		int cantidad= 0;
		for (int i = 0; i < 9; i++)
		{
			for (int j = 0; j < 9; j++)
			{
				if (tablero[i][j].getPosibilidades().size()!= 0) 
				{
					cantidad++; //Aumenta la cantidad si la casilla tiene una o más posibilidades.
				}
			}
		}
		return cantidad;
	}
	
	/**
	 * Metodo que cuenta la cantidad de casillas con posibilidades 
	 * @return int Casillas vacías en el tablero
	 */
	
	public int contarCasillasVacias()
	{
		int cantidad= 0;
		for (int i = 0; i < 9; i++)
		{
			for (int j = 0; j < 9; j++)
			{
				if (tablero[i][j].getDato()== 0) 
				{
					cantidad++; //si la casilla está vacía, aumenta la cantidad
				}
			}
		}
		return cantidad;
	}
	
	/**
	 * Metodo que determina la cantidad de posibilidades de cada una de las casillas
	 */
	public void calcularPosibilidades()
	{
		for (int i = 0; i < 9; i++)
		{
			for (int j = 0; j < 9; j++) //Doble for usado para verificar todas las casillas del tablero
			{
				if(!tablero[i][j].isFijo())
				{
					Vector<Integer> posibilidades = new Vector<>(0, 1);
					for (int k = 1; k <= 9; k++)// For de 1 - 9 que verifica los digitos que puede usar el sudoku
					{
						boolean noUsado = true; //Variable auxiliar que determina si un digo ya se encuentra en uso ya sea vertical horizontal o en la submatriz
						for (int hor = 0; hor < 9; hor++) //For que verifica si el digito actual esta uso en la linea horizontal
						{
							if(tablero[i][hor].getDato()== k) //Si el valor en la casilla corresponde al dato que se esta verificando se cambia el estado de la condicion de parada
							{
								noUsado= !noUsado;
								break;// Se detiene el ciclo
							}
						}
						if(noUsado) //Solo continua si el valor no ha sido encontrado en alguno de los criterios anteriores
						{
							for (int ver = 0; ver < 9; ver++)//For que verifica si el digito actual esta uso en la linea vertical
							{
								if(tablero[ver][j].getDato()== k) //Si el valor en la casilla corresponde al dato que se esta verificando se cambia el estado de la condicion de parada
								{
									noUsado= !noUsado;
									break; // Se detiene el ciclo
								}
							}
							if(noUsado) //Solo continua si el valor no ha sido encontrado en alguno de los criterios anteriores
							{
								if (i<3 && j<3) // Si esta condicion se cumple se determina que el valor evaluado se encuentra en el subcuadrado 1, 1 (Arriba izquierda)
								{
									for (int h = 0; h < 3; h++)
									{
										for (int p = 0; p < 3; p++)//Doble For que verifica si el digito actual esta uso en el subcuadrado 1, 1 (Arriba izquierda)
										{
											if(tablero[h][p].getDato()== k) //Si el valor en la casilla corresponde al dato que se esta verificando se cambia el estado de la condicion de parada
											{
												noUsado= !noUsado;
												break;
											}
										}
									}
								}
								if(noUsado) //Solo continua si el valor no ha sido encontrado en alguno de los criterios anteriores
								{
									if (i<3 && j>2 && j<6) // Si esta condicion se cumple se determina que el valor evaluado se encuentra en el subcuadrado 1, 2 (Arriba centro)
									{
										for (int h = 0; h < 3; h++)
										{
											for (int p = 3; p < 6; p++)//Doble For que verifica si el digito actual esta uso en el subcuadrado 1, 2 (Arriba centro)
											{
												if(tablero[h][p].getDato()== k) //Si el valor en la casilla corresponde al dato que se esta verificando se cambia el estado de la condicion de parada
												{
													noUsado= !noUsado;
													break;
												}
											}
										}
									}
									if(noUsado) //Solo continua si el valor no ha sido encontrado en alguno de los criterios anteriores
									{
										if (i<3 && j>5) // Si esta condicion se cumple se determina que el valor evaluado se encuentra en el subcuadrado 1, 3 (Arriba derecha)
										{
											for (int h = 0; h < 3; h++)
											{
												for (int p = 6; p < 9; p++)//Doble For que verifica si el digito actual esta uso en el subcuadrado 1, 3 (Arriba derecha)
												{
													if(tablero[h][p].getDato()== k) //Si el valor en la casilla corresponde al dato que se esta verificando se cambia el estado de la condicion de parada
													{
														noUsado= !noUsado;
														break;
													}
												}
											}
										}
										if(noUsado) //Solo continua si el valor no ha sido encontrado en alguno de los criterios anteriores
										{
											if (i>2 && i<6 && j<3) // Si esta condicion se cumple se determina que el valor evaluado se encuentra en el subcuadrado 2, 1 (centro izquierda)
											{
												for (int h = 3; h < 6; h++)
												{
													for (int p = 0; p < 3; p++)//Doble For que verifica si el digito actual esta uso en el subcuadrado 2, 1 (centro izquierda)
													{
														if(tablero[h][p].getDato()== k) //Si el valor en la casilla corresponde al dato que se esta verificando se cambia el estado de la condicion de parada
														{
															noUsado= !noUsado;
															break;
														}
													}
												}
											}
											if(noUsado) //Solo continua si el valor no ha sido encontrado en alguno de los criterios anteriores
											{
												if (i>2 && i<6 && j>2 && j<6) // Si esta condicion se cumple se determina que el valor evaluado se encuentra en el subcuadrado 2, 2 (centro centro)
												{
													for (int h = 3; h < 6; h++)
													{
														for (int p = 3; p < 6; p++)//Doble For que verifica si el digito actual esta uso en el subcuadrado 2, 2 (centro centro)
														{
															if(tablero[h][p].getDato()== k) //Si el valor en la casilla corresponde al dato que se esta verificando se cambia el estado de la condicion de parada
															{
																noUsado= !noUsado;
																break;
															}
														}
													}
												}
												if(noUsado) //Solo continua si el valor no ha sido encontrado en alguno de los criterios anteriores
												{
													if (i>2 && i<6 && j>5) // Si esta condicion se cumple se determina que el valor evaluado se encuentra en el subcuadrado 2, 3 (centro derecha)
													{
														for (int h = 3; h < 6; h++)
														{
															for (int p = 6; p < 9; p++)//Doble For que verifica si el digito actual esta uso en el subcuadrado 2, 3 (centro derecha)
															{
																if(tablero[h][p].getDato()== k) //Si el valor en la casilla corresponde al dato que se esta verificando se cambia el estado de la condicion de parada
																{
																	noUsado= !noUsado;
																	break;
																}
															}
														}
													}
													if(noUsado) //Solo continua si el valor no ha sido encontrado en alguno de los criterios anteriores
													{
														if (i>5 && j<3) // Si esta condicion se cumple se determina que el valor evaluado se encuentra en el subcuadrado 3, 1 (abajo izquierda)
														{
															for (int h = 6; h < 9; h++)
															{
																for (int p = 0; p < 3; p++)//Doble For que verifica si el digito actual esta uso en el subcuadrado 3, 1 (abajo izquierda)
																{
																	if(tablero[h][p].getDato()== k) //Si el valor en la casilla corresponde al dato que se esta verificando se cambia el estado de la condicion de parada
																	{
																		noUsado= !noUsado;
																		break;
																	}
																}
															}
														}
														if(noUsado) //Solo continua si el valor no ha sido encontrado en alguno de los criterios anteriores
														{
															if (i>5 && j>2 && j<6) // Si esta condicion se cumple se determina que el valor evaluado se encuentra en el subcuadrado 3, 2 (abajo centro)
															{
																for (int h = 6; h < 9; h++)
																{
																	for (int p = 3; p < 6; p++)//Doble For que verifica si el digito actual esta uso en el subcuadrado 3, 2 (abajo centro)
																	{
																		if(tablero[h][p].getDato()== k) //Si el valor en la casilla corresponde al dato que se esta verificando se cambia el estado de la condicion de parada
																		{
																			noUsado= !noUsado;
																			break;
																		}
																	}
																}
															}
															if(noUsado) //Solo continua si el valor no ha sido encontrado en alguno de los criterios anteriores
															{
																if (i>5 && j>5) // Si esta condicion se cumple se determina que el valor evaluado se encuentra en el subcuadrado 3, 3 (abajo derecha)
																{
																	for (int h = 6; h < 9; h++)
																	{
																		for (int p = 6; p < 9; p++)//Doble For que verifica si el digito actual esta uso en el subcuadrado 3, 3 (abajo derecha)
																		{
																			if(tablero[h][p].getDato()== k) //Si el valor en la casilla corresponde al dato que se esta verificando se cambia el estado de la condicion de parada
																			{
																				noUsado= !noUsado;
																				break;
																			}
																		}
																	}
																}
																if(noUsado) // Si esta condicion se cumple se puede determina que el valor analizado no se encuentra  en la vertical, horizontal o el subcuadrado
																{			// por ello se considera una posibilidad
																	posibilidades.add(k); // Se agrega el valor analaizado al arrego de posibilidades	
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
					if (tablero[i][j].getDato()!=0)// si el dato que se encuentra en la casilla es diferente de cero, es porque cuenta con un valor asignado por la aplicación
					{
						tablero[i][j].setPosibilidades(new Vector<>(0, 1)); //Al contar ya con un valor se asigna un vector de posibilidades vacio
					}
					else
					{
						tablero[i][j].setPosibilidades(posibilidades); //Al ser una casilla vacia se procede a asignar su respectivo vector de posibilidades
					}
				}
			}
		}
	}


	/**
	* Metodo que imprime todos valores que se encuentran en el tablero
	*/
	public void imprimirTablero()
	{
		for (int i = 0; i < 9; i++) 
		{
			for (int j = 0; j < 9; j++) 
			{
				System.out.print(getTablero()[i][j].getDato()+" ");
			}
			System.out.println("");
		}
	}
	
	/**
	* Metodo que imprime la cantidad de posibilidades de cada una de las casillas del tablero
	*/
	public void imprimirPosibilidades()
	{
		for (int i = 0; i < 9; i++) 
		{
			for (int j = 0; j < 9; j++) 
			{
				System.out.print(getTablero()[i][j].getPosibilidades().size()+" ");
			}
			System.out.println("");
		}
	}
	
	/**
	* Metodo que asigna el valor a usar en una casilla haciendo uso del Vector casillas y su primera posibildiad
	*/
	public void asignarHijo() 
	{
		tablero[casillas.firstElement()[0]][casillas.firstElement()[1]].setDato(posibilidadesCasilla.remove(0));// Se asigna el dato a la casilla haciendo uso de la primera posibilidad
		tablero[casillas.firstElement()[0]][casillas.firstElement()[1]].setPosibilidades(new Vector<Integer>(0,1));// Se vacia el vector posibilidades
		calcularPosibilidades(); //Se calculan nuevamente todas las posibilidades ya que el nuevo dato cambias las posibilidades de algunas casillas
	}
	
	/**
	* Metodo que cambia el valor asignado a una casilla en caso de que el valor anterior no haya funcionado
	*/
	public void cambiarCasilla()
	{
		tablero[casillas.firstElement()[0]][casillas.firstElement()[1]].setDato(0);//Se asigna un valor de cero(vacio) a la casilla
		calcularPosibilidades();// Se calculan nuevamente las posibilidades tablero
		casillas.remove(0);// Se borra la posibilidad usada anteriormente
		tablero[casillas.firstElement()[0]][casillas.firstElement()[1]].setDato(0);//Se asigna un valor de cero(vacio) a la casilla
		calcularPosibilidades();// Se calculan nuevamente las posibilidades del tablero
		for(int i=0; i<tablero[casillas.firstElement()[0]][casillas.firstElement()[1]].getPosibilidades().size(); i++)//For que llena el arreglo que contiene las posibilidades de una casilla
		{
			posibilidadesCasilla.add(tablero[casillas.firstElement()[0]][casillas.firstElement()[1]].getPosibilidades().get(i));
		}
		calcularPosibilidades();// Se calculan nuevamente las posibilidades del tablero
	}
	
	/**
	* Metodo que retorna la menor cantidad de posibilidades que tiene al menos una casilla del tablero
	* @param tablero El tablero que sera analizado
	* @return La menor cantidad de posibilidades que tiene al menos una de las casillas del tablero
	*/
	public int menorPosibilidad(Valor[][] tablero)
	{
		int posibilidad= 9;// numero mayor de posibilidades
		for(int i= 0; i<9; i++)
		{
			for(int j= 0; j<9; j++) // Doble for que recorre todas las casillas del tablero
			{
				if(tablero[i][j].getPosibilidades().size()>0 && tablero[i][j].getPosibilidades().size()<posibilidad) // Si la casilla evaluada tiene menor cantidad de posibilidades
				{																									 // dicho valor es asignado a la cantidad de posibilidades
					posibilidad= tablero[i][j].getPosibilidades().size();
				}
			}
		}
		return posibilidad; // Se retorna la menor cantidad de posibilidades con que cuenta al menos una de las casilla del tablero
	}
	
	/**
	* Metodo que crea los hijos de las casillas con menor numero de posibilidades
	*/
	public void terminarEstadoInicial()
	{		
		int menorPosibilidad= menorPosibilidad(tablero); //Se almacena el valor de la menos posibilidad del tablero
		for(int i= 0; i<9; i++)
		{
			for (int j = 0; j < 9; j++) // Doble for que verifica todas las casillas del tablero
			{
				if(tablero[i][j].getPosibilidades().size()==menorPosibilidad) //Si esta casilla es la que tiene menor cantidad de posibilidades
				{
					int[] nuevaCasilla= {i,j};
					casillas.add(nuevaCasilla); // Se almacenan las casillas que cuentan con la menor posibilidad
				}
			}
		}		
		tablero[casillas.firstElement()[0]][casillas.firstElement()[1]].setDato(0); // Limpio el valor de la casilla (asignando un valor de cero)
		calcularPosibilidades(); //Calculo nuevamente todas las posibilidades
		for(int i=0; i<tablero[casillas.firstElement()[0]][casillas.firstElement()[1]].getPosibilidades().size(); i++) // for que itera por la contidad de posibilidades de la casilla
		{
			posibilidadesCasilla.add(tablero[casillas.firstElement()[0]][casillas.firstElement()[1]].getPosibilidades().get(i));//Se crean las posibilidades de la casilla (hijos)
		}
		//En este momento, el estado tiene los hijos y expande el hijo de la derecha
	}
	
	/**
	 * Método Get de la variable tablero
	 * @return El valor de tablero
	 */
	public Valor[][] getTablero() {
		return tablero;
	}

	/**
	 * Método set de la variable tablero
	 * @param tablero El valor de tablero a modificar
	 */
	public void setTablero(Valor[][] tablero) {
		this.tablero = tablero;
	}
	
	/**
	 * Método Get de la variable casillas
	 * @return El valor de casillas
	 */
	public Vector<int[]> getCasillas() {
		return casillas;
	}

	/**
	 * Método set de la variable casillas
	 * @param casillas El valor de casillas a modificar
	 */
	public void setCasillas(Vector<int[]> casillas) {
		this.casillas = casillas;
	}

	/**
	 * Método Get de la variable posibilidadesCasilla
	 * @return El valor de posibilidadesCasilla
	 */
	public Vector<Integer> getPosibilidadesCasilla() {
		return posibilidadesCasilla;
	}

	/**
	 * Método set de la variable posibilidadesCasilla
	 * @param posibilidadesCasilla El valor de posibilidadesCasilla a modificar
	 */
	public void setPosibilidadesCasilla(Vector<Integer> posibilidadesCasilla) {
		this.posibilidadesCasilla = posibilidadesCasilla;
	}
}