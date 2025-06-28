package audio;

import java.util.HashMap;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

import facade.Facade;
import modelo.KVEventListener;
import modelo.game.Game;

public class AudioManager implements KVEventListener
{
    private final static String CONFIG_AUDIO_FILE = "audio_config.json";
    private static final Json json = new Json();

    private Music musicMain;
    private AssetManager manager;
    private AudioConfig audioConfig;
    private HashMap<Integer, Sound> hashMapSounds = new HashMap<Integer, Sound>();
    private Sound[] mummyDeathSounds = new Sound[3];
    private Sound[] playerDeathSounds = new Sound[5];
    private Sound[] throwSwordSounds = new Sound[5];
    private Sound[] doorOpenCloseSounds = new Sound[3];
    private static final Random random = new Random();

    private static void saveConfig(AudioConfig config)
    {
	FileHandle file = Gdx.files.local(CONFIG_AUDIO_FILE);
	json.setUsePrototypes(false);
	file.writeString(json.prettyPrint(config), false);
    }

    private static AudioConfig loadConfig()
    {
	FileHandle file = Gdx.files.local(CONFIG_AUDIO_FILE);
	if (file.exists())
	{
	    return json.fromJson(AudioConfig.class, file);
	}
	return new AudioConfig(); // Valores por defecto
    }

    public AudioManager(AssetManager manager)
    {
	super();
	this.manager = manager;

	this.audioConfig = AudioManager.loadConfig();
	// saveConfig(audioConfig);
	this.manager.load(audioConfig.getMainMusicFile(), Music.class);
	this.manager.load(audioConfig.getDoorOpenClose1File(), Sound.class);
	this.manager.load(audioConfig.getDoorOpenClose2File(), Sound.class);
	this.manager.load(audioConfig.getDoorOpenClose3File(), Sound.class);
	this.manager.load(audioConfig.getGiratoryFile(), Sound.class);
	this.manager.load(audioConfig.getMummyAppearFile(), Sound.class);
	this.manager.load(audioConfig.getMummyDeath1File(), Sound.class);
	this.manager.load(audioConfig.getMummyDeath2File(), Sound.class);
	this.manager.load(audioConfig.getMummyDeath3File(), Sound.class);
	this.manager.load(audioConfig.getPickingFile(), Sound.class);
	this.manager.load(audioConfig.getPickupJewelFile(), Sound.class);
	this.manager.load(audioConfig.getPickupPickerFile(), Sound.class);
	this.manager.load(audioConfig.getPickupSwordFile(), Sound.class);
	this.manager.load(audioConfig.getPlayerDeath1File(), Sound.class);
	this.manager.load(audioConfig.getPlayerDeath2File(), Sound.class);
	this.manager.load(audioConfig.getPlayerDeath3File(), Sound.class);
	this.manager.load(audioConfig.getPlayerDeath4File(), Sound.class);
	this.manager.load(audioConfig.getPlayerDeath5File(), Sound.class);

	this.manager.load(audioConfig.getPlayerJumpFile(), Sound.class);
	this.manager.load(audioConfig.getSwordClashFleshFile(), Sound.class);
	this.manager.load(audioConfig.getSwordClashFile(), Sound.class);
	this.manager.load(audioConfig.getSwordStuckFile(), Sound.class);
	this.manager.load(audioConfig.getSwordThrow1File(), Sound.class);
	this.manager.load(audioConfig.getSwordThrow2File(), Sound.class);
	this.manager.load(audioConfig.getSwordThrow3File(), Sound.class);
	this.manager.load(audioConfig.getSwordThrow4File(), Sound.class);
	this.manager.load(audioConfig.getSwordThrow5File(), Sound.class);
	this.manager.load(audioConfig.getTrapMechanismActivate(), Sound.class);

    }

    public void create()
    {
	this.manager.finishLoading();
	this.musicMain = this.manager.get(audioConfig.getMainMusicFile(), Music.class);

	this.hashMapSounds.put(KVEventListener.ENTER_GIRATORY,
		this.manager.get(audioConfig.getGiratoryFile(), Sound.class));
	this.hashMapSounds.put(KVEventListener.MUMMY_APPEAR,
		this.manager.get(audioConfig.getMummyAppearFile(), Sound.class));
	this.hashMapSounds.put(KVEventListener.PLAYER_PICKING,
		this.manager.get(audioConfig.getPickingFile(), Sound.class));
	this.hashMapSounds.put(KVEventListener.PICKUP_PICKER,
		this.manager.get(audioConfig.getPickupPickerFile(), Sound.class));
	this.hashMapSounds.put(KVEventListener.PICKUP_DAGGER,
		this.manager.get(audioConfig.getPickupSwordFile(), Sound.class));
	this.hashMapSounds.put(KVEventListener.PICKUP_JEWEL,
		this.manager.get(audioConfig.getPickupJewelFile(), Sound.class));
	this.hashMapSounds.put(KVEventListener.PLAYER_JUMP,
		this.manager.get(audioConfig.getPlayerJumpFile(), Sound.class));
	this.hashMapSounds.put(KVEventListener.SWORD_CLASH,
		this.manager.get(audioConfig.getSwordClashFile(), Sound.class));
	this.hashMapSounds.put(KVEventListener.SWORD_CLASH_FLESH,
		this.manager.get(audioConfig.getSwordClashFleshFile(), Sound.class));
	this.hashMapSounds.put(KVEventListener.SWORD_STUCK,
		this.manager.get(audioConfig.getSwordStuckFile(), Sound.class));

	this.hashMapSounds.put(KVEventListener.ACTIVATE_TRAP,
		this.manager.get(audioConfig.getTrapMechanismActivate(), Sound.class));

	this.doorOpenCloseSounds[0] = this.manager.get(audioConfig.getDoorOpenClose1File(), Sound.class);
	this.doorOpenCloseSounds[1] = this.manager.get(audioConfig.getDoorOpenClose2File(), Sound.class);
	this.doorOpenCloseSounds[2] = this.manager.get(audioConfig.getDoorOpenClose3File(), Sound.class);

	this.mummyDeathSounds[0] = this.manager.get(audioConfig.getMummyDeath1File(), Sound.class);

	this.mummyDeathSounds[1] = this.manager.get(audioConfig.getMummyDeath2File(), Sound.class);
	this.mummyDeathSounds[2] = this.manager.get(audioConfig.getMummyDeath3File(), Sound.class);

	this.playerDeathSounds[0] = this.manager.get(audioConfig.getPlayerDeath1File(), Sound.class);
	this.playerDeathSounds[1] = this.manager.get(audioConfig.getPlayerDeath2File(), Sound.class);
	this.playerDeathSounds[2] = this.manager.get(audioConfig.getPlayerDeath3File(), Sound.class);
	this.playerDeathSounds[3] = this.manager.get(audioConfig.getPlayerDeath4File(), Sound.class);
	this.playerDeathSounds[4] = this.manager.get(audioConfig.getPlayerDeath5File(), Sound.class);

	this.throwSwordSounds[0] = this.manager.get(audioConfig.getSwordThrow1File(), Sound.class);
	this.throwSwordSounds[1] = this.manager.get(audioConfig.getSwordThrow2File(), Sound.class);
	this.throwSwordSounds[2] = this.manager.get(audioConfig.getSwordThrow3File(), Sound.class);
	this.throwSwordSounds[3] = this.manager.get(audioConfig.getSwordThrow4File(), Sound.class);
	this.throwSwordSounds[4] = this.manager.get(audioConfig.getSwordThrow5File(), Sound.class);

    }

    @Override
    public void eventFired(int eventCode, Object param)
    {
	Sound sound = (this.hashMapSounds.get(eventCode));
	if (sound != null)
	    sound.play(Game.getInstance().getGameConfig().getMasterVolume()
		    * Game.getInstance().getGameConfig().getSoundsVolume());

	if (eventCode == KVEventListener.THROW_DAGGER)
	{
	    int i = random.nextInt(this.throwSwordSounds.length);
	    this.throwSwordSounds[i].play(Game.getInstance().getGameConfig().getMasterVolume()
		    * Game.getInstance().getGameConfig().getSoundsVolume());
	}

	if (eventCode == KVEventListener.THROW_DAGGER)
	{
	    this.playRandomSound(throwSwordSounds);
	}

	if (eventCode == KVEventListener.PLAYER_DIE)
	{
	    this.playRandomSound(this.playerDeathSounds);
	}

	if (eventCode == KVEventListener.MUMMY_KILLED_BY_SWORD)
	{
	    this.playRandomSound(this.mummyDeathSounds);

	}

	if (eventCode == KVEventListener.OPEN_DOOR || eventCode == KVEventListener.CLOSE_DOOR)
	{
	    this.playRandomSound(this.doorOpenCloseSounds);
	}

	switch (eventCode)
	{
	case KVEventListener.FINISH_ALL_LEVELS:

	    break;

	case KVEventListener.MUMMY_KILLED_BY_SWORD:

	    break;

	case KVEventListener.FINISH_CURRENT_LEVEL:

	    break;
	case KVEventListener.ENTER_LEVEL:
	    this.musicMain.setVolume(Game.getInstance().getGameConfig().getMasterVolume()
		    * Game.getInstance().getGameConfig().getMusicVolume());
	    this.musicMain.setLooping(true);
	    this.musicMain.play();

	}

    }

    private void playRandomSound(Sound[] arrayOfSound)
    {
	int i = random.nextInt(arrayOfSound.length);
	arrayOfSound[i].play(Game.getInstance().getGameConfig().getMasterVolume()
		* Game.getInstance().getGameConfig().getSoundsVolume());
    }

    public void updateMusicVolume()
    {

	this.musicMain.setVolume(Facade.getInstance().getGameConfig().getMasterVolume()
		* Facade.getInstance().getGameConfig().getMusicVolume());

    }
}
