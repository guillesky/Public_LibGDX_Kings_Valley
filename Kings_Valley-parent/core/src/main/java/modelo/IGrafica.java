package modelo;

public interface IGrafica
{

  

    void addGraphicElement(Object element);

    void removeGraphicElement(Object element);

    void reset();

    float getTimeToExitLevel();

    float getTimeToEnterLevel();

    float getTimeDying();

    float getTimeToEndGame();

}
