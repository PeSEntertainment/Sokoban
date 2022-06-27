package com.pes.sokoban.Scenes;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.pes.sokoban.Constants.Constants;
import com.pes.sokoban.Screens.PlayScreen;

import static com.pes.sokoban.Global.Game.textureAtlasCommon;
import static com.pes.sokoban.Global.Game.virtualHeight;
import static com.pes.sokoban.Global.Game.virtualWidth;
import static com.pes.sokoban.Global.Game.portrait;

public class OSC implements Disposable {
    public Viewport viewport;
    public Stage stage;
    private PlayScreen screen;
    private int buttonSize;

    public OSC(SpriteBatch sb, final PlayScreen screen, Boolean areplayMode) {
        this.screen = screen;

        Integer stepBackRepPosX, stepBackRepPosY,
                stepForwardRepPosX, stepForwardRepPosY,
                replayRepPosX, replayRepPosY,
                backplayRepPosX, backplayRepPosY,
                pauseRepPosX, pauseRepPosY,

                stepBackPosX, stepBackPosY,
                stepForwardPosX, stepForwardPosY,
                upPosX, upPosY,
                downPosX, downPosY,
                leftPosX, leftPosY,
                rightPosX, rightPosY;

        buttonSize = 80;

        viewport = new FitViewport(virtualWidth, virtualHeight, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        if (portrait) {
            stepBackRepPosX=0; stepBackRepPosY=0;
            stepForwardRepPosX=virtualWidth - buttonSize; stepForwardRepPosY= 0;
            replayRepPosX=3*buttonSize; replayRepPosY= 0;
            backplayRepPosX=buttonSize; backplayRepPosY= 0;
            pauseRepPosX=2*buttonSize; pauseRepPosY= 0;

            stepBackPosX=virtualWidth - buttonSize; stepBackPosY= 2*buttonSize;
            stepForwardPosX=virtualWidth - buttonSize; stepForwardPosY= 3*buttonSize;
            upPosX=0; upPosY=buttonSize;
            downPosX=0; downPosY=0;
            leftPosX=virtualWidth - 2 * buttonSize;leftPosY=0;
            rightPosX=virtualWidth - buttonSize;rightPosY=0;
        }
        else {
            //Todo nastavit Landscape
            stepBackRepPosX=0; stepBackRepPosY=0;
            stepForwardRepPosX=virtualWidth - buttonSize; stepForwardRepPosY= 0;

            pauseRepPosX=0; pauseRepPosY= buttonSize;
            backplayRepPosX=virtualWidth-buttonSize; backplayRepPosY= buttonSize;
            replayRepPosX=backplayRepPosX; replayRepPosY= backplayRepPosY+buttonSize;



            stepBackPosX=virtualWidth - buttonSize; stepBackPosY= buttonSize;
            stepForwardPosX=virtualWidth - buttonSize; stepForwardPosY= buttonSize+buttonSize;
            upPosX=0; upPosY=buttonSize;
            downPosX=0; downPosY=0;
            leftPosX=virtualWidth - 2 * buttonSize;leftPosY=0;
            rightPosX=virtualWidth - buttonSize;rightPosY=0;
        }

        stage.addListener(new InputListener());

        if (areplayMode) { // LEVEL IS SOLVED - SET TO REPLAY MODE
            // STEP BACK
            Image stepBackImg = new Image(textureAtlasCommon.findRegion(Constants.S_OSCREPPREV));
            stepBackImg.setPosition(stepBackRepPosX, stepBackRepPosY);
            stepBackImg.setSize(buttonSize, buttonSize);
            stage.addActor(stepBackImg);
            stepBackImg.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    screen.player.back();
                    return true;
                }
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                }
            });
            // STEP FORWARD
            Image stepForwardImg = new Image(textureAtlasCommon.findRegion(Constants.S_OSCREPNEXT));
            stepForwardImg.setPosition(stepForwardRepPosX, stepForwardRepPosY);
            stepForwardImg.setSize(buttonSize, buttonSize);
            stage.addActor(stepForwardImg);
            stepForwardImg.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    screen.player.forward();
                    return true;
                }
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                }
            });
            // AUTO REPLAY
            Image playImg = new Image(textureAtlasCommon.findRegion(Constants.S_OSCREPFORWARD));
            playImg.setPosition(replayRepPosX, replayRepPosY);
            playImg.setSize(buttonSize, buttonSize);
            stage.addActor(playImg);
            playImg.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    screen.player.autoReplay();
                    return true;
                }
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                }
            });
            // AUTO BACKPLAY
            Image backplayImg = new Image(textureAtlasCommon.findRegion(Constants.S_OSCREPBACK));
            backplayImg.setPosition(backplayRepPosX, backplayRepPosY);
            backplayImg.setSize(buttonSize, buttonSize);
            stage.addActor(backplayImg);
            backplayImg.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    screen.player.autoBackplay();
                    return true;
                }
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                }
            });
            // PAUSE
            Image pauseImg = new Image(textureAtlasCommon.findRegion(Constants.S_OSCREPPAUSE));
            pauseImg.setPosition(pauseRepPosX, pauseRepPosY);
            pauseImg.setSize(buttonSize, buttonSize);
            stage.addActor(pauseImg);
            pauseImg.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    screen.player.pause();
                    return true;
                }
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                }
            });


        }
        else {  // LEVEL IS NOT SOLVED - SET TO PLAY MODE
            // UP
            Image upImg = new Image(textureAtlasCommon.findRegion(Constants.S_OSCUP));
            upImg.setPosition(upPosX, upPosY);
            upImg.setSize(buttonSize, buttonSize);
            stage.addActor(upImg);
            upImg.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    screen.player.up();
                    return true;
                }
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    screen.player.stopmoving();
                }
            });
            // DOWN
            Image downImg = new Image(textureAtlasCommon.findRegion(Constants.S_OSCDOWN));
            downImg.setPosition(downPosX, downPosY);
            downImg.setSize(buttonSize, buttonSize);
            stage.addActor(downImg);
            downImg.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    screen.player.down();
                    return true;
                }
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    screen.player.stopmoving();
                }
            });
            // RIGHT
            Image rightImg = new Image(textureAtlasCommon.findRegion(Constants.S_OSCRIGHT));
            rightImg.setPosition(rightPosX, rightPosY);
            rightImg.setSize(buttonSize, buttonSize);
            stage.addActor(rightImg);
            rightImg.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    screen.player.right();
                    return true;
                }
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    screen.player.stopmoving();
                }
            });
            // LEFT
            Image leftImg = new Image(textureAtlasCommon.findRegion(Constants.S_OSCLEFT));
            leftImg.setPosition(leftPosX, leftPosY);
            leftImg.setSize(buttonSize, buttonSize);
            stage.addActor(leftImg);
            leftImg.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    screen.player.left();
                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    screen.player.stopmoving();
                }
            });
            // STEP BACK
            Image backImg = new Image(textureAtlasCommon.findRegion(Constants.S_OSCPREV));
            backImg.setPosition(stepBackPosX, stepBackPosY);
            backImg.setSize(buttonSize, buttonSize);
            stage.addActor(backImg);
            backImg.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    screen.player.back();
                    return true;
                }
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                }
            });
            // STEP FORWARD
            Image forwardImg = new Image(textureAtlasCommon.findRegion(Constants.S_OSCNEXT));
            forwardImg.setPosition(stepForwardPosX, stepForwardPosY);
            forwardImg.setSize(buttonSize, buttonSize);
            stage.addActor(forwardImg);
            forwardImg.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    screen.player.forward();
                    return true;
                }
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                }
            });
        }
    }

    public void draw() {
        stage.draw();
    }

    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
