package com.pes.sokoban.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.pes.sokoban.Constants.Constants;
import com.pes.sokoban.Screens.PlayScreen;
import static com.pes.sokoban.Global.Game.textureAtlasSkin;


public class DeskItem extends Sprite{
    protected PlayScreen screen;
    protected int ID;
    protected int i,j;
    protected TextureRegion textureRegion;

    public DeskItem(PlayScreen screen, int x, int y) {
        this.screen = screen;
        ID = Constants.ID_EMPTY;
        i = x;
        j = y;
        setPosition(x, y);
        setBounds(x, y, 1f , 1f);
        setTextureRegion();
        setRegion(textureRegion);
    }

    public void setTextureRegion() {
        textureRegion = textureAtlasSkin.findRegion(Constants.S_SOKOBANEMPTY);
    }

    public void update(float dt) { }


    public TextureRegion getTextureRegion(){
        return textureRegion;
    }

}
