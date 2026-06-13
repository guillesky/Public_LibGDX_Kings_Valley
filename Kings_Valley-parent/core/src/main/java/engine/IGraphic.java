package engine;

/**
 * Define el contrato de comunicacion entre la logica del juego y la capa de
 * representacion visual.
 *
 * <p>
 * Esta interfaz constituye el principal mecanismo de desacoplamiento entre el
 * modelo del juego y la implementacion grafica concreta. Las clases de la
 * logica interactuan exclusivamente con esta abstraccion, evitando dependencias
 * directas respecto de bibliotecas o motores de renderizado. La logica del
 * juego conoce la existencia de esta interfaz, pero desconoce completamente la
 * implementacion grafica concreta utilizada por el sistema.
 * </p>
 *
 * <p>
 * Ademas de operaciones relacionadas con la incorporacion y eliminacion de
 * elementos visuales, la interfaz provee acceso a temporizaciones asociadas a
 * animaciones y transiciones graficas. Esto permite coordinar determinados
 * comportamientos del juego sin exponer detalles de implementacion de la capa
 * visual.
 * </p>
 *
 * <p>
 * Desde el punto de vista arquitectonico, esta interfaz materializa el
 * principio de separacion de responsabilidades y favorece el desacoplamiento
 * entre el dominio del juego y su representacion grafica.
 * </p>
 *
 * @author Guillermo Lazzurri
 */
public interface IGraphic
{

    /**
     * Agrega un elemento grafico al juego. Puede ser durante la inicializacion o
     * durante el juego (por ejemplo agregar la celda siendo picada)
     * 
     * @param element Objeto dibujable que debe agregarse
     */
    void addGraphicElement(DrawableElement element);

    /**
     * Elimina un elemento grafico del juego. Por ejemplo al recolectar una joya.
     * 
     * @param element Objeto dibujable que debe eliminarse
     */
    void removeGraphicElement(DrawableElement element);

    /**
     * Incializa los elementos necesarios
     */
    void inicialize();

    /**
     * Retorna el tiempo necesario para salir del nivel
     * 
     * @return El tiempo necesario para salir del nivel
     */
    float getTimeToExitLevel();

    /**
     * Retorna el tiempo necesario para entrar al nivel
     * 
     * @return El tiempo necesario para entrar al nivel
     */
    float getTimeToEnterLevel();

    /**
     * Retorna el tiempo que tarda el player en morir
     * 
     * @return El tiempo que tarda el player en morir
     */
    float getTimeDying();

    /**
     * Retorna el tiempo que tardar el juego en terminar (por ejemplo para mostrar
     * el cartel de Game Over)
     * 
     * @return El tiempo que tardar el juego en terminar (por ejemplo para mostrar
     *         el cartel de Game Over)
     */
    float getTimeToEndGame();

   

}
