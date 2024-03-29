package hunt.snake.com.snaketreasurehunt.communication;

import java.util.LinkedList;

import hunt.snake.com.snaketreasurehunt.gamelogic.Snake;
import hunt.snake.com.snaketreasurehunt.messages.GameMessage;

public class DataTransferHandler {
    private static LinkedList<GameMessage> messageList = new LinkedList<GameMessage>();

    private static boolean receivedMessage; // haben wir eine Nachricht empfangen?
    private static int messageType;         // welche Nachrichtenart haben wir als letztes empfangen?

    // Data transferred when starting the game (GAMESTART_MESSAGE)
    private static int fieldWidth;          // Spielfeldbreite
    private static int fieldHeight;         // Spielfeldhöhe
    private static int numOfOccupiedTiles;  // Zahl der belegten Tiles (= Größe der Arrays unten)
    private static int[] tileXPos;          // X-Positionen aller belegter Tiles
    private static int[] tileYPos;          // Y-Positionen aller belegter Tiles
    private static int[] tileType;          // Typen aller belegter Tiles

    // Data transferred when stitching out (STITCHOUT_MESSAGE)
    private static float tickTime;          // aktuelle Tickzeit (für Synchronisation)
    private static float timestamp;         // Zeitpunkt des Stitchens
    private static int stitchingDirection;  // Richtung des Stitchens (Oben, Rechts, Unten, Links)
    private static int topLeftXPos;         // X-Position des links oberen Tiles
    private static int topLeftYPos;         // Y-Position des links oberen Tiles
    private static int numOfSnakeBodyparts; // Zahl der Körperteile der Schlange (= Größe der Arrays unten)
    private static int[] bodypartXPos;      // X-Positionen aller Schlangen-Körperteile
    private static int[] bodypartYPos;      // Y-Positionen aller Schlangen-Körperteile
    private static Snake.Direction headDirection;

    // Data transferred after the snake has eaten and a new gutti is spawned (NEWGUTTI_MESSAGE)
    private static int foodXPos;            // X-Position des Guttis
    private static int foodYPos;            // Y-Position des Guttis

    // Data transferred when the game is over (GAMEOVER_MESSAGE)
    private static int score;               // aktueller Spielstand

    //FOR MOVEMENT
    private static Snake.Direction movementDirection;


    public static boolean hasReceivedMessage() {
        return receivedMessage;
    }

    public static void setReceivedMessage(boolean receivedMessage) {
        DataTransferHandler.receivedMessage = receivedMessage;
    }

    public static int getMessageType() {
        return messageType;
    }

    public static void setMessageType(int messageType) {
        DataTransferHandler.messageType = messageType;
    }

    public static int getFieldWidth() {
        return fieldWidth;
    }

    public static void setFieldWidth(int fieldWidth) {
        DataTransferHandler.fieldWidth = fieldWidth;
    }

    public static int getFieldHeight() {
        return fieldHeight;
    }

    public static void setFieldHeight(int fieldHeight) {
        DataTransferHandler.fieldHeight = fieldHeight;
    }

    public static int getNumOfOccupiedTiles() {
        return numOfOccupiedTiles;
    }

    public static void setNumOfOccupiedTiles(int numOfOccupiedTiles) {
        DataTransferHandler.numOfOccupiedTiles = numOfOccupiedTiles;
    }

    public static int[] getTileXPos() {
        return tileXPos;
    }

    public static void setTileXPos(int[] tileXPos) {
        DataTransferHandler.tileXPos = tileXPos;
    }

    public static int[] getTileYPos() {
        return tileYPos;
    }

    public static void setTileYPos(int[] tileYPos) {
        DataTransferHandler.tileYPos = tileYPos;
    }

    public static int[] getTileType() {
        return tileType;
    }

    public static void setTileType(int[] tileType) {
        DataTransferHandler.tileType = tileType;
    }

    public static float getTickTime() {
        return tickTime;
    }

    public static void setTickTime(float tickTime) {
        DataTransferHandler.tickTime = tickTime;
    }

    public static float getTimestamp() {
        return timestamp;
    }

    public static void setTimestamp(float timestamp) {
        DataTransferHandler.timestamp = timestamp;
    }

    public static int getStitchingDirection() {
        return stitchingDirection;
    }

    public static void setStitchingDirection(int stitchingDirection) {
        DataTransferHandler.stitchingDirection = stitchingDirection;
    }

    public static int getTopLeftXPos() {
        return topLeftXPos;
    }

    public static void setTopLeftXPos(int topLeftXPos) {
        DataTransferHandler.topLeftXPos = topLeftXPos;
    }

    public static int getTopLeftYPos() {
        return topLeftYPos;
    }

    public static void setTopLeftYPos(int topLeftYPos) {
        DataTransferHandler.topLeftYPos = topLeftYPos;
    }

    public static int getNumOfSnakeBodyparts() {
        return numOfSnakeBodyparts;
    }

    public static void setNumOfSnakeBodyparts(int numOfSnakeBodyparts) {
        DataTransferHandler.numOfSnakeBodyparts = numOfSnakeBodyparts;
    }

    public static int[] getBodypartXPos() {
        return bodypartXPos;
    }

    public static void setBodypartXPos(int[] bodypartXPos) {
        DataTransferHandler.bodypartXPos = bodypartXPos;
    }

    public static int[] getBodypartYPos() {
        return bodypartYPos;
    }

    public static void setBodypartYPos(int[] bodypartYPos) {
        DataTransferHandler.bodypartYPos = bodypartYPos;
    }

    public static void setHeadDirection(Snake.Direction headDirection) { DataTransferHandler.headDirection = headDirection; }

    public static Snake.Direction getHeadDirection() { return headDirection; }

    public static int getFoodXPos() {
        return foodXPos;
    }

    public static void setFoodXPos(int foodXPos) {
        DataTransferHandler.foodXPos = foodXPos;
    }

    public static int getFoodYPos() {
        return foodYPos;
    }

    public static void setFoodYPos(int foodYPos) {
        DataTransferHandler.foodYPos = foodYPos;
    }

    public static int getScore() {
        return score;
    }

    public static void setScore(int score) {
        DataTransferHandler.score = score;
    }

    public static Snake.Direction getMovementDirection() { return movementDirection; }

    public static void setMovementDirection(Snake.Direction movementDirection) {DataTransferHandler.movementDirection = movementDirection; }

    public static void pushMessage(GameMessage message) {
        messageList.addLast(message);
    }

    public static GameMessage pollMessage() {
        return messageList.pollFirst();
    }

}
