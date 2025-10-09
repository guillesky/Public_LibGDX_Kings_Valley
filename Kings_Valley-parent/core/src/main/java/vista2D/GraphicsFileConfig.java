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
     * @return Archivo con la textura del cielo de fondo
     */
    public String getArchiSky()
    {
	return archiSky;
    }

    /**
     * @return Archivo con las textura de animacion del movimiento regular del
     *         player
     */
    public String getArchiPlayer()
    {
	return archiPlayer;
    }

    /**
     * @return Archivo con las texturas de animaion de celda siendo picada
     */
    public String getArchiPickingCell()
    {
	return archiPickingCell;
    }

    /**
     * @return Archivo con las animaciones de los items recolectables (joyas, picos,
     *         dagas)
     */
    public String getArchiCollectables()
    {
	return archiCollectables;
    }

    /**
     * @return Archivo con las texturas de las puertas giratorias (un solo tile)
     */
    public String getArchiGiratory()
    {
	return archiGiratory;
    }

    /**
     * @return Archivo con las texturas de las anumaciones de las momias
     */
    public String getArchiMummys()
    {
	return archiMummys;
    }

    /**
     * @return cantidad de frames de la animacion iddle de las momias
     */
    public int getMummyCountIddle()
    {
	return mummyCountIddle;
    }

    /**
     * @return cantidad de frames de la animacion de caida de las momias
     */
    public int getMummyCountFall()
    {
	return mummyCountFall;
    }

    /**
     * @return cantidad de frames de la animacion de caminata de las momias
     */
    public int getMummyCountWalk()
    {
	return mummyCountWalk;
    }

    /**
     * @return cantidad de frames de la puerta giratoria
     */
    public int getGiratoryCount()
    {
	return giratoryCount;
    }

    /**
     * @return cantidad de frames de la animacion iddle del player
     */
    public int getPlayerCountIddle()
    {
	return playerCountIddle;
    }

    /**
     * @return cantidad de frames de la animacion de caida del player
     */

    public int getPlayerCountFall()
    {
	return playerCountFall;
    }

    /**
     * @return cantidad de frames de la animacion de caminata del player
     */

    public int getPlayerCountWalk()
    {
	return playerCountWalk;
    }

    /**
     * @return cantidad de frames de la animacion de muerte del player
     */

    public int getPlayerCountDeath()
    {
	return playerCountDeath;
    }

    /**
     * @return cantidad de frames de la animacion de cada Collectable
     */
    public int getCollectableCount()
    {
	return collectableCount;
    }

    /**
     * @return Duracion de cada frame por defecto
     */
    public float getFrameDuration()
    {
	return frameDuration;
    }

    /**
     * @return cantidad de frames de la animacion de aparicion de las momias
     */
    public int getMummyCountAppear()
    {
	return mummyCountAppear;
    }

    /**
     * @return cantidad de frames de la animacion de muerte de las momias
     */
    public int getMummyCountDeath()
    {
	return mummyCountDeath;
    }

    /**
     * @return cantidad de frames de la animacion iddle del player cuando porta un
     *         pico
     */
    public int getPlayerPickerCountIddle()
    {
	return playerPickerCountIddle;
    }

    /**
     * @return cantidad de frames de la animacion de caida del player cuando porta
     *         un pico
     */
    public int getPlayerPickerCountFall()
    {
	return playerPickerCountFall;
    }

    /**
     * @return cantidad de frames de la animacion de caminata del player cuando
     *         porta un pico
     */
    public int getPlayerPickerCountWalk()
    {
	return playerPickerCountWalk;
    }

    /**
     * @return cantidad de frames de la animacion de estar picando del player
     */
    public int getPlayerPickerCountPicking()
    {
	return playerPickerCountPicking;
    }

    /**
     * @return cantidad de frames de la animacion iddle del player cuando porta una
     *         espada
     */
    public int getPlayerDaggerCountIddle()
    {
	return playerDaggerCountIddle;
    }

    /**
     * @return cantidad de frames de la animacion de caida del player cuando porta
     *         una espada
     */
    public int getPlayerDaggerCountFall()
    {
	return playerDaggerCountFall;
    }

    /**
     * @return cantidad de frames de la animacion de caminata del player cuando
     *         porta una espada
     */
    public int getPlayerDaggerCountWalk()
    {
	return playerDaggerCountWalk;
    }

    /**
     * @return cantidad de frames de la animacion de lanzar una espada del player
     */
    public int getPlayerDaggerCountThrowing()
    {
	return playerDaggerCountThrowing;
    }

    /**
     * @return Archivo con las texturas de la daga volando
     */
    public String getArchiFlyingDagger()
    {
	return archiFlyingDagger;
    }

    /**
     * @return cantidad de frames de la animacion de la daga volando
     */
    protected int getFlyingDaggerCount()
    {
	return flyingDaggerCount;
    }

    /**
     * @return cantidad de frames de la animacion de la celda siendo picada
     */
    protected int getPickingCellCount()
    {
	return pickingCellCount;
    }

    /**
     * @return Archivo con la textura del pasage de las puertas de entrada / salida
     */
    public String getArchiDoorPassage()
    {
	return archiDoorPassage;
    }

    /**
     * @return Archivo con la textura de la hoja izquierda de una puerta
     */
    public String getArchiDoorLeft()
    {
	return archiDoorLeft;
    }

    /**
     * @return Archivo con la textura de la hoja derecha de una puerta
     */

    public String getArchiDoorRight()
    {
	return archiDoorRight;
    }

    /**
     * @return Archivo con la animacion de la palanca de una puerta
     */
    public String getArchiDoorLever()
    {
	return archiDoorLever;
    }

    /**
     * @return Cantidad de frames de la animacion de la palanca de una puerta
     */
    public int getDoorLeverCount()
    {
	return doorLeverCount;
    }

    /**
     * @return Cantidad de frames de la animacion de salto del player
     */
    protected int getPlayerCountJump()
    {
	return playerCountJump;
    }

    /**
     * @return Archivo con las texturas de las animaciones especiales del player
     *         (picando y lanzando espadas)
     */
    public String getArchiPlayerSpecial()
    {
	return archiPlayerSpecial;
    }

    /**
     * @return Cantidad de frames de la animacion de salto de las momias
     */
    public int getMummyCountJump()
    {
	return mummyCountJump;
    }

}
