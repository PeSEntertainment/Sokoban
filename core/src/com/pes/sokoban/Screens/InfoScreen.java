package com.pes.sokoban.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.pes.sokoban.Constants.Constants;
import com.pes.sokoban.Constants.Languages;
import com.pes.sokoban.Global.LevelPlayed;
import com.pes.sokoban.Global.Package;
import com.pes.sokoban.SokobanGame;

import static com.pes.sokoban.Global.Game.FULLVERSION;
import static com.pes.sokoban.Global.Game.versionCode;
import static com.pes.sokoban.Constants.Constants.midiFiles;
import static com.pes.sokoban.Global.Game.activeLevel;
import static com.pes.sokoban.Global.Game.language;
import static com.pes.sokoban.Global.Game.lastLevel;
import static com.pes.sokoban.Global.Game.activePackage;
import static com.pes.sokoban.Global.Game.actualMIDI;
import static com.pes.sokoban.Global.Game.assetManager;
import static com.pes.sokoban.Global.Game.atlas;
import static com.pes.sokoban.Global.Game.dir;
import static com.pes.sokoban.Global.Game.infoLevel;
import static com.pes.sokoban.Global.Game.infoMode;
import static com.pes.sokoban.Global.Game.levelPackages;
import static com.pes.sokoban.Global.Game.levelsPlayed;
import static com.pes.sokoban.Global.Game.musicEnabled;
import static com.pes.sokoban.Global.Game.musicVolume;
import static com.pes.sokoban.Global.Game.portrait;
import static com.pes.sokoban.Global.Game.replayStepIn;
import static com.pes.sokoban.Global.Game.returnMode;
import static com.pes.sokoban.Global.Game.returnScreen;
import static com.pes.sokoban.Global.Game.screenOrientation;
import static com.pes.sokoban.Global.Game.soundEnabled;
import static com.pes.sokoban.Global.Game.soundVolume;
import static com.pes.sokoban.Global.Game.textureAtlasCommon;
import static com.pes.sokoban.Global.Game.textureAtlasSkin;
import static com.pes.sokoban.Global.Game.versionCode;
import static com.pes.sokoban.Global.Game.virtualHeight;
import static com.pes.sokoban.Global.Game.virtualWidth;
import static com.pes.sokoban.Global.Game.skin;
import static com.pes.sokoban.Global.Game.soundTap;
import static com.pes.sokoban.Constants.Languages.strings;


public class InfoScreen implements Screen, InputProcessor {
    private SokobanGame game;
    private Stage stage;
    protected InputMultiplexer im;
    private int border;
    private float colR,colG,colB,colA;

    public InfoScreen(SokobanGame pgame) {
        game = pgame;
    }

    @Override
    public void show() {

        stage = new Stage(game.fitViewport);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        if (soundEnabled) {
            assetManager.get(Constants.S_SOUNDOPENWINDOW, Sound.class).play(soundVolume);
        }
        im = new InputMultiplexer();
        im.addProcessor(this);
        im.addProcessor(stage);
        Gdx.input.setInputProcessor(im);


        String  sBrkgrMain, sUnderContainer, sMainLabelText, sIcon, sActionBtn;
        int containerWidth, containerHeight,
            containerPosX, containterPosY,
            iconPosX, iconPosY, iconWidth, iconHeight,
            labelWidth,
            actionBtnPosX, actionBtnPosY, actionBtnWidth, actionBtnHeight,
            mainLabelPosX, mainLabelPosY, mainLabelWidth, mainLabelHeight;


        iconWidth = 50; iconHeight = 100; border = 140;

        iconPosX = 10; iconPosY=virtualHeight - iconHeight - 10;
        if (portrait) {
            // mail label
            containerPosX = 0; containterPosY = 0;
            containerWidth = virtualWidth; containerHeight = virtualHeight - border;
            sUnderContainer = Constants.S_BCKGRCONTAINER;
            actionBtnWidth = 100; actionBtnHeight = 100;
            actionBtnPosX = virtualWidth - 20 - actionBtnWidth; actionBtnPosY = virtualHeight - border + 20;
            sBrkgrMain = Constants.S_BCKGRMAINP;
            mainLabelPosX = iconPosX+iconWidth+10; mainLabelPosY = iconPosY;
            mainLabelWidth = virtualWidth - 10 - mainLabelPosX; mainLabelHeight = iconHeight;
        }
        else {
            labelWidth = border-20;
            containerPosX = labelWidth; containterPosY = 0;
            containerWidth = virtualWidth - labelWidth; containerHeight = virtualHeight;

            sUnderContainer = Constants.S_BCKGRCONTAINER;
            actionBtnWidth = 100; actionBtnHeight = 100;
            actionBtnPosX = border - 20 - actionBtnWidth; actionBtnPosY = 20;
            sBrkgrMain = Constants.S_BCKGRMAINL;
            mainLabelPosX = iconPosX+iconWidth+5; mainLabelPosY = 10; mainLabelWidth = 30; mainLabelHeight = virtualHeight - 10;

        }
        sIcon = null; sActionBtn = null; sMainLabelText = "Test";
        colR = colG = colB = 0; colA = 0.4f;
        returnScreen = Constants.MENU; returnMode = Constants.IM_HELP;
        switch (infoMode) {
            case Constants.IM_HELPSETTINGS:
            case Constants.IM_HELP:
                sMainLabelText = strings[Languages.ID_HELP][language];
                colG = 0.7f; colB = 0.8f;
                sIcon = Constants.S_BTNO2UP;
                if (infoMode == Constants.IM_HELP) sActionBtn = Constants.S_YOUTUBE;
                else { // pro jiny help nez hlavni navrat do hlavniho help
                    returnScreen = Constants.INFO;
                }
                break;
            case Constants.IM_COPYRIGHT:
                sMainLabelText = strings[Languages.ID_COPYRIGHT][language];
                colR = 0.5f; colG = 0.8f; colB = 0.8f;
                returnScreen = Constants.INFO;
                break;
            case Constants.IM_FULLVERSION:
                sMainLabelText = strings[Languages.ID_FULL][language];
                colR = 0.8f; colG = 0.5f; colB = 0.5f;
                break;
            case Constants.IM_WIN:
                sMainLabelText = strings[Languages.ID_YOUWON][language];
//                colR = 0.9f; colG = 0.7f;  colA = 0.5f;
                colR = 0.4f; colG = 0.4f; colB = 0.4f;
                break;
            case Constants.IM_SUMMARY:
                sMainLabelText = strings[Languages.ID_SUMMARY][language];
                colR = 0.6f; colB = 0.6f; colG = 0.6f;
                sIcon = Constants.S_BTNSUP;
                break;
            case Constants.IM_SKINS:
                sMainLabelText = strings[Languages.ID_SKINS][language];
                colB = 0.6f; colG = 0.8f;
                sIcon = Constants.S_BTNKUP;
                break;
            case Constants.IM_PREFERENCES:
                sMainLabelText = strings[Languages.ID_SETTINGS][language];
                colR = 0.4f; colG = 0.4f; colB = 0.4f;
                sIcon = Constants.S_BTNO1UP;
                break;
            case Constants.IM_PACKAGES:
                sMainLabelText = strings[Languages.ID_PACKAGES][language];
                colG = 0.6f; colB = 0.5f;
                sIcon = Constants.S_SWPACKAGE;
                break;
            case Constants.IM_LEVELINFO:
                sMainLabelText = strings[Languages.ID_LEVELINFO][language];
                colR = 0.5f; colG = 0.9f; colB = 0.5f;
                break;
            default:

                break;
        }
        if (!portrait) sMainLabelText = game.convertPortrait2Landscape(sMainLabelText);
        // background
        Image imageBckgrMain = new Image(textureAtlasSkin.findRegion(sBrkgrMain));
        imageBckgrMain.setSize(virtualWidth, virtualHeight);
        imageBckgrMain.setPosition(0, 0);
        imageBckgrMain.setDebug(false);
        stage.addActor(imageBckgrMain);
        // underContainer
        Image imageUnderContainer = new Image(textureAtlasSkin.findRegion(sUnderContainer));
        imageUnderContainer.setSize(containerWidth, containerHeight);
        imageUnderContainer.setPosition(containerPosX, containterPosY);
        imageUnderContainer.setColor(colR, colG, colB, colA);
        stage.addActor(imageUnderContainer);
        // scrolled info table
        Table table = new Table();
        ScrollPane scroll = new ScrollPane(table, skin);
        stage.addActor(scroll);


        // icon
        if (sIcon!=null) {
            Image imageIcon = new Image(textureAtlasSkin.findRegion(sIcon));
            imageIcon.setSize(iconWidth,iconHeight);
            imageIcon.setPosition(iconPosX, iconPosY);
            imageIcon.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (soundEnabled) soundTap.play(soundVolume);
                    game.changeScreen(Constants.MENU);
                }
            });
            stage.addActor(imageIcon);
        }

        // labelText
        if (sMainLabelText!="") {
            Label labelText = new Label(sMainLabelText,skin,"title");
            labelText.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (soundEnabled) soundTap.play(soundVolume);
                    infoMode = returnMode;
                    game.changeScreen(returnScreen);
                }
            });
            labelText.setPosition(mainLabelPosX, mainLabelPosY);
            labelText.setSize(mainLabelWidth, mainLabelHeight);
            labelText.setFontScale(2f);
            labelText.setAlignment(Align.topLeft);
            stage.addActor(labelText);
        }


        if (sActionBtn!=null) {
            Image imageAction;
            if (sActionBtn.equals(Constants.S_YOUTUBE)) imageAction = new Image(textureAtlasCommon.findRegion(sActionBtn));
            else imageAction = new Image(textureAtlasSkin.findRegion(sActionBtn));


            imageAction.setSize(actionBtnWidth,actionBtnHeight);
            imageAction.setPosition(actionBtnPosX, actionBtnPosY);
            imageAction.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (soundEnabled) soundTap.play(soundVolume);
                    switch (infoMode) {
                        case Constants.IM_HELP :
                            goToUrl(strings[Languages.ID_HS_OPENYOUTUBE][language], "https://www.youtube.com/results?search_query=sokoban");
                            break;
                        case Constants.IM_PREFERENCES:
                            infoMode = Constants.IM_SKINS;
                            game.changeScreen(Constants.INFO);
                            break;
                    }
                }
            });
            stage.addActor(imageAction);
        }

        // define table in container
//Todo        table.align(Align.top);
        table.center().top();
        scroll.setSize(containerWidth, containerHeight);
        scroll.setPosition(containerPosX, containterPosY);
        scroll.setScrollingDisabled(true, false);
        scroll.setTouchable(Touchable.enabled);
        scroll.setOverscroll(false, false);
        scroll.setFlickScroll(true);
        scroll.updateVisualScroll();
        scroll.setScrollbarsVisible(false);

        switch (infoMode) {
            case Constants.IM_COPYRIGHT:
                setCopyrightTable(table);
                break;
            case Constants.IM_HELP:
                setHelpTable(table);
                break;
            case Constants.IM_HELPSETTINGS:
                setHelpSettingsTable(table);
                break;
            case Constants.IM_SKINS:
                setSkinsTable(table);
                break;
            case Constants.IM_FULLVERSION:
                setFullVersionInfoTable(table);
                break;
            case Constants.IM_WIN:
                setWinTable(table);
                break;
            case Constants.IM_PREFERENCES:
                setPreferencesTable(table);
                break;
            case Constants.IM_PACKAGES:
                setPackagesTable(table);
                break;
            case Constants.IM_LEVELINFO:
                setLeveInfoTable(table);
                break;
            case Constants.IM_SUMMARY:
                setSummaryTable(table);
                break;

        }
        // button Close
        TextButton tbClose = new TextButton(strings[Languages.ID_HS_XCLOSE][language], skin);
        tbClose.setColor(0.8f, 0f, 0f, 1);
        tbClose.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                closeTyped();
            }
        });
        table.add(tbClose);
        table.add(new Label("", skin));table.row();
        table.add(new Label("", skin));table.row();
    }

    private void closeTyped(){
        if (soundEnabled) soundTap.play(soundVolume);
        if (infoMode == Constants.IM_PACKAGES){
            if (activePackage == null){
                Dialog d = new Dialog("", skin);
                d.text(new Label(strings[Languages.ID_HS_CHOSEPACKAGE][language], skin));
                d.button("OK", true);
                d.setColor(1f, 0.3f, 0.3f,1f);
                if (soundEnabled) assetManager.get(Constants.S_SOUNDDIALOG, Sound.class).play(soundVolume);
                d.show(stage);
            }
            else {
                infoMode = returnMode;
                game.changeScreen(returnScreen);
            }
        }
        else {
            infoMode = returnMode;
            game.changeScreen(returnScreen);
        }
    }

    @Override
    public void render(float delta) {
        stage.act();
//      Gdx.gl.glClearColor(colR, colG, colB, colA);
//      Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        if (game.setViewport()) {
            if (stage != null) {
                im.removeProcessor(stage);
                stage.dispose();
            }
            show();
        }
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
//        game.platform.pause();
        im.removeProcessor(this);
        im.removeProcessor(stage);
        stage.dispose();
    }

    @Override
    public void dispose() {
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.BACK) {
            closeTyped();
            return true;
        } else return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
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

    private void setHelpTable(Table aTable) {
        aTable.add(new Label(strings[Languages.ID_GAMETASK][language], skin,"title"));aTable.row();
        aTable.add(new Label(strings[Languages.ID_ISEASY][language], skin));aTable.row();
        Image imgBox = new Image(textureAtlasSkin.findRegion(Constants.S_SOKOBANBOX));
        aTable.add(imgBox).size(40);aTable.row();
        aTable.add(new Label(strings[Languages.ID_TOTARGET][language], skin));aTable.row();
        Image imgTarget = new Image(textureAtlasSkin.findRegion(Constants.S_SOKOBANTARGET));
        aTable.add(imgTarget).size(40);aTable.row();
        aTable.add(new Label(strings[Languages.ID_BUTITSNOT][language], skin));aTable.row();
        aTable.add(new Label("", skin));aTable.row();
        // MAIN SCREEN
        Image imgPlayed = new Image(textureAtlasSkin.findRegion(Constants.S_SWINWORK));
        Image imgSolved = new Image(textureAtlasSkin.findRegion(Constants.S_SWSOLVED));
        Image imgAll = new Image(textureAtlasSkin.findRegion(Constants.S_SWALL));
        Image imgPackages = new Image(textureAtlasSkin.findRegion(Constants.S_SWPACKAGE));
        Image imgSummary = new Image(textureAtlasSkin.findRegion(Constants.S_BTNSUP));
        Image imgSettings = new Image(textureAtlasSkin.findRegion(Constants.S_BTNO1UP));
        Image imgSkins = new Image(textureAtlasSkin.findRegion(Constants.S_BTNKUP));
        Image imgHelp = new Image(textureAtlasSkin.findRegion(Constants.S_BTNO2UP));
        Image imgQuit = new Image(textureAtlasSkin.findRegion(Constants.S_BTNBANUP));

        aTable.add(new Label(strings[Languages.ID_HS_MAINSCREEN][language], skin,"title"));aTable.row();
        aTable.add(imgSummary).size(30,60);aTable.row();
        aTable.add(new Label(strings[Languages.ID_HS_STATISTIC][language], skin));aTable.row();
        aTable.add(imgSettings).size(30,60);aTable.row();
        aTable.add(new Label(strings[Languages.ID_HS_SETTINGSSCREEN][language], skin));aTable.row();
        aTable.add(imgSkins).size(30,60);aTable.row();
        aTable.add(new Label(strings[Languages.ID_HS_SKINSSCREEN][language], skin));aTable.row();
        aTable.add(imgHelp).size(30,60);aTable.row();
        aTable.add(new Label(strings[Languages.ID_HS_HELPSCREEN][language], skin));aTable.row();
        aTable.add(imgQuit).size(30,60);aTable.row();
        aTable.add(new Label(strings[Languages.ID_HS_CLOSEPROGRAM][language], skin));aTable.row();
        aTable.add(new Label("", skin));aTable.row();

        aTable.add(imgPlayed).size(40);aTable.row();
        aTable.add(new Label(strings[Languages.ID_HS_LISTOFFALLPLAYED][language], skin));aTable.row();
        aTable.add(imgAll).size(40);aTable.row();
        aTable.add(new Label(strings[Languages.ID_HS_LISTOFFALLLEVELS][language], skin));aTable.row();
        aTable.add(imgPackages).size(40);aTable.row();
        aTable.add(new Label(strings[Languages.ID_HS_SELECTEDPACKAGE][language], skin));aTable.row();
        aTable.add(imgSolved).size(40);aTable.row();
        aTable.add(new Label(strings[Languages.ID_HS_ALLSOLVED][language], skin));aTable.row();
        aTable.add(new Label("", skin));aTable.row();


        // PLAY SCREEN
        Image imgMoveJump = new Image(textureAtlasSkin.findRegion(Constants.S_MODEJUMP));
        Image imgMoveWalk = new Image(textureAtlasSkin.findRegion(Constants.S_MODEWALK));
        Image imgHome = new Image(textureAtlasSkin.findRegion(Constants.S_IMGHOME));

        aTable.add(new Label(strings[Languages.ID_HS_PLAYSCREEN][language], skin,"title"));aTable.row();

        Table tableJumpWalk = new Table();
        tableJumpWalk.center();
        tableJumpWalk.add(imgMoveJump).size(40).pad(10);
        tableJumpWalk.add(imgMoveWalk).size(40);
        aTable.add(tableJumpWalk).center().row();

        aTable.add(new Label(strings[Languages.ID_HS_SWITCHBEETWEEN][language], skin));aTable.row();
        aTable.add(imgHome).size(40);aTable.row();
        aTable.add(new Label(strings[Languages.ID_HS_GOTOMAIN][language], skin));aTable.row();
        aTable.add(new Label("", skin));aTable.row();

        // OSC PLAY MODE
        Image imgUp = new Image(textureAtlasCommon.findRegion(Constants.S_OSCUP));
        Image imgDown = new Image(textureAtlasCommon.findRegion(Constants.S_OSCDOWN));
        Image imgLeft = new Image(textureAtlasCommon.findRegion(Constants.S_OSCLEFT));
        Image imgRight = new Image(textureAtlasCommon.findRegion(Constants.S_OSCRIGHT));
        Image imgNext = new Image(textureAtlasCommon.findRegion(Constants.S_OSCNEXT));
        Image imgPrev = new Image(textureAtlasCommon.findRegion(Constants.S_OSCPREV));
        aTable.add(new Label(strings[Languages.ID_HS_PLAYMODE][language], skin,"title"));aTable.row();

        Table tableMove = new Table();
        tableMove.center();
        tableMove.add(imgUp).size(40);
        tableMove.add(imgDown).size(40);
        tableMove.add(imgLeft).size(40);
        tableMove.add(imgRight).size(40);
        aTable.add(tableMove).center().row();

        aTable.add(new Label(strings[Languages.ID_HS_MOVESOKOBAN][language], skin));aTable.row();

        Table tablePrevNext = new Table();
        tablePrevNext.center();
        tablePrevNext.add(imgPrev).size(40).pad(10);
        tablePrevNext.add(imgNext).size(40);
        aTable.add(tablePrevNext).center().row();

        aTable.add(new Label(strings[Languages.ID_HS_PREVNEXTMOVE][language], skin));aTable.row();
        aTable.add(new Label("", skin));aTable.row();
        TextButton button = new TextButton(strings[Languages.ID_HS_LEVELNAME][language], skin);
        button.setColor(Color.GOLD);
        button.setTouchable(Touchable.disabled);
        aTable.add(button);aTable.row();
        aTable.add(new Label(strings[Languages.ID_HS_GOTOSTART][language], skin));aTable.row();
        aTable.add(new Label("", skin));aTable.row();


        // OSC REPLAY MODE
        Image imgRepPrev = new Image(textureAtlasCommon.findRegion(Constants.S_OSCREPPREV));
        Image imgRepNext = new Image(textureAtlasCommon.findRegion(Constants.S_OSCREPNEXT));
        Image imgRepForward = new Image(textureAtlasCommon.findRegion(Constants.S_OSCREPFORWARD));
        Image imgRepBack = new Image(textureAtlasCommon.findRegion(Constants.S_OSCREPBACK));
        Image imgRepPause = new Image(textureAtlasCommon.findRegion(Constants.S_OSCREPPAUSE));

        aTable.add(new Label(strings[Languages.ID_HS_REPLAYMODE][language], skin, "title"));aTable.row();

        Table tableRepPrevNext = new Table();
        tableRepPrevNext.center();
        tableRepPrevNext.add(imgRepPrev).size(40).pad(10);
        tableRepPrevNext.add(imgRepNext).size(40);
        aTable.add(tableRepPrevNext).center().row();

        aTable.add(new Label(strings[Languages.ID_HS_PREVNEXTREPLAY][language], skin));aTable.row();

        Table tableReplay = new Table();
        tableReplay.center();
        tableReplay.add(imgRepForward).size(40);
        tableReplay.add(imgRepBack).size(40);
        tableReplay.add(imgRepPause).size(40);
        aTable.add(tableReplay).center().row();

        aTable.add(new Label(strings[Languages.ID_HS_AUTOREPLAY][language], skin));aTable.row();
        aTable.add(new Label("", skin));aTable.row();
        TextButton button2 = new TextButton(strings[Languages.ID_HS_LEVELNAME][language], skin);
        button2.setColor(Color.GOLD);
        button2.setTouchable(Touchable.disabled);
        aTable.add(button2);aTable.row();
        aTable.add(new Label(strings[Languages.ID_HS_IFYOUWANT][language], skin));aTable.row();
        aTable.add(new Label("", skin));aTable.row();

        // ADD PACKAGE
        aTable.add(new Label(strings[Languages.ID_ADDPACKAGE][language], skin,"title"));aTable.row();

        TextButton buttonAddPackage = new TextButton(strings[Languages.ID_ADDPACKAGE][language], skin);
        buttonAddPackage.setColor(Color.GOLD);
        buttonAddPackage.setTouchable(Touchable.disabled);
        aTable.add(buttonAddPackage);aTable.row();
        aTable.add(new Label(strings[Languages.ID_HS_INFULLOPENDIALOG][language], skin));aTable.row();

        TextButton buttonSelectFile = new TextButton(strings[Languages.ID_SELECTFILE][language], skin);
        buttonSelectFile.setColor(Color.GREEN);
        buttonSelectFile.setTouchable(Touchable.disabled);
        aTable.add(buttonSelectFile);aTable.row();
        aTable.add(new Label(strings[Languages.ID_HS_IFGREEN][language], skin));aTable.row();

        TextButton buttonSelectFileBad = new TextButton(strings[Languages.ID_SELECTFILE][language], skin);
        buttonSelectFileBad.setColor(Color.RED);
        buttonSelectFileBad.setTouchable(Touchable.disabled);
        aTable.add(buttonSelectFileBad);aTable.row();
        aTable.add(new Label(strings[Languages.ID_HS_IFRED][language], skin));aTable.row();
        aTable.add(new Label("", skin));aTable.row();
        // MARSHMALLOW INFO
        aTable.add(new Label(strings[Languages.ID_MARSHMALLOW][language], skin));aTable.row();
        aTable.add(new Label("", skin));aTable.row();
        // GAMEPAD
        aTable.add(new Label("GAMEPAD", skin,"title"));aTable.row();
        aTable.add(new Label(strings[Languages.ID_HS_YOUCANUSEGAMEPAD][language], skin));aTable.row();
        aTable.add(new Label("", skin));aTable.row();
        Image imgCopyright = new Image(textureAtlasCommon.findRegion("btnCopyright"));
        imgCopyright.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (soundEnabled) soundTap.play(soundVolume);
                infoMode = Constants.IM_COPYRIGHT;
                game.changeScreen(Constants.INFO);
            }
        });
        aTable.add(new Label(strings[Languages.ID_COPYRIGHT][language], skin,"title"));aTable.row();
        aTable.add(imgCopyright).size(40);
        aTable.add(new Label("", skin));aTable.row();
        aTable.add(new Label("", skin));aTable.row();
    }


    private void setHelpSettingsTable(Table aTable) {
        aTable.add(new Label("", skin));aTable.row();
        aTable.add(new Label(strings[Languages.ID_SETTINGS][language], skin));aTable.row();
        aTable.add(new Label("", skin));aTable.row();
        aTable.add(new Label("", skin));aTable.row();
    }


    private void setSkinsTable(Table aTable) {

        aTable.add(new Label("", skin));aTable.row();
        // ROBI
        Image imgRobi = new Image(textureAtlasCommon.findRegion(Constants.S_IMAGESKINROBI));
        imgRobi.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!atlas.equals(Constants.S_ATLASROBI)) changeSkin(Constants.S_ATLASROBI, Constants.S_DIRROBI);
            }
        });
        aTable.add(imgRobi);
        // TUCI
        aTable.add(new Label("", skin));aTable.row();
        aTable.add(new Label("", skin));aTable.row();

        Image imgTuci = new Image(textureAtlasCommon.findRegion(Constants.S_IMAGESKINTUCI));
        imgTuci.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!atlas.equals(Constants.S_ATLASTUCI)) changeSkin(Constants.S_ATLASTUCI, Constants.S_DIRTUCI);
            }
        });
        aTable.add(imgTuci).row();
        aTable.add(new Label("", skin));aTable.row();
        // LABEL IN FULL VERSION
        if (!FULLVERSION) {
            aTable.add(new Label(strings[Languages.ID_ONLYFULL][language],skin,"title")).row();
            aTable.add(new Label("", skin));aTable.row();
        }
        // BIBI
        Image imgBeruska = new Image(textureAtlasCommon.findRegion(Constants.S_IMAGESKINBERUSKA));
        imgBeruska.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!atlas.equals(Constants.S_ATLASBERUSKA)) {
                    if (FULLVERSION) changeSkin(Constants.S_ATLASBERUSKA, Constants.S_DIRBERUSKA);
                    else {
                        infoMode = Constants.IM_FULLVERSION;
                        game.changeScreen(Constants.INFO);
                    }
                }
            }
        });
        aTable.add(imgBeruska).row();
        // RYBKA
        aTable.add(new Label("", skin));aTable.row();
        Image imgRybka = new Image(textureAtlasCommon.findRegion(Constants.S_IMAGESKINRYBKA));
        imgRybka.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!atlas.equals(Constants.S_ATLASRYBKA)) {
                    if (FULLVERSION) changeSkin(Constants.S_ATLASRYBKA, Constants.S_DIRRYBKA);
                    else {
                        infoMode = Constants.IM_FULLVERSION;
                        game.changeScreen(Constants.INFO);
                    }
                }
            }
        });
        aTable.add(imgRybka);
        //
        aTable.add(new Label("", skin));aTable.row();
        aTable.add(new Label("", skin));aTable.row();
    }

    private void changeSkin(String newAtlas, String newDir){
        final Dialog dialog = new Dialog("", skin, "dialog") {
                boolean secondrun = false;
                @Override
                public void act(float delta) {
                    if (secondrun) {
                        if (soundEnabled) assetManager.get(Constants.S_SOUNDSKIN, Sound.class).play(soundVolume);
                        textureAtlasSkin = assetManager.get(atlas, TextureAtlas.class);
                        lastLevel = null;
                        game.getThTextures();
                        game.createThumbnails();
                        game.changeScreen(Constants.INFO);
                        remove();
                    } else secondrun = true;

                }
        };
        atlas = newAtlas; dir = newDir;
        dialog.setColor(1f, 0.3f, 0.3f,1f);
        dialog.text(new Label(strings[Languages.ID_WORKING][language],skin));
        dialog.show(stage);
    }

    private void setCopyrightTable(Table aTable) {

        aTable.add(new Label("", skin));aTable.row();
        aTable.add(new Label(strings[Languages.ID_CS_CREATED][language], skin)); aTable.row();
        aTable.add(new Label("2019", skin)); aTable.row();
        Image imageIcon =new Image(textureAtlasCommon.findRegion("icon"));
        imageIcon.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (soundEnabled) soundTap.play(soundVolume);
                goToUrl(strings[Languages.ID_CS_OPENHOMEPAGE][language], "http://www.pesentertainment.com");
            }
        });
        aTable.add(imageIcon).size(60,60); aTable.row();
        aTable.add(new Label(strings[Languages.ID_CS_HI][language], skin)).center(); aTable.row();

        aTable.add(new Label("", skin));aTable.row();

        aTable.add(new Label(strings[Languages.ID_CS_PROGRAMMED][language], skin));aTable.row();
        Image imageLibGDX = new Image(textureAtlasCommon.findRegion("libGDX"));
        imageLibGDX.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (soundEnabled) soundTap.play(soundVolume);
                goToUrl(strings[Languages.ID_CS_OPENLIBGDX][language], "https://www.badlogicgames.com/");
            }
        });
        aTable.add(imageLibGDX).size(180, 60);aTable.row();
        aTable.add(new Label(strings[Languages.ID_CS_LIBGDX][language], skin));aTable.row();
        aTable.add(new Label("", skin));aTable.row();

        aTable.add(new Label(strings[Languages.ID_CS_THANKSTO][language], skin));aTable.row();
        aTable.add(new Label("", skin));aTable.row();

        aTable.add(new Label(strings[Languages.ID_CS_LEVELS][language], skin));aTable.row();
        Image imageSourcecode =new Image(textureAtlasCommon.findRegion("logo_sourcecode.se"));
        imageSourcecode.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (soundEnabled) soundTap.play(soundVolume);
                goToUrl(strings[Languages.ID_CS_OPENSOKOBAN][language], "http://www.sourcecode.se/sokoban/levels");
            }
        });
        aTable.add(imageSourcecode).size(180,60); aTable.row();
        aTable.add(new Label("", skin));aTable.row();
    }

    private void setSummaryTable(Table aTable) {
        aTable.add(new Label("", skin));aTable.row();
        aTable.add(new Label("", skin));aTable.row();
        aTable.add(new Label(strings[Languages.ID_SS_PLAYED][language], skin)); aTable.row();
        int played = levelsPlayed.size;
        aTable.add(new Label(Integer.toString(played) + strings[Languages.ID_SS_LEVELS][language], skin)); aTable.row();
        aTable.add(new Label("", skin));aTable.row();
        int numSolved = 0;
        for (LevelPlayed levelPlayed: levelsPlayed) if (levelPlayed.solved) numSolved++;
        aTable.add(new Label(strings[Languages.ID_SS_SOLVED][language], skin)); aTable.row();
        aTable.add(new Label(Integer.toString(numSolved) + strings[Languages.ID_SS_LEVELS][language], skin)); aTable.row();
        aTable.add(new Label("", skin));aTable.row();
        if (played!=0) {
            int procent = 100*numSolved/played;
            aTable.add(new Label(Integer.toString(procent) +strings[Languages.ID_SUCCESS][language], skin)); aTable.row();
        }
        aTable.add(new Label("", skin));aTable.row();
    }


    private void setFullVersionInfoTable(Table aTable) {
        if (!FULLVERSION) game.platform.getPurchased();
        aTable.add(new Label("", skin));aTable.row();
        aTable.add(new Label(strings[Languages.ID_FS_THISIS][language], skin));aTable.row();

        Image imageSourcecode =new Image(textureAtlasCommon.findRegion("logo_sourcecode.se"));
        imageSourcecode.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (soundEnabled) soundTap.play(soundVolume);
                goToUrl(strings[Languages.ID_CS_OPENSOKOBAN][language], "http://www.sourcecode.se/sokoban/levels");
            }
        });
        aTable.add(imageSourcecode).size(180,60); aTable.row();
        aTable.add(new Label("", skin));aTable.row();
        aTable.add(new Label(strings[Languages.ID_SKINS][language],skin,"title")).row();
        aTable.add(new Label("", skin));aTable.row();
        // SKIN BERUSKA
        Image imgBeruska = new Image(textureAtlasCommon.findRegion(Constants.S_IMAGESKINBERUSKA));
        aTable.add(imgBeruska).row();
        aTable.add(new Label("", skin));aTable.row();
        Image imgPrevBeruska = new Image(textureAtlasCommon.findRegion("prevBeruska"));
        aTable.add(imgPrevBeruska).size(188,300).row();
        aTable.add(new Label("", skin));aTable.row();
        // SKIN RYBKA
        aTable.add(new Label("", skin));aTable.row();
        Image imgRybka = new Image(textureAtlasCommon.findRegion(Constants.S_IMAGESKINRYBKA));
        aTable.add(imgRybka).row();
        aTable.add(new Label("", skin));aTable.row();
        Image imgPrevRybka = new Image(textureAtlasCommon.findRegion("prevRybka"));
        aTable.add(imgPrevRybka).size(188,300).row();
        aTable.add(new Label("", skin));aTable.row();
        // GetInOnGooglePlay
        Image imageGetIt =new Image(textureAtlasCommon.findRegion(Constants.S_GETITONGOOGLEPLAY));
        imageGetIt.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (soundEnabled) soundTap.play(soundVolume);
                game.platform.purchase();
            }
        });
        aTable.add(imageGetIt).size(180,60); aTable.row();
        aTable.add(new Label("", skin));aTable.row();
        aTable.row();
    }


    private void setWinTable(Table aTable) {
        aTable.add(new Label("",skin));aTable.row();
        aTable.add(new Label("",skin));aTable.row();
        aTable.add(new Label(strings[Languages.ID_WS_CONGRATULATION][language], skin));aTable.row();
        aTable.add(new Label("", skin));aTable.row();
        aTable.add(new Label(strings[Languages.ID_WS_YOUWON][language], skin));aTable.row();
        aTable.add(new Label("", skin));aTable.row();

        aTable.add(new Label(strings[Languages.ID_WS_YOURSOLUTION][language], skin));aTable.row();
        aTable.add(new Label(activeLevel.moves.length()+ strings[Languages.ID_WS_MOVES][language], skin));aTable.row();
        aTable.add(new Label("", skin));aTable.row();
        if (activeLevel.solved) {
            if (activeLevel.moves.length() < activeLevel.solved_moves.length()) {
                aTable.add(new Label(strings[Languages.ID_WS_NEWBEST][language], skin));aTable.row();
            }
            else if(activeLevel.moves.length() == activeLevel.solved_moves.length()) {
                aTable.add(new Label(strings[Languages.ID_WS_SAMEBEST][language], skin));aTable.row();
            }
            else {
                aTable.add(new Label(strings[Languages.ID_WS_BESTSOLUTION][language], skin));aTable.row();
                aTable.add(new Label(activeLevel.solved_moves.length()+ strings[Languages.ID_WS_MOVES][language], skin));aTable.row();
            }
        }
        aTable.row();
        aTable.add(new Label("",skin));aTable.row();

        activeLevel.updateAfterSolution(); // nunte udelat az po kontrole jestli neni toto reseni kratsi kvuli zprave vyse
        game.updateLevelsPlayed(activeLevel);
        game.save();
        activeLevel.setToStart();
    }


    private void setLeveInfoTable(Table aTable) {
        if (infoLevel != null) {
            aTable.add(new Label("", skin));aTable.row();
            aTable.add(new Label(strings[Languages.ID_LI_PACKAGE][language], skin));aTable.row();
            aTable.add(new Label(infoLevel.lPackage.getName(), skin));aTable.row();
            aTable.add(new Label("ID", skin));aTable.row();
            aTable.add(new Label(infoLevel.id, skin));aTable.row();
            if (infoLevel.solved) {
                aTable.add(new Label(strings[Languages.ID_LI_SOLVEDIN][language]+infoLevel.solved_moves.length()+strings[Languages.ID_WS_MOVES][language], skin));aTable.row();
            }
            else {
                aTable.add(new Label(strings[Languages.ID_LI_NOTSOLVED][language], skin));aTable.row();
            }

            Image image = new Image(infoLevel.textureThumbnail);
            int height = 300*infoLevel.height/infoLevel.width;
            aTable.add(image).size(300, height);aTable.row();
            aTable.add(new Label(infoLevel.width.toString()+" x "+infoLevel.height.toString(), skin));aTable.row();
            aTable.add(new Label("", skin));aTable.row();
            aTable.add(new Label(strings[Languages.ID_LI_TITLE][language], skin));aTable.row();
            aTable.add(new Label(infoLevel.lPackage.title, skin));aTable.row();
            aTable.add(new Label("", skin));aTable.row();
            aTable.add(new Label(strings[Languages.ID_LI_DESCRIPTION][language], skin));aTable.row();
            aTable.add(new Label(stringWrap(infoLevel.lPackage.description,Constants.MAXSTRLENGHT), skin));aTable.row();
            aTable.add(new Label(strings[Languages.ID_LI_COPYRIGHT][language], skin));aTable.row();
            aTable.add(new Label(stringWrap(infoLevel.lPackage.copyright, Constants.MAXSTRLENGHT), skin));aTable.row();

            aTable.add(new Label("", skin));aTable.row();
            aTable.add(new Label("URL", skin));aTable.row();
            aTable.add(new Label(infoLevel.lPackage.url, skin));aTable.row();
            aTable.add(new Label("email", skin));aTable.row();
            aTable.add(new Label(infoLevel.lPackage.email, skin));aTable.row();

            aTable.add(new Label("", skin));aTable.row();
        }
    }




    private void setPreferencesTable(Table aTable) {
        // MUSIC LABEL
        Label musicOnOffLabel = new Label(strings[Languages.ID_PR_MUSIC][language], skin,"title");
        aTable.add(new Label("", skin));aTable.row();
        // MUSIC CHECK BOX
        final CheckBox musicCheckbox = new CheckBox(null, skin);
        musicCheckbox.setChecked(musicEnabled);
        musicCheckbox.addListener( new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean enabled = musicCheckbox.isChecked();
                if (musicEnabled != enabled) {
                    if (enabled) game.platform.play();
                    else game.platform.pause();
                }
                musicEnabled = enabled;
                return false;
            }
        });
        // MUSIC VOLUME LABEL
        Label volumeMusicLabel = new Label(strings[Languages.ID_PR_MUSICVOL][language], skin);
        // MUSIC VOLUME SLIDER
        final Slider volumeMusicSlider = new Slider( 0f, 1f, 0.1f,false, skin );
        volumeMusicSlider.setValue(musicVolume);
        volumeMusicSlider.addListener( new EventListener() {
            @Override
            public boolean handle(Event event) {
                musicVolume = (volumeMusicSlider.getValue());
                game.platform.setVolume(musicVolume);
                return false;
            }
        });
        // SELECT MUSIC
        final SelectBox<String> musicSelectBox = new SelectBox<String>(skin);
        musicSelectBox.setItems("   "+midiFiles[0],"   "+midiFiles[1],"   "+midiFiles[2],"   "+midiFiles[3],"   "+midiFiles[4],
                "   "+midiFiles[5],"   "+midiFiles[6],"   "+midiFiles[7],"   "+midiFiles[8]);
        musicSelectBox.setSelected("   "+midiFiles[actualMIDI]);
        musicSelectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String selected = musicSelectBox.getSelected().substring(3);
                for (int i=0;i<midiFiles.length;i++) {
                    if (selected.equals(midiFiles[i])) actualMIDI = i;
                }
                game.platform.change();
                if (musicEnabled) game.platform.play();
            }
        });
        // SOUND LABEL
        Label soundOnOffLabel = new Label(strings[Languages.ID_PR_SOUND][language], skin,"title");
        // SOUND CHECK BOX
        final CheckBox soundCheckbox = new CheckBox(null, skin);
        soundCheckbox.setChecked( soundEnabled );
        soundCheckbox.addListener( new EventListener() {
            @Override
            public boolean handle(Event event) {
                if (soundEnabled != soundCheckbox.isChecked()) {
                    soundEnabled = soundCheckbox.isChecked();
                    if (soundEnabled) soundTap.play(soundVolume);
                }
                return false;
            }
        });
        // SOUND VOLUME LABEL
        Label volumeSoundLabel = new Label(strings[Languages.ID_PR_SOUNDVOL][language], skin);
        // SOUND VOLUME SLIDER
        final Slider volumeSoundSlider = new Slider( 0f, 1f, 0.1f,false, skin );
        volumeSoundSlider.setValue(soundVolume);
        volumeSoundSlider.addListener( new EventListener() {
            @Override
            public boolean handle(Event event) {
                if (!volumeSoundSlider.isDragging()) {
                    if (soundVolume!=volumeSoundSlider.getValue()) {
                        soundVolume = (volumeSoundSlider.getValue());
                        if (soundEnabled) soundTap.play(soundVolume);
                    }
                }
                return false;
            }
        });
        // REPLAY SPEED LABEL
        Label replaySpeedLabel = new Label(strings[Languages.ID_PR_REPLAY][language], skin,"title");
        // REPLAY SPEED SLIDER
        final Slider replaySpeedSlider = new Slider( 1f, 20f, 1f,false, skin );
        replaySpeedSlider.setValue(1/replayStepIn);
        replaySpeedSlider.addListener( new EventListener() {
            @Override
            public boolean handle(Event event) {
                replayStepIn = (1/replaySpeedSlider.getValue());
                return false;
            }
        });
        // SCREEN ORIENTATION
        Label screenOrientationLabel = new Label(strings[Languages.ID_PR_ORIENTATION][language], skin,"title");
        Table orientationTable = new Table();
        final ButtonGroup orientationChoiceGroup = new ButtonGroup<CheckBox>();
        CheckBox sensorCheckBox = new CheckBox(strings[Languages.ID_PR_SENSOR][language], skin); sensorCheckBox.setName("sensor");
        CheckBox portraitCheckBox = new CheckBox(strings[Languages.ID_PR_PORTRAIT][language], skin); portraitCheckBox.setName("portrait");
        CheckBox landscapeCheckBox = new CheckBox(strings[Languages.ID_PR_LANDSCAPE][language], skin); landscapeCheckBox.setName("landscape");
        orientationChoiceGroup.add(sensorCheckBox);
        orientationChoiceGroup.add(portraitCheckBox);
        orientationChoiceGroup.add(landscapeCheckBox);
        orientationChoiceGroup.setMaxCheckCount(1);
        orientationChoiceGroup.setMinCheckCount(1);
        orientationChoiceGroup.setUncheckLast(true);

        if (screenOrientation.equals("sensor")) orientationChoiceGroup.setChecked(strings[Languages.ID_PR_SENSOR][language]);
        else if (screenOrientation.equals("portrait")) orientationChoiceGroup.setChecked(strings[Languages.ID_PR_PORTRAIT][language]);
        else orientationChoiceGroup.setChecked(strings[Languages.ID_PR_LANDSCAPE][language]);

//        orientationChoiceGroup.setChecked(screenOrientation);
        orientationTable.add(sensorCheckBox).center().padBottom(15).row();
        orientationTable.add(portraitCheckBox).center().padBottom(15).row();
        orientationTable.add(landscapeCheckBox).center().padBottom(40).row();
        orientationTable.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(soundEnabled) soundTap.play(soundVolume);
                if (orientationChoiceGroup.getChecked()!=null){
                    String choise = orientationChoiceGroup.getChecked().getName();
                    screenOrientation = choise;
                    game.platform.setOrientation(choise);
                }
            }
        });


        // LANGUAGE
        Label languageLabel = new Label(strings[Languages.ID_PR_LANGUAGE][language], skin,"title");
        Table languageTable = new Table();
        final ButtonGroup languageChoiceGroup = new ButtonGroup<CheckBox>();
        CheckBox languageEnglish = new CheckBox(strings[Languages.ID_LANGUAGES][Languages.LANG_ENGLISH], skin); languageEnglish.setName(strings[Languages.ID_LANGUAGES][Languages.LANG_ENGLISH]);
        CheckBox languageCzech = new CheckBox(strings[Languages.ID_LANGUAGES][Languages.LANG_CZECH], skin); languageCzech.setName(strings[Languages.ID_LANGUAGES][Languages.LANG_CZECH]);
        languageChoiceGroup.add(languageEnglish);
        languageChoiceGroup.add(languageCzech);
        languageChoiceGroup.setMaxCheckCount(1);
        languageChoiceGroup.setMinCheckCount(1);
        languageChoiceGroup.setUncheckLast(true);
        languageChoiceGroup.setChecked(strings[Languages.ID_LANGUAGES][language]);
        languageTable.add(languageEnglish).center().padBottom(15).row();
        languageTable.add(languageCzech).center().padBottom(15).row();
        languageTable.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(soundEnabled) soundTap.play(soundVolume);
                if (languageChoiceGroup.getChecked()!=null){
                    String choiseL = languageChoiceGroup.getChecked().getName();
                    if (choiseL.equals(strings[Languages.ID_LANGUAGES][Languages.LANG_CZECH])) language = Languages.LANG_CZECH;
                    else language = Languages.LANG_ENGLISH;
                    game.changeScreen(Constants.INFO);
                }
            }
        });


        aTable.add(musicOnOffLabel).center();aTable.row();
        aTable.add(musicCheckbox).center();aTable.row();
        aTable.add(volumeMusicLabel);aTable.row();
        aTable.add(volumeMusicSlider).center();aTable.row();
        aTable.add(musicSelectBox); aTable.row();
        aTable.add(new Label("", skin));aTable.row();
        aTable.add(soundOnOffLabel);aTable.row();
        aTable.add(soundCheckbox).center();aTable.row();
        aTable.add(volumeSoundLabel);aTable.row();
        aTable.add(volumeSoundSlider).center();aTable.row();
        aTable.add(new Label("", skin));aTable.row();
        aTable.add(replaySpeedLabel).center();aTable.row();
        aTable.add(replaySpeedSlider).center();aTable.row();
        aTable.add(new Label("", skin));aTable.row();

        aTable.add(screenOrientationLabel);aTable.row();
        aTable.add(new Label("", skin));aTable.row();
        aTable.add(orientationTable);aTable.row();

        aTable.add(languageLabel);aTable.row();
        aTable.add(new Label("", skin));aTable.row();
        aTable.add(languageTable);aTable.row();

        aTable.add(new Label("", skin));aTable.row();
        aTable.add(new Label("", skin));aTable.row();
    }


    


    private void dialogFileChoose(){
        FileChooser2 files = new FileChooser2("", skin, game) {
            @Override
            protected void result(Object object) {
                if (soundEnabled) soundTap.play(soundVolume);
                if ((Boolean) object) {
                    FileHandle file = getFile();
                    if (file != null) {
                        levelPackages.add(new Package(file.toString(), false));
                        levelPackages.get(levelPackages.size-1).load();
                        activePackage = levelPackages.get(levelPackages.size-1);
                        for (int j = 0; j < activePackage.levels.size; j++) {
                            activePackage.levels.get(j).createNiceThumbnail();
                        }
                        game.changeScreen(Constants.MENU);
                    }
                } else hide();
            }
        };
        if (Gdx.files.isExternalStorageAvailable()) {
            if (soundEnabled) {
                if (soundEnabled) assetManager.get(Constants.S_SOUNDDIALOG, Sound.class).play(soundVolume);
            }
            files.setDirectory(Gdx.files.external("/"));
            files.setColor(Color.LIGHT_GRAY);
            files.show(stage);
            files.setFillParent(true);
        }
        else {
            //Todo External storage is not awailable
            Dialog dialog = new Dialog("",skin);
            dialog.text(new Label(strings[Languages.ID_SORRY][language],skin));
            dialog.setColor(Color.RED);
            dialog.button("OK",true);
            dialog.show(stage);
        }
    }

    
    private void setPackagesTable(Table aTable){
        if (!FULLVERSION) game.platform.getPurchased();
        // BUTTON LOAD
        TextButton buttonLoad = new TextButton(strings[Languages.ID_ADDPACKAGE][language], skin);
        buttonLoad.setColor(Color.ORANGE);
        buttonLoad.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (soundEnabled) soundTap.play(soundVolume);
                if (FULLVERSION) {
                    dialogFileChoose();
                }
                else {
                    infoMode = Constants.IM_FULLVERSION;
                    game.changeScreen(Constants.INFO);
                }
            }
        });
        aTable.add(new Label("",skin));aTable.row();
        aTable.add(buttonLoad).width(250);aTable.row();
        aTable.add(new Label("", skin));aTable.row();
        for (int i = 0; i < levelPackages.size; i++) {

            TextButton button = new TextButton(levelPackages.get(i).getName(), skin);
            aTable.add(button).width(250);
            final int finalI = i;
            button.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    if (soundEnabled) soundTap.play(soundVolume);
                    activePackage = levelPackages.get(finalI);
                    activeLevel = null; //
                    game.changeScreen(Constants.MENU);
                }
            });
            Label labelNumLeves = new Label(levelPackages.get(i).numLevels.toString(), skin);
            labelNumLeves.setColor(Color.WHITE);
            aTable.add(labelNumLeves);
            labelNumLeves.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    if (soundEnabled) soundTap.play(soundVolume);
                    dialogInfo(levelPackages.get(finalI));
                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                }
            });
            aTable.row();

        }
        aTable.add(new Label("", skin));aTable.row();
        aTable.add(new Label("", skin));aTable.row();
    }

    private void dialogInfo(final Package aPackage) {
        Dialog d = new Dialog("", skin) {
            public void result(Object obj) {
                if (soundEnabled) soundTap.play(soundVolume);
                if ((Boolean) obj) {
                    levelPackages.removeValue(aPackage,true);
                    activePackage = null;
                    game.changeScreen(returnScreen);

//                    resize(virtualWidth, virtualHeight);
                }
                remove();
            }
        };
        Label label = new Label(aPackage.getInfoText(Constants.MAXSTRLENGHT), skin);
        label.setFillParent(true);
        d.text(label);
        d.button(strings[Languages.ID_CLOSE][language], false);
        d.setColor(Color.YELLOW);
        if (!aPackage.local) {
            TextButton btnRemove = new TextButton(strings[Languages.ID_REMOVE][language],skin);
            btnRemove.setColor(Color.RED);
            d.button(btnRemove,true);
//            d.button("Remove", true);
        }
        if (soundEnabled) assetManager.get(Constants.S_SOUNDDIALOG, Sound.class).play(soundVolume);
        d.show(stage);
        d.setPosition(Math.round((stage.getWidth() - d.getWidth()) / 2), Math.round((stage.getHeight() - d
                .getHeight()) / 2));
    }


    private String stringWrap(String aString, int aLenght) {
        String ret = "";
        int lenght = aString.length();
        int begin,end;
        begin = 0;
        while (begin<lenght-1) {
            end = begin + aLenght;
            if (end>lenght-1) end = lenght-1;
            ret = ret.concat(aString.substring(begin, end))+" \n ";
            begin = end;
        }
        return ret;
    }

    private void goToUrl(String infoText, final String aUrl){
        Dialog dialog = new Dialog(" ", skin) {
            public void result(Object obj) {
                if (soundEnabled) soundTap.play(soundVolume);
                if ((Boolean) obj) {
                    Gdx.net.openURI(aUrl);
                    remove();
                }
                else remove();
            }
//        }.text("This open web pages\ncontinue?").button("Yes", true).button("No", false);
        }.text(infoText).button(strings[Languages.ID_YES][language], true).button(strings[Languages.ID_NO][language], false);
        if (soundEnabled) assetManager.get(Constants.S_SOUNDDIALOG, Sound.class).play(soundVolume);
        dialog.setColor(1f, 0.3f, 0.3f,1f);
        dialog.show(stage) ;
    }


}