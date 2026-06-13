package keyboardGameControl;

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

    private Vector2 movementDirection;

    private HashMap<Integer, Boolean> hashMapActionBooleans = new HashMap<Integer, Boolean>();
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
	this.registerKey(Input.Keys.P, IGameControl.PAUSE);
	this.registerKey(Input.Keys.ESCAPE, IGameControl.PAUSE);
	this.registerKey(Input.Keys.S, IGameControl.AUX_BUTTON1);
	

	this.hashMapActionBooleans.put(IGameControl.ACTION, false);
	this.hashMapActionBooleans.put(IGameControl.PAUSE, false);
	this.hashMapActionBooleans.put(IGameControl.AUX_BUTTON1, false);
	this.hashMapActionBooleans.put(IGameControl.AUX_BUTTON2, false);
	this.hashMapActionBooleans.put(IGameControl.AUX_BUTTON3, false);
	this.hashMapActionBooleans.put(IGameControl.AUX_BUTTON4, false);
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
     * @param actionCode codigo de accion del juego. Los valores definidos por la
     *                   interfaz son: <br>
     *                   IGameControl.ACTION = 0; <br>
     *                   IGameControl.PAUSE = 1; <br>
     *                   IGameControl.AUX_BUTTON1 = 1001; <br>
     *                   IGameControl.AUX_BUTTON2 = 1002; <br>
     *                   IGameControl.AUX_BUTTON3 = 1003; <br>
     *                   IGameControl.AUX_BUTTON4 = 1004; <br>
     *                   <br>
     *                   La implementacion puede definir codigos adicionales para
     *                   incorporar nuevas acciones del juego sin necesidad de
     *                   modificar esta interfaz.
     *
     */
    public void registerKey(int key, int actionCode)
    {
	this.hashMapKeyboarToActionCode.put(key, actionCode);
    }

    /**
     * Indica si el codigo de accion pasado por parametro ha sido pulsado
     * 
     * @param actionCode codigo de accion a analizar. Los valores definidos por la
     *                   interfaz son: <br>
     *                   IGameControl.ACTION = 0; <br>
     *                   IGameControl.PAUSE = 1; <br>
     *                   IGameControl.AUX_BUTTON1 = 1001; <br>
     *                   IGameControl.AUX_BUTTON2 = 1002; <br>
     *                   IGameControl.AUX_BUTTON3 = 1003; <br>
     *                   IGameControl.AUX_BUTTON4 = 1004; <br>
     *                   <br>
     *                   La implementacion puede definir codigos adicionales para
     *                   incorporar nuevas acciones del juego sin necesidad de
     *                   modificar esta interfaz.
     * @return true si actionCode fue pulsado, false en caso contrario
     */
    @Override
    public boolean getShot(int actionCode)
    {
	boolean isShooted = this.hashMapActionBooleans.get(actionCode);
	if (isShooted)
	    this.hashMapActionBooleans.put(actionCode, false);
	return isShooted;
    }

    /**
     * procesa la tecla pasada por parametro, actualizando sus atributos enabled y
     * pushed
     * 
     * @param key codigo de la tecla a procesar
     */

    private void processKey(int key)
    {
	int actionCode = this.hashMapKeyboarToActionCode.get(key);
	boolean isJustPressed = Gdx.input.isKeyJustPressed(key);
	if (!this.hashMapActionBooleans.get(actionCode) && isJustPressed)
	    this.hashMapActionBooleans.put(actionCode, true);
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
