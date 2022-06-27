package com.pes.sokoban.Sprites;

import com.pes.sokoban.Constants.Constants;
import com.pes.sokoban.Screens.PlayScreen;
import static com.pes.sokoban.Global.Game.textureAtlasSkin;

public class DeskItemGround extends DeskItem {

    public DeskItemGround(PlayScreen screen, int x, int y) {
        super(screen, x, y);
        ID = Constants.ID_GROUND;
    }

    public void setTextureRegion() {
        textureRegion = textureAtlasSkin.findRegion(Constants.S_SOKOBANGROUND);
    }


}
