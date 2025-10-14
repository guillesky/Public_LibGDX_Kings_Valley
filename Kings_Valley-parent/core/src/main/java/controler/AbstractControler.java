package controler;

/**
 * Clase abstracta Controlador en el patron MVC. Se utiliza para controlar la
 * Vista de la interfaz de usuario
 * 
 * @author Guillermo Lazzurri
 */
public abstract class AbstractControler
{
    /**
     * Vista que controlora la clase (patron MVC)
     */
    protected IView view;

    /**
     * Codigo para comando de salir
     */
    public static final int EXIT = 0;
    /**
     * Codigo para comando de nuevo juego
     */
    public static final int NEW_GAME = 1;

    /**
     * Codigo para comando de nivel de dificultad
     */
    public static final int DIFICULT_LEVEL = 10;
    /**
     * Codigo para comando de volumen general
     */
    public static final int MASTER_VOLUME = 11;
    /**
     * Codigo para comando de volumen de sonido
     */
    public static final int FX_VOLUME = 12;
    /**
     * Codigo para comando de volumen de musica
     */
    public static final int MUSIC_VOLUME = 13;
    /**
     * Codigo para comando de reintentar el nivel
     */
    public static final int RETRY = 20;
    /**
     * Codigo para comando de volver al menu principal
     */
    public static final int MAIN_MENU = 21;
    /**
     * Codigo para comando de mostrar mapa
     */
    public static final int SHOW_MAP = 30;
    /**
     * Codigo para comando de ocultar el mapa
     */
    public static final int HIDE_MAP = 31;

    /**
     * Constructor de clase
     * 
     * @param view Vista de interfaz de usuario que debera controlar. En el
     *             costructor se realiza la vinculacion con el controlador y
     *             gestiona la doble referencia.
     */
    public AbstractControler(IView view)
    {
	this.setView(view);
    }

    /**
     * Retorna el atributo de tipo IView, representa la vista interfaz de usuario
     * 
     * @return El atributo de tipo IView, representa la vista interfaz de usuario
     */
    public IView getView()
    {
	return view;
    }

    /**
     * Setea el atributo de tipo IView, y gestiona la doble referencia.
     * 
     * @param view Representa la vista interfaz de usuario
     */
    public void setView(IView view)
    {
	this.view = view;
	this.view.setControler(this);
    }

}
