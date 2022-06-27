package com.pes.sokoban.Sprites;

import com.pes.sokoban.Constants.Constants;
import com.pes.sokoban.Screens.PlayScreen;
import static com.pes.sokoban.Global.Game.textureAtlasSkin;

public class DeskItemTarget extends DeskItem {

    public DeskItemTarget(PlayScreen screen, int x, int y) {
        super(screen, x, y);
        ID = Constants.ID_TARGET;
    }

    public void setTextureRegion() {
        textureRegion = textureAtlasSkin.findRegion(Constants.S_SOKOBANTARGET);
    }

}
