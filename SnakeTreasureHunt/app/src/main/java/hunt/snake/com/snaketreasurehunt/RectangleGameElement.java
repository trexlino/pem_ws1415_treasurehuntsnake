package hunt.snake.com.snaketreasurehunt;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by Tom on 11/23/14.
 */
public class RectangleGameElement extends GameElement {

    private Orientation orientation;
    private int[] offsets;
    private Rect rect;

    public RectangleGameElement(Canvas canvas) {
        super(canvas);
        rect = new Rect(0, 0, 0, 0);
    }

    public RectangleGameElement() {
        rect = new Rect(0, 0, 0, 0);
    }

    @Override
    public void drawGameElement() {
        /*
        GameElementType type = getType();
        int left = getTile().getPosX() * Constants.TILE_WIDTH.getValue() + ((Constants.TILE_WIDTH.getValue() - type.getWidth()) / 2);
        int top = getTile().getPosY() * Constants.TILE_HEIGHT.getValue() + ((Constants.TILE_HEIGHT.getValue() - type.getHeight()) / 2);
        int width = getType().getWidth() + left;
        int height = getType().getHeight() + top;
        Paint paint = getType().getPaint();
        //rect = new Rect(left, top, width, height);
        */
        Paint paint = getType().getPaint();
        System.out.println(rect);
        getCanvas().drawRect(rect, paint);
    }

    @Override
    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
        updateRectangle();
    }

    private void updateRectangle() {
        GameElementType type = getType();

        int left = getTile().getPosX() * Constants.TILE_WIDTH.getValue() + ((Constants.TILE_WIDTH.getValue() - type.getWidth()) / 2);
        int top = getTile().getPosY() * Constants.TILE_HEIGHT.getValue() + ((Constants.TILE_HEIGHT.getValue() - type.getHeight()) / 2);
        int width = getType().getWidth() + left;
        int height = getType().getHeight() + top;

        switch(orientation) {
            case NORTH:
                height = height - getType().getHeight() / 2 + getType().getWidth() / 2;
                break;
            case EAST:
                width = width - getType().getWidth() / 2 + getType().getHeight() / 2;
                break;
            case SOUTH:
                top = top + getType().getHeight() / 2 - getType().getWidth() / 2;
                break;
            case WEST:
                left = left + getType().getWidth() / 2 - getType().getHeight() / 2;
                break;
        }

        rect = new Rect(left, top, width, height);
    }
}