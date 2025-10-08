package engine.control;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

/**
 * Clase que representa el estado de los controles del juego (teclado)
 * 
 * @author Guillermo Lazzurri
 */
public class Controls
{
	/**
	 * @author Guillermo Lazzurri inner class que maneja los estado de una tecla
	 *         (pulsada o disponible) es para que las teclas de accion disparen un
	 *         unico evento
	 */
	private class KeyBooleans
	{
		protected boolean pushed = false;
		protected boolean enabled = true;
	}

	private Vector2 nuevoRumbo;

	private HashMap<Integer, KeyBooleans> hashMapKeys = new HashMap<Integer, KeyBooleans>();

	/**
	 * 
	 * @return objeto de tipo Vector2 que indica la direccion pretendida para el
	 *         player
	 */
	public Vector2 getNuevoRumbo()
	{
		return nuevoRumbo;
	}

	/**
	 * @param nuevoRumbo objeto de tipo Vector2 que indica la direccion pretendida
	 *                   para el player
	 */
	public void setNuevoRumbo(Vector2 nuevoRumbo)
	{
		this.nuevoRumbo = nuevoRumbo;
	}

	/**
	 * Metodo llamado cuando se pulsa una tecla de accion (por ejemplo la barra
	 * espaciadora)
	 * 
	 * @param key codigo de la tecla de accion pulsada
	 */
	private void shot(int key)
	{
		KeyBooleans kb = this.hashMapKeys.get(key);
		if (kb == null)
		{
			kb = new KeyBooleans();
			this.hashMapKeys.put(key, kb);
		}
		kb.pushed = true;
		kb.enabled = false;
	}

	/**
	 * Indica si la tecla pasada por parametro ha sido pulsada
	 * 
	 * @param key codigo de la tecla de accion a analizar
	 * @return true si key fue pulsada, false en caso contrario
	 */
	public boolean getShot(int key)
	{
		KeyBooleans kb = this.hashMapKeys.get(key);
		if (kb == null)
		{
			kb = new KeyBooleans();
			this.hashMapKeys.put(key, kb);
		}
		boolean r = kb.pushed;
		kb.pushed = false;
		return r;
	}

	/**
	 * Indica si la tecla pasada por parametro esta disponible para ser pulsada
	 * 
	 * @param key codigo de la tecla de accion a analizar
	 * @return true si key esta habilitada, false en caso contrario
	 */
	private boolean isShotEnabled(int key)
	{
		KeyBooleans kb = this.hashMapKeys.get(key);
		if (kb == null)
		{
			kb = new KeyBooleans();
			this.hashMapKeys.put(key, kb);
		}
		return kb.enabled;
	}

	/**
	 * Habilita una tecla de accion para ser pulsada
	 * 
	 * @param key codigo de la tecla de accion a habilitar
	 */
	private void enableShotEnabled(int key)
	{
		KeyBooleans kb = this.hashMapKeys.get(key);
		if (kb == null)
		{
			kb = new KeyBooleans();
			this.hashMapKeys.put(key, kb);
		}
		kb.enabled = true;
	}

	/**
	 * procesa la tecla pasada por parametro, actualizando sus atributos enabled y
	 * pushed
	 * 
	 * @param key codigo de la tecla de accion a procesar
	 */
	public void processKey(int key)
	{
		if (Gdx.input.isKeyPressed(key) && this.isShotEnabled(key))
			this.shot(key);
		if (!Gdx.input.isKeyPressed(key) && !this.isShotEnabled(key))
			this.enableShotEnabled(key);

	}

}
