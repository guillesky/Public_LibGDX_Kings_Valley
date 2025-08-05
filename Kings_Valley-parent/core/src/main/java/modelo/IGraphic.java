package modelo;

public interface IGraphic
{

  

    void addGraphicElement(Object element);

    void removeGraphicElement(Object element);

    void reset();

    float getTimeToExitLevel();

    float getTimeToEnterLevel();

    float getTimeDying();

    float getTimeToEndGame();

}
