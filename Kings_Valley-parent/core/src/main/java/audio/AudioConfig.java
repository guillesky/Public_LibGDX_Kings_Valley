package audio;

/**
 * Clase que contiene los getters de los parametros de configuracion del audio.
 * Los nombres de los archivos de audio correspondientes a efectos de sonido y
 * musica.
 * 
 * @author Guillermo Lazzurri
 */
public class AudioConfig
{

    private String mainMusicFile = "music/KV_MAIN.mp3";

    private String mummyAppearFile = "sfx/Mummy_Appear.wav";

    private String mummyDeath1File = "sfx/mummy_Death1.wav";
    private String mummyDeath2File = "sfx/mummy_Death2.wav";
    private String mummyDeath3File = "sfx/mummy_Death3.wav";

    private String pickingFile = "sfx/Picking.wav";
    private String pickupPickerFile = "sfx/Pickup_Picker.wav";
    private String pickupSwordFile = "sfx/Sword_Pickup.wav";
    private String pickupJewelFile = "sfx/Pickup_Gem.wav";

    private String swordThrow1File = "sfx/Sword_Throw1.wav";
    private String swordThrow2File = "sfx/Sword_Throw2.wav";
    private String swordThrow3File = "sfx/Sword_Throw3.wav";
    private String swordThrow4File = "sfx/Sword_Throw4.wav";
    private String swordThrow5File = "sfx/Sword_Throw5.wav";

    private String swordStuckFile = "sfx/Sword_Stuck.wav";
    private String swordClashFile = "sfx/Sword_Clash.wav";
    private String swordClashFleshFile = "sfx/Sword_Flesh.wav";

    private String playerDeath1File = "sfx/pl_Death1.wav";
    private String playerDeath2File = "sfx/pl_Death2.wav";
    private String playerDeath3File = "sfx/pl_Death3.wav";
    private String playerDeath4File = "sfx/pl_Death4.wav";
    private String playerDeath5File = "sfx/pl_Death5.wav";

    private String playerJumpFile = "sfx/Pl_Jump.wav";
    private String giratoryFile = "sfx/giratory.wav";

    private String doorOpenClose1File = "sfx/Door_OC1.wav";
    private String doorOpenClose2File = "sfx/Door_OC2.wav";
    private String doorOpenClose3File = "sfx/Door_OC3.wav";
    private String doorEnteringFile = "sfx/Door_Entering.wav";
    private String extraLifeFile = "sfx/extraLife.wav";

    private String trapMechanismActivate;

    /**
     * Retorna el nombre del archivo con la musica principal del juego
     * 
     * @return nombre del archivo con la musica principal del juego
     */
    public String getMainMusicFile()
    {
	return mainMusicFile;
    }

    /**
     * Retorna el nombre del archivo con el sonido de momia apareciendo
     * 
     * @return nombre del archivo con el sonido de momia apareciendo
     */

    public String getMummyAppearFile()
    {
	return mummyAppearFile;
    }

    /**
     * Retorna el nombre del archivo con el sonido de momia muriendo (opcion 1)
     * 
     * @return nombre del archivo con el sonido de momia muriendo (opcion 1)
     */
    public String getMummyDeath1File()
    {
	return mummyDeath1File;
    }

    /**
     * Retorna el nombre del archivo con el sonido de momia muriendo (opcion 2)
     * 
     * @return nombre del archivo con el sonido de momia muriendo (opcion 2)
     */

    public String getMummyDeath2File()
    {
	return mummyDeath2File;
    }

    /**
     * Retorna el nombre del archivo con el sonido de momia muriendo (opcion 3)
     * 
     * @return nombre del archivo con el sonido de momia muriendo (opcion 3)
     */

    public String getMummyDeath3File()
    {
	return mummyDeath3File;
    }

    /**
     * Retorna el nombre del archivo con el sonido de picar celdas
     * 
     * @return nombre del archivo con el sonido de picar celdas
     */

    public String getPickingFile()
    {
	return pickingFile;
    }

    /**
     * Retorna el nombre del archivo con el sonido de recolectar un pico
     * 
     * @return nombre del archivo con el sonido de recolectar un pico
     */

    public String getPickupPickerFile()
    {
	return pickupPickerFile;
    }

    /**
     * Retorna el nombre del archivo con el sonido de recolectar una espada
     * 
     * @return nombre del archivo con el sonido de recolectar una espada
     */
    public String getPickupSwordFile()
    {
	return pickupSwordFile;
    }

    /**
     * Retorna el nombre del archivo con el sonido de recolectar una gema
     * 
     * @return nombre del archivo con el sonido de recolectar una gema
     */
    public String getPickupJewelFile()
    {
	return pickupJewelFile;
    }

    /**
     * Retorna el nombre del archivo con el sonido de lanzar una espada (Opcion 1)
     * 
     * @return nombre del archivo con el sonido de lanzar una espada (Opcion 1)
     */
    public String getSwordThrow1File()
    {
	return swordThrow1File;
    }

    /**
     * Retorna el nombre del archivo con el sonido de lanzar una espada (Opcion 2)
     * 
     * @return nombre del archivo con el sonido de lanzar una espada (Opcion 2)
     */

    public String getSwordThrow2File()
    {
	return swordThrow2File;
    }

    /**
     * Retorna el nombre del archivo con el sonido de lanzar una espada (Opcion 3)
     * 
     * @return nombre del archivo con el sonido de lanzar una espada (Opcion 3)
     */

    public String getSwordThrow3File()
    {
	return swordThrow3File;
    }

    /**
     * Retorna el nombre del archivo con el sonido de lanzar una espada (Opcion 4)
     * 
     * @return nombre del archivo con el sonido de lanzar una espada (Opcion 4)
     */

    public String getSwordThrow4File()
    {
	return swordThrow4File;
    }

    /**
     * Retorna el nombre del archivo con el sonido de lanzar una espada (Opcion 5)
     * 
     * @return nombre del archivo con el sonido de lanzar una espada (Opcion 5)
     */

    public String getSwordThrow5File()
    {
	return swordThrow5File;
    }

    /**
     * Retorna el nombre del archivo con el sonido de la espada clavandose al piso
     * 
     * @return nombre del archivo con el sonido de la espada clavandose al piso
     */

    public String getSwordStuckFile()
    {
	return swordStuckFile;
    }

    /**
     * Retorna el nombre del archivo con el sonido de la espada golpeando la pared
     * 
     * @return nombre del archivo con el sonido de la espada golpeando la pared
     */
    public String getSwordClashFile()
    {
	return swordClashFile;
    }

    /**
     * Retorna el nombre del archivo con el sonido de la espada gopeando una momia
     * 
     * @return nombre del archivo con el sonido de la espada gopeando una momia
     */
    public String getSwordClashFleshFile()
    {
	return swordClashFleshFile;
    }

    /**
     * Retorna el nombre del archivo con el sonido del player muriendo (Opcion 1)
     * 
     * @return nombre del archivo con el sonido del player muriendo (Opcion 1)
     */
    public String getPlayerDeath1File()
    {
	return playerDeath1File;
    }

    /**
     * Retorna el nombre del archivo con el sonido del player muriendo (Opcion 2)
     * 
     * @return nombre del archivo con el sonido del player muriendo (Opcion 2)
     */
    public String getPlayerDeath2File()
    {
	return playerDeath2File;
    }

    /**
     * Retorna el nombre del archivo con el sonido del player muriendo (Opcion 3)
     * 
     * @return nombre del archivo con el sonido del player muriendo (Opcion 3)
     */
    public String getPlayerDeath3File()
    {
	return playerDeath3File;
    }

    /**
     * Retorna el nombre del archivo con el sonido del player muriendo (Opcion 4)
     * 
     * @return nombre del archivo con el sonido del player muriendo (Opcion 4)
     */
    public String getPlayerDeath4File()
    {
	return playerDeath4File;
    }

    /**
     * Retorna el nombre del archivo con el sonido del player muriendo (Opcion 5)
     * 
     * @return nombre del archivo con el sonido del player muriendo (Opcion 5)
     */
    public String getPlayerDeath5File()
    {
	return playerDeath5File;
    }

    /**
     * Retorna el nombre del archivo con el sonido del player saltando
     * 
     * @return nombre del archivo con el sonido del player saltando
     */
    public String getPlayerJumpFile()
    {
	return playerJumpFile;
    }

    /**
     * Retorna el nombre del archivo con el sonido del player activando una
     * giratoria
     * 
     * @return nombre del archivo con el sonido del player activando una giratoria
     */
    public String getGiratoryFile()
    {
	return giratoryFile;
    }

    /**
     * Retorna el nombre del archivo con el sonido del player activando un muro
     * trampa
     * 
     * @return nombre del archivo con el sonido del player activando un muro trampa
     */
    public String getTrapMechanismActivate()
    {
	return trapMechanismActivate;
    }

    /**
     * Retorna el nombre del archivo con el sonido de una puerta de entrada y salida
     * abriendo o cerrandose (Opcion 1)
     * 
     * @return nombre del archivo con el sonido de una puerta de entrada y salida
     *         abriendo o cerrandose (Opcion 1)
     */

    protected String getDoorOpenClose1File()
    {
	return doorOpenClose1File;
    }

    /**
     * Retorna el nombre del archivo con el sonido de una puerta de entrada y salida
     * abriendo o cerrandose (Opcion 2)
     * 
     * @return nombre del archivo con el sonido de una puerta de entrada y salida
     *         abriendo o cerrandose (Opcion 2)
     */

    protected String getDoorOpenClose2File()
    {
	return doorOpenClose2File;
    }

    /**
     * Retorna el nombre del archivo con el sonido de una puerta de entrada y salida
     * abriendo o cerrandose (Opcion 3)
     * 
     * @return nombre del archivo con el sonido de una puerta de entrada y salida
     *         abriendo o cerrandose (Opcion 3)
     */

    protected String getDoorOpenClose3File()
    {
	return doorOpenClose3File;
    }

    /**
     * Retorna el nombre del archivo con el sonido del player entrando al nivel
     * 
     * @return nombre del archivo con el sonido del player entrando al nivel
     */
    protected String getDoorEnteringFile()
    {
	return doorEnteringFile;
    }

    /**
     * Retorna el nombre del archivo con el sonido de obtener una vide extra
     * 
     * @return nombre del archivo con el sonido de obtener una vide extra
     */

    public String getExtraLifeFile()
    {
	return extraLifeFile;
    }

}
