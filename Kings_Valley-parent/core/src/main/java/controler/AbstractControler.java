package controler;

/**
 * @author Guillermo Lazzurri Clase abstracta Controlador en el patron MVC. Se
 *         utiliza para controlar la Vista de la interfaz de usuario
 */
public abstract class AbstractControler
{
    protected IView view;
    public static final int EXIT = 0;
    public static final int NEW_GAME = 1;
    public static final int OPTIONS = 2;

    public static final int CREDITS = 3;
    public static final int DIFICULT_LEVEL = 10;
    public static final int MASTER_VOLUME = 11;
    public static final int FX_VOLUME = 12;
    public static final int MUSIC_VOLUME = 13;
    public static final int RETRY = 20;
    public static final int MAIN_MENU = 21;
    public static final int SHOW_MAP = 30;
    public static final int HIDE_MAP = 31;

    /**
     * @param view Vista de interfaz de usuario que debera controlar. En el
     *             costructor se realiza la vinculacion con el controlador y
     *             gestiona la doble referencia.
     */
    public AbstractControler(IView view)
    {
	this.setView(view);
    }

    /**
     * @return retorna el atributo de tipo IView, representa la vista interfaz de usuario
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
