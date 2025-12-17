package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Clase que contiene valores constantes usados internamente por el juego.
 * 
 * @author Guillermo Lazzurri
 */
public class Constants
{
	/**
	 * Codigo identificatorio del player
	 */
	public static final int PLAYER = 1;
	/**
	 * Codigo identificatorio de una puerta giratoria
	 */

	public static final int IT_GIRATORY = 107;
	/**
	 * Codigo identificatorio de una espada
	 */
	public static final int IT_DAGGER = 137;
	/**
	 * Codigo identificatorio de un pico
	 */
	public static final int IT_PICKER = 138;
	/**
	 * Codigo identificatorio de una joya
	 */
	public static final int IT_JEWEL = 74;
	/**
	 * Codigo identificatorio de un palanca de puerta de entrada y salida
	 */
	public static final int IT_DOOR_LEVER = 127;
	/**
	 * Codigo identificatorio de un muro trampa
	 */
	public static final int IT_WALL_TRAP = 254;
	/**
	 * Codigo identificatorio de una momia
	 */
	public static final int IT_MUMMY = 77;
	/**
	 * Codigo identificatorio de un inicio o fin de escalera
	 */
	public static final int IT_STAIR = 69;
	/**
	 * Codigo identificatorio de un activador de muro trampa
	 */
	public static final int IT_TRAP_ACTIVATOR = 100;
	/**
	 * Codigo identificatorio de un pasage de puerta de entrada y salida
	 */
	public static final int IT_DOOR_PASSAGE = 200;

	/**
	 * Codigo identificatorio de direccion arriba
	 */
	public static final int UP = 1000;
	/**
	 * Codigo identificatorio de direccion abajo
	 */

	public static final int DOWN = 1001;
	/**
	 * Codigo identificatorio de direccion izquierda
	 */

	public static final int LEFT = 1002;
	/**
	 * Codigo identificatorio de direccion derecha
	 */

	public static final int RIGHT = 1003;

	/**
	 * Codigo identificatorio de inicio de escalera hacia arriba a la derecha
	 */

	public static final int STAIR_UR = 0;

	/**
	 * Codigo identificatorio de inicio de escalera hacia abajo a la izquierda
	 */

	public static final int STAIR_DL = 1;
	/**
	 * Codigo identificatorio de inicio de escalera hacia arriba a la izquierda
	 */

	public static final int STAIR_UL = 2;
	/**
	 * Codigo identificatorio de inicio de escalera hacia abajo a la derecha
	 */

	public static final int STAIR_DR = 3;

	/**
	 * Codigo identificatorio de joya tipo 1
	 */

	public static final int JEWEL_1 = 130;
	/**
	 * Codigo identificatorio de joya tipo 2
	 */

	public static final int JEWEL_2 = 131;
	/**
	 * Codigo identificatorio de joya tipo 3
	 */

	public static final int JEWEL_3 = 132;
	/**
	 * Codigo identificatorio de joya tipo 4
	 */

	public static final int JEWEL_4 = 133;
	/**
	 * Codigo identificatorio de joya tipo 5
	 */

	public static final int JEWEL_5 = 134;
	/**
	 * Codigo identificatorio de joya tipo 6
	 */

	public static final int JEWEL_6 = 135;
	/**
	 * Codigo identificatorio de joya tipo 7
	 */
	public static final int JEWEL_7 = 136;

	/**
	 * Codigo indicando que el drawable es una puerta de salida
	 */

	public static final int DRAWABLE_EXIT_DOOR = 2000;
	/**
	 * Codigo indicando que el drawable es un item del nivel
	 */
	public static final int DRAWABLE_LEVEL_ITEM = 3000;
	/**
	 * Codigo indicando que el drawable es un muro trampa
	 */
	public static final int DRAWABLE_TRAP = 3001;
	/**
	 * Codigo indicando que el drawable es una puerta giratoria
	 */
	public static final int DRAWABLE_GYRATORY = 3002;
	/**
	 * Codigo indicando que el drawable es una celda siendo picada
	 */
	public static final int DRAWABLE_PICKING_CELL = 4000;
	/**
	 * Codigo indicando que el drawable es una espada volando
	 */
	public static final int DRAWABLE_FLYING_DAGGER = 5000;
	/**
	 * Codigo indicando que el drawable es un analisis de plataforma (solo usado
	 * durante debug para visualizar resultados)
	 */
	public static final int DRAWABLE_PLATFORM_ANALYSIS_RESULT = 10000;

	/**
	 * convierte el codigo en String, solo usado en debug para los toString
	 */
	public static final HashMap<Integer, String> identificacion = new HashMap<Integer, String>();

	/**
	 * convierte el String en codigo, usado cuando se lee el nivel en formato tmx
	 */

	public static final HashMap<String, Integer> stringToInteger = new HashMap<String, Integer>();
	/**
	 * Contine los nombres de los archivos de cada nivel del juego (version clasica)
	 */
	public static final HashMap<Integer, String> classicLevelFileName = new HashMap<Integer, String>();

	/**
	 * Contine los nombres de los archivos de cada nivel del juego (version
	 * extendida)
	 */
	public static final HashMap<Integer, String> extendedLevelFileName = new HashMap<Integer, String>();

	/**
	 * Puntaje correspondiente a matar una momia con la espada
	 */
	public static final int MUMMY_KILLED_BY_SWORD_SCORE = 100;
	/**
	 * Puntaje correspondiente a recolectar una gema
	 */

	public static final int PICKUP_JEWEL_SCORE = 500;
	/**
	 * Puntaje correspondiente a terminar el nivel actual
	 */

	public static final int FINISH_CURRENT_LEVEL_SCORE = 2000;
	/**
	 * tiles correspondientes a escaleras con pendiente positiva
	 */
	public static ArrayList<Integer> tilesPositiveStairs;
	/**
	 * tiles correspondientes a escaleras con pendiente negativa
	 */

	public static ArrayList<Integer> tilesNegativeStairs;
	/**
	 * tiles correspondientes a las celdas previas a la escalera (no son picables)
	 */

	public static ArrayList<Integer> tilesPreviusToStairs;

	/**
	 * Indica el numero actual de version de juego y fecha de release
	 */
	public static final String VERSION = "v 1.1.0 r 2025-12-17";
	private static final int LAST_EXTENDED_LEVEL = 60;
	private static final int LAST_CLASSIC_LEVEL = 15;

	static
	{

		identificacion.put(IT_GIRATORY, "Giratory");
		identificacion.put(IT_DAGGER, "Dagger");
		identificacion.put(IT_PICKER, "Picker");
		identificacion.put(IT_JEWEL, "Jewel");
		identificacion.put(IT_DOOR_LEVER, "Door");
		identificacion.put(IT_WALL_TRAP, "Wall");
		identificacion.put(IT_MUMMY, "Mummy");
		identificacion.put(IT_STAIR, "Stairs");
		identificacion.put(IT_TRAP_ACTIVATOR, "Activator");

		stringToInteger.put("Dagger", IT_DAGGER);
		stringToInteger.put("Picker", IT_PICKER);
		stringToInteger.put("Jewel", IT_JEWEL);
		stringToInteger.put("Door", IT_DOOR_LEVER);
		stringToInteger.put("Wall", IT_WALL_TRAP);
		stringToInteger.put("Mummy", IT_MUMMY);
		stringToInteger.put("Stairs", IT_STAIR);
		stringToInteger.put("Giratory", IT_GIRATORY);
		stringToInteger.put("Activator", IT_TRAP_ACTIVATOR);

		for (int i = 1; i <= LAST_CLASSIC_LEVEL; i++)
		{
			if (i < 10)
			{
				classicLevelFileName.put(i, "maps/classic_level_0" + i + ".tmx");
			} else
			{
				classicLevelFileName.put(i, "maps/classic_level_" + i + ".tmx");
			}
		}
		
		
		for (int i = 1; i <= LAST_EXTENDED_LEVEL; i++)
		{
			if (i < 10)
			{
				extendedLevelFileName.put(i, "maps/extended_level_0" + i + ".tmx");
			} else
			{
				extendedLevelFileName.put(i, "maps/extended_level_" + i + ".tmx");
			}
		}

		extendedLevelFileName.put(LAST_EXTENDED_LEVEL+1, "maps/goal.tmx");
		
		
		
		classicLevelFileName.put(16, "maps/goal.tmx");
		Integer[] valuesPreviusToStair =
		{ 19, 24, 27, 32, 37, 42, 45, 50 };
		Integer[] valuesPositiveStair =
		{ 20, 38, 28, 46 };
		Integer[] valuesNegativeStair =
		{ 23, 41, 31, 49 };
		tilesPositiveStairs = new ArrayList<Integer>(Arrays.asList(valuesPositiveStair));
		tilesNegativeStairs = new ArrayList<Integer>(Arrays.asList(valuesNegativeStair));
		tilesPreviusToStairs = new ArrayList<Integer>(Arrays.asList(valuesPreviusToStair));

	}

}
