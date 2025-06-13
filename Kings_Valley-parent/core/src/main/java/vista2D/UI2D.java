package vista2D;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import controler.Controler;
import controler.IView;
import util.GameConfig;

public class UI2D implements IView, ApplicationListener
{
    private String backgroundFile = "ui/background.jpg";
    private String skinFile = "skin/golden-ui-skin.json";
    private Texture backgroundText;
    private Image background;
    private Skin skin;
    private Stage stage;
    private Label outputLabel;
    protected BitmapFont fontLarge = new BitmapFont();
    private BitmapFont fontSmall = new BitmapFont();
    private FreeTypeFontGenerator fontGenerator;
    private final String fontSmallName = "fontSmall";
    private final String fontLargeName = "fontLarge";

    public UI2D(AssetManager manager, FreeTypeFontGenerator fontGenerator)
    {
	this.fontGenerator = fontGenerator;
	manager.load(this.backgroundFile, Texture.class);
	manager.load(this.skinFile, Skin.class);
	manager.finishLoading();
	this.backgroundText = manager.get(this.backgroundFile, Texture.class);
	this.background = new Image(backgroundText);
	this.skin = manager.get(this.skinFile, Skin.class);

    }

    @Override
    public void create()
    {
	stage = new Stage(new ScreenViewport());
	Gdx.input.setInputProcessor(stage);
	this.prepareFonts();
	// this.background.setFillParent(true); // Se adapta al tamaño del stage
	this.calulateBackground(stage.getWidth(), stage.getHeight());

	stage.addActor(background); // ¡Agregarlo antes que todo lo demás!
	Table table = new Table();
	table.setFillParent(true);
	table.top();
	stage.addActor(table);

	int row_height = Gdx.graphics.getWidth() / 12;
	int col_width = Gdx.graphics.getWidth() / 12;

	Label.LabelStyle labelStyle = new Label.LabelStyle();
	labelStyle.font = skin.getFont(this.fontLargeName);

	skin.add("miLabelStyle", labelStyle);

	Label title = new Label("Kings Valley Remake", skin, "miLabelStyle");
	title.setSize(Gdx.graphics.getWidth(), row_height * 2);
	title.setPosition(0, Gdx.graphics.getHeight() - row_height * 2);
	title.setAlignment(Align.center);

	// Button
	Button button1 = new Button(skin);
	button1.setSize(col_width * 4, row_height);
	button1.setPosition(col_width, Gdx.graphics.getHeight() - row_height * 3);
	button1.addListener(new InputListener()
	{
	    @Override
	    public void touchUp(InputEvent event, float x, float y, int pointer, int button)
	    {
		outputLabel.setText("Press a Button");
	    }

	    @Override
	    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
	    {
		outputLabel.setText("Pressed Button");
		return true;
	    }
	});

	// Text Button
	// TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
	TextButton.TextButtonStyle buttonStyle = skin.get("default", TextButton.TextButtonStyle.class);

	buttonStyle.font = skin.getFont(this.fontSmallName);
	// skin.add("buttonStyle", buttonStyle);

	Button button2 = new TextButton("Text Button lalala lolo uololo", skin, "default");
	button2.setSize(col_width * 4, row_height);
	button2.setPosition(col_width * 7, Gdx.graphics.getHeight() - row_height * 3);
	button2.addListener(new InputListener()
	{
	    @Override
	    public void touchUp(InputEvent event, float x, float y, int pointer, int button)
	    {
		outputLabel.setText("Press a Button");
	    }

	    @Override
	    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
	    {
		outputLabel.setText("Pressed Text Button");
		return true;
	    }
	});

	// ImageButton
	ImageButton button3 = new ImageButton(skin);
	button3.setSize(col_width * 4, (float) (row_height * 2));
	/*
	 * button3.getStyle().imageUp = new TextureRegionDrawable( new TextureRegion(new
	 * Texture(Gdx.files.internal("switch_off.png")))); button3.getStyle().imageDown
	 * = new TextureRegionDrawable( new TextureRegion(new
	 * Texture(Gdx.files.internal("switch_on.png"))));
	 */
	button3.setPosition(col_width, Gdx.graphics.getHeight() - row_height * 6);
	button3.addListener(new InputListener()
	{
	    @Override
	    public void touchUp(InputEvent event, float x, float y, int pointer, int button)
	    {
		outputLabel.setText("Press a Button");
	    }

	    @Override
	    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
	    {
		outputLabel.setText("Pressed Image Button");
		return true;
	    }
	});

	// ImageTextButton
	ImageTextButton button4 = new ImageTextButton("ImageText Btn", skin);
	button4.setSize(col_width * 4, (float) (row_height * 2));
	/*
	 * button4.getStyle().imageUp = new TextureRegionDrawable( new TextureRegion(new
	 * Texture(Gdx.files.internal("switch_off.png")))); button4.getStyle().imageDown
	 * = new TextureRegionDrawable( new TextureRegion(new
	 * Texture(Gdx.files.internal("switch_on.png"))));
	 */
	button4.setPosition(col_width * 7, Gdx.graphics.getHeight() - row_height * 6);
	button4.addListener(new InputListener()
	{
	    @Override
	    public void touchUp(InputEvent event, float x, float y, int pointer, int button)
	    {
		outputLabel.setText("Press a Button");
	    }

	    @Override
	    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
	    {
		outputLabel.setText("Pressed Image Text Button");
		return true;
	    }
	});

	outputLabel = new Label("Press a Button", skin);
	outputLabel.setSize(Gdx.graphics.getWidth(), row_height);
	outputLabel.setPosition(0, row_height);
	outputLabel.setAlignment(Align.center);

	// Fila del título, centrado arriba
	title.setAlignment(Align.center);

	table.add(title).colspan(3).expandX().fillX().pad(20).row();

	table.row();

	// Botones alineados a la izquierda
/*
	table.add(button1).uniformX().fillX().expandY().pad(10).left();
	
	table.row();
	table.add(button2).uniformX().fillX().expandY().pad(10).left();
	
	table.row();
	table.add(button3).uniformX().fillX().expandY().pad(10).left();
	
	table.row();
	table.add(button4).uniformX().fillX().expandY().pad(10).left();
	
	table.row();
	table.add(outputLabel).left().pad(10).row();
*/
	
	table.add(button1).size(400, 100).expandY().pad(10).left();
	
	table.row();
	
	table.add(button2).expandY().pad(10).left();
	
	table.row();
	table.add(button3).expandY().pad(10).left();
	
	table.row();
	table.add(button4).expandY().pad(10).left();
	
	table.row();

    
    
    }

    private void prepareFonts()
    {
	int fontSize = (int) (Gdx.graphics.getHeight() * (1.f / 12.f));

	FreeTypeFontGenerator.FreeTypeFontParameter parameterLarge = new FreeTypeFontGenerator.FreeTypeFontParameter();
	parameterLarge.size = fontSize; // Tamaño de fuente
	parameterLarge.color = Color.GOLD;
	parameterLarge.borderColor = Color.BLACK;
	fontLarge = fontGenerator.generateFont(parameterLarge);

	FreeTypeFontGenerator.FreeTypeFontParameter parameterSmall = new FreeTypeFontGenerator.FreeTypeFontParameter();
	parameterSmall.size = (int) (fontSize * .4); // Tamaño de fuente
	parameterSmall.color = Color.WHITE;
	parameterSmall.borderColor = Color.BLACK;
	parameterSmall.borderWidth = 1;

	fontSmall = fontGenerator.generateFont(parameterSmall);

	if (skin.has(this.fontSmallName, BitmapFont.class))
	{
	    skin.getFont(this.fontSmallName).dispose();
	    skin.remove(this.fontSmallName, BitmapFont.class);
	}
	skin.add(this.fontSmallName, this.fontSmallName);

	// Hay que actualizar los estilos

	skin.add(this.fontSmallName, fontSmall);
	skin.add(this.fontLargeName, fontLarge);

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

    }

    @Override
    public void render()
    {
	Gdx.gl.glClearColor(0, 0, 0, 1);
	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	stage.act();
	stage.draw();

    }

    @Override
    public void resize(int width, int height)
    {
	stage.getViewport().update(width, height, true);
	this.calulateBackground(width, height);
    }

    @Override
    public void pause()
    {
	// TODO Auto-generated method stub

    }

    @Override
    public void resume()
    {
	// TODO Auto-generated method stub

    }

    private void calulateBackground(float width, float height)
    {

	float imageRatio = background.getWidth() / (float) background.getHeight();
	float stageRatio = width / stage.getHeight();

	if (imageRatio < stageRatio)
	{
	    background.setWidth(width);
	    background.setHeight(width / imageRatio);
	} else
	{
	    background.setHeight(height);
	    background.setWidth(height * imageRatio);
	}

	background.setPosition((width - background.getWidth()) / 2f, (height - background.getHeight()) / 2f);

    }
}
