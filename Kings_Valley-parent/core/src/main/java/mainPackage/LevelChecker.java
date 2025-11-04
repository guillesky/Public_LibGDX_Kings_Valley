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

	private void arrayDeImagenesIguales(ArrayList<FileHandle> pngFiles, boolean isHorizontal)
	{
		// Asumimos que todos los PNG tienen las mismas dimensiones
		Pixmap firstImage = new Pixmap(pngFiles.get(0));
		int singleWidth = firstImage.getWidth();
		int singleHeight = firstImage.getHeight();

		int totalDimension;
		Pixmap result;
		if (isHorizontal)
		{
			totalDimension = singleWidth * pngFiles.size();
			result = new Pixmap(totalDimension, singleHeight, Pixmap.Format.RGBA8888);
		} else
		{
			totalDimension = singleHeight * pngFiles.size();
			result = new Pixmap(singleWidth, totalDimension, Pixmap.Format.RGBA8888);
		}

		for (int i = 0; i < pngFiles.size(); i++)
		{
			System.out.println(pngFiles.get(i).name());
			Pixmap current = new Pixmap(pngFiles.get(i));

			if (isHorizontal)
				result.drawPixmap(current, i * singleWidth, 0);
			else
				result.drawPixmap(current, 0, i * singleHeight);
			current.dispose();
		}
		// Guardar la imagen final
		FileHandle output = Gdx.files.local("output/merged.png");
		PixmapIO.writePNG(output, result);
		result.dispose();
		firstImage.dispose();
		System.out.println("Imagen final guardada en: " + output.file().getAbsolutePath());

	}

	private void arrayDeImagenesVariables(ArrayList<FileHandle> pngFiles)
	{
		// Asumimos que todos los PNG tienen las mismas dimensiones
		ArrayList<Pixmap> pixmaps = new ArrayList<Pixmap>();

		// Imagen final

		int totalWidth = 0;
		for (int i = 0; i < pngFiles.size(); i++)
		{
			System.out.println(pngFiles.get(i).name());
			Pixmap current = new Pixmap(pngFiles.get(i));
			pixmaps.add(current);
			totalWidth += current.getWidth();
		}
		int height = pixmaps.get(0).getHeight();
		Pixmap result = new Pixmap(totalWidth, height, Pixmap.Format.RGBA8888);
		int offsetX = 0;
		for (int i = 0; i < pixmaps.size(); i++)
		{
			Pixmap current = pixmaps.get(i);

			result.drawPixmap(current, offsetX, 0);
			offsetX += current.getWidth();
			current.dispose();
		}
		// Guardar la imagen final

		FileHandle output = Gdx.files.local("output/merged.png");
		PixmapIO.writePNG(output, result);
		result.dispose();

		System.out.println("Imagen final guardada en: " + output.file().getAbsolutePath());

	}

}
