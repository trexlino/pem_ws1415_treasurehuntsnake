package hunt.snake.com.snaketreasurehunt.communication;

import com.google.gson.Gson;
import hunt.snake.com.snaketreasurehunt.messages.GameMessage;

/**
 * Created by lino on 18.01.15.
 */
public class STHMessageParser {

    public STHMessageParser(){}

    public void deserializeSTHMessage(String incomingMessage) {

        if (!incomingMessage.contains("null")) {

            Gson gson = new Gson();
            GameMessage msg = gson.fromJson(incomingMessage, GameMessage.class);

            int messageType = msg.getType();

            switch (messageType) {
                case STHMessage.GAMESTART_MESSAGE:

                    int fieldheight = msg.getGameStart().getFieldHeight();
                    int fieldwidth = msg.getGameStart().getFieldWidth();
                    int[] tileXPos = msg.getGameStart().getTileXPos();
                    int[] tileYPos = msg.getGameStart().getTileYPos();
                    int[] tileType = msg.getGameStart().getTileType();

                    System.out.println("Width: " + fieldwidth);
                    System.out.println("Height: " + fieldheight);
                    System.out.println("tileX: " + tileXPos + ", tileY: " + tileYPos + ", tileType: " + tileType);
                    for(int i : tileXPos) {
                       System.out.println("x: " + i);
                    }

                    DataTransferHandler.setFieldHeight(fieldheight);
                    DataTransferHandler.setFieldWidth(fieldwidth);
                    DataTransferHandler.setTileXPos(tileXPos);
                    DataTransferHandler.setTileYPos(tileYPos);
                    DataTransferHandler.setTileType(tileType);

                    break;

                case STHMessage.GAMEOVER_MESSAGE:

                    int score = msg.getGameOver().getScore();

                    DataTransferHandler.setScore(score);

                    break;
                case STHMessage.STITCHING_MESSAGE:

                    float tickTime = msg.getGameStitching().getTickTime();
                    float timestamp = msg.getGameStitching().getTimestamp();
                    int stitchingDirection = msg.getGameStitching().getStitchingDirection();
                    int topLeftXPos = msg.getGameStitching().getTopLeftXPos();
                    int topLeftYPos = msg.getGameStitching().getTopLeftYPos();
                    int[] bodypartXPos = msg.getGameStitching().getSnake().getBodypartXPos();
                    int[] bodypartYPos = msg.getGameStitching().getSnake().getBodypartYPos();
                    boolean[] cornerPart = msg.getGameStitching().getSnake().getCornerPart();
                    int[] origin = msg.getGameStitching().getSnake().getOrigin();
                    int[] direction = msg.getGameStitching().getSnake().getDirection();

                    System.out.println("tickTime: " + tickTime + ", timeStamp: " + timestamp);
                    System.out.println("stitching: " + stitchingDirection + ", topleft: " + topLeftXPos + ", " + topLeftYPos);
                    System.out.println("snake: " + msg.getGameStitching().getSnake());


                    DataTransferHandler.setTickTime(tickTime);
                    DataTransferHandler.setTimestamp(timestamp);
                    DataTransferHandler.setStitchingDirection(stitchingDirection);
                    DataTransferHandler.setTopLeftXPos(topLeftXPos);
                    DataTransferHandler.setTopLeftYPos(topLeftYPos);
                    DataTransferHandler.setBodypartXPos(bodypartXPos);
                    DataTransferHandler.setBodypartYPos(bodypartYPos);
                    DataTransferHandler.setCornerPart(cornerPart);
                    DataTransferHandler.setOrigin(origin);
                    DataTransferHandler.setDirection(direction);

                    break;

                case STHMessage.NEWGUTTI_MESSAGE:

                    int foodXPos = msg.getGameNewGutti().getFoodXPos();
                    int foodYPos = msg.getGameNewGutti().getFoodYPos();

                    System.out.println("food: " + foodXPos + ", " + foodYPos);

                    DataTransferHandler.setFoodXPos(foodXPos);
                    DataTransferHandler.setFoodYPos(foodYPos);

                    break;

                case STHMessage.GAMEPAUSE_START_MESSAGE:

                    break;

                case STHMessage.GAMEPAUSE_STOP_MESSAGE:

                    float ticks = msg.getGamePauseStop().getTickTime();

                    System.out.println("ticks: " + ticks);

                    DataTransferHandler.setTickTime(ticks);

                    break;
            }

        }
    }
}
