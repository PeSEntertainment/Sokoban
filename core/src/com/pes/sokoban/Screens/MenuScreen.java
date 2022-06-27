package com.pes.sokoban.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.pes.sokoban.Constants.Constants;
import com.pes.sokoban.Constants.Languages;
import com.pes.sokoban.Global.Level;
import com.pes.sokoban.Global.Package;
import com.pes.sokoban.SokobanGame;

import static com.pes.sokoban.Constants.Constants.SPACE;
import static com.pes.sokoban.Global.Game.activePackage;
import static com.pes.sokoban.Global.Game.assetManager;
import static com.pes.sokoban.Global.Game.infoLevel;
import static com.pes.sokoban.Global.Game.infoMode;
import static com.pes.sokoban.Global.Game.language;
import static com.pes.sokoban.Global.Game.levelPackages;
import static com.pes.sokoban.Global.Game.musicEnabled;
import static com.pes.sokoban.Global.Game.screenOrientation;
import static com.pes.sokoban.Global.Game.soundEnabled;
import static com.pes.sokoban.Global.Game.soundVolume;
import static com.pes.sokoban.Global.Game.portrait;
import static com.pes.sokoban.Global.Game.activeLevel;
import static com.pes.sokoban.Global.Game.skin;
import static com.pes.sokoban.Global.Game.soundTap;
import static com.pes.sokoban.Global.Game.textureAtlasCommon;
import static com.pes.sokoban.Global.Game.textureAtlasSkin;
import static com.pes.sokoban.Global.Game.virtualHeight;
import static com.pes.sokoban.Global.Game.virtualWidth;
import static com.pes.sokoban.Constants.Languages.strings;


public class MenuScreen implements Screen, InputProcessor {
    protected SokobanGame game;
    private Stage stage;
    private InputMultiplexer im;
    private int menuMode;
    private Image btnPlayedActive;
    private Image btnSolvedActive;
    private Image btnAllActive;
    private Image btnPackagesActive;
    private int infoButtonSize,rowHalfSPACE, thumbWidth;
    private Image semitransparentImage;

    public MenuScreen(SokobanGame pGame){
        game = pGame;
        menuMode = Constants.MM_PLAYED;

        semitransparentImage = new Image(textureAtlasCommon.findRegion(Constants.S_SEMITRANSPARENT));
        semitransparentImage.setVisible(false);
        semitransparentImage.setPosition(0,0);
        semitransparentImage.setSize(virtualWidth, virtualHeight);

    }

    @Override
    public void show() {

        if (musicEnabled) game.platform.play();

        // stage musi byt vytvorena kvuli hide(), kde je stage.dispose()
        stage = new Stage(game.fitViewport);

        // nastaveni activePackages, pripadne prepnuti na screen volby Packages
       if (menuMode == Constants.MM_PACKAGE && activePackage == null) {
           infoScreen(Constants.IM_PACKAGES);
       }
       else {
           Array<Package> selectedPackages;

           selectedPackages = new Array<Package>();
           if (menuMode == Constants.MM_PACKAGE) {
               selectedPackages.add(activePackage);
           }
           else {
               selectedPackages = levelPackages;
               if (selectedPackages.size == 0) {
                   menuMode = Constants.MM_PACKAGE;
                   game.changeScreen(Constants.MENU);
               }
           }
           stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
           // nastaveni vstupu
           im = new InputMultiplexer();
           im.addProcessor(this);
           im.addProcessor(stage);
           Gdx.input.setInputProcessor(im);
           // nastaveni rozmeru a poloh tlacitek pro mody portrait a landscape
           String  sBrkgrMain, sUnderContainer;
           int smallButtonSize, switchButtonSize, border2,
                   playedButtonX, playedButtonY,
                   solvedButtonX, solvedButtonY,
                   allButtonX, allButtonY,
                   packagesButtonX, packagesButtonY,
                   containerPosX, containerPosY, containerSize,
                   SposX, SposY, Swidth, Sheight, O1posX, O1posY, O1width, O1height, KposX, KposY, Kwidth, Kheight, O2posX, O2posY, O2width, O2height,
                   banPosX, banPosY, banWidth, banHeight,
                   modePosX, modePosY, modeWidth,modeHeight;
           String sBanUp, sBanDown;

           infoButtonSize = 30;
           thumbWidth = 230;
           smallButtonSize = 40;
           switchButtonSize = 60;
           sUnderContainer = Constants.S_BCKGRCONTAINER;
           containerSize = Constants.BASIC_SIZE - 2*SPACE;
           border2 = switchButtonSize + 2*SPACE;
           int border;
           if (portrait) { // PORTRAIT
               border = virtualHeight - containerSize - border2;
               // background
               sBrkgrMain = Constants.S_BCKGRMAINP;
               // Sokoban
               Swidth = (virtualWidth -10)/ 5; Sheight = border - SPACE - smallButtonSize;
               SposX = 10; SposY = virtualHeight - Sheight;
               O1posX = SposX + Swidth; O1posY = SposY; O1width = Swidth; O1height = Sheight;
               KposX = O1posX + Swidth; KposY = SposY; Kwidth = Swidth; Kheight = Sheight;
               O2posX = KposX + Swidth; O2posY = SposY; O2width = Swidth; O2height = Sheight;
               banPosX= O2posX + Swidth; banPosY = SposY; banWidth = Swidth; banHeight= Sheight;
               sBanUp = Constants.S_BTNBANUP; sBanDown = Constants.S_BTNBANDOWN;
               // container
               containerPosX = SPACE ; containerPosY =  border2;
               // modeLabel
               modeWidth = containerSize; modeHeight = smallButtonSize;
               modePosX = SPACE; modePosY= virtualHeight - border + SPACE;
               // switche
               playedButtonX = (virtualWidth - 4*switchButtonSize - 3*20)/2; playedButtonY = SPACE;
               allButtonX = playedButtonX+switchButtonSize+20; allButtonY =  playedButtonY;
               packagesButtonX = allButtonX+switchButtonSize+20; packagesButtonY =  allButtonY;
               solvedButtonX = packagesButtonX+switchButtonSize+20; solvedButtonY =  packagesButtonY;
           }
           else { // LANDSCAPE
               border = virtualWidth - containerSize - border2;
               // background
               sBrkgrMain = Constants.S_BCKGRMAINL;
               // Sokoban
               Swidth = (border -2*SPACE-smallButtonSize)/2; Sheight = 2*virtualHeight/5;
               SposX = 0; SposY = 20+3*Sheight/2;
               O1posX = Swidth; O1posY = SposY; O1width = Swidth; O1height = Sheight;
               KposX = 0; KposY = Sheight/2; Kwidth = Swidth; Kheight = Sheight;
               O2posX = Swidth; O2posY = KposY; O2width = Swidth; O2height = Sheight;
               banPosX=0; banPosY = 0; banWidth = 2*Swidth; banHeight= Sheight/2;
               sBanUp = Constants.S_BTNBANUPL; sBanDown = Constants.S_BTNBANDOWNL;
               // container
               containerPosX = border; containerPosY = SPACE;
               // mode label/button
               modePosX = banWidth+SPACE; modePosY= SPACE; modeWidth = smallButtonSize; modeHeight = containerSize;
               // switche
               playedButtonX = virtualWidth-SPACE-switchButtonSize; playedButtonY =  virtualHeight - (virtualHeight - 4*switchButtonSize-3*20)/2-switchButtonSize;
               allButtonX = playedButtonX; allButtonY =  playedButtonY - switchButtonSize-20;
               packagesButtonX = allButtonX; packagesButtonY = allButtonY  - switchButtonSize-20;
               solvedButtonX = packagesButtonX; solvedButtonY = packagesButtonY - switchButtonSize-20;

           }
           rowHalfSPACE = containerSize-thumbWidth-2*infoButtonSize-20;
           if (rowHalfSPACE<0) rowHalfSPACE=0;
           else rowHalfSPACE = rowHalfSPACE/2;

           // sestavovani obrazovky
           // background
           Image imageBckgrMain = new Image(textureAtlasSkin.findRegion(sBrkgrMain));
           imageBckgrMain.setSize(virtualWidth, virtualHeight);
           imageBckgrMain.setPosition(0, 0);
           imageBckgrMain.setDebug(false);
           stage.addActor(imageBckgrMain);
           // underContainer
           Image imageUnderContainer = new Image(textureAtlasSkin.findRegion(sUnderContainer));
           imageUnderContainer.setSize(containerSize, containerSize);
           imageUnderContainer.setPosition(containerPosX, containerPosY);
           imageUnderContainer.setColor(0.8f, 0.8f, 0.8f, 0.5f);
           stage.addActor(imageUnderContainer);
           // S
           Button btnS = new Button(new TextureRegionDrawable(textureAtlasSkin.findRegion(Constants.S_BTNSUP)), new TextureRegionDrawable(textureAtlasSkin.findRegion(Constants.S_BTNSDOWN)));
           btnS.addListener(new ClickListener(){
               @Override
               public void clicked(InputEvent event, float x, float y) {
                   if (soundEnabled) soundTap.play(soundVolume);
                   infoScreen(Constants.IM_SUMMARY);
               }
           });

           btnS.setSize(Swidth, Sheight);
           btnS.setPosition(SposX, SposY);
           btnS.setDebug(false);
           stage.addActor(btnS);
           // O1
           Button btnO1 = new Button(new TextureRegionDrawable(textureAtlasSkin.findRegion(Constants.S_BTNO1UP)), new TextureRegionDrawable(textureAtlasSkin.findRegion(Constants.S_BTNO1DOWN)));
           btnO1.addListener(new ClickListener(){
               @Override
               public void clicked(InputEvent event, float x, float y) {
                   if (soundEnabled) soundTap.play(soundVolume);
                   infoScreen(Constants.IM_PREFERENCES);
                  }
           });
           btnO1.setSize(O1width, O1height);
           btnO1.setPosition(O1posX, O1posY);
           btnO1.setDebug(false);
           stage.addActor(btnO1);
           // K
           Button btnK = new Button(new TextureRegionDrawable(textureAtlasSkin.findRegion(Constants.S_BTNKUP)), new TextureRegionDrawable(textureAtlasSkin.findRegion(Constants.S_BTNKDOWN)));
           btnK.addListener(new ClickListener(){
               @Override
               public void clicked(InputEvent event, float x, float y) {
                   if (soundEnabled) soundTap.play(soundVolume);
                   infoScreen(Constants.IM_SKINS);
               }
           });

           btnK.setSize(Kwidth, Kheight);
           btnK.setPosition(KposX, KposY);
           btnK.setDebug(false);
           stage.addActor(btnK);
           // O2
           Button btnO2 = new Button(new TextureRegionDrawable(textureAtlasSkin.findRegion(Constants.S_BTNO2UP)), new TextureRegionDrawable(textureAtlasSkin.findRegion(Constants.S_BTNO2DOWN)));
           btnO2.addListener(new ClickListener(){
               @Override
               public void clicked(InputEvent event, float x, float y) {
                   if (soundEnabled) soundTap.play(soundVolume);
                   infoScreen(Constants.IM_HELP);
               }
           });
           btnO2.setSize(O2width, O2height);
           btnO2.setPosition(O2posX, O2posY);
           btnO2.setDebug(false);
           stage.addActor(btnO2);
           //BAN
           Button btnBan = new Button(new TextureRegionDrawable(textureAtlasSkin.findRegion(sBanUp)), new TextureRegionDrawable(textureAtlasSkin.findRegion(sBanDown)));
           btnBan.addListener(new ClickListener(){
               @Override
               public void clicked(InputEvent event, float x, float y) {
                   if (soundEnabled) soundTap.play(soundVolume);
                   dialogSureQuit();
               }
           });
           btnBan.setSize(banWidth, banHeight);
           btnBan.setPosition(banPosX, banPosY);
           btnBan.setDebug(false);
           stage.addActor(btnBan);
           // container
           Table container;
           container = new Table();
           stage.addActor(container);
           // label Mode
//           Label btnMode = new Label("", skin, "title-small");
           Label btnMode = new Label("", skin, "titleblack");
           btnMode.setPosition(modePosX, modePosY);
           btnMode.setSize(modeWidth,modeHeight);
//           btnMode.setFontScale(1.5f);
           btnMode.setAlignment(Align.center);
           String btnModeString = "";
           switch (menuMode) {
               case Constants.MM_ALL:
                   btnModeString = strings[Languages.ID_ALLLEVELS][language];
                   break;
               case Constants.MM_PLAYED:
                   btnModeString = strings[Languages.ID_INWORK][language];
                   break;
               case Constants.MM_SOLVED:
                   btnModeString = strings[Languages.ID_SOLVED][language];
                   break;
               case Constants.MM_PACKAGE:
                   btnModeString = activePackage.getName();
                   break;
           }
           if (!portrait) btnModeString = game.convertPortrait2Landscape(btnModeString);
           btnMode.setText(btnModeString);

           stage.addActor(btnMode);
           btnMode.setPosition(modePosX, modePosY);
           btnMode.setSize(modeWidth,modeHeight);

           // button All levels
           Image btnAll = new Image(textureAtlasSkin.findRegion(Constants.S_SWALL));
           btnAll.addListener(new ClickListener(){
               @Override
               public void clicked(InputEvent event, float x, float y) {
                   if (soundEnabled) soundTap.play(soundVolume);
                   switchMode(Constants.MM_ALL);
               }
           });
           btnAll.setPosition(allButtonX, allButtonY);
           btnAll.setSize(switchButtonSize, switchButtonSize);
           stage.addActor(btnAll);
           // active
           btnAllActive = new Image(textureAtlasSkin.findRegion(Constants.S_SWALLACTIVE));
           btnAllActive.setPosition(allButtonX, allButtonY);
           btnAllActive.setSize(switchButtonSize, switchButtonSize);
           stage.addActor(btnAllActive);
           // button Played levels
           Image btnPlayed = new Image(textureAtlasSkin.findRegion(Constants.S_SWINWORK));
           btnPlayed.addListener(new ClickListener(){
               @Override
               public void clicked(InputEvent event, float x, float y) {
                   if (soundEnabled) soundTap.play(soundVolume);
                   switchMode(Constants.MM_PLAYED);
               }
           });
           btnPlayed.setPosition(playedButtonX, playedButtonY);
           btnPlayed.setSize(switchButtonSize, switchButtonSize);
           stage.addActor(btnPlayed);
           // active
           btnPlayedActive = new Image(textureAtlasSkin.findRegion(Constants.S_SWINWORKACTIVE));
           btnPlayedActive.setPosition(playedButtonX, playedButtonY);
           btnPlayedActive.setSize(switchButtonSize, switchButtonSize);
           stage.addActor(btnPlayedActive);
           // button Solved levels
           Image btnSolved = new Image(textureAtlasSkin.findRegion(Constants.S_SWSOLVED));
           btnSolved.addListener(new ClickListener(){
               @Override
               public void clicked(InputEvent event, float x, float y) {
                   if (soundEnabled) soundTap.play(soundVolume);
                   switchMode(Constants.MM_SOLVED);
               }
           });
           btnSolved.setPosition(solvedButtonX, solvedButtonY);
           btnSolved.setSize(switchButtonSize, switchButtonSize);
           stage.addActor(btnSolved);
           // active
           btnSolvedActive = new Image(textureAtlasSkin.findRegion(Constants.S_SWSOLVEDACTIVE));
           btnSolvedActive.setPosition(solvedButtonX, solvedButtonY);
           btnSolvedActive.setSize(switchButtonSize, switchButtonSize);
           stage.addActor(btnSolvedActive);
           // button Package levels
           Image btnPackages = new Image(textureAtlasSkin.findRegion(Constants.S_SWPACKAGE));
           btnPackages.addListener(new ClickListener(){
               @Override
               public void clicked(InputEvent event, float x, float y) {
                   if (soundEnabled) soundTap.play(soundVolume);
                       switchMode(Constants.MM_PACKAGE);
               }
           });
           btnPackages.setPosition(packagesButtonX, packagesButtonY);
           btnPackages.setSize(switchButtonSize, switchButtonSize);
           stage.addActor(btnPackages);
           // active
           btnPackagesActive = new Image(textureAtlasSkin.findRegion(Constants.S_SWPACKAGEACTIVE));
           btnPackagesActive.addListener(new ClickListener(){
               @Override
               public void clicked(InputEvent event, float x, float y) {
                   if (soundEnabled) soundTap.play(soundVolume);
                   if (menuMode == Constants.MM_PACKAGE) {
                       infoScreen(Constants.IM_PACKAGES);
                   }
               }
           });
           btnPackagesActive.setPosition(packagesButtonX, packagesButtonY);
           btnPackagesActive.setSize(switchButtonSize, switchButtonSize);
           stage.addActor(btnPackagesActive);
           setSwitches();
           // container
           // SEZNAM LEVELU
           container.setPosition(containerPosX, containerPosY);
           container.setSize(containerSize, containerSize);
           container.align(Align.top);

           // add filter button to container table
           container.row();container.row();

           Table table = new Table();
           ScrollPane scroll = new ScrollPane(table, skin) {
           };
           container.add(scroll).center();

           scroll.setSize(virtualWidth - 120, virtualHeight - 200-100);
           scroll.setScrollingDisabled(true, false);
           scroll.setFlickScroll(true);

           scroll.setTouchable(Touchable.enabled);
           scroll.setOverscroll(false, false);
           scroll.setScrollBarTouch(true);
           scroll.updateVisualScroll();
           scroll.setScrollbarsVisible(false);

           table.pad(10).defaults().expandX().space(1).align(Align.left);
           table.add(new Label("",skin)).width(20);table.row();
           if (activeLevel!=null && menuMode==Constants.MM_PLAYED && activeLevel.inPlay) {
               addRow(activeLevel, table);
               table.add(new Label("",skin)).width(20);table.row();
               table.add(new Label("",skin)).width(20);table.row();
           }
           for (int j=0; j<selectedPackages.size; j++) {
               for (int i = 0; i < selectedPackages.get(j).levels.size; i++) {
                   int finalI = i;
                   if (filter(selectedPackages.get(j).levels.get(i))) {
                       // button info
                       if (menuMode != Constants.MM_PLAYED) {
                           addRow(selectedPackages.get(j).levels.get(finalI), table);table.row();
                       }
                       else if (selectedPackages.get(j).levels.get(finalI) != activeLevel) {
                           addRow(selectedPackages.get(j).levels.get(finalI), table);table.row();
                       }
                       table.add(new Label("",skin)).width(20);table.row();
                       table.add(new Label("",skin)).width(20);table.row();
                   }
               }
           }
           stage.addActor(semitransparentImage);

           im.addProcessor(stage);
           Gdx.input.setInputProcessor(im);
           game.platform.setOrientation(screenOrientation);
       }
    }

    private void addRow(final Level aLevel, Table aTable) {
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = skin.getFont("default");
        labelStyle.fontColor = Color.BLACK;

//        aTable.row();
        aTable.add().width(rowHalfSPACE);
        // button info
        TextButton buttonInfo = new TextButton("i", skin);
        buttonInfo.setColor(Color.OLIVE);
        buttonInfo.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                if (soundEnabled) soundTap.play(soundVolume);
                infoLevel = aLevel;
                infoScreen(Constants.IM_LEVELINFO);

            }
        });
        aTable.add(buttonInfo).size(infoButtonSize);
        aTable.add().width(10);
        // thumbnail
        Image image = new Image(aLevel.textureThumbnail);
        image.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (soundEnabled) soundTap.play(soundVolume);
                activeLevel = aLevel;
                game.changeScreen(Constants.PLAY);
            }
        });
        int height = thumbWidth*aLevel.height/aLevel.width;
        aTable.add(image).size(thumbWidth, height);
        aTable.add().width(10);
        // right button
        if (aLevel.solved) {
            // button solved
            TextButton buttonRight = new TextButton("s", skin);
            buttonRight.setColor(Color.GOLD);
            buttonRight.setTouchable(Touchable.disabled);
            aTable.add(buttonRight).size(infoButtonSize);
        }
        else {
            if (aLevel.inPlay)
            {
                // button mark as not played
                TextButton buttonRight = new TextButton("x", skin);
                buttonRight.setColor(Color.FIREBRICK);
                buttonRight.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                        if (soundEnabled) soundTap.play(soundVolume);
                        Dialog dialog = new Dialog(" ", skin) {
                            public void result(Object obj) {
                                if (soundEnabled) soundTap.play(soundVolume);
                                if ((Boolean) obj) {
                                    aLevel.inPlay = false;
                                    aLevel.time = 0;
                                    aLevel.moves = "";
                                    aLevel.nextMove = 0;
                                    game.changeScreen(Constants.MENU);
                                }
                                remove();
                            }
                        }.text(strings[Languages.ID_MARKASNOT][language]).button(strings[Languages.ID_YES][language], true).button(strings[Languages.ID_NO][language], false);
                        dialog.setColor(1f, 0.3f, 0.3f,1f);
//                        dialog.setColor(Color.YELLOW);
                        dialog.show(stage) ;
                    }
                });
                aTable.add(buttonRight).size(infoButtonSize);

            }
            else {
                aTable.add().width(infoButtonSize);
            }
        }
        aTable.add().width(rowHalfSPACE);
    }


    private boolean filter(Level aLevel){
       boolean ret = true;
       switch (menuMode) {
           case Constants.MM_PLAYED:
               ret = (aLevel.inPlay && !aLevel.solved);
               break;
           case Constants.MM_SOLVED:
               ret = aLevel.solved;
               break;
       }
       return (ret);
    }

    @Override
    public void render(float delta) {
        stage.act();
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

//        im.removeProcessor(stage);
        stage.dispose();
    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.BACK) {
            if (soundEnabled) soundTap.play(soundVolume);
            dialogSureQuit();
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

    private void dialogSureQuit(){
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = skin.getFont("default");
        labelStyle.fontColor = Color.RED;
        Dialog dialog = new Dialog(" ", skin) {
            public void result(Object obj) {
                if (soundEnabled) soundTap.play(soundVolume);
                if ((Boolean) obj) {
                    game.save();
                    Gdx.app.exit();
                }
                else {
                    remove();
                }
                semitransparentImage.setVisible(false);
            }
        }.text(strings[Languages.ID_QUITGAME][language], labelStyle).button(strings[Languages.ID_YES][language], true).button(strings[Languages.ID_NO][language], false);
        if (soundEnabled) assetManager.get(Constants.S_SOUNDDIALOG, Sound.class).play(soundVolume);
        semitransparentImage.setVisible(true);
        dialog.show(stage) ;
    }

    private void switchMode(int aMode) {
        menuMode = aMode;
        setSwitches();
        game.changeScreen(Constants.MENU);
    }

    private void setSwitches(){
        btnPlayedActive.setVisible(false);
        btnAllActive.setVisible(false);
        btnPackagesActive.setVisible(false);
        btnSolvedActive.setVisible(false);
        switch (menuMode) {
            case Constants.MM_ALL:
                btnAllActive.setVisible(true);
                break;
            case Constants.MM_PLAYED:
                btnPlayedActive.setVisible(true);
                break;
            case Constants.MM_SOLVED:
                btnSolvedActive.setVisible(true);
                break;
            case Constants.MM_PACKAGE:
                btnPackagesActive.setVisible(true);
                break;
        }
    }

    private void infoScreen(int aInfoMode) {
        infoMode = aInfoMode;
        game.changeScreen(Constants.INFO);
    }

}


