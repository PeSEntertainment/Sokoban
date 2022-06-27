package com.pes.sokoban.Screens;
import java.util.Arrays;
import java.util.Comparator;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.pes.sokoban.Constants.Languages;
import com.pes.sokoban.SokobanGame;

import static com.pes.sokoban.Global.Game.language;
import static com.pes.sokoban.Global.Game.soundEnabled;
import static com.pes.sokoban.Global.Game.soundVolume;
import static com.pes.sokoban.Global.Game.virtualHeight;
import static com.pes.sokoban.Global.Game.virtualWidth;
import static com.pes.sokoban.Global.Game.soundTap;
import static com.pes.sokoban.Constants.Languages.strings;


public class FileChooser2 extends Dialog {

    private SokobanGame game;
    private Skin skin;
    private FileHandle directory;
    private FileHandle file;

    public FileHandle getFile() {
        return file;
    }

    @Override
    public Dialog show(Stage stage) {
        return super.show(stage);
    }


    public void setDirectory(FileHandle directory) {
        if (this.directory != directory) {
            this.directory = directory;
            this.file = null;
            buildList();
        }
    }

    public void setFile(FileHandle file) {
        if (this.file != file) {
            if (this.file != null) {
                Label label = this.findActor(this.file.name());
                label.setColor(Color.BLACK);
            }
            Label label = this.findActor(file.name());
            label.setColor(Color.RED);
            this.file = file;
            buildList();
        }
    }

    public FileChooser2(String title, Skin skin, SokobanGame game) {
        super(title, skin);
        this.game = game;
        this.getCell(getButtonTable()).expandX().fill();
        this.getButtonTable().defaults();
        this.button(strings[Languages.ID_CANCEL][language], false);
        this.setModal(true);
        this.skin = skin;
    }

    private void buildList() {
        FileHandle[] files = directory.list();
        Arrays.sort(files, new Comparator<FileHandle>() {
            @Override
            public int compare(FileHandle o1, FileHandle o2) {
                if (o1.isDirectory() && !o2.isDirectory()) {
                    return -1;
                }
                if (o2.isDirectory() && !o1.isDirectory()) {
                    return +1;
                }
                return o1.name().compareToIgnoreCase(o2.name());
            }
        });

        Table container = new Table();
        container.setFillParent(true);

        Table table = new Table().top().left();
        ScrollPane scroll = new ScrollPane(table, skin);
        scroll.setScrollingDisabled(true, false);
        scroll.setTouchable(Touchable.enabled);
        scroll.setOverscroll(false, false);
        scroll.setScrollBarTouch(true);
        scroll.setFlickScroll(true);
        scroll.updateVisualScroll();
        scroll.setScrollbarsVisible(false);

        table.defaults().left();
        ClickListener fileClickListener = new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (soundEnabled) soundTap.play(soundVolume);
                Label target = (Label) event.getTarget();
                if (target.getName().equals("..")) {
                    setDirectory(directory.parent());
                } else {
                    FileHandle handle = directory.child(target.getName());
                    if (handle.isDirectory()) {
                        setDirectory(handle);
                    } else {
                        setFile(handle);
                    }
                }
            }
        };
        table.row();
        Label label = new Label("  ..", skin);
        label.setFontScale(1.5f);
        label.setName("..");
        label.addListener(fileClickListener);
        table.add(label).expandX().fillX();
        for (FileHandle file : files) {
            table.row();
            label = new Label("  "+file.name(), skin);
            label.setName(file.name());
            label.setFontScale(1.5f);
            label.addListener(fileClickListener);
            table.add(label).expandX().fillX().width(virtualWidth-20);
        }
        String selectFileText = strings[Languages.ID_SELECTFILE][language];
        if (file!=null) selectFileText = file.name();
        TextButton buttonSelectedFile = new TextButton(selectFileText, skin);
        buttonSelectedFile.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (file!=null) {
                    if (game.notSlcFile(file)||game.packageInPackages(file)) cancel();
                    else {
                        result(true);
                    }
                }
            }
        });

        buttonSelectedFile.setColor(Color.GREEN);
        if (file!=null) {
            if (game.notSlcFile(file)||game.packageInPackages(file)) buttonSelectedFile.setColor(Color.RED);
        }
        else buttonSelectedFile.setColor(Color.RED);
        container.add(buttonSelectedFile).width(320);
        container.row();
        container.add(scroll).width(virtualWidth).height(virtualHeight-200);
        container.row();

        scroll.setActor(table);
        this.getContentTable().reset();
        this.getContentTable().add(container).prefHeight(virtualHeight-100).expand().fill();
    }

}
