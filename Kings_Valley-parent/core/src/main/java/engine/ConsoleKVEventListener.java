package engine;

import java.util.HashMap;

/**
 * @author Guillermo Lazzurri
 * 
 *         Clase usada solo para debug. Muestra por consola los eventos
 *         disparados en el juego
 */
public class ConsoleKVEventListener implements KVEventListener
{

	/**
	 * Asigna a cada codigo de evento un String representativo para mostrarlo por
	 * consola (utiliza el mismo nombre de la variable del evento)
	 */
	private HashMap<Integer, String> messages = new HashMap<Integer, String>();

	public ConsoleKVEventListener()
	{
		this.messages.put(ENTERING_LEVEL, "ENTERING_LEVEL");
		this.messages.put(ENTER_LEVEL, "ENTER_LEVEL");
		this.messages.put(EXITING_LEVEL, "EXITING_LEVEL");
		this.messages.put(EXIT_LEVEL, "EXIT_LEVEL");
		this.messages.put(THROW_DAGGER, "THROW_DAGGER");
		this.messages.put(ENTER_GIRATORY, "ENTER_GIRATORY");
		this.messages.put(EXIT_GIRATORY, "EXIT_GIRATORY");
		this.messages.put(ACTIVATE_TRAP, "ACTIVATE_TRAP");
		this.messages.put(TRAP_END_DOWN, "TRAP_END_DOWN");
		this.messages.put(CHARACTER_JUMP, "CHARACTER_JUMP");
		this.messages.put(CHARACTER_END_JUMP, "CHARACTER_END_JUMP");
		this.messages.put(CHARACTER_BEGIN_FALL, "CHARACTER_BEGIN_FALL");
		this.messages.put(CHARACTER_END_FALL, "CHARACTER_END_FALL");
		this.messages.put(CHARACTER_ENTER_STAIR, "CHARACTER_ENTER_STAIR");
		this.messages.put(CHARACTER_EXIT_STAIR, "CHARACTER_EXIT_STAIR");
		this.messages.put(PLAYER_DIE, "PLAYER_DIE");
		this.messages.put(PLAYER_PICKING, "PLAYER_PICKING");
		this.messages.put(PLAYER_RESPAWN, "PLAYER_RESPAWN");
		this.messages.put(PLAYER_JUMP, "PLAYER_JUMP");
		this.messages.put(MUMMY_APPEAR, "MUMMY_APPEAR");
		this.messages.put(MUMMY_DIE, "MUMMY_DIE");
		this.messages.put(MUMMY_KILLED_BY_SWORD, "MUMMY_KILLED_BY_SWORD");
		this.messages.put(MUMMY_JUMP, "MUMMY_JUMP");
		this.messages.put(ADD_EXTRA_LIFE, "ADD_EXTRA_LIFE");
		this.messages.put(PICKUP_JEWEL, "PICKUP_JEWEL");
		this.messages.put(PICKUP_DAGGER, "PICKUP_DAGGER");
		this.messages.put(PICKUP_PICKER, "PICKUP_PICKER");
		this.messages.put(PICKUP_ALL_JEWEL, "PICKUP_ALL_JEWEL");
		this.messages.put(OPENING_DOOR, "OPENING_DOOR");
		this.messages.put(CLOSING_DOOR, "CLOSING_DOOR");
		this.messages.put(SWORD_STUCK, "SWORD_STUCK");
		this.messages.put(SWORD_CLASH, "SWORD_CLASH");
		this.messages.put(SWORD_CLASH_FLESH, "SWORD_CLASH_FLESH");
		this.messages.put(PAUSED_IS_CHANGED, "PAUSED_IS_CHANGED");
		this.messages.put(FINISH_ALL_LEVELS, "FINISH_ALL_LEVELS");
		this.messages.put(GAME_OVER, "GAME_OVER");
		this.messages.put(GAME_ENDING, "GAME_ENDING");

	}

	/**
	 * Muestra por consola el String asiciado al codio de evento, y el parametro
	 * recibido
	 */
	@Override
	public void eventFired(int eventCode, Object param)
	{
		System.out.println("EVENT FIRED-> Code : " + this.messages.get(eventCode) + " Param: " + param);

	}

	@Override
	public void updateframe(float deltaTime)
	{

	}

}
