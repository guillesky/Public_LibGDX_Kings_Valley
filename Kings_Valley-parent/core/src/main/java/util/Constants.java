package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * @author Guillermo Lazzurri
 * Clase que contiene valores constantes usados internamente por el juego.
 */
public class Constants
{	public static final int PLAYER = 1;
	public static final int IT_GIRATORY = 107;
	public static final int IT_DAGGER = 137;
	public static final int IT_PICKER = 138;
	public static final int IT_JEWEL = 74;
	public static final int IT_DOOR_LEVER = 127;
	public static final int IT_WALL_TRAP = 254;
	public static final int IT_MUMMY = 77;
	public static final int IT_STAIR = 69;
	public static final int IT_TRAP_ACTIVATOR = 100;
	public static final int IT_DOOR_PASSAGE = 200;

	public static final int UP = 1000;
	public static final int DOWN = 1001;
	public static final int LEFT = 1002;
	public static final int RIGHT = 1003;

	public static final int STAIR_UR = 0;
	public static final int STAIR_DL = 1;
	public static final int STAIR_UL = 2;
	public static final int STAIR_DR = 3;

	public static final int JEWEL_1 = 130;
	public static final int JEWEL_2 = 131;
	public static final int JEWEL_3 = 132;
	public static final int JEWEL_4 = 133;
	public static final int JEWEL_5 = 134;
	public static final int JEWEL_6 = 135;
	public static final int JEWEL_7 = 136;

	public static final int DRAWABLE_EXIT_DOOR = 2000;
	public static final int DRAWABLE_LEVEL_ITEM = 3000;
	public static final int DRAWABLE_TRAP = 3001;
	public static final int DRAWABLE_GYRATORY = 3002;
	public static final int DRAWABLE_PICKING_CELL = 4000;
	public static final int DRAWABLE_FLYING_DAGGER = 5000;

	public static final HashMap<Integer, String> identificacion = new HashMap<Integer, String>();
	public static final HashMap<String, Integer> stringToInteger = new HashMap<String, Integer>();
	public static final HashMap<Integer, String> levelFileName = new HashMap<Integer, String>();
	public static final int MUMMY_KILLED_BY_SWORD_SCORE = 100;
	public static final int PICKUP_JEWEL_SCORE = 500;
	public static final int FINISH_CURRENT_LEVEL_SCORE = 2000;
	public static ArrayList<Integer> tilesPositiveStairs;
	public static ArrayList<Integer> tilesNegativeStairs;
	public static ArrayList<Integer> tilesPreviusToStairs;
	public static final String VERSION="v 1.0.0 r 2025-07-12";
	

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

		for (int i = 1; i <= 15; i++)
		{
			if (i < 10)
			{
				levelFileName.put(i, "maps/level_0" + i + ".tmx");
			} else
				levelFileName.put(i, "maps/level_" + i + ".tmx");

		}
		levelFileName.put(16, "maps/goal.tmx");
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
