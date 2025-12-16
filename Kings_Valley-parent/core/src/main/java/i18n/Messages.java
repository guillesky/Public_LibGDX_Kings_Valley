package i18n;

/**
 * Enumerado que representa los mensajes del juego en el idioma que este
 * seleccionado. Internamente es llamado para mostrar todos los textos del juego
 * 
 * @author Guillermo Lazzurri
 */
public enum Messages
{

    /**
     * Mensaje correspondiente a "Juego Pausado"
     */
    GAME_PAUSED("Juego Pausado"),
    /**
     * Mensaje correspondiente a "Piramide Actual"
     */
    CURRENT_PYRAMID("Piramide Actual: "),
    /**
     * Mensaje correspondiente a "Puntaje: "
     */
    SCORE("Score: "),
    /**
     * Mensaje correspondiente a "Vidas: "
     */

    LIVES("Lives: "),
    /**
     * Mensaje correspondiente al nombre del idioma actual
     */

    LANGUAGE_NAME("English"),
    /**
     * Mensaje correspondiente a "Juego Nuevo"
     */

    NEW_GAME("New Game"),
    /**
     * Mensaje correspondiente a "Opciones"
     */

    OPTIONS("Options"),
    /**
     * Mensaje correspondiente a "Creditos"
     */

    CREDITS("Credits"),
    /**
     * Mensaje correspondiente a "Salir"
     */
    EXIT("Exit"),
    /**
     * Mensaje correspondiente a "Volver"
     */
    GO_BACK("Go Back"),
    /**
     * Mensaje correspondiente a "Volumen de musica"
     */
    MUSIC_VOLUME("Music Volume"),
    /**
     * Mensaje correspondiente a "Volumen de Sonidos"
     */
    FX_VOLUME("Sounds Volume"),
    /**
     * Mensaje correspondiente a "Volumen General"
     */

    MASTER_VOLUME("Master Volume"),
    /**
     * Mensaje correspondiente a "Nivel de Dificultad"
     */
    DIFICULT_LEVEL("Dificult Level"),
    /**
     * Mensaje correspondiente a "Progreso de Carga"
     */
    LOAD_PROGRESS("Load Progress"),
    /**
     * Mensaje correspondiente a "Idioma"
     */

    LANGUAGE("Language"),
    /**
     * Mensaje correspondiente a "Facil"
     */

    EASY("Easy"),
    /**
     * Mensaje correspondiente a "Normal"
     */
    NORMAL("Normal"),
    /**
     * Mensaje correspondiente a "Dificil"
     */
    HARD("Hard"),
    /**
     * Mensaje correspondiente a "Menu Principal"
     */
    MAIN_MENU("Main Menu"),
    /**
     * Mensaje correspondiente a "Reintentar"
     */
    RETRY("Retry"),
    /**
     * Mensaje correspondiente a "Mapa"
     */
    MAP("Map"),
    /**
     * Mensaje correspondiente a "Volviendo a la Piramide"
     */
    GOING_BACK("Back to Pyramid "),
    /**
     * Mensaje correspondiente a "Entrando a la Piramide "
     */

    ENTERING("Entering Pyramid "),
    /**
     * Mensaje correspondiente a "Juego Terminado"
     */
    GAME_OVER("Game Over"),
    /**
     * Mensaje correspondiente a "Seguro deseas reintentar el nivel? (Pierdes una
     * vida)"
     */

    DIALOG_RETRY_TEXT("Are you sure you want to retry the level? (You will lose a life)"),
    /**
     * Mensaje correspondiente a "Confirmar Reintentar"
     */

    DIALOG_RETRY_TITLE("Confirm Retry"),
    /**
     * Mensaje correspondiente a "Confirmar Salir"
     */
    DIALOG_EXIT_TITLE("Confirm Exit"),
    /**
     * Mensaje correspondiente a "Seguro deseas salir del juego?"
     */

    DIALOG_EXIT_TEXT("Are you sure you want to exit the game?"),
    /**
     * Mensaje correspondiente a "Si"
     */
    YES("Yes"),
    /**
     * Mensaje correspondiente a "No"
     */
    NO("No"),

    /**
     * Mensaje correspondiente a "Confirmar volver al menu principal"
     */

    DIALOG_GO_MAIN_MENU_TITLE("Confirm Go Back to Main Menu"),
    /**
     * Mensaje correspondiente a "Seguro deseas volver al menu principal? (Perderas
     * todo el progreso)"
     */

    DIALOG_GO_MAIN_MENU_TEXT("Are you sure you want to return to the main menu? (You will lose all progress)"),
    /**
     * Mensaje correspondiente a "Version Clasica"
     */

    CLASSIC_VERSION("Classic Version"),
    /**
     * Mensaje correspondiente a "Version Extendida"
     */

    EXTENDED_VERSION("Extended Version"),
    /**
     * Mensaje correspondiente a "Episodio"
     */

    EPISODE("Episode "),
    /**
     * Mensaje correspondiente a "Completa un episodio para desbloquear el
     * siguiente"
     */
    ENABLED_EPISODE_MESSAGE("Finish an episode to unlock the next"),
    FINISH_EPISODE("Congratulations! You have completed an episode.\nYou can start from here next time."),
    ENTER_GOAL_LEVEL("You have conquered all levels!\nThe treasure is yours."),
    FINISH_GAME("You have mastered all levels.\nDare to face a higher difficulty?");

    private String value;

    /**
     * Setea el texto del mensaje
     * 
     * @param value Texto del mensaje
     */
    private Messages(String value)
    {
	this.value = value;
    }

    /**
     * Retorna el texto del mensaje
     * 
     * @return texto del mensaje
     */
    public String getValue()
    {
	return value;
    }

    /**
     * Setea el valor texto del mensaje
     * 
     * @param valor texto del mensaje
     */
    public void setValue(String valor)
    {
	this.value = valor;
    }

}
