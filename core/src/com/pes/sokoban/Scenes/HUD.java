package com.pes.sokoban.Scenes;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.pes.sokoban.Constants.Constants;
import com.pes.sokoban.Constants.Languages;
import com.pes.sokoban.Screens.PlayScreen;

import static com.pes.sokoban.Constants.Languages.strings;
import static com.pes.sokoban.Global.Game.activeLevel;
import static com.pes.sokoban.Global.Game.assetManager;
import static com.pes.sokoban.Global.Game.language;
import static com.pes.sokoban.Global.Game.skin;
import static com.pes.sokoban.Global.Game.soundEnabled;
import static com.pes.sokoban.Global.Game.soundTap;
import static com.pes.sokoban.Global.Game.soundVolume;
import static com.pes.sokoban.Global.Game.textureAtlasSkin;
import static com.pes.sokoban.Global.Game.virtualHeight;
import static com.pes.sokoban.Global.Game.virtualWidth;

public class HUD implements Disposable
{
    public Stage stage;
    protected Viewport viewport;
    protected PlayScreen screen;

    private Integer numMoves;

    private Label numMovesLabel;

    private float fontZoom;

    private Integer  elementSize, doubleElementSize;

    public HUD(SpriteBatch sb, final PlayScreen screen){
        this.screen = screen;
        Integer labelNamePosX, labelNamePosY, labelNameWidth, labelNameHeight,
                homeImagePosX, homeImagePosY, homeImageWidth, homeImageHeight,
                stepsImagePosX, stepsImagePosY, stepsImageWidth, stepsImageHeight,
                labelStepsPosX, labelStepsPosY, labelStepsWidth, labelStepsHeight;
        numMoves = 0;
        elementSize = 40; doubleElementSize = 2*elementSize;

        viewport = new FitViewport(virtualWidth, virtualHeight, new OrthographicCamera());
        stage = new Stage(viewport, sb);
        stage.addListener(new InputListener());
        homeImageWidth= doubleElementSize-10;homeImageHeight=doubleElementSize-10;
        homeImagePosX=virtualWidth-doubleElementSize; homeImagePosY=virtualHeight-doubleElementSize;
        stepsImagePosX=5; stepsImagePosY=virtualHeight-doubleElementSize-elementSize/2;
        labelStepsPosX=5; labelStepsPosY=virtualHeight-elementSize-elementSize/2-3;

        labelNamePosX = doubleElementSize+elementSize/2; labelNamePosY = virtualHeight-doubleElementSize;
        labelNameWidth=elementSize*5; labelNameHeight = elementSize*2;
        stepsImageWidth=doubleElementSize; stepsImageHeight=doubleElementSize;
        labelStepsWidth=elementSize*2; labelStepsHeight=elementSize;

        //  button name
        TextButton button = new TextButton(activeLevel.id, skin);
        button.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (screen.replayMode) {
                    screen.player.pause();
                    Label.LabelStyle labelStyle = new Label.LabelStyle();
                    labelStyle.font = skin.getFont("button");
                    labelStyle.fontColor = Color.RED;
                    final Dialog dialog = new Dialog(" ", skin) {
                        public void result(Object obj) {
                            if (soundEnabled) soundTap.play(soundVolume);
                            if ((Boolean) obj) {
                                screen.replayMode = false;
                                screen.setOSC();
                                activeLevel.setInPlayFromSolved();
                                remove();
                            }
                            else remove();
                        }
                    }.text(strings[Languages.ID_TRYFINDBETTER][language], labelStyle).button(strings[Languages.ID_YES][language], true).button(strings[Languages.ID_NO][language], false);
                    if (soundEnabled) {
                        assetManager.get(Constants.S_SOUNDDIALOG, Sound.class).play(soundVolume);
                    }
                    dialog.show(stage) ;
                }
                else {
                    screen.setToStart();
                }
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            }
        });
        button.setColor(Color.GOLD);
        button.setColor(button.getColor().r,button.getColor().g,button.getColor().b, 0.75f);
        button.setPosition(labelNamePosX,labelNamePosY);
        button.setWidth(labelNameWidth);
        stage.addActor(button);

        // home image
        Image homeImg = new Image(textureAtlasSkin.findRegion(Constants.S_IMGHOME));
        homeImg.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (soundEnabled) soundTap.play(soundVolume);
                screen.game.updateLevelsPlayed(activeLevel);
                screen.game.changeScreen(Constants.MENU);
            }
        });
        homeImg.setPosition(homeImagePosX,homeImagePosY);
        homeImg.setSize(homeImageWidth,homeImageHeight) ;
        stage.addActor(homeImg);


        // steps counter image
        //Todo zmena obrazku podle rezimu walk / jump
        String sImgWalking = Constants.S_MODEJUMP;
        if (screen.walkMode) sImgWalking = Constants.S_MODEWALK;
//        final Image walkImg = new Image(textureAtlasSkin.findRegion(Constants.S_MODEJUMP));
        final Image walkImg = new Image(textureAtlasSkin.findRegion(sImgWalking));
        walkImg.setPosition(stepsImagePosX,stepsImagePosY);
        walkImg.setSize(stepsImageWidth,stepsImageHeight);
        stage.addActor(walkImg);

        // num moves label
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = skin.getFont("default");
        labelStyle.fontColor = Color.WHITE;
        fontZoom = elementSize/labelStyle.font.getLineHeight()/2;

        numMovesLabel = new Label(String.format("%03d", numMoves), labelStyle);
        numMovesLabel.setColor(Color.WHITE);
        numMovesLabel.setPosition(labelStepsPosX,labelStepsPosY);
        numMovesLabel.setSize(labelStepsWidth,labelStepsHeight);
        numMovesLabel.setAlignment(0);
        numMovesLabel.setFontScale(fontZoom);
        stage.addActor(numMovesLabel);
        numMovesLabel.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (soundEnabled) soundTap.play(soundVolume);
                screen.walkMode = !screen.walkMode;
                if (screen.walkMode) walkImg.setDrawable(new TextureRegionDrawable(textureAtlasSkin.findRegion(Constants.S_MODEWALK)));
                else walkImg.setDrawable(new TextureRegionDrawable(textureAtlasSkin.findRegion(Constants.S_MODEJUMP)));
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            }
        });
    }

    public void update(float dt){
        // num moves label
        if (activeLevel.nextMove<activeLevel.moves.length()) numMovesLabel.setColor(Color.GREEN);
        else numMovesLabel.setColor(Color.WHITE);
        numMovesLabel.setText(String.format("%03d", activeLevel.nextMove));
    }

    public void dispose() {
        stage.dispose();
    }


}
