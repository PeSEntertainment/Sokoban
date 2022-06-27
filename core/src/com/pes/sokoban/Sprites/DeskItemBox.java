package com.pes.sokoban.Sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.pes.sokoban.Constants.Constants;
import com.pes.sokoban.Screens.PlayScreen;
import static com.pes.sokoban.Global.Game.activeLevel;
import static com.pes.sokoban.Global.Game.textureAtlasSkin;


public class DeskItemBox extends DeskItem {
    private TextureRegion textureRegionBoxOnTarget;

    public DeskItemBox(PlayScreen screen, int x, int y) {
        super(screen, x, y);
        ID = Constants.ID_BOX;
    }

    public void setTextureRegion() {
        textureRegion = textureAtlasSkin.findRegion(Constants.S_SOKOBANBOX);
        textureRegionBoxOnTarget = textureAtlasSkin.findRegion(Constants.S_SOKOBANBOXONTARGET);
    }

    public void update(float dt) {
        if (activeLevel.desk[i][j]==Constants.ID_TARGET) setRegion(textureRegionBoxOnTarget);
        else setRegion(textureRegion);
    }

}
