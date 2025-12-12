package audio;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

import engine.KVEventListener;
import engine.game.Game;

/**
 * Clase que maneja el audio durante el juego. Implementa la interfaz
 * KVEventListener
 * 
 * @author Guillermo Lazzurri
 */
public class AudioManager implements KVEventListener
{
    private final static String CONFIG_AUDIO_FILE = "audio_config.json";
    private static final Json json = new Json();

    private Music musicMain;
    private AssetManager assetManager;
    private AudioConfig audioConfig;
    private HashMap<Integer, Sound> hashMapSounds = new HashMap<Integer, Sound>();
    private Sound[] mummyDeathSounds = new Sound[3];
    private Sound[] playerDeathSounds = new Sound[5];
    private Sound[] throwSwordSounds = new Sound[5];
    private Sound[] doorOpenCloseSounds = new Sound[3];
    private boolean fadeOutMusic = false;

    private float volimueInitialForFade = 0;
    private boolean initFade = true;

    /**
     * @param config Graba el archivo de configuracion de audio en audio_config.json
     */
    private static void saveConfig(AudioConfig config)
    {
	FileHandle file = Gdx.files.local(CONFIG_AUDIO_FILE);
	json.setUsePrototypes(false);
	file.writeString(json.prettyPrint(config), false);
    }

    /**
     * @return el objecto de AudioConfig a partir del archivo de audio_config.json
     */
    private static AudioConfig loadConfig()
    {
	FileHandle file = Gdx.files.local(CONFIG_AUDIO_FILE);
	if (file.exists())
	{
	    return json.fromJson(AudioConfig.class, file);
	}
	return new AudioConfig();
    }

    /**
     * Retorna el AssetManager para gestionar la carga de los archivos de audio.
     * 
     * @param assetManager Objecto de tipo AssetManager para gestionar la carga de
     *                     los archivos de audio.
     */
    public AudioManager(AssetManager assetManager)
    {
	super();
	this.assetManager = assetManager;

	this.audioConfig = AudioManager.loadConfig();
	// saveConfig(audioConfig);
	this.assetManager.load(audioConfig.getMainMusicFile(), Music.class);
	this.assetManager.load(audioConfig.getDoorOpenClose1File(), Sound.class);
	this.assetManager.load(audioConfig.getDoorOpenClose2File(), Sound.class);
	this.assetManager.load(audioConfig.getDoorOpenClose3File(), Sound.class);
	this.assetManager.load(audioConfig.getDoorEnteringFile(), Sound.class);

	this.assetManager.load(audioConfig.getGiratoryFile(), Sound.class);
	this.assetManager.load(audioConfig.getMummyAppearFile(), Sound.class);
	this.assetManager.load(audioConfig.getMummyDeath1File(), Sound.class);
	this.assetManager.load(audioConfig.getMummyDeath2File(), Sound.class);
	this.assetManager.load(audioConfig.getMummyDeath3File(), Sound.class);
	this.assetManager.load(audioConfig.getPickingFile(), Sound.class);
	this.assetManager.load(audioConfig.getPickupJewelFile(), Sound.class);
	this.assetManager.load(audioConfig.getPickupPickerFile(), Sound.class);
	this.assetManager.load(audioConfig.getPickupSwordFile(), Sound.class);
	this.assetManager.load(audioConfig.getPlayerDeath1File(), Sound.class);
	this.assetManager.load(audioConfig.getPlayerDeath2File(), Sound.class);
	this.assetManager.load(audioConfig.getPlayerDeath3File(), Sound.class);
	this.assetManager.load(audioConfig.getPlayerDeath4File(), Sound.class);
	this.assetManager.load(audioConfig.getPlayerDeath5File(), Sound.class);

	this.assetManager.load(audioConfig.getPlayerJumpFile(), Sound.class);
	this.assetManager.load(audioConfig.getSwordClashFleshFile(), Sound.class);
	this.assetManager.load(audioConfig.getSwordClashFile(), Sound.class);
	this.assetManager.load(audioConfig.getSwordStuckFile(), Sound.class);
	this.assetManager.load(audioConfig.getSwordThrow1File(), Sound.class);
	this.assetManager.load(audioConfig.getSwordThrow2File(), Sound.class);
	this.assetManager.load(audioConfig.getSwordThrow3File(), Sound.class);
	this.assetManager.load(audioConfig.getSwordThrow4File(), Sound.class);
	this.assetManager.load(audioConfig.getSwordThrow5File(), Sound.class);
	this.assetManager.load(audioConfig.getTrapMechanismActivate(), Sound.class);
	this.assetManager.load(audioConfig.getExtraLifeFile(), Sound.class);
    }

    /**
     * metodo que inicializa los atributos a partir de los archivos de audio. Llama
     * al metodo finishLoading() del AssetManager
     */
    public void create()
    {
	this.assetManager.finishLoading();
	this.musicMain = this.assetManager.get(audioConfig.getMainMusicFile(), Music.class);

	this.hashMapSounds.put(KVEventListener.ENTER_GIRATORY,
		this.assetManager.get(audioConfig.getGiratoryFile(), Sound.class));
	this.hashMapSounds.put(KVEventListener.MUMMY_APPEAR,
		this.assetManager.get(audioConfig.getMummyAppearFile(), Sound.class));
	this.hashMapSounds.put(KVEventListener.PLAYER_PICKING,
		this.assetManager.get(audioConfig.getPickingFile(), Sound.class));
	this.hashMapSounds.put(KVEventListener.PICKUP_PICKER,
		this.assetManager.get(audioConfig.getPickupPickerFile(), Sound.class));
	this.hashMapSounds.put(KVEventListener.PICKUP_DAGGER,
		this.assetManager.get(audioConfig.getPickupSwordFile(), Sound.class));
	this.hashMapSounds.put(KVEventListener.PICKUP_JEWEL,
		this.assetManager.get(audioConfig.getPickupJewelFile(), Sound.class));
	this.hashMapSounds.put(KVEventListener.PLAYER_JUMP,
		this.assetManager.get(audioConfig.getPlayerJumpFile(), Sound.class));
	this.hashMapSounds.put(KVEventListener.SWORD_CLASH,
		this.assetManager.get(audioConfig.getSwordClashFile(), Sound.class));
	this.hashMapSounds.put(KVEventListener.SWORD_CLASH_FLESH,
		this.assetManager.get(audioConfig.getSwordClashFleshFile(), Sound.class));
	this.hashMapSounds.put(KVEventListener.SWORD_STUCK,
		this.assetManager.get(audioConfig.getSwordStuckFile(), Sound.class));

	this.hashMapSounds.put(KVEventListener.ENTERING_LEVEL,
		this.assetManager.get(audioConfig.getDoorEnteringFile(), Sound.class));

	this.hashMapSounds.put(KVEventListener.ACTIVATE_TRAP,
		this.assetManager.get(audioConfig.getTrapMechanismActivate(), Sound.class));
	this.hashMapSounds.put(KVEventListener.ADD_EXTRA_LIFE,
		this.assetManager.get(audioConfig.getExtraLifeFile(), Sound.class));

	this.doorOpenCloseSounds[0] = this.assetManager.get(audioConfig.getDoorOpenClose1File(), Sound.class);
	this.doorOpenCloseSounds[1] = this.assetManager.get(audioConfig.getDoorOpenClose2File(), Sound.class);
	this.doorOpenCloseSounds[2] = this.assetManager.get(audioConfig.getDoorOpenClose3File(), Sound.class);

	this.mummyDeathSounds[0] = this.assetManager.get(audioConfig.getMummyDeath1File(), Sound.class);

	this.mummyDeathSounds[1] = this.assetManager.get(audioConfig.getMummyDeath2File(), Sound.class);
	this.mummyDeathSounds[2] = this.assetManager.get(audioConfig.getMummyDeath3File(), Sound.class);

	this.playerDeathSounds[0] = this.assetManager.get(audioConfig.getPlayerDeath1File(), Sound.class);
	this.playerDeathSounds[1] = this.assetManager.get(audioConfig.getPlayerDeath2File(), Sound.class);
	this.playerDeathSounds[2] = this.assetManager.get(audioConfig.getPlayerDeath3File(), Sound.class);
	this.playerDeathSounds[3] = this.assetManager.get(audioConfig.getPlayerDeath4File(), Sound.class);
	this.playerDeathSounds[4] = this.assetManager.get(audioConfig.getPlayerDeath5File(), Sound.class);

	this.throwSwordSounds[0] = this.assetManager.get(audioConfig.getSwordThrow1File(), Sound.class);
	this.throwSwordSounds[1] = this.assetManager.get(audioConfig.getSwordThrow2File(), Sound.class);
	this.throwSwordSounds[2] = this.assetManager.get(audioConfig.getSwordThrow3File(), Sound.class);
	this.throwSwordSounds[3] = this.assetManager.get(audioConfig.getSwordThrow4File(), Sound.class);
	this.throwSwordSounds[4] = this.assetManager.get(audioConfig.getSwordThrow5File(), Sound.class);

    }

    /**
     * metodo declarado en la interfaz KVEventListener. Es invocado internamente por
     * el juego. De acuerdo al evento, reproduce el sonido correspondiente
     */
    @Override
    public void eventFired(int eventCode, Object param)
    {
	Sound sound = (this.hashMapSounds.get(eventCode));
	if (sound != null)
	    sound.play(Game.getInstance().getGameConfig().getMasterVolume()
		    * Game.getInstance().getGameConfig().getSoundsVolume());

	switch (eventCode)
	{

	case KVEventListener.EXITING_LEVEL:
	    this.playRandomSound(this.doorOpenCloseSounds);
	    this.fadeOutMusic = true;
	    break;

	case KVEventListener.ENTER_LEVEL:
	    this.endFade();
	    this.musicMain.setVolume(Game.getInstance().getGameConfig().getMasterVolume()
		    * Game.getInstance().getGameConfig().getMusicVolume());
	    this.musicMain.setLooping(true);
	    this.musicMain.play();
	    break;

	case KVEventListener.GAME_OVER:
	    this.musicMain.stop();
	    break;

	case KVEventListener.MUMMY_KILLED_BY_SWORD:
	    this.playRandomSound(this.mummyDeathSounds);

	    break;

	case KVEventListener.GAME_ENDING:
	    this.fadeOutMusic = true;
	    break;

	case KVEventListener.PLAYER_DIE:

	    this.playRandomSound(this.playerDeathSounds);
	    this.fadeOutMusic = true;
	    break;

	case KVEventListener.THROW_DAGGER:
	    this.playRandomSound(throwSwordSounds);
	    break;
	case KVEventListener.OPENING_DOOR:
	case KVEventListener.CLOSING_DOOR:

	    this.playRandomSound(this.doorOpenCloseSounds);
	    break;

	}

    }

    /**
     * @param arrayOfSound array de sonidos, es llamado internamente para reproducir
     *                     un sonido aleatorio de una coleccion
     */
    private void playRandomSound(Sound[] arrayOfSound)
    {
	int i = Game.random.nextInt(arrayOfSound.length);
	arrayOfSound[i].play(Game.getInstance().getGameConfig().getMasterVolume()
		* Game.getInstance().getGameConfig().getSoundsVolume());
    }

    /**
     * Actualiza el volumen de la musica con el volumen pasado por parametro
     * 
     * @param volumen valor que debe tomar el volumen de la musica
     */
    public void updateMusicVolume(float volumen)
    {

	this.musicMain.setVolume(volumen);

    }

    /**
     * metodo declarado en la interfaz KVEvenListener. Es llamado internamente por
     * el juego en cada frame. Se utiliza para manejar los fadeOuts de la musica en
     * caso de ser necesario
     */
    @Override
    public void updateframe(float deltaTime)
    {
	if (this.fadeOutMusic)
	{
	    if (this.initFade)
	    {
		this.volimueInitialForFade = this.musicMain.getVolume();
		this.initFade = false;
	    }

	    float timeToDie = Game.getInstance().getInterfaz().getTimeDying();
	    float factorFade = deltaTime / timeToDie;

	    float volume = this.musicMain.getVolume() - this.volimueInitialForFade * factorFade;
	    if (volume < 0)
	    {
		volume = 0;
		this.endFade();

	    }
	    this.musicMain.setVolume(volume);
	}

    }

    /**
     * Metodo que se ejecuta al finalizar el proceso de fadeOut
     */
    private void endFade()
    {
	this.musicMain.stop();
	this.fadeOutMusic = false;
	this.initFade = true;

    }
}
