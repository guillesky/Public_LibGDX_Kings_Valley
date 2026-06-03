package engine;

import com.badlogic.gdx.math.Vector2;

/**
 * Interface que debera ser implementada por la calse que maneje los controles
 * del juego (Joystick, Teclado, GamePad, etc.)
 * 
 * @author Guillermo Lazzurri
 */
public interface IGameControl
{
    /**
     * Codigo que representa el boton de accion (saltar, picar, lanzar espada)
     */
    public static final int ACTION = 0;
    /**
     * Codigo para pausar el juego
     */
    public static final int PAUSE = 1;
    /**
     * Codigo auxiliar (no usado durante el juego original, disponible para
     * extensiones o debug)
     */
    public static final int AUX_BUTTON1 = 1000;
    /**
     * Codigo auxiliar (no usado durante el juego original, disponible para
     * extensiones o debug)
     */
    public static final int AUX_BUTTON2 = 1002;
    /**
     * Codigo auxiliar (no usado durante el juego original, disponible para
     * extensiones o debug)
     */

    public static final int AUX_BUTTON3 = 1003;
    /**
     * Codigo auxiliar (no usado durante el juego original, disponible para
     * extensiones o debug)
     */

    public static final int AUX_BUTTON4 = 1004;

    /**
     * Retorna el Vector2 que indica la direccion pretendida para el player
     * 
     * @return objeto de tipo Vector2 que indica la direccion pretendida para el
     *         player
     */
    Vector2 getMovementDirection();

    /**
     * Indica si el codigo de botona pasado por parametro ha sido pulsado
     * 
     * @param buttonCode codigo del boton a analizar, podria ser:<br>
     *                   IGameControl.ACTION=0;<br>
     *                   IGameControl.PAUSE=1;<br>
     *                   IGameControl.AUX_BUTTON1=1000;<br>
     *                   IGameControl.AUX_BUTTON2=1002;<br>
     *                   IGameControl.AUX_BUTTON3=1003;<br>
     *                   IGameControl.AUX_BUTTON4=1004;<br>
     * 
     * @return true si buttonCode fue pulsado, false en caso contrario
     */
    boolean getShot(int buttonCode);

    void updateControl();

}