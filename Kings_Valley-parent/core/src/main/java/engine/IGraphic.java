package engine;

/**
 * Interfaz que debera ser implementada por la clase encargada de la
 * representacion grafica del juego.
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
