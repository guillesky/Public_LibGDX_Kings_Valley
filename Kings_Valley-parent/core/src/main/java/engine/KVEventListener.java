package engine;

/**
 * Define el contrato que deben implementar los componentes capaces de recibir
 * notificaciones de eventos generados durante la ejecucion del juego.
 *
 * <p>
 * Los eventos permiten comunicar cambios de estado y situaciones relevantes
 * entre distintos componentes del sistema sin establecer dependencias directas
 * entre ellos.
 * </p>
 *
 * <p>
 * Cada evento se identifica mediante un codigo numerico y puede incluir
 * opcionalmente informacion adicional asociada al mismo.
 * </p>
 *
 * <p>
 * Esta estrategia permite implementar un mecanismo de comunicacion simple y
 * flexible, evitando la necesidad de crear una jerarquia especifica de clases
 * para representar cada tipo de evento.
 * </p>
 *
 * <p>
 * Desde el punto de vista arquitectonico, esta interfaz forma parte de un
 * sistema de comunicacion basado en eventos que favorece el desacoplamiento
 * entre los componentes que generan eventos y aquellos encargados de
 * procesarlos.
 * </p>
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
     * Codigo del evento: Termino un episodio
     */
    int FINISH_EPISODE = 101;

    /**
     * Codigo del evento: Termino un gran templo
     */
    int FINISH_GREAT_TEMPLE = 102;

    /**
     * Codigo del evento: termino el juego
     */
    int GAME_OVER = 200;
    /**
     * Codigo del evento: El juego esta terminando
     */

    int GAME_ENDING = 201;
    
    /**
     * Codigo del evento: Se solicito una captura de pantalla
     */

    int SCREENSHOT_REQUIRED = 300;

    /**
     * Metodo invocado cuando se dispara un evento del sistema.
     *
     * <p>
     * El codigo recibido identifica el tipo de evento ocurrido. Los valores
     * posibles se encuentran definidos como constantes en la clase KVEvent.
     * </p>
     *
     * <p>
     * Dependiendo del evento generado, el parametro adicional puede contener
     * informacion complementaria asociada al mismo.
     * </p>
     *
     * @param eventCode codigo identificador del evento disparado
     * @param param     informacion adicional asociada al evento. Su tipo concreto
     *                  depende del evento recibido y puede requerir conversion
     *                  mediante casting.
     */
    void eventFired(int eventCode, Object param);

    /**
     * Metodo invocado periodicamente durante la actualizacion del juego.
     *
     * <p>
     * Permite ejecutar tareas dependientes del tiempo o realizar actualizaciones
     * continuas asociadas al componente que implementa el listener.
     * </p>
     *
     * @param deltaTime tiempo transcurrido, expresado en segundos, desde la ultima
     *                  actualizacion.
     */
    void updateFrame(float deltaTime);


}