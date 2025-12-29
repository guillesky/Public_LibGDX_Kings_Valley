package util;

import java.util.HashMap;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.ObjectMap;

import i18n.Language;
import i18n.Messages;

/**
 * Contiene metodos estaticos utilizados internamente por el juego.
 * 
 * @author Guillermo Lazzurri
 */
public class Utils
{

	/**
	 * Actualiza los enumerados Messages de acuerdo a los valores del codigo de
	 * idioma pasado por parametro leyendo el archivo de idioma correspondiente
	 * 
	 * @param languageCode Cogido de idioma (por ejemplo "es" para espanol, o "en"
	 *                     para english)
	 */
	@SuppressWarnings("unchecked")
	public static void i18n(String languageCode)
	{
		String path = "i18n/" + languageCode + ".json";
		FileHandle file = Gdx.files.internal(path);
		if (!file.exists())
		{
			throw new RuntimeException("Language file not found: " + path);
		}

		Json json = new Json();
		ObjectMap<String, String> loaded = json.fromJson(ObjectMap.class, file);

		for (Messages key : Messages.values())
		{
			String msg = loaded.get(key.name());
			if (msg != null)
			{
				key.setValue(msg);
			} else
			{
				key.setValue("???" + key.name());
			}
		}
	}

	/**
	 * Devuelve un objeto de tipo de Language a partir de un codigo de idioma
	 * 
	 * @param languageCode Cogido de idioma (por ejemplo "es" para espanol, o "en"
	 *                     para english)
	 * @return Objecto de tipo Language con los datos del codigo de idioma pasado
	 *         por aparametro
	 */
	@SuppressWarnings("unchecked")
	public static Language i18nToLanguage(String languageCode)
	{
		HashMap<String, String> words = new HashMap<String, String>();
		String path = "i18n/" + languageCode + ".json";
		FileHandle file = Gdx.files.internal(path);
		if (!file.exists())
		{
			throw new RuntimeException("Language file not found: " + path);
		}

		Json json = new Json();
		ObjectMap<String, String> loaded = json.fromJson(ObjectMap.class, file);

		Iterator<String> keys = loaded.keys().iterator();
		while (keys.hasNext())
		{
			String key = keys.next();
			words.put(key, loaded.get(key));
		}
		return new Language(languageCode, words);
	}

	/**
	 * Actualiza todos los mensajes a partir del idioma pasado por parametro
	 * 
	 * @param language Idioma que debe setearse en todos los mensajes
	 */
	public static void i18n(Language language)
	{

		for (Messages key : Messages.values())
		{
			String msg = language.getWords().get(key.name());
			if (msg != null)
			{
				key.setValue(msg);
			} else
			{
				key.setValue("???" + key.name());
			}
		}
	}



}
