package util;

import java.util.HashMap;

public class Constantes
{
    public static final int PLAYER = 1;
    public static final int It_none = 0;
    public static final int It_brick = 76;
    public static final int It_giratory = 71;
    public static final int It_rgiratory = 62;
    public static final int It_lgiratory = 60;
    public static final int It_dagger = 176;
    public static final int It_picker = 177;
    public static final int It_jewel = 74;
    public static final int It_door = 84;
    public static final int It_wall = 85;
    public static final int It_mummy = 77;
    public static final int It_stairs = 69;
    public static final int It_push_in = 73;
    public static final int It_push_out = 79;
    
    public static final int UP = 1000;
    public static final int DOWN = 1001;
    public static final int LEFT = 1002;
    public static final int RIGHT = 1003;
    
    public static final int STAIR_UR = 0;
    public static final int STAIR_DL = 1;
    public static final int STAIR_UL = 2;
    public static final int STAIR_DR = 3;
    
    public static final int JEWEL_1=114;
    public static final int JEWEL_2=134;
    public static final int JEWEL_3=141;
    public static final int JEWEL_4=148;
    public static final int JEWEL_5=155;
    public static final int JEWEL_6=162;
    public static final int JEWEL_7=169;
    
    
    
    
    
    
    
    
    public static final HashMap<Integer, String> identificacion = new HashMap<Integer, String>();
    public static final HashMap<String, Integer> stringToInteger = new HashMap<String, Integer>();
    public static final HashMap<Integer, String> levelFileName = new HashMap<Integer, String>();
    static
    {
	identificacion.put(It_brick, "Brick");
	identificacion.put(It_giratory, "Giratory");
	identificacion.put(It_rgiratory, "It_rgiratory");
	identificacion.put(It_lgiratory, "It_lgiratory");
	identificacion.put(It_dagger, "Dagger");
	identificacion.put(It_picker, "Picker");
	identificacion.put(It_jewel, "Jewel");
	identificacion.put(It_door, "Door");
	identificacion.put(It_wall, "Wall");
	identificacion.put(It_mummy, "Mummy");
	identificacion.put(It_stairs, "Stairs");
	

	identificacion.put(It_push_in, "It_push_in");
	identificacion.put(It_push_out, "It_push_out");

	stringToInteger.put("Dagger", It_dagger);
	stringToInteger.put("Picker", It_picker);
	stringToInteger.put("Jewel", It_jewel);
	stringToInteger.put("Door", It_door);
	stringToInteger.put("Wall", It_wall);
	stringToInteger.put("Mummy", It_mummy);
	stringToInteger.put("Stairs", It_stairs);
	stringToInteger.put("Giratory", It_giratory);

	for (int i = 1; i <= 15; i++)
	{
	    if (i < 10)
	    {
		levelFileName.put(i, "maps/level_0" + i + ".tmx");
	    } else
		levelFileName.put(i, "maps/level_" + i + ".tmx");

	}

    }

}
