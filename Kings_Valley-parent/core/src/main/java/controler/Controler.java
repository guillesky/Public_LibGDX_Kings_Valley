package controler;

public class Controler
{
	private IView view;

	public Controler(IView view)
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
