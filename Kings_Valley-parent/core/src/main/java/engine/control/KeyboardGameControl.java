package engine.control;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

import engine.IGameControl;

/**
 * Clase que representa el estado de los controles del juego (teclado)
 * 
 * @author Guillermo Lazzurri
 */
public class KeyboardGameControl implements IGameControl
{

    private class KeyBooleans
    {
	protected boolean pushed = false;
	protected boolean enabled = true;
    }

    private Vector2 movementDirection;

    private HashMap<Integer, KeyBooleans> hashMapKeyBooleans = new HashMap<Integer, KeyBooleans>();
    private HashMap<Integer, Integer> hashMapKeyboarToActionCode = new HashMap<Integer, Integer>();

    /**
     * Constructor de clase, registra por defecto:<br>
     * this.registerKey(Input.Keys.SPACE, IGameControl.ACTION);<br>
     * this.registerKey(Input.Keys.P, IGameControl.PAUSE);<br>
     * this.registerKey(Input.Keys.ESCAPE, IGameControl.PAUSE);<br>
     * this.registerKey(Input.Keys.S, IGameControl.AUX_BUTTON1);<br>
     */
    public KeyboardGameControl()
    {
	this.registerKey(Input.Keys.SPACE, IGameControl.ACTION);
//	this.registerKey(Input.Keys.P, IGameControl.PAUSE);
	this.registerKey(Input.Keys.ESCAPE, IGameControl.PAUSE);
	this.registerKey(Input.Keys.S, IGameControl.AUX_BUTTON1);
    }

    /**
     * Retorna el Vector2 que indica la direccion pretendida para el player
     * 
     * @return objeto de tipo Vector2 que indica la direccion pretendida para el
     *         player
     */
    @Override
    public Vector2 getMovementDirection()
    {
	return movementDirection;
    }

    /**
     * Registra una tecla del teclado a un codigo de accion del juego
     * 
     * @param key        codigo de la tecla
     * @param actionCode codigo de accion del juego, podria ser:<br>
     *                   IGameControl.ACTION=0;<br>
     *                   IGameControl.PAUSE=1;<br>
     *                   IGameControl.AUX_BUTTON1=1000;<br>
     *                   IGameControl.AUX_BUTTON2=1002;<br>
     *                   IGameControl.AUX_BUTTON3=1003;<br>
     *                   IGameControl.AUX_BUTTON4=1004;<br>
     */
    public void registerKey(int key, int actionCode)
    {
	this.hashMapKeyboarToActionCode.put(key, actionCode);
    }

    /**
     * Metodo llamado cuando se pulsa una tecla de accion (por ejemplo la barra
     * espaciadora)
     * 
     * @param actionCode codigo de la tecla de accion pulsada
     */
    private void shot(int actionCode)
    {
	KeyBooleans kb = this.hashMapKeyBooleans.get(actionCode);
	if (kb == null)
	{
	    kb = new KeyBooleans();
	    this.hashMapKeyBooleans.put(actionCode, kb);
	}
	kb.pushed = true;
	kb.enabled = false;
    }

    /**
     * Indica si la tecla pasada por parametro ha sido pulsada
     * 
     * @param actionCode codigo de la tecla de accion a analizar
     * @return true si key fue pulsada, false en caso contrario
     */
    @Override
    public boolean getShot(int actionCode)
    {
	KeyBooleans kb = this.hashMapKeyBooleans.get(actionCode);
	if (kb == null)
	{
	    kb = new KeyBooleans();
	    this.hashMapKeyBooleans.put(actionCode, kb);
	}
	boolean r = kb.pushed;
	kb.pushed = false;
	return r;
    }

    /**
     * Indica si la tecla pasada por parametro esta disponible para ser pulsada
     * 
     * @param actionCode codigo de la tecla de accion a analizar
     * @return true si key esta habilitada, false en caso contrario
     */
    private boolean isShotEnabled(int actionCode)
    {
	KeyBooleans kb = this.hashMapKeyBooleans.get(actionCode);
	if (kb == null)
	{
	    kb = new KeyBooleans();
	    this.hashMapKeyBooleans.put(actionCode, kb);
	}
	return kb.enabled;
    }

    /**
     * Habilita una tecla de accion para ser pulsada
     * 
     * @param actionCode codigo de la tecla de accion a habilitar
     */
    private void enableShotEnabled(int actionCode)
    {
	KeyBooleans kb = this.hashMapKeyBooleans.get(actionCode);
	if (kb == null)
	{
	    kb = new KeyBooleans();
	    this.hashMapKeyBooleans.put(actionCode, kb);
	}
	kb.enabled = true;
    }

    /**
     * procesa la tecla pasada por parametro, actualizando sus atributos enabled y
     * pushed
     * 
     * @param key codigo de la tecla de accion a procesar
     */

    private void processKey(int key)
    {
	int actionCode = this.hashMapKeyboarToActionCode.get(key);
	if (Gdx.input.isKeyPressed(key) && this.isShotEnabled(actionCode))
	    this.shot(actionCode);
	if (!Gdx.input.isKeyPressed(key) && !this.isShotEnabled(actionCode))
	    this.enableShotEnabled(actionCode);
    }

    @Override
    public void updateControl()
    {
	float x = 0, y = 0;

	if (Gdx.input.isKeyPressed(Input.Keys.UP))
	    y += 1;
	if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
	    y -= 1;
	if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
	    x += 1;
	if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
	    x -= 1;
	this.movementDirection = new Vector2(x, y);

	for (int key : this.hashMapKeyboarToActionCode.keySet())
	    this.processKey(key);
    }

}
