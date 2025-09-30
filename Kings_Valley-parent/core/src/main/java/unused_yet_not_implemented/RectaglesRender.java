package unused_yet_not_implemented;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;

import util.Utils;

public class RectaglesRender
{
    ArrayList<Rectangle> rectangles;
    private ShapeRenderer shapeRenderer=new ShapeRenderer();
    public RectaglesRender(TiledMap map)
    {

	TiledMapTileLayer layer2 = (TiledMapTileLayer) map.getLayers().get("front");
	int mapWidthInTiles = map.getProperties().get("width", Integer.class);
	int mapHeightInTiles = map.getProperties().get("height", Integer.class);
	this.rectangles = UtilNotUsed.generateRectangles(mapWidthInTiles,mapHeightInTiles, layer2);
    }
    public void render(Matrix4 combined) {
	shapeRenderer.setProjectionMatrix(combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);
        for (Rectangle rect : rectangles) {
            Rectangle2 r=(Rectangle2) rect;
            shapeRenderer.setColor(r.color);
            shapeRenderer.rect(rect.x, rect.y, rect.width, rect.height);
        }
        shapeRenderer.end();
    }

    public void dispose() {
        shapeRenderer.dispose();
    }

}
