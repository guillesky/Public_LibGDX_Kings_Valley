package io.github.some_example_name;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import modelo.game.Game;

public class MapCreator extends ApplicationAdapter
{
    private AssetManager manager = new AssetManager();
    private String mapFile = "ui/baseMap.png";
    private String piramydActualFile = "ui/actualPyramid.png";
    private String piramydCompletedFile = "ui/completedPyramid.png";

    @Override
    public void create()
    {

	this.manager.load(mapFile, Texture.class);
	this.manager.load(piramydActualFile, Texture.class);
	this.manager.load(piramydCompletedFile, Texture.class);
	this.manager.finishLoading();
	Pixmap r=this.createMap();
	// Cierra la aplicación después de crear la imagen
	FileHandle output = Gdx.files.local("output/merged.png");
	PixmapIO.writePNG(output, r);
	r.dispose();
	
	Gdx.app.exit();
    }

    private Pixmap createMap()
    {
	Pixmap result;

	Pixmap actualPiramidPixmap;
	Pixmap completedPiramidPixmap;

	Texture mapText = manager.get(this.mapFile, Texture.class);
	mapText.getTextureData().prepare();
	result = mapText.getTextureData().consumePixmap();

	Texture actualPyramidText = manager.get(this.piramydActualFile, Texture.class);
	actualPyramidText.getTextureData().prepare();
	actualPiramidPixmap = actualPyramidText.getTextureData().consumePixmap();

	Texture completedPiramidText = manager.get(this.piramydCompletedFile, Texture.class);
	completedPiramidText.getTextureData().prepare();
	completedPiramidPixmap = completedPiramidText.getTextureData().consumePixmap();

	int width = mapText.getWidth() / 6;
	int height = mapText.getHeight() / 6;

	HashMap<Integer, Boolean> completedLevels =new HashMap<Integer, Boolean>();
	
	for (int i=1;i<16;i++)
	    completedLevels.put(i, true);
	
	for (int i=16;i<=16;i++)
	    completedLevels.put(i, false);
	
	
	int currentLevel = 16;

	int p = 1;
	int w = 1;
	int h = 1;
	int direction = 1;

	while (p <= 16)
	{
	    if (p == currentLevel)
		result.drawPixmap(actualPiramidPixmap, w * width, h * height);
	    if (completedLevels.get(p))
		result.drawPixmap(completedPiramidPixmap, w * width, h * height);
	    p++;
	    w += direction;
	    if (w == 0 || w == 5)
	    {
		h++;
		direction *= -1;
		w+=direction;

	    }
	}
	//result.dispose();
	completedPiramidPixmap.dispose();
	actualPiramidPixmap.dispose();
	return result;
    }
}
