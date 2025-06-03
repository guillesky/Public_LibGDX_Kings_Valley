package io.github.some_example_name;



import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;

public class PNGMerger extends ApplicationAdapter
{

	@Override
	public void create()
	{
		// Ruta de los archivos PNG
		FileHandle inputFolder = Gdx.files.local("input/");
		FileHandle[] allFiles = inputFolder.list();
		ArrayList<FileHandle> pngFiles = new ArrayList<FileHandle>();

		for (FileHandle file : allFiles) {
		    if (file.extension().toLowerCase().equals("png")) {
		        pngFiles.add(file);
		    }
		}
		if (pngFiles.isEmpty())
		{
			System.out.println("No se encontraron archivos PNG en la carpeta input/");
			Gdx.app.exit();
			return;
		}

		// Asumimos que todos los PNG tienen las mismas dimensiones
		Pixmap firstImage = new Pixmap(pngFiles.get(0));
		int singleWidth = firstImage.getWidth();
		int height = firstImage.getHeight();
		int totalWidth = singleWidth * pngFiles.size();

		// Imagen final
		Pixmap result = new Pixmap(totalWidth, height, Pixmap.Format.RGBA8888);

		// Pegar todas las imágenes una al lado de la otra
		for (int i = 0; i < pngFiles.size(); i++)
		{System.out.println(pngFiles.get(i).name()); 
			Pixmap current = new Pixmap(pngFiles.get(i));
			result.drawPixmap(current, i * singleWidth, 0);
			current.dispose();
		}

		// Guardar la imagen final
		FileHandle output = Gdx.files.local("output/merged.png");
		PixmapIO.writePNG(output, result);
		result.dispose();
		firstImage.dispose();

		System.out.println("Imagen final guardada en: " + output.file().getAbsolutePath());

		// Cierra la aplicación después de crear la imagen
		Gdx.app.exit();
	}

}
