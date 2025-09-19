package modelo;

/**
 * @author Guillermo Lazzurri
 * 
 * Interfaz que responde a los eventos generados durante el juego. Es invocada internamente
 */
public interface KVEventListener
{

	int ENTERING_LEVEL = 0;
	int ENTER_LEVEL = 1;
	int EXITING_LEVEL = 2;
	int EXIT_LEVEL = 3;
	
	
	int THROW_DAGGER = 5;
	int ENTER_GIRATORY = 6;
	int ACTIVATE_TRAP = 7;
	
	int PLAYER_JUMP = 10;
	int PLAYER_LANDING = 11;
	int PLAYER_BEGIN_FALL = 12;
	int PLAYER_DIE = 13;
	int PLAYER_PICKING = 14;
	int PLAYER_RESPAWN = 15;
	
	int MUMMY_APPEAR = 20;
	int MUMMY_DIE = 21;
	int MUMMY_KILLED_BY_SWORD = 22;
	int FINISH_CURRENT_LEVEL = 30;
	int ADD_EXTRA_LIFE = 40;
	int PICKUP_JEWEL = 51;
	int PICKUP_DAGGER = 52;
	int PICKUP_PICKER = 53;
	int PICKUP_ALL_JEWEL = 54;
	int OPENING_DOOR=60;
	int CLOSING_DOOR=61;
	int SWORD_STUCK=70;
	int SWORD_CLASH=71;
	int SWORD_CLASH_FLESH=72;
	int PAUSED_IS_CHANGED = 80;
	
	int FINISH_ALL_LEVELS=100;
	int GAME_OVER = 200;
	int GAME_OVER_INIT = 201;
	
	
	
	

	/**
	 * Metodo llamado cuando sucede un evento durante el juego
	 * @param eventCode codigo numerico que indica el evento disparado
	 * @param param parametro extra, dependiendo el tipo de evento debera castearse a la clase correspondiente
	 */
	void eventFired(int eventCode, Object param);





	/**
	 * Metodo llamado cada vez que se actualiza una frame, puede usarse para realizar cualquier accion necesaria
	 * @param deltaTime indica el tiempo medido en segundos desde la ultima actualizacion
	 */
	void updateframe(float deltaTime);
}