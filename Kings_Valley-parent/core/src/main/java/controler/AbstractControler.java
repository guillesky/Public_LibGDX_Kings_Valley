package controler;

public abstract class AbstractControler
{
	protected IView view;
	public static final int EXIT=0;
	public static final int NEW_GAME=1;
	public static final int OPTIONS=2;
	
	public static final int CREDITS=3;
	public static final int DIFICULT_LEVEL = 10;
	public static final int MASTER_VOLUME = 11;
	public static final int FX_VOLUME = 12;
	public static final int MUSIC_VOLUME = 13;
	
	
	
	
	public AbstractControler(IView view)
	{
		this.setView(view);
	}

	public IView getView()
	{
		return view;
	}

	public void setView(IView view)
	{
		this.view = view;
		this.view.setControler(this);
	}

}
