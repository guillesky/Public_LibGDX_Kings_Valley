package mainPackage;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import util.Constants;
import util.Utils;

/**
 * 
 * NO USADA DURANTE EL JUEGO. Funcionalidad escrita para desarrollo. Permite
 * verificar la intergridad de los niveles
 * 
 * @author Guillermo Lazzurri
 */
public class LevelChecker extends ApplicationAdapter
{

	@Override
	public void create()
	{
		TmxMapLoader mapLoader = new TmxMapLoader();
		
		for (String archi : Constants.extendedLevelFileName.values())
		{
			System.out.println(archi);
			TiledMap map= mapLoader.load(archi);
			
			
		}
		Gdx.app.exit();
	}

	

}
