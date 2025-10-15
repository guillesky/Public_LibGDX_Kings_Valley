package vista2D;

/**
 * Clase que representa la configuracion de los elementos graficos para una
 * representacion en 2D
 * 
 * @author Guillermo Lazzurri
 */
public class GraphicsFileConfig
{
    private String archiPlayer;
    private String archiPlayerSpecial;
    private String archiPickingCell;
    private String archiCollectables;
    private String archiGiratory;
    private String archiMummys;
    private String archiFlyingDagger;
    private String archiDoorPassage;
    private String archiDoorLeft;
    private String archiDoorRight;
    private String archiDoorLever;
    private String archiSky;

    private float frameDuration;

    private int mummyCountIddle;

    private int mummyCountFall;

    private int mummyCountWalk;
    private int mummyCountAppear;
    private int mummyCountDeath;
    private int mummyCountJump;

    private int giratoryCount;
    private int collectableCount;
    private int playerCountIddle;
    private int playerCountFall;
    private int playerCountJump;
    private int playerCountWalk;
    private int playerPickerCountIddle;
    private int playerPickerCountFall;
    private int playerPickerCountWalk;
    private int playerPickerCountPicking;
    private int playerDaggerCountIddle;
    private int playerDaggerCountFall;
    private int playerDaggerCountWalk;
    private int playerDaggerCountThrowing;
    private int playerCountDeath;
    private int flyingDaggerCount;
    private int pickingCellCount;
    private int doorLeverCount;

    /**
     * Retorna el archivo con la textura del cielo de fondo
     * 
     * @return Archivo con la textura del cielo de fondo
     */
    public String getArchiSky()
    {
	return archiSky;
    }

    /**
     * Retorna el archivo con las textura de animacion del movimiento regular del
     * player
     * 
     * @return Archivo con las textura de animacion del movimiento regular del
     *         player
     */
    public String getArchiPlayer()
    {
	return archiPlayer;
    }

    /**
     * Retorna el archivo con las texturas de animaion de celda siendo picada
     * 
     * @return Archivo con las texturas de animaion de celda siendo picada
     */
    public String getArchiPickingCell()
    {
	return archiPickingCell;
    }

    /**
     * Retorna el archivo con las animaciones de los items recolectables (joyas,
     * picos, dagas)
     * 
     * @return Archivo con las animaciones de los items recolectables (joyas, picos,
     *         dagas)
     */
    public String getArchiCollectables()
    {
	return archiCollectables;
    }

    /**
     * Retorna el Archivo con las texturas de las puertas giratorias (un solo tile)
     * 
     * @return Archivo con las texturas de las puertas giratorias (un solo tile)
     */
    public String getArchiGiratory()
    {
	return archiGiratory;
    }

    /**
     * Retorna el archivo con las texturas de las anumaciones de las momias
     * 
     * @return Archivo con las texturas de las anumaciones de las momias
     */
    public String getArchiMummys()
    {
	return archiMummys;
    }

    /**
     * Retorna la cantidad de frames de la animacion iddle de las momias
     * 
     * @return cantidad de frames de la animacion iddle de las momias
     */
    public int getMummyCountIddle()
    {
	return mummyCountIddle;
    }

    /**
     * Retorna la cantidad de frames de la animacion de caida de las momias
     * 
     * @return cantidad de frames de la animacion de caida de las momias
     */
    public int getMummyCountFall()
    {
	return mummyCountFall;
    }

    /**
     * Retorna la cantidad de frames de la animacion de caminata de las momias
     * 
     * @return cantidad de frames de la animacion de caminata de las momias
     */
    public int getMummyCountWalk()
    {
	return mummyCountWalk;
    }

    /**
     * Retorna la cantidad de frames de la puerta giratoria
     * 
     * @return cantidad de frames de la puerta giratoria
     */
    public int getGiratoryCount()
    {
	return giratoryCount;
    }

    /**
     * Retorna la cantidad de frames de la animacion iddle del player
     * 
     * @return cantidad de frames de la animacion iddle del player
     */
    public int getPlayerCountIddle()
    {
	return playerCountIddle;
    }

    /**
     * Retorna la cantidad de frames de la animacion de caida del player
     * 
     * @return cantidad de frames de la animacion de caida del player
     */

    public int getPlayerCountFall()
    {
	return playerCountFall;
    }

    /**
     * Retorna la cantidad de frames de la animacion de caminata del player
     * 
     * @return cantidad de frames de la animacion de caminata del player
     */

    public int getPlayerCountWalk()
    {
	return playerCountWalk;
    }

    /**
     * Retorna la cantidad de frames de la animacion de muerte del player
     * 
     * @return cantidad de frames de la animacion de muerte del player
     */

    public int getPlayerCountDeath()
    {
	return playerCountDeath;
    }

    /**
     * Retorna la cantidad de frames de la animacion de cada Collectable
     * 
     * @return cantidad de frames de la animacion de cada Collectable
     */
    public int getCollectableCount()
    {
	return collectableCount;
    }

    /**
     * Retorna la duracion de cada frame por defecto
     * 
     * @return Duracion de cada frame por defecto
     */
    public float getFrameDuration()
    {
	return frameDuration;
    }

    /**
     * Retorna la cantidad de frames de la animacion de aparicion de las momias
     * 
     * @return cantidad de frames de la animacion de aparicion de las momias
     */
    public int getMummyCountAppear()
    {
	return mummyCountAppear;
    }

    /**
     * Retorna la cantidad de frames de la animacion de muerte de las momias
     * 
     * @return cantidad de frames de la animacion de muerte de las momias
     */
    public int getMummyCountDeath()
    {
	return mummyCountDeath;
    }

    /**
     * Retorna la cantidad de frames de la animacion iddle del player cuando porta
     * un pico
     * 
     * @return cantidad de frames de la animacion iddle del player cuando porta un
     *         pico
     */
    public int getPlayerPickerCountIddle()
    {
	return playerPickerCountIddle;
    }

    /**
     * Retorna la cantidad de frames de la animacion de caida del player cuando
     * porta un pico
     * 
     * @return cantidad de frames de la animacion de caida del player cuando porta
     *         un pico
     */
    public int getPlayerPickerCountFall()
    {
	return playerPickerCountFall;
    }

    /**
     * Retorna la cantidad de frames de la animacion de caminata del player cuando
     * porta un pico
     * 
     * @return cantidad de frames de la animacion de caminata del player cuando
     *         porta un pico
     */
    public int getPlayerPickerCountWalk()
    {
	return playerPickerCountWalk;
    }

    /**
     * Retorna la cantidad de frames de la animacion de estar picando del player
     * 
     * @return cantidad de frames de la animacion de estar picando del player
     */
    public int getPlayerPickerCountPicking()
    {
	return playerPickerCountPicking;
    }

    /**
     * Retorna
     * 
     * @return cantidad de frames de la animacion iddle del player cuando porta una
     *         espada
     */
    public int getPlayerDaggerCountIddle()
    {
	return playerDaggerCountIddle;
    }

    /**
     * Retorna
     * 
     * @return cantidad de frames de la animacion de caida del player cuando porta
     *         una espada
     */
    public int getPlayerDaggerCountFall()
    {
	return playerDaggerCountFall;
    }

    /**
     * Retorna
     * 
     * @return cantidad de frames de la animacion de caminata del player cuando
     *         porta una espada
     */
    public int getPlayerDaggerCountWalk()
    {
	return playerDaggerCountWalk;
    }

    /**
     * Retorna la cantidad de frames de la animacion de lanzar una espada del player
     * 
     * @return cantidad de frames de la animacion de lanzar una espada del player
     */
    public int getPlayerDaggerCountThrowing()
    {
	return playerDaggerCountThrowing;
    }

    /**
     * Retorna el archivo con las texturas de la daga vola
     * 
     * @return Archivo con las texturas de la daga volando
     */
    public String getArchiFlyingDagger()
    {
	return archiFlyingDagger;
    }

    /**
     * Retorna la cantidad de frames de la animacion de la daga volando
     * 
     * @return cantidad de frames de la animacion de la daga volando
     */
    protected int getFlyingDaggerCount()
    {
	return flyingDaggerCount;
    }

    /**
     * Retorna la cantidad de frames de la animacion de la daga volando
     * 
     * @return cantidad de frames de la animacion de la celda siendo picada
     */
    protected int getPickingCellCount()
    {
	return pickingCellCount;
    }

    /**
     * Retorna el archivo con la textura del pasage de las puertas de entrada /
     * salida
     * 
     * @return Archivo con la textura del pasage de las puertas de entrada / salida
     */
    public String getArchiDoorPassage()
    {
	return archiDoorPassage;
    }

    /**
     * Retorna el archivo con la textura de la hoja izquierda de una puerta
     * 
     * @return Archivo con la textura de la hoja izquierda de una puerta
     */
    public String getArchiDoorLeft()
    {
	return archiDoorLeft;
    }

    /**
     * Retorna archivo con la textura de la hoja derecha de una puerta
     * 
     * @return Archivo con la textura de la hoja derecha de una puerta
     */

    public String getArchiDoorRight()
    {
	return archiDoorRight;
    }

    /**
     * Retorna el archivo con la animacion de la palanca de una puerta
     * 
     * @return Archivo con la animacion de la palanca de una puerta
     */
    public String getArchiDoorLever()
    {
	return archiDoorLever;
    }

    /**
     * Retorna la cantidad de frames de la animacion de la palanca de una puerta
     * 
     * @return Cantidad de frames de la animacion de la palanca de una puerta
     */
    public int getDoorLeverCount()
    {
	return doorLeverCount;
    }

    /**
     * Retorna la cantidad de frames de la animacion de salto del player
     * 
     * @return Cantidad de frames de la animacion de salto del player
     */
    protected int getPlayerCountJump()
    {
	return playerCountJump;
    }

    /**
     * Retorna el archivo con las texturas de las animaciones especiales del player
     * (picando y lanzando espadas)
     * 
     * @return Archivo con las texturas de las animaciones especiales del player
     *         (picando y lanzando espadas)
     */
    public String getArchiPlayerSpecial()
    {
	return archiPlayerSpecial;
    }

    /**
     * Retorna la cantidad de frames de la animacion de salto de las momias
     * 
     * @return Cantidad de frames de la animacion de salto de las momias
     */
    public int getMummyCountJump()
    {
	return mummyCountJump;
    }

}
