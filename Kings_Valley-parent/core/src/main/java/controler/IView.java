package controler;

import util.GameConfig;

/**
 * Interfaz que declara los metodos que debera implementar la interfaz de
 * usuario
 * 
 * @author Guillermo Lazzurri
 */
public interface IView
{


	
	/**
	 * @return retorna el nivel de dificultad seleccionado por el jugador
	 */
	int getDificultLevel();

	
	/**
	 * @return retorna el valor del volumen general del juego seleccionado por el
	 *         usuario
	 */
	float getMasterVolume();

	/**
	 * @return retorna el valor del volumen de la musica seleccionado por el usuario
	 */
	float getMusicVolume();

	/**
	 * @return retorna el valor del volumen de los efectos de sonido seleccionado
	 *         por el usuario
	 */
	float getSoundsVolume();

	/**
	 * Setea un controlador para responder a los eventos. Para respetar el patron
	 * MVC, este metodo no deberia invocarse en forma directa, sino que es llamado
	 * internamente desde el controlador.
	 * 
	 * @param controler Controlador que respondera los eventos del usuario.
	 */
	void setControler(AbstractControler controler);


	/**
	 * Se debe llamar para actualizar los textos luego de cambiar el idioma
	 */
	void updateLanguage();

}
