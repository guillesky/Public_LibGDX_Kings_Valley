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
     * 
     * @param controler 
     */
    void setControler(AbstractControler controler);

    void updateMasterVolume();

    void updateMusicVolume();

    void updateSoundsVolume();

    void updateLanguage();

}
