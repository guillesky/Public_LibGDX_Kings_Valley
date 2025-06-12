package vista2D;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import controler.Controler;
import controler.IView;
import util.GameConfig;

public class UI2D implements IView
{
	private String backgroundFile = "Luxor-and-the-Valley-of-the-Kings.jpg";
	private String skinFile = "skin/golden-ui-skin.json";
	private Texture backgroundText;
	private Image background; 
	private Skin skin;
	private Stage stage;
	

	public UI2D(AssetManager manager)
	{
		manager.load(this.backgroundFile, Texture.class);
		manager.load(this.skinFile, Skin.class);
		manager.finishLoading();

		this.backgroundText = manager.get(this.backgroundFile, Texture.class);
		this.background=new Image(backgroundText);
		this.skin = manager.get(this.skinFile, Skin.class);
		this.init();
	}

	private void init()
	{
		this.background.setFillParent(true); // Se adapta al tamaño del stage

		
		stage.addActor(background); // ¡Agregarlo antes que todo lo demás!
		
	}

	@Override
	public void updateCredits(String credits)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void updateGameConfig(GameConfig gameConfig)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public int getDificultLevel()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getLanguage()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float getMasterVolume()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getMusicVolume()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getSoundsVolume()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setControler(Controler Controler)
	{
		// TODO Auto-generated method stub

	}

	public void dispose()
	{
		this.backgroundText.dispose();
		this.skin.dispose();
		
	}

}
