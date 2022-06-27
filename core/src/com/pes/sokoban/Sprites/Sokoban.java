package com.pes.sokoban.Sprites;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.pes.sokoban.Constants.Constants;
import com.pes.sokoban.Screens.MenuScreen;
import com.pes.sokoban.Screens.PlayScreen;

import static com.pes.sokoban.Global.Game.activeLevel;
import static com.pes.sokoban.Global.Game.infoMode;
import static com.pes.sokoban.Global.Game.music;
import static com.pes.sokoban.Global.Game.musicEnabled;
import static com.pes.sokoban.Global.Game.soundEnabled;
import static com.pes.sokoban.Global.Game.soundVolume;
import static com.pes.sokoban.Global.Game.replayStepIn;
import static com.pes.sokoban.Global.Game.assetManager;
import static com.pes.sokoban.Global.Game.textureAtlasSkin;

public class Sokoban extends DeskItem {
    private enum State {STANDING, WALKING, PUSHING, WIN, TIMEUP, AUTOREPLAY, AUTOBACKPLAY };

    private static final int MD_STANDING = 0;
    private static final int MD_UP = 1;
    private static final int MD_DOWN = 2;
    private static final int MD_LEFT = 3;
    private static final int MD_RIGHT = 4;

    public State currentState;
    protected float stateTimer, soundTimer;
    protected boolean soundMute;
    private int directionX, directionY;
    private DeskItem movedBox;
    private boolean moving;
    private int movingDirection = MD_STANDING;

    private static final float SPEED = 2;
    private Animation
                    sokobanWalkingLeft,sokobanWalkingRight,sokobanWalkingUp,sokobanWalkingDown,
                    sokobanStanding,
                    sokobanPushing,
                    sokobanWin;
    private Sound   soundWalk,
                    soundJump,
                    soundPush,
                    soundWin;


    public Sokoban(PlayScreen screen, int x, int y) {
        super(screen, x, y);
        ID = Constants.ID_SOKOBAN;
        assingAssets();

        currentState = State.STANDING; stateTimer = 0;
        directionX = 0; directionY = 0;
        soundMute = true;
        moving = false;
    }

    private void assingAssets() {
        soundWalk = assetManager.get(Constants.S_SOUNDWALK, Sound.class);
        soundWin = assetManager.get(Constants.S_SOUNDWIN, Sound.class);
        soundPush = assetManager.get(Constants.S_SOUNDPUSH, Sound.class);
        soundJump = assetManager.get(Constants.S_SOUNDJUMP, Sound.class);

        sokobanWalkingLeft = new Animation(1 / 60f,
                textureAtlasSkin.findRegion("Levo"),
                textureAtlasSkin.findRegion("chuze, levo L1"),
                textureAtlasSkin.findRegion("chuze, levo L2"),
                textureAtlasSkin.findRegion("chuze, levo L3"),
                textureAtlasSkin.findRegion("chuze, levo L2"),
                textureAtlasSkin.findRegion("chuze, levo L1"),
                textureAtlasSkin.findRegion("Levo"),
                textureAtlasSkin.findRegion("chuze, levo P1"),
                textureAtlasSkin.findRegion("chuze, levo P2"),
                textureAtlasSkin.findRegion("chuze, levo P3"),
                textureAtlasSkin.findRegion("chuze, levo P2"),
                textureAtlasSkin.findRegion("chuze, levo P1"));
        sokobanWalkingRight = new Animation(1 / 60f,
                textureAtlasSkin.findRegion("Pravo"),
                textureAtlasSkin.findRegion("chuze, pravo L1"),
                textureAtlasSkin.findRegion("chuze, pravo L2"),
                textureAtlasSkin.findRegion("chuze, pravo L3"),
                textureAtlasSkin.findRegion("chuze, pravo L2"),
                textureAtlasSkin.findRegion("chuze, pravo L1"),
                textureAtlasSkin.findRegion("Pravo"),
                textureAtlasSkin.findRegion("chuze, pravo P1"),
                textureAtlasSkin.findRegion("chuze, pravo P2"),
                textureAtlasSkin.findRegion("chuze, pravo P3"),
                textureAtlasSkin.findRegion("chuze, pravo P2"),
                textureAtlasSkin.findRegion("chuze, pravo P1"));
        sokobanWalkingUp = new Animation(1 / 60f,
                textureAtlasSkin.findRegion("Zad"),
                textureAtlasSkin.findRegion("chuze, zad L1"),
                textureAtlasSkin.findRegion("chuze, zad L2"),
                textureAtlasSkin.findRegion("chuze, zad L3"),
                textureAtlasSkin.findRegion("chuze, zad L2"),
                textureAtlasSkin.findRegion("chuze, zad L1"),
                textureAtlasSkin.findRegion("Zad"),
                textureAtlasSkin.findRegion("chuze, zad P1"),
                textureAtlasSkin.findRegion("chuze, zad P2"),
                textureAtlasSkin.findRegion("chuze, zad P3"),
                textureAtlasSkin.findRegion("chuze, zad P2"),
                textureAtlasSkin.findRegion("chuze, zad P1"));
        sokobanWalkingDown = new Animation(1 / 60f,
                textureAtlasSkin.findRegion("Pred"),
                textureAtlasSkin.findRegion("chuze, pred L1"),
                textureAtlasSkin.findRegion("chuze, pred L2"),
                textureAtlasSkin.findRegion("chuze, pred L3"),
                textureAtlasSkin.findRegion("chuze, pred L2"),
                textureAtlasSkin.findRegion("chuze, pred L1"),
                textureAtlasSkin.findRegion("Pred"),
                textureAtlasSkin.findRegion("chuze, pred P1"),
                textureAtlasSkin.findRegion("chuze, pred P2"),
                textureAtlasSkin.findRegion("chuze, pred P3"),
                textureAtlasSkin.findRegion("chuze, pred P2"),
                textureAtlasSkin.findRegion("chuze, pred P1"));

        sokobanStanding = new Animation(1 / 10f,
                textureAtlasSkin.findRegion("Pred"));
        sokobanPushing = new Animation(1 / 30f,
                textureAtlasSkin.findRegion("Pred"));
        sokobanWin = new Animation(1 / 15f,
                textureAtlasSkin.findRegion("Pred"),
                textureAtlasSkin.findRegion("Levo"),
                textureAtlasSkin.findRegion("Zad"),
                textureAtlasSkin.findRegion("Pravo"));
    }

    public void update(float dt) {
        stateTimer += dt;
        soundTimer += dt;
        boolean newPosition = false;
        switch (currentState) {
            case PUSHING:
                // SOUND START
                if (screen.walkMode && soundMute) {
                    if (soundEnabled) soundPush.play(soundVolume);
                    soundMute = false;
                }
                if (directionY == 0) {
                    if (directionX <0) {
                        if (getX() < i + directionX) newPosition = true;
                    }
                    else if (getX() > i+directionX) newPosition=true;
                }
                else {
                    if (directionY<0) {
                        if (getY() < j + directionY) newPosition = true;
                    }
                    else if (getY() > j+directionY) newPosition = true;
                }
                if (newPosition) updatePushingMove();
                else {
                    translateX(directionX * SPEED * dt);
                    translateY(directionY * SPEED * dt);
                    movedBox.translateX(directionX * SPEED * dt);
                    movedBox.translateY(directionY * SPEED * dt);
                }
                break;
            case WALKING:
                if (directionY == 0) {
                    if (directionX <0) {
                        if (getX() < i + directionX) newPosition = true;
                    }
                    else if (getX() > i+directionX) newPosition=true;
                }
                else {
                    if (directionY<0) {
                        if (getY() < j + directionY) newPosition = true;
                    }
                    else if (getY() > j+directionY) newPosition = true;
                }
                if (newPosition) updateWalkingMove();
                else {
                    translateX(directionX * SPEED * dt);
                    translateY(directionY * SPEED * dt);
                }
                break;
            case STANDING:
                soundMute = true;
                if (screen.walkMode) {
                    if (moving) {
                        switch (movingDirection) {
                            case MD_UP:
                                up();
                                break;
                            case MD_DOWN:
                                down();
                                break;
                            case MD_LEFT:
                                left();
                                break;
                            case MD_RIGHT:
                                right();
                                break;
                        }
                    }
                }
                break;
            case WIN:
                 if ((!soundEnabled&&stateTimer>2) || stateTimer>4) {
                     currentState = State.STANDING;
                     infoMode = Constants.IM_WIN;
                     screen.game.changeScreen(Constants.INFO);
                     if (musicEnabled) screen.game.platform.play();
                 }
                 break;
            case AUTOREPLAY:
                if (stateTimer > replayStepIn) {
                    if (activeLevel.nextMove <activeLevel.moves.length()) {
                        stateTimer = 0;
                        forwardPosition();
                    }
                    else currentState = State.STANDING;
                }
                break;
            case AUTOBACKPLAY:
                if (stateTimer > replayStepIn) {
                    if (activeLevel.nextMove >0) {
                        stateTimer = 0;
                        backPosition();
                    }
                    else currentState = State.STANDING;
                }
                break;

        }
        setRegion(getFrame(dt));
    }

    private void updateWalkingPosition(){
        if (!screen.walkMode && !screen.replayMode) if (soundEnabled) soundJump.play(soundVolume);
        activeLevel.situation [i][j] = Constants.ID_EMPTY;;
        i += directionX; j += directionY; setX(i); setY(j);
        activeLevel.situation [i][j] = Constants.ID_SOKOBAN;
        activeLevel.nextMove+=1;
    }

    private void updatePushingPosition() {
        updateWalkingPosition();
        activeLevel.situation [i+directionX][j+directionY] = Constants.ID_BOX;
        setBoxNewPosition();
    }

    private void updateWalkingMove(){
        currentState = State.STANDING; stateTimer = 0;
        updateWalkingPosition();
        // STORE MOVE
        if (directionY==0) {
            if (directionX < 0) activeLevel.moves = activeLevel.moves.concat("l");
            else activeLevel.moves = activeLevel.moves.concat("r");
        }
        else {
            if (directionY < 0) activeLevel.moves = activeLevel.moves.concat("d");
            else activeLevel.moves = activeLevel.moves.concat("u");
        }
    }

    private void updatePushingMove(){
        currentState = State.STANDING; stateTimer = 0;
        updatePushingPosition();
        // STORE MOVE
        if (directionY==0) {
            if (directionX < 0) activeLevel.moves = activeLevel.moves.concat("L");
            else activeLevel.moves = activeLevel.moves.concat("R");
        }
        else {
            if (directionY < 0) activeLevel.moves = activeLevel.moves.concat("D");
            else activeLevel.moves = activeLevel.moves.concat("U");
        }
        if (activeLevel.isJustSolved()) {
            if (soundEnabled) {
                if (musicEnabled) screen.game.platform.pause();
                soundWin.play(soundVolume);
            }
            currentState = State.WIN;
//            activeLevel.solved = true;
//            screen.game.updateLevelsPlayed(activeLevel);
        }
    }
    
    
    private TextureRegion getFrame(float dt) {
        TextureRegion region;
        switch (currentState) {
            case PUSHING:
            case WALKING:
                if (directionX!=0) {
//                    region = (TextureRegion) sokobanWalkingLeft.getKeyFrame(stateTimer, true);
//                    if (directionX > 0) region.isFlipX();
                    if (directionX <0) region = (TextureRegion) sokobanWalkingLeft.getKeyFrame(stateTimer, true);
                    else region = (TextureRegion) sokobanWalkingRight.getKeyFrame(stateTimer, true);
/*
                    else {
                        region = (TextureRegion) sokobanWalkingLeft.getKeyFrame(stateTimer, true);
                        region.flip(true, false);
                    }
*/
                }
                else {
                    if (directionY <0) region = (TextureRegion) sokobanWalkingDown.getKeyFrame(stateTimer, true);
                    else region = (TextureRegion) sokobanWalkingUp.getKeyFrame(stateTimer, true);

                }
                break;
            case WIN:
                region = (TextureRegion) sokobanWin.getKeyFrame(stateTimer, true);
                break;
            default:
                if (directionY == 0) {
/*                    if (directionX == 0)
                        region = (TextureRegion) sokobanStanding.getKeyFrame(stateTimer, true);
                    else {

                        region = (TextureRegion) sokobanWalkingLeft.getKeyFrame(0, false);
                        if (directionX > 0) region.isFlipX();
                    }
                }
*/


                    if (directionX<0) region = (TextureRegion) sokobanWalkingLeft.getKeyFrame(0, false);
                    else if (directionX>0) region = (TextureRegion) sokobanWalkingRight.getKeyFrame(0, false);
                    else region = (TextureRegion) sokobanStanding.getKeyFrame(stateTimer, true);
                }


                else {
                    if (directionY<0) region = (TextureRegion) sokobanWalkingDown.getKeyFrame(0, false);
                    else region = (TextureRegion) sokobanWalkingUp.getKeyFrame(0, false);
                }
                break;
        }
        return region;
    }

    public void stopmoving(){
        moving = false;
    }

    public void up() {
        movingDirection = MD_UP;
        eventuallyMove(0,1);
    }
    public void down() {
        movingDirection = MD_DOWN;
        eventuallyMove(0,-1);
    }
    public void left(){
        movingDirection = MD_LEFT;
        eventuallyMove(-1,0);
    }
    public void right(){
        movingDirection = MD_RIGHT;
        eventuallyMove(1,0);
    }


    public void back(){
        moving = false;
        currentState = State.STANDING;
        backPosition();
    }

    private void backPosition(){
          if (activeLevel.nextMove >0) {
              if (!screen.walkMode && !screen.replayMode) if (soundEnabled) soundJump.play(soundVolume);
//              currentState = State.STANDING;
              activeLevel.nextMove-=1;
              Character undoMove = activeLevel.moves.charAt(activeLevel.nextMove);
              switch (undoMove) {
                case 'U':
                    // boxes graphic
                    for (DeskItem box: screen.boxes) {
                        if (box.i == i && box.j == j + 1) {
                            box.j -= 1; box.setPosition(box.i, box.j);
                        }
                    }    
                    // situation    
                    activeLevel.situation[i][j] = Constants.ID_BOX;
                    activeLevel.situation[i][j+1] = Constants.ID_EMPTY;
                    setY(getY()-1); j -= 1;
                    activeLevel.situation[i][j] = Constants.ID_SOKOBAN;
                    break;
                case 'D':
                    // boxes graphic
                    for (DeskItem box: screen.boxes) {
                        if (box.i == i && box.j == j - 1) {
                            box.j += 1; box.setPosition(box.i, box.j);
                        }
                    }    
                    // situation    
                    activeLevel.situation[i][j] = Constants.ID_BOX;
                    activeLevel.situation[i][j-1] = Constants.ID_EMPTY;
                    setY(getY()+1); j += 1;
                    activeLevel.situation[i][j] = Constants.ID_SOKOBAN;
                    break;
                case 'L':
                    // boxes graphic
                    for (DeskItem box: screen.boxes) {
                        if (box.i == i-1 && box.j == j) {
                            box.i += 1; box.setPosition(box.i, box.j);
                        }
                    }
                    // situation    
                    activeLevel.situation[i][j] = Constants.ID_BOX;
                    activeLevel.situation[i-1][j] = Constants.ID_EMPTY;
                    setX(getX()+1); i += 1;
                    activeLevel.situation[i][j] = Constants.ID_SOKOBAN;
                    break;
                case 'R':
                    // boxes graphic
                    for (DeskItem box: screen.boxes) {
                        if (box.i == i+1 && box.j == j) {
                            box.i -= 1; box.setPosition(box.i, box.j);
                        }
                    }
                    // situation    
                    activeLevel.situation[i][j] = Constants.ID_BOX;
                    activeLevel.situation[i+1][j] = Constants.ID_EMPTY;
                    setX(getX()-1); i -= 1;
                    activeLevel.situation[i][j] = Constants.ID_SOKOBAN;
                    break;
                case 'u':
                    // situation    
                    activeLevel.situation[i][j] = Constants.ID_EMPTY;
                    setY(getY()-1); j -= 1;
                    activeLevel.situation[i][j] = Constants.ID_SOKOBAN;
                    break;
                case 'd':
                    activeLevel.situation[i][j] = Constants.ID_EMPTY;
                    setY(getY()+1); j += 1;
                    activeLevel.situation[i][j] = Constants.ID_SOKOBAN;
                    break;
                case 'l':
                    activeLevel.situation[i][j] = Constants.ID_EMPTY;
                    setX(getX()+1); i += 1;
                    activeLevel.situation[i][j] = Constants.ID_SOKOBAN;
                    break;
                case 'r':
                    activeLevel.situation[i][j] = Constants.ID_EMPTY;
                    setX(getX()-1); i -= 1;
                    activeLevel.situation[i][j] = Constants.ID_SOKOBAN;
                    break;
            }
        }
    }

    public void forward(){
        currentState = State.STANDING;
        moving = false;
        forwardPosition();
    }

    private void forwardPosition(){
        if (activeLevel.nextMove <activeLevel.moves.length()) {
//            currentState = State.STANDING;
            char makeMove = activeLevel.moves.charAt(activeLevel.nextMove);
            switch (makeMove) {
                case 'U':
                    directionX = 0; directionY = 1;
                    updatePushingPosition();
                    break;
                case 'D':
                    directionX = 0; directionY = -1;
                    updatePushingPosition();
                    break;
                case 'L':
                    directionX = -1; directionY = 0;
                    updatePushingPosition();
                    break;
                case 'R':
                    directionX = 1; directionY = 0;
                    updatePushingPosition();
                    break;
                case 'u':
                    directionX = 0; directionY = 1;
                    updateWalkingPosition();
                    break;
                case 'd':
                    directionX = 0; directionY = -1;
                    updateWalkingPosition();
                    break;
                case 'l':
                    directionX = -1; directionY = 0;
                    updateWalkingPosition();
                    break;
                case 'r':
                    directionX = 1; directionY = 0;
                    updateWalkingPosition();
                    break;
            }
        }
    }

    public void pause(){
        stateTimer = 0;
        currentState = State.STANDING;
    }


    public void autoReplay(){
        stateTimer = 0;
        currentState = State.AUTOREPLAY;
    }

    public void autoBackplay(){
        stateTimer = 0;
        currentState = State.AUTOBACKPLAY;
    }


    private void eventuallyMove(int dirX, int dirY) {
        switch (currentState) {
            case STANDING:
                directionX = dirX; directionY = dirY;
                int allowMove = allowMove();
                if (allowMove>0) {   //0 nemuzu, 1 muzu, 2 muzu a budu tlacit
                    moving = false;
                    // CUT moves TO LAST MOVE
                    if (activeLevel.moves.length()>activeLevel.nextMove) {
                        activeLevel.moves=activeLevel.moves.substring(0,activeLevel.nextMove);
                    }
                    stateTimer = 0;
                    if (allowMove == 1) {
                        moving = true;
                        currentState = State.WALKING;
                        if (!screen.walkMode) updateWalkingMove(); // run mode
                    }
                    else {
                        moving = true;
                        currentState = State.PUSHING;
                        if (!screen.walkMode) updatePushingMove();  // run mode
                    }
                }
                break;
        }
    }

/*
    private void setBoxNewPosition(DeskItem aBox) {
        for (DeskItem box : screen.boxes) {
            if (box.i == aBox.i && box.j == aBox.j) {
                box.i += directionX;
                box.j += directionY;
                box.setX(box.i);box.setY(box.j);
            }
        }
    }
*/

    private void setBoxNewPosition() {
        for (DeskItem box : screen.boxes) {
            if (box.i == i && box.j == j) {
                box.i += directionX;
                box.j += directionY;
                box.setX(box.i);box.setY(box.j);
            }
        }
    }


    private int allowMove(){
        if (directionX<0 || directionY<0) {
            if (i + directionX < 0 || j + directionY < 0) return 0;
        }
        else {
            if (i + directionX > activeLevel.width-1 || j+directionY>activeLevel.height-1)return 0;
        }
//        if (activeLevel.desk[i+directionX][j+directionY]==Constants.ID_WALL) return 0;
        if (activeLevel.desk[i+directionX][j+directionY]>=Constants.ID_WALL) return 0;
        if (activeLevel.situation[i+directionX][j+directionY]==Constants.ID_BOX) {
            if (activeLevel.situation[i+directionX+directionX][j+directionY+directionY]==Constants.ID_EMPTY
//               && activeLevel.desk[i+directionX+directionX][j+directionY+directionY]!=Constants.ID_WALL) { //za bednou je volno
                    && activeLevel.desk[i+directionX+directionX][j+directionY+directionY]<Constants.ID_WALL) { //za bednou je volno
                for (DeskItem box: screen.boxes) {
                    if (box.i == i + directionX && box.j == j + directionY) {
                        movedBox = box;
                        return 2;
                    }
                }
            }
            else return 0;
        }
        return 1;
    }


    public void setPos(int x, int y){
        i = x; j = y;
//        posX = x; posY = y;
    }
}