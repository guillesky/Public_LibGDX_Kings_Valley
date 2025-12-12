package engine;

/**
 * Interfaz que responde a los eventos generados durante el juego. Es invocada
 * internamente
 * 
 * @author Guillermo Lazzurri
 */
public interface KVEventListener
{

	/**
	 * Codigo del evento: entrando al nivel
	 */
	int ENTERING_LEVEL = 0;
	/**
	 * Codigo del evento: entro al nivel
	 */

	int ENTER_LEVEL = 1;
	/**
	 * Codigo del evento: saliendo del nivel
	 */
	int EXITING_LEVEL = 2;
	/**
	 * Codigo del evento: salio al nivel
	 */

	int EXIT_LEVEL = 3;

	/**
	 * Codigo del evento: se arrojo una espada
	 */
	int THROW_DAGGER = 5;
	/**
	 * Codigo del evento: entro a una giratoria
	 */

	int ENTER_GIRATORY = 6;
	/**
	 * Codigo del evento: salio de una giratoria
	 */

	int EXIT_GIRATORY = 7;
	/**
	 * Codigo del evento: se activo una trampa
	 */
	int ACTIVATE_TRAP = 8;
	/**
	 * Codigo del evento: la trampa llego al piso
	 */
	int TRAP_END_DOWN = 9;
	/**
	 * Codigo del evento: salto un caracter
	 */
	int CHARACTER_JUMP = 10;
	/**
	 * Codigo del evento: termino el salto de un caracter
	 */
	int CHARACTER_END_JUMP = 11;
	/**
	 * Codigo del evento: un caracter comenzo a caer
	 */
	int CHARACTER_BEGIN_FALL = 12;

	/**
	 * Codigo del evento: un caracter termino de caer
	 */

	int CHARACTER_END_FALL = 13;
	/**
	 * Codigo del evento: un caracter entro a una escalera
	 */

	int CHARACTER_ENTER_STAIR = 14;

	/**
	 * Codigo del evento: un caracter salio de una escalera
	 */

	int CHARACTER_EXIT_STAIR = 15;

	/**
	 * Codigo del evento: player murio
	 */

	int PLAYER_DIE = 16;
	/**
	 * Codigo del evento: player picando
	 */

	int PLAYER_PICKING = 17;
	/**
	 * Codigo del evento: player revivio
	 */

	int PLAYER_RESPAWN = 18;
	/**
	 * Codigo del evento: player salto
	 */

	int PLAYER_JUMP = 19;

	/**
	 * Codigo del evento: una momia aparecio
	 */
	int MUMMY_APPEAR = 20;
	/**
	 * Codigo del evento: una momia murio
	 */

	int MUMMY_DIE = 21;
	/**
	 * Codigo del evento: una momia murio por una espada
	 */
	int MUMMY_KILLED_BY_SWORD = 22;
	/**
	 * Codigo del evento: una momia salto
	 */
	int MUMMY_JUMP = 23;

	/**
	 * Codigo del evento: se agrego vida extra
	 */
	int ADD_EXTRA_LIFE = 40;
	/**
	 * Codigo del evento: se recolecto una gema
	 */
	int PICKUP_JEWEL = 51;
	/**
	 * Codigo del evento: se recolecto una espada
	 */

	int PICKUP_DAGGER = 52;
	/**
	 * Codigo del evento: se recolecto un pico
	 */

	int PICKUP_PICKER = 53;
	/**
	 * Codigo del evento: se recolecto la ultima gema
	 */

	int PICKUP_ALL_JEWEL = 54;
	/**
	 * Codigo del evento: abriendo puerta
	 */

	int OPENING_DOOR = 60;
	/**
	 * Codigo del evento: cerrando puerta
	 */

	int CLOSING_DOOR = 61;
	/**
	 * Codigo del evento: Se clavo la espada en el piso
	 */

	int SWORD_STUCK = 70;
	/**
	 * Codigo del evento: La espada choco la pared
	 */

	int SWORD_CLASH = 71;
	/**
	 * Codigo del evento: La espada golpeo una momia
	 */

	int SWORD_CLASH_FLESH = 72;
	/**
	 * Codigo del evento: se entro o salio del modo pausado
	 */

	int PAUSED_IS_CHANGED = 80;
	/**
	 * Codigo del evento: Se terminaron todos los niveles
	 */

	int FINISH_ALL_LEVELS = 100;
	/**
	 * Codigo del evento: termino el juego
	 */

	int FINISH_EPISODE = 101;
	/**
	 * Codigo del evento: Termino un nuevo episodio
	 */

	
	int GAME_OVER = 200;
	/**
	 * Codigo del evento: El juego esta terminando
	 */

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
	 *                  int CHARACTER_ENTER_STAIR=14;<br>
	 *                  int CHARACTER_EXIT_STAIR=15;<br>
	 *                  int PLAYER_DIE = 16;<br>
	 *                  int PLAYER_PICKING = 17;<br>
	 *                  int PLAYER_RESPAWN = 18;<br>
	 *                  int PLAYER_JUMP = 19;<br>
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