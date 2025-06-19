package controler;

public abstract class AbstractControler
{
	private IView view;

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
