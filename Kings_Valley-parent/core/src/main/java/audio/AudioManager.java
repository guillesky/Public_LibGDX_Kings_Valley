package audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

import vista2D.GraphicsFileConfig;

public class AudioManager
{   private final static String CONFIG_AUDIO_FILE = "audio_config.json";
    private static final Json json = new Json();

    
   
    private Music musicMain;
    private AssetManager manager;
    private AudioConfig audioConfig;
    
    
    
    
    
    
    private  static void saveConfig(AudioConfig config)
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
	
	this.audioConfig=AudioManager.loadConfig();
	
	this.manager.load(audioConfig.getMainMusicFile(), Music.class);
	
    }
    
    public void create() 
    {
	this.manager.finishLoading();
	this.musicMain=this.manager.get(audioConfig.getMainMusicFile(), Music.class);
	
    }

   
    
    
    
    
   

}
