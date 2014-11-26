package hunt.snake.com.snaketreasurehunt;

import android.graphics.Paint;

import java.util.List;

import hunt.snake.com.framework.Game;
import hunt.snake.com.framework.Graphics;
import hunt.snake.com.framework.Input.TouchEvent;
import hunt.snake.com.framework.Screen;
import hunt.snake.com.framework.impl.AndroidGame;

public class GameScreen extends Screen {
    enum GameState {
        READY,
        RUNNING,
        PAUSED,
        GAME_OVER
    }

    private static final String READY_TEXT = "Ready?";
    private static final String PAUSED_TEXT = "Paused";

    GameState state;
    GameBoard gameBoard;
    int oldScore;
    String score;

    public GameScreen(Game game) {
        super(game);
        gameBoard = new GameBoard();
        init();
    }

    private void init() {
        state = GameState.READY;
        gameBoard.init();
        oldScore = 0;
        score = "0";
    }

    @Override
    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();

        if(state == GameState.READY) {
            updateReady(touchEvents);
            return;
        }
        if(state == GameState.RUNNING) {
            updateRunning(touchEvents, deltaTime);
            return;
        }
        if(state == GameState.PAUSED) {
            updatePaused(touchEvents);
            return;
        }
        if(state == GameState.GAME_OVER) {
            updateGameOver(touchEvents);
        }
    }

    private void updateReady(List<TouchEvent> touchEvents) {
        if(touchEvents.size() > 0) {
            // change from "ready" to "running" if screen was touched
            state = GameState.RUNNING;
        }
    }

    private void updateRunning(List<TouchEvent> touchEvents, float deltaTime) {
        int size = touchEvents.size();
        for(int i = 0; i < size; i++) {
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP) {
                // change from "running" to "paused" if screen was touched
                state = GameState.PAUSED;
                return;
            }
        }

        // update the game board and check whether the game is over
        gameBoard.update(deltaTime);
        if(gameBoard.isGameOver()) {
            state = GameState.GAME_OVER;
        }

        // only update score if it has changed --> avoid unnecessary String object creation
        if(oldScore != gameBoard.getScore()) {
            oldScore = gameBoard.getScore();
            score = "" + oldScore;
        }
    }

    private void updatePaused(List<TouchEvent> touchEvents) {
        int size = touchEvents.size();
        for(int i = 0; i < size; i++) {
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP) {
                // change from "paused" to "running" if screen was touched
                state = GameState.RUNNING;
                return;
            }
        }
    }

    private void updateGameOver(List<TouchEvent> touchEvents) {
    }

    @Override
    public void present(float deltaTime) {
        Graphics g = game.getGraphics();

        drawGameBoard(g);

        if(state == GameState.READY) {
            drawReadyUI(g);
        }
        if(state == GameState.RUNNING) {
            drawRunningUI(g);
        }
        if(state == GameState.PAUSED) {
            drawPausedUI(g);
        }
        if(state == GameState.GAME_OVER) {
            drawGameOverUI(g);
        }
    }

    private void drawGameBoard(Graphics g) {
        gameBoard.draw(g);
    }

    private void drawReadyUI(Graphics g) {
        // darken screen with transparent layer
        g.drawRect(0, 0, AndroidGame.getScreenWidth(), AndroidGame.getScreenHeight(), Constants.LAYER_COLOR.getValue());

        // draw ready text
        g.drawText(READY_TEXT, AndroidGame.getScreenWidth() / 2, AndroidGame.getScreenHeight() / 2, Constants.TEXT_COLOR.getValue(), Constants.TEXT_SIZE_XXL.getValue(), Paint.Align.CENTER);
    }

    private void drawRunningUI(Graphics g) {
        // draw score
        g.drawText(score, 50, 50, Constants.TEXT_COLOR.getValue(), Constants.TEXT_SIZE_S.getValue(), Paint.Align.CENTER);
    }

    private void drawPausedUI(Graphics g) {
        // darken screen with transparent layer
        g.drawRect(0, 0, AndroidGame.getScreenWidth(), AndroidGame.getScreenHeight(), Constants.LAYER_COLOR.getValue());

        // draw paused text
        g.drawText(PAUSED_TEXT, AndroidGame.getScreenWidth() / 2, AndroidGame.getScreenHeight() / 2, Constants.TEXT_COLOR.getValue(), Constants.TEXT_SIZE_XXL.getValue(), Paint.Align.CENTER);
    }

    private void drawGameOverUI(Graphics g) {
    }

    @Override
    public void pause() {
        // pause game if app is closed and continues operating in background
        if(state == GameState.RUNNING) {
            state = GameState.PAUSED;
        }
    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}