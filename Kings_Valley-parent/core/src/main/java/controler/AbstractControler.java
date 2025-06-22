package controler;

public abstract class AbstractControler
{
	private IView view;
	public static final int EXIT=0;
	public static final int NEW_GAME=1;
	public static final int OPTIONS=2;
	
	public static final int CREDITS=3;
	
	
	
	
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
