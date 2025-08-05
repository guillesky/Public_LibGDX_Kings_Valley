package controler;

import util.GameConfig;

/**
 * @author Guillermo Lazzurri Interfaz que declara los metodos que debera
 *         implementar la interfaz de usuario
 */
public interface IView
{

    /**
     * @param credits String con los creditos del juego, debera actualizar los
     *                creditos en el idioma correspondiente
     */
    void updateCredits(String credits);

    /**
     * Actualiza la configuracion del juego.
     * 
     * @param gameConfig Contiene los parametros de configuracion del juego
     */
    void updateGameConfig(GameConfig gameConfig);

    /**
     * @return retorna el nivel de dificultad seleccionado por el jugador
     */
    int getDificultLevel();

    /**
     * @return retorna el codigo de idioma seleccionado por el jugador.
     */
    String getLanguage();

    /**
     * @return retorna el valor del volumen general del juego seleccionado por el usuario
     */
    float getMasterVolume();

    /**
     * @return retorna el valor del volumen de la musica seleccionado por el usuario
     */
    float getMusicVolume();

    /**
     * @return retorna el valor del volumen de los efectos de sonido seleccionado por el usuario
     */
    float getSoundsVolume();

    /**
     * Setea un controlador para responder a los eventos. Para respetar el patron MVC, este metodo no deberia invocarse en forma directa, sino que es llamado internamente desde el controlador.
     * @param controler  Controlaor que respondera los eventos del usuario.
     */
    void setControler(AbstractControler controler);

    /**
     * Se debe llamar para actualizar el volumen principal en la vista
     */
    void updateMasterVolume();

    /**
     * Se debe llamar para actualizar el volumen de la musica en la vista
     */
    void updateMusicVolume();

    /**
     * Se debe llamar para actualizar el volumen de los efectos de sonido en la vista
     */
    void updateSoundsVolume();

    /**
     * Se debe llamar para actualizar los textos luego de cambiar el idioma
     */
    void updateLanguage();

}
