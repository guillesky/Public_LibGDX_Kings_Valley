package util;

import java.util.HashMap;

public class Constantes
{
	public static final int It_none = 0;
	public static final int It_brick = 76;
	public static final int It_giratory = 71;
	public static final int It_rgiratory = 62;
	public static final int It_lgiratory = 60;
	public static final int It_dagger = 68;
	public static final int It_picker = 80;
	public static final int It_jewel = 74;
	public static final int It_door = 84;
	public static final int It_wall = 85;
	public static final int It_mummy = 77;
	public static final int It_stairs = 69;
	public static final int It_stair_ur = 48;
	public static final int It_stair_dl = 49;
	public static final int It_stair_ul = 50;
	public static final int It_stair_dr = 51;
	public static final int It_push_in = 73;
	public static final int It_push_out = 79;
	public static final int It_activator = 100;
	
	public static final HashMap<Integer, String> identificacion = new HashMap<Integer, String>();

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
identificacion.put(It_stair_ur, "It_stair_ur");
identificacion.put(It_stair_dr, "It_stair_dr");
identificacion.put(It_stair_ul, "It_stair_ul");
identificacion.put(It_stair_dl, "It_stair_dl");

identificacion.put(It_push_in, "It_push_in");
identificacion.put(It_push_out, "It_push_out");
identificacion.put(It_activator, "Activator");





}

}
