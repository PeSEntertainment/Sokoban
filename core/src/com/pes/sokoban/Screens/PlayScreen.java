package com.pes.sokoban.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.pes.sokoban.Constants.Constants;
import com.pes.sokoban.Constants.Ipega;
import com.pes.sokoban.Scenes.HUD;
import com.pes.sokoban.Scenes.OSC;
import com.pes.sokoban.Sprites.DeskItem;
import com.pes.sokoban.Sprites.DeskItemBox;
import com.pes.sokoban.Sprites.DeskItemGround;
import com.pes.sokoban.Sprites.DeskItemTarget;
import com.pes.sokoban.Sprites.DeskItemWall;
import com.pes.sokoban.Sprites.Sokoban;
import com.pes.sokoban.SokobanGame;

import static com.pes.sokoban.Global.Game.musicEnabled;
import static com.pes.sokoban.Global.Game.screenAspectRatio;
import static com.pes.sokoban.Global.Game.activeLevel;
import static com.pes.sokoban.Global.Game.lastLevel;
import static com.pes.sokoban.Global.Game.soundEnabled;
import static com.pes.sokoban.Global.Game.soundTap;
import static com.pes.sokoban.Global.Game.soundVolume;
import static com.pes.sokoban.Global.Game.textureAtlasSkin;
import static com.pes.sokoban.Global.Game.virtualHeight;
import static com.pes.sokoban.Global.Game.virtualWidth;


public class PlayScreen implements Screen, GestureDetector.GestureListener, InputProcessor, ControllerListener {
    public SokobanGame game;
    private OrthographicCamera camera;
    private FitViewport fitViewport;
    private HUD hud;
    private OSC onScreenController;
    private SpriteBatch spriteBatch;
    // Sprites
    public Sokoban player;
    public Array<DeskItem> boxes;
    private Array<DeskItem> deskItems;
    // Active level
    public boolean replayMode = Constants.OSC_PLAY;

    public boolean walkMode=false;

    private InputMultiplexer im;

    private float magnify;
    private float origDistance, baseDistance, origZoom;


    public PlayScreen(SokobanGame pGame) {
        game = pGame;
        loadAssets();

        deskItems = new Array<DeskItem>();
        boxes =  new Array<DeskItem>();
        player = new Sokoban(this,1,1);

        camera = new OrthographicCamera(12 , 12);
        lastLevel = null;
        loadActiveLevel();
        setView();

        spriteBatch = new SpriteBatch();

        GestureDetector gd = new GestureDetector(this);
        Controllers.addListener(this);
/*        if(Controllers.getControllers().size == 0)
        {
            hasControllers = false;
        }
*/        im = new InputMultiplexer();
        im.addProcessor(gd);
        im.addProcessor(this);

    }


    private void loadAssets(){
    }

    private void setView(){
        float cameraWidth = activeLevel.width;
        float cameraHeight = activeLevel.height;
        camera.zoom = 1; origZoom = 1; origDistance=0; baseDistance=0;

        if ((cameraWidth / cameraHeight)>screenAspectRatio) cameraHeight = cameraWidth /screenAspectRatio;
        else cameraWidth = cameraHeight *screenAspectRatio;
        magnify = Gdx.graphics.getWidth()/ cameraWidth;

        camera.setToOrtho(false, cameraWidth, cameraHeight);
        camera.position.x = (float)activeLevel.width/2;
        camera.position.y = (float) activeLevel.height/2;

        fitViewport = new FitViewport(cameraWidth, cameraHeight, camera);  // ////////////////////////////
        fitViewport.setCamera(camera);

    }

    private void setHUD(){
        if (hud!=null) {
            im.removeProcessor(hud.stage);
            hud.dispose();
        }
        hud = new HUD(spriteBatch, this);
        im.addProcessor(hud.stage);
    }


    public void setOSC(){
        if (onScreenController!= null) {
            im.removeProcessor(onScreenController.stage);
            onScreenController.dispose();
        }
        onScreenController = new OSC(spriteBatch, this, replayMode);
        im.addProcessor(onScreenController.stage);
    }


    @Override
    public void show() {

        Gdx.input.setInputProcessor(im);
        if (activeLevel!=lastLevel) loadActiveLevel();
        replayMode = false;
        if(activeLevel.solved) {
                replayMode = true;
                activeLevel.moves = activeLevel.solved_moves;
                activeLevel.time = activeLevel.solved_time;
                activeLevel.setToStart();
                activeLevel.inPlay = false;
                loadActiveLevel();
        }
        else {
            activeLevel.inPlay = true;
        }
        setView();
        setOSC();
        setHUD();


    }

    public void setToStart(){
        activeLevel.setToStart();
        loadActiveLevel();
        setOSC();
    }

     private void loadActiveLevel(){
         lastLevel = activeLevel;
         activeLevel.inPlay = true;
         game.addLevelToPlayed(activeLevel);
         deskItems.clear(); boxes.clear();
         player = new Sokoban(this,1,1);
         for (int j=0; j<activeLevel.height; j++)
             for (int i=0; i<activeLevel.width; i++) {
                 switch (activeLevel.desk[i][j]) {
                     case Constants.ID_EMPTY:
                         break;
                     case Constants.ID_GROUND:
                         deskItems.add(new DeskItemGround(this, i, j));
                         break;
                     case Constants.ID_TARGET:
                         deskItems.add(new DeskItemTarget(this, i, j));
                         break;
                     case Constants.ID_BOX:
                         deskItems.add(new DeskItemGround(this, i, j));
                         break;
                     case Constants.ID_BOXONTARGET:
                         deskItems.add(new DeskItemTarget(this, i, j));
                         break;
                     default: // nebyl to ani jeden z predchozich, tak to musi byt wall
                         deskItems.add(new DeskItemWall(this, i, j, activeLevel.desk[i][j]));
                         break;

                 }
                 switch (activeLevel.situation[i][j]) {
                     case Constants.ID_BOX:
                         boxes.add(new DeskItemBox(this,i,j));
                         break;
                     case Constants.ID_BOXONTARGET:
                         boxes.add(new DeskItemBox(this,i,j));
                         break;
                     case Constants.ID_SOKOBAN:
                         player.setPosition(i,j); player.setPos(i,j);
                         break;
                 }
             }
     }

    public void render(float delta) {
        update(delta);
        // CLEAR
//        Gdx.gl.glClearColor(0.4f, 0.4f, 0.4f, 1);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.setProjectionMatrix(camera.combined); fitViewport.apply();
        spriteBatch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        spriteBatch.begin();

        float bgX, bgY, bgW, bgH;
        bgW = fitViewport.getScreenWidth(); bgH = fitViewport.getScreenHeight();
        bgX = (virtualWidth - bgW)/2; bgY = (virtualHeight - bgH)/2;
        spriteBatch.draw(textureAtlasSkin.findRegion(Constants.S_BCKGRPLAY),bgX, bgY, bgW, bgH);

        // draw desk
        for (DeskItem deskItem: deskItems) deskItem.draw(spriteBatch);
        // player
        player.draw(spriteBatch);
        // draw situation
        for (DeskItem box: boxes) box.draw(spriteBatch);
        //
        spriteBatch.end();
        // HUD DISPLAY
        spriteBatch.setProjectionMatrix(hud.stage.getCamera().combined);fitViewport.apply();
        hud.stage.draw();
        // ON SCREEN CONTROLLER
        spriteBatch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_DST_ALPHA);
        spriteBatch.setProjectionMatrix(onScreenController.stage.getCamera().combined);
        onScreenController.draw();
    }

    @Override
    public void resize(int width, int height) {
        game.setViewport();
        show();
        fitViewport.update(width,height, false);
    }

    @Override
    public void pause() {
        game.platform.pause();
    }

    @Override
    public void resume() {
        if (musicEnabled) game.platform.play();
    }

    @Override
    public void hide() {
        if (activeLevel!=null) {
            game.updateLevelsPlayed(activeLevel);
            game.save();
        }
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        onScreenController.dispose();
        hud.dispose();
//        textureAtlas.dispose();
    }


//    private void handleInput(float dt) {
//    }

    private void update(float dt){
//        handleInput(dt);
        for (DeskItem box: boxes) box.update(dt);
        player.update(dt);
        hud.update(dt);
        camera.update();
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        if (count == 2) {
            camera.zoom = 1;
            camera.position.x = (float) activeLevel.width/2;
            camera.position.y = (float) activeLevel.height/2;
            return true;
        }
        else return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        float cameraNewPosX, cameraNewPosY;
        if (camera.zoom == 1) {
            camera.position.x = (float)activeLevel.width/2;
            camera.position.y = (float)activeLevel.height/2;
        }
        else {
            cameraNewPosX = camera.position.x - deltaX / magnify * camera.zoom;
            cameraNewPosY = camera.position.y + deltaY / magnify * camera.zoom;
            camera.position.x = cameraNewPosX;
            camera.position.y = cameraNewPosY;
        }
        return true;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        if(origDistance != initialDistance){
            origDistance = initialDistance;
            baseDistance = initialDistance;
            origZoom = camera.zoom;
        }
        float ratio = baseDistance/distance;
        float newZoom = origZoom*ratio;

        if (newZoom >= 1) {
            camera.zoom = 1;
            origZoom = 1;
            baseDistance = distance;
        } else if (newZoom <= 4/magnify) {
            camera.zoom = (float) 4/magnify;
            origZoom = (float) 4/magnify;
            baseDistance = distance;
        } else {
            camera.zoom = newZoom;
        }
        return true;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;

    }

    @Override
    public void pinchStop() {
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.BACK) {
            if (soundEnabled) soundTap.play(soundVolume);
            game.updateLevelsPlayed(activeLevel);
            game.changeScreen(Constants.MENU);
            return true;
        }
        else return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        if (character == Input.Keys.UP) player.up();
        if (character == Input.Keys.DOWN) player.down();
        if (character == Input.Keys.RIGHT) player.right();
        if (character == Input.Keys.LEFT) player.left();
        if (character == Input.Keys.SPACE) player.back();
        if (character == Input.Keys.Q) {
//            game.setScreen(new LevelsScreen(game));
            game.changeScreen(Constants.MENU);
            dispose();
        }
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    @Override
    public void connected(Controller controller) {
//        hasControllers = true;
    }

    @Override
    public void disconnected(Controller controller) {
//        hasControllers = false;
    }

    @Override
    public boolean buttonDown(Controller controller, int buttonCode) {
        if(buttonCode == Ipega.BUTTON_Y)
            player.up();
        if(buttonCode == Ipega.BUTTON_A)
            player.down();
        if(buttonCode == Ipega.BUTTON_X)
            player.left();
        if(buttonCode == Ipega.BUTTON_B)
            player.right();

        if(buttonCode == Ipega.BUTTON_L2)
            player.back();
        if(buttonCode == Ipega.BUTTON_R2)
            player.forward();
        if(buttonCode == Ipega.BUTTON_L1) {
            game.updateLevelsPlayed(activeLevel);
            game.changeScreen(Constants.MENU);
        }


        return false;
    }

    @Override
    public boolean buttonUp(Controller controller, int buttonCode) {
        if(buttonCode == Ipega.BUTTON_Y)
            player.stopmoving();
        if(buttonCode == Ipega.BUTTON_A)
            player.stopmoving();
        if(buttonCode == Ipega.BUTTON_X)
            player.stopmoving();
        if(buttonCode == Ipega.BUTTON_B)
            player.stopmoving();
        return false;
    }

    @Override
    public boolean axisMoved(Controller controller, int axisCode, float value) {
        // Left Stick
        if(axisCode == Ipega.AXIS_LEFT_X) {
            if (value < -0.9f) player.left();  //0.96 fungovalo
            else if (value > 0.9f) player.right();
            else player.stopmoving();
        }
        if(axisCode == Ipega.AXIS_LEFT_Y) {
            if (value < -0.9f) player.up();
            else if (value > 0.9f) player.down();
            else player.stopmoving();
        }
        // Right stick
        if(axisCode == Ipega.AXIS_RIGHT_X) {
            if (value < -0.5f) player.back();
            else if (value > 0.5f) player.forward();
        }
        if(axisCode == Ipega.AXIS_RIGHT_Y) {
            if (value < -0.9f) player.forward();
            else if (value > 0.9f) player.back();
        }

        return false;
    }

    @Override
    public boolean povMoved(Controller controller, int povCode, PovDirection value) {
        if (value == PovDirection.north) player.up();
        if (value == PovDirection.south) player.down();
        if (value == PovDirection.east) player.right();
        if (value == PovDirection.west) player.left();

        return false;
    }

    @Override
    public boolean xSliderMoved(Controller controller, int sliderCode, boolean value) {
        return false;
    }

    @Override
    public boolean ySliderMoved(Controller controller, int sliderCode, boolean value) {
        return false;
    }

    @Override
    public boolean accelerometerMoved(Controller controller, int accelerometerCode, Vector3 value) {
        return false;
    }
}
