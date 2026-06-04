package engine;

import com.badlogic.gdx.math.Vector2;

/**
 * Define el contrato de comunicacion entre la logica del juego y los mecanismos
 * de control utilizados por el jugador.
 *
 * <p>
 * Esta interfaz constituye el principal mecanismo de desacoplamiento entre el
 * modelo del juego y los dispositivos de entrada concretos. Las clases de la
 * logica interactuan exclusivamente con esta abstraccion, evitando dependencias
 * directas respecto de teclados, joysticks, pantallas tactiles u otros sistemas
 * de control.
 * </p>
 *
 * <p>
 * La logica del juego conoce la existencia de esta interfaz, pero desconoce
 * completamente la implementacion concreta utilizada para obtener las acciones
 * del jugador. Esto permite reemplazar o incorporar nuevos mecanismos de
 * control sin modificar el codigo del dominio.
 * </p>
 *
 * <p>
 * Gracias a esta abstraccion es posible implementar distintos esquemas de
 * entrada, como control por teclado, joystick, inteligencia artificial o
 * reproduccion automatizada de acciones, manteniendo inalterada la logica
 * principal del juego.
 * </p>
 *
 * <p>
 * Desde el punto de vista arquitectonico, esta interfaz materializa el
 * principio de separacion de responsabilidades y favorece el desacoplamiento
 * entre el dominio del juego y los mecanismos de entrada.
 * </p>
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
     * @param buttonCode codigo del boton a analizar. Los valores definidos por
     *                   la interfaz son: <br>
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
     * 
     * @return true si buttonCode fue pulsado, false en caso contrario
     */
    boolean getShot(int buttonCode);

    /**
     * Actualiza el estado del control de entrada
     */
    void updateControl();

}