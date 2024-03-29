package hunt.snake.com.snaketreasurehunt.gamelogic;

import java.util.LinkedList;

import hunt.snake.com.framework.Graphics;
import hunt.snake.com.snaketreasurehunt.communication.DataTransferHandler;

public class Snake {

    public enum Direction {
        NORTH,
        EAST,
        SOUTH,
        WEST
    }

    private int length;
    //current tile where the head is
    private Tile headTile;
    private Tile[][] tiles;
    //cardinal direction of the head
    private Direction headDirection;
    //list of all body parts of the snake (head + neck is only one part, as well as an edge part)
    private LinkedList<GameElement> bodyParts;
    private GameElement food;

    private boolean hasEaten;
    //tile where food was picked up
    private Tile foodTile;

    public Snake() {
        bodyParts = new LinkedList<GameElement>();
    }

    public void init(Tile startTile, Tile[][] tiles, int length, Direction headDirection) {
        this.length = length;
        this.tiles = tiles;
        this.headDirection = headDirection;
        this.headTile = startTile;

        hasEaten = false;
        bodyParts.clear();
        createSnake();
    }

    public void init(Tile[][] tiles) {
        this.tiles = tiles;
        int[] bodyPartsX = DataTransferHandler.getBodypartXPos();
        int[] bodyPartsY = DataTransferHandler.getBodypartYPos();
        Direction headDirection = DataTransferHandler.getHeadDirection();

        this.headDirection = headDirection;
        System.out.println("Tiles: " + tiles);
        System.out.println("X " + bodyPartsX + " Y " + bodyPartsY);
        headTile = tiles[bodyPartsX[0]][bodyPartsY[0]];
        hasEaten = false;
        bodyParts.clear();

        GameElement bodyPart;
        bodyPart = createHead();
        bodyParts.addFirst(bodyPart);

        Tile tile;
        Tile nextTile;
        Direction prevDirection = headDirection;
        Direction direction = null;


        for(int i = 1; i < bodyPartsX.length; i++) {
            tile = tiles[bodyPartsX[i]][bodyPartsY[i]];
            if(i != bodyPartsX.length - 1) {
                nextTile = tiles[bodyPartsX[i+1]][bodyPartsY[i+1]];
                direction = getBodyPartDirection(tile, nextTile, prevDirection);
                if(direction == prevDirection) {
                    bodyPart = createBodyPart(tile, direction);
                } else {
                    bodyPart = createCornerBodyPart(tile, direction, prevDirection);
                    prevDirection = direction;
                }
            } else {
                bodyPart = createBodyPart(tile, prevDirection);
            }
            System.out.println("Bodypart: x: " + tile.getPosX() + ", y: " + tile.getPosY() + ", direction: " + direction);
            bodyParts.add(bodyPart);
        }
    }

    private Direction getBodyPartDirection(Tile tile, Tile nextTile, Direction prevDirection) {
        Direction direction = prevDirection;

        switch(prevDirection) {
            case NORTH:
            case SOUTH:
                if(nextTile.getPosX() == tile.getPosX()) {
                    direction = prevDirection;
                } else if(nextTile.getPosX() < tile.getPosX()) {
                    direction = Direction.EAST;
                } else {
                    direction = Direction.WEST;
                }
                break;
            case EAST:
            case WEST:
                if(nextTile.getPosY() == tile.getPosY()) {
                    direction = prevDirection;
                } else if(nextTile.getPosY() > tile.getPosY()) {
                    direction = Direction.NORTH;
                } else {
                    direction = Direction.SOUTH;
                }
                break;
        }
        return direction;
    }

    // draws the snake on the canvas, but with a shifted position relative to (0,0)
    public void drawSnake(Graphics g, int deltaX, int deltaY) {
        for(GameElement bodyPart : bodyParts)
            bodyPart.draw(g, deltaX, deltaY);
    }

    public void createSnake() {
        GameElement head = createHead();
        bodyParts.addFirst(head);

        int parts = 1;
        int startX = headTile.getPosX();
        int startY = headTile.getPosY();

        GameElement bodyPart;

        while(parts < length) {
            switch(headDirection) {
                case NORTH:
                    bodyPart = createBodyPart(tiles[startX][startY + parts], headDirection);
                    break;
                case EAST:
                    bodyPart = createBodyPart(tiles[startX - parts][startY], headDirection);
                    break;
                case SOUTH:
                    bodyPart = createBodyPart(tiles[startX][startY - parts], headDirection);
                    break;
                case WEST:
                    bodyPart = createBodyPart(tiles[startX + parts][startY], headDirection);
                    break;
                default:
                    bodyPart = createBodyPart(tiles[startX - parts][startY], headDirection);
                    break;
            }
            bodyParts.add(bodyPart);
            parts++;
        }
    }

    private GameElement createBodyPart(Tile tile, Direction direction) {
        GameElementType type;
        if(direction == Direction.EAST || direction == Direction.WEST)
            type = GameElementType.SNAKE_BODY_HORIZONTAL;
        else
            type = GameElementType.SNAKE_BODY_VERTICAL;

        GameElement body = type.getGameElement();
        body.setTile(tile);

        return body;
    }

    //head plus neck
    private GameElement createHead() {
        GameElementType headType;
        GameElementType neckType;

        if(headDirection == Direction.EAST || headDirection == Direction.WEST) {
            headType = GameElementType.SNAKE_HEAD_HORIZONTAL;
            neckType = GameElementType.SNAKE_BODY_HORIZONTAL_SHORT;
        } else {
            headType = GameElementType.SNAKE_HEAD_VERTICAL;
            neckType = GameElementType.SNAKE_BODY_VERTICAL_SHORT;
        }

        GameElement head = headType.getGameElement();
        GameElement neck = neckType.getGameElement();
        head.setTile(headTile);
        head.addElement(neck);

        neck.setPosition(getOppositePositionOfDirection(headDirection));

        return head;
    }

    //returns next tile depending on the cardinal direction the snake is moving
    //return null if tile is out of bounds or will kill
    private Tile getNextTile(Direction direction) {
        System.out.println("HeadTile: " + headTile);
        int posX = headTile.getPosX();
        int posY = headTile.getPosY();

        System.out.println("Como direction? " + direction);

        switch(direction) {
            case EAST:
                posX += 1;
                break;
            case NORTH:
                posY -= 1;
                break;
            case WEST:
                posX -= 1;
                break;
            case SOUTH:
                posY += 1;
                break;
            default:
                break;
        }

        if(posX < 0 || posX > tiles.length - 1 || posY < 0 || posY > tiles[posX].length - 1)
            return null;

        Tile nextTile = tiles[posX][posY];

        if(nextTile.hasGameElement()) {
            if(nextTile.getGameElementType() == GameElementType.FOOD) {
                eat(nextTile);
            } else {
                System.out.println("NextTileType: " + nextTile.getGameElement().getType());
                System.out.println("NextTileInMethod: [" + nextTile.getPosX() + ", " + nextTile.getPosY() + "]");
                return null;
            }
        }

        return tiles[posX][posY];
    }

    //create corner body part, from origin to destination
    private GameElement createCornerBodyPart(Tile tile, Direction origin, Direction destination) {
        GameElementType typeFirst;
        GameElementType typeSecond;

        if(origin == Direction.NORTH || origin == Direction.SOUTH) {
            typeFirst = GameElementType.SNAKE_BODY_VERTICAL_SHORT;
            typeSecond = GameElementType.SNAKE_BODY_HORIZONTAL_SHORT;
        } else {
            typeFirst = GameElementType.SNAKE_BODY_HORIZONTAL_SHORT;
            typeSecond = GameElementType.SNAKE_BODY_VERTICAL_SHORT;
        }

        GameElement first = typeFirst.getGameElement();
        GameElement second = typeSecond.getGameElement();

        first.setTile(tile);
        first.addElement(second);

        first.setPosition(getOppositePositionOfDirection(origin));
        second.setPosition(getPositionByDirection(destination));

        return first;
    }

    private GameElement.Position getOppositePositionOfDirection(Direction direction) {
        GameElement.Position pos;
        switch(direction) {
            case EAST:
                pos = GameElement.Position.WEST;
                break;
            case NORTH:
                pos = GameElement.Position.SOUTH;
                break;
            case WEST:
                pos = GameElement.Position.EAST;
                break;
            case SOUTH:
                pos = GameElement.Position.NORTH;
                break;
            default:
                pos = GameElement.Position.NONE;
                break;
        }
        return pos;
    }

    private GameElement.Position getPositionByDirection(Direction direction) {
        GameElement.Position pos;
        switch(direction) {
            case EAST:
                pos = GameElement.Position.EAST;
                break;
            case NORTH:
                pos = GameElement.Position.NORTH;
                break;
            case WEST:
                pos = GameElement.Position.WEST;
                break;
            case SOUTH:
                pos = GameElement.Position.SOUTH;
                break;
            default:
                pos = GameElement.Position.NONE;
                break;
        }
        return pos;
    }

    //Move in one of the four cardinal directions
    //returns false if movement is not allowed (edge, obstacle, etc)
    public boolean move(Direction direction) {
        GameElement newBodyPart;
        Tile oldTile = headTile;
        Tile nextTile = getNextTile(direction);


        if(nextTile != null) {
            System.out.println("NextTile: [" + nextTile.getPosX() + ", " + nextTile.getPosY() + "]");
            System.out.println("onBorder: " + nextTile.isOnBorder());
        } else {
            System.out.println("NextTile is null");
        }

        if(nextTile == null || nextTile.isOnBorder())
            return false;

        if(direction == headDirection) {
            newBodyPart = createBodyPart(oldTile, direction);
        } else {
            newBodyPart = createCornerBodyPart(oldTile, headDirection, direction);
        }

        bodyParts.set(0, newBodyPart);

        headTile = nextTile;
        headDirection = direction;

        GameElement head = createHead();
        bodyParts.addFirst(head);

        if(hasEaten) {
            if(bodyParts.getLast().getTile() == foodTile) {
                food.hidden(true);
                hasEaten = false;
            } else {
                if(bodyParts.get(1).getTile() == foodTile) {
                    food.hidden(false);
                }
                GameElement lastPart = bodyParts.getLast();
                lastPart.getTile().setHasGameElement(false);
                bodyParts.removeLast();
            }
        } else {
            GameElement lastPart = bodyParts.getLast();
            lastPart.getTile().setHasGameElement(false);
            bodyParts.removeLast();
        }
        System.out.println("MOVE");
        return true;
    }

    public void eat(Tile tile) {
        hasEaten = true;
        foodTile = tile;
        food = tile.getGameElement();
        food.changeColor(GameElementType.SNAKE_HEAD_HORIZONTAL.getColor());
        food.hidden(true);
    }

    public boolean hasEaten() {
        return hasEaten;
    }

    public Tile getHeadTile() {
        return headTile;
    }

    public void parseToDataTransferHandler() {
        DataTransferHandler.setHeadDirection(headDirection);
        int[] bpX = new int[bodyParts.size()];
        int[] bpY = new int[bodyParts.size()];;
        int i = 0;
        for(GameElement bp : bodyParts) {
            System.out.println("BP: " + bp.getTile().getPosX() + " y: " + bp.getTile().getPosY());
            bpX[i] = bp.getTile().getPosX();
            bpY[i] = bp.getTile().getPosY();
            i++;
        }
        DataTransferHandler.setBodypartXPos(bpX);
        DataTransferHandler.setBodypartYPos(bpY);
    }

    public LinkedList<GameElement> getBodyParts() {
        return bodyParts;
    }
}
