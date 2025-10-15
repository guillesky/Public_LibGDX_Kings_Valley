package i18n;

import java.util.HashMap;

/**
 * Clase que representa un idioma durante el juego
 * 
 * @author Guillermo Lazzurri
 */
public class Language
{
    private String fileCode;
    private HashMap<String, String> words = new HashMap<String, String>();

    /**
     * Constructor de clase
     * 
     * @param fileCode representa el codigo de archivo (es = espanol; en= english;
     *                 etc.)
     * @param words    HashMap[String, String] con todos los valores de los mensajes
     *                 del idioma. Los mensajes se correponden al enumerate Messages
     */
    public Language(String fileCode, HashMap<String, String> words)
    {
	super();
	this.fileCode = fileCode;
	this.words = words;
    }

    /**
     * Retorna el codigo de archivo (es = espanol; en= english; etc.)
     * 
     * @return codigo de archivo (es = espanol; en= english; etc.)
     */
    public String getFileCode()
    {
	return fileCode;
    }

    /**
     * Retorna el HashMap [String, String] con todos los valores de los mensajes del
     * idioma. Los mensajes se correponden al enumerate Messages
     * 
     * @return HashMap [String, String] con todos los valores de los mensajes del
     *         idioma. Los mensajes se correponden al enumerate Messages
     */
    public HashMap<String, String> getWords()
    {
	return words;
    }

}
