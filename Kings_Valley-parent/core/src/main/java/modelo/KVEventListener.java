package modelo;

/**
 * @author Guillermo Lazzurri
 * 
 *         Interfaz que responde a los eventos generados durante el juego. Es
 *         invocada internamente
 */
public interface KVEventListener
{

	int ENTERING_LEVEL = 0;
	int ENTER_LEVEL = 1;
	int EXITING_LEVEL = 2;
	int EXIT_LEVEL = 3;

	int THROW_DAGGER = 5;
	int ENTER_GIRATORY = 6;
	int EXIT_GIRATORY = 7;
	int ACTIVATE_TRAP = 8;
	int TRAP_END_DOWN = 9;

	int CHARACTER_JUMP = 10;
	int CHARACTER_END_JUMP = 11;
	int CHARACTER_BEGIN_FALL = 12;
	int CHARACTER_END_FALL = 13;

	int PLAYER_DIE = 14;
	int PLAYER_PICKING = 15;
	int PLAYER_RESPAWN = 16;
	int PLAYER_JUMP = 17;

	int MUMMY_APPEAR = 20;
	int MUMMY_DIE = 21;
	int MUMMY_KILLED_BY_SWORD = 22;
	int MUMMY_JUMP = 23;

	int ADD_EXTRA_LIFE = 40;
	int PICKUP_JEWEL = 51;
	int PICKUP_DAGGER = 52;
	int PICKUP_PICKER = 53;
	int PICKUP_ALL_JEWEL = 54;
	int OPENING_DOOR = 60;
	int CLOSING_DOOR = 61;
	int SWORD_STUCK = 70;
	int SWORD_CLASH = 71;
	int SWORD_CLASH_FLESH = 72;
	int PAUSED_IS_CHANGED = 80;

	int FINISH_ALL_LEVELS = 100;
	int GAME_OVER = 200;
	int GAME_ENDING = 201;

	/**
	 * Metodo llamado cuando sucede un evento durante el juego
	 * 
	 * @param eventCode codigo numerico que indica el evento disparado<br>
	 *                  Puede tomar los valores: <br>
	 *                  int ENTERING_LEVEL = 0;<br>
	 *                  int ENTER_LEVEL = 1;<br>
	 *                  int EXITING_LEVEL = 2;<br>
	 *                  int EXIT_LEVEL = 3;<br>
	 * 
	 *                  int THROW_DAGGER = 5;<br>
	 *                  int ENTER_GIRATORY = 6;<br>
	 *                  int EXIT_GIRATORY = 7;<br>
	 *                  int ACTIVATE_TRAP = 8;<br>
	 *                  int TRAP_END_DOWN = 9;<br>
	 * 
	 *                  int CHARACTER_JUMP=10;<br>
	 *                  int CHARACTER_END_JUMP=11;<br>
	 *                  int CHARACTER_BEGIN_FALL = 12;<br>
	 *                  int CHARACTER_END_FALL = 13;<br>
	 * 
	 *                  int PLAYER_DIE = 14;<br>
	 *                  int PLAYER_PICKING = 15;<br>
	 *                  int PLAYER_RESPAWN = 16;<br>
	 *                  int PLAYER_JUMP = 17;<br>
	 * 
	 * 
	 *                  int MUMMY_APPEAR = 20;<br>
	 *                  int MUMMY_DIE = 21;<br>
	 *                  int MUMMY_KILLED_BY_SWORD = 22;<br>
	 *                  int MUMMY_JUMP=23;<br>
	 * 
	 * 
	 *                  int ADD_EXTRA_LIFE = 40;<br>
	 *                  int PICKUP_JEWEL = 51;<br>
	 *                  int PICKUP_DAGGER = 52;<br>
	 *                  int PICKUP_PICKER = 53;<br>
	 *                  int PICKUP_ALL_JEWEL = 54;<br>
	 *                  int OPENING_DOOR = 60;<br>
	 *                  int CLOSING_DOOR = 61;<br>
	 *                  int SWORD_STUCK = 70;<br>
	 *                  int SWORD_CLASH = 71;<br>
	 *                  int SWORD_CLASH_FLESH = 72;<br>
	 *                  int PAUSED_IS_CHANGED = 80;<br>
	 * 
	 *                  int FINISH_ALL_LEVELS = 100;<br>
	 *                  int GAME_OVER = 200;<br>
	 *                  int GAME_ENDING = 201;<br>
	 * 
	 * @param param     parametro extra, dependiendo el tipo de evento debera
	 *                  castearse a la clase correspondiente
	 */
	void eventFired(int eventCode, Object param);

	/**
	 * Metodo llamado cada vez que se actualiza una frame, puede usarse para
	 * realizar cualquier accion necesaria
	 * 
	 * @param deltaTime indica el tiempo medido en segundos desde la ultima
	 *                  actualizacion
	 */
	void updateframe(float deltaTime);
}