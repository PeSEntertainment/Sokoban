package com.pes.sokoban.Sprites;

import com.pes.sokoban.Constants.Constants;
import com.pes.sokoban.Screens.PlayScreen;

import static com.pes.sokoban.Constants.Constants.ID_WALL_;
import static com.pes.sokoban.Constants.Constants.ID_WALL_D;
import static com.pes.sokoban.Constants.Constants.ID_WALL_D_L;
import static com.pes.sokoban.Constants.Constants.ID_WALL_D_L_R;
import static com.pes.sokoban.Constants.Constants.ID_WALL_D_R;
import static com.pes.sokoban.Constants.Constants.ID_WALL_L;
import static com.pes.sokoban.Constants.Constants.ID_WALL_L_R;
import static com.pes.sokoban.Constants.Constants.ID_WALL_R;
import static com.pes.sokoban.Constants.Constants.ID_WALL_U;
import static com.pes.sokoban.Constants.Constants.ID_WALL_U_D;
import static com.pes.sokoban.Constants.Constants.ID_WALL_U_D_L;
import static com.pes.sokoban.Constants.Constants.ID_WALL_U_D_L_R;
import static com.pes.sokoban.Constants.Constants.ID_WALL_U_D_R;
import static com.pes.sokoban.Constants.Constants.ID_WALL_U_L;
import static com.pes.sokoban.Constants.Constants.ID_WALL_U_L_R;
import static com.pes.sokoban.Constants.Constants.ID_WALL_U_R;
import static com.pes.sokoban.Global.Game.textureAtlasSkin;

public class DeskItemWall extends DeskItem {
    private int wallType;

    public DeskItemWall(PlayScreen screen, int x, int y, int wallNr) {
        super(screen, x, y);
        ID = Constants.ID_WALL;
        wallType = wallNr-ID;
    }


    public void setTextureRegion() {
        switch (wallType) {
            case ID_WALL_ :
                textureRegion = textureAtlasSkin.findRegion(Constants.S_WALL_);
                break;
            case ID_WALL_U :
                textureRegion = textureAtlasSkin.findRegion(Constants.S_WALL_);
                break;
            case ID_WALL_L :
                textureRegion = textureAtlasSkin.findRegion(Constants.S_WALL_);
                break;
            case ID_WALL_U_L :
                textureRegion = textureAtlasSkin.findRegion(Constants.S_WALL_);
                break;
            case ID_WALL_R :
                textureRegion = textureAtlasSkin.findRegion(Constants.S_WALL_);
                break;
            case ID_WALL_U_R :
                textureRegion = textureAtlasSkin.findRegion(Constants.S_WALL_);
                break;
            case ID_WALL_L_R :
                textureRegion = textureAtlasSkin.findRegion(Constants.S_WALL_);
                break;
            case ID_WALL_U_L_R :
                textureRegion = textureAtlasSkin.findRegion(Constants.S_WALL_);
                break;
            case ID_WALL_D :
                textureRegion = textureAtlasSkin.findRegion(Constants.S_WALL_);
                break;
            case ID_WALL_U_D :
                textureRegion = textureAtlasSkin.findRegion(Constants.S_WALL_);
                break;
            case ID_WALL_D_L :
                textureRegion = textureAtlasSkin.findRegion(Constants.S_WALL_);
                break;
            case ID_WALL_U_D_L :
                textureRegion = textureAtlasSkin.findRegion(Constants.S_WALL_);
                break;
            case ID_WALL_D_R :
                textureRegion = textureAtlasSkin.findRegion(Constants.S_WALL_);
                break;
            case ID_WALL_U_D_R :
                textureRegion = textureAtlasSkin.findRegion(Constants.S_WALL_);
                break;
            case ID_WALL_D_L_R :
                textureRegion = textureAtlasSkin.findRegion(Constants.S_WALL_);
                break;
            case ID_WALL_U_D_L_R :
                textureRegion = textureAtlasSkin.findRegion(Constants.S_WALL_);
                break;
            default:
                textureRegion = textureAtlasSkin.findRegion(Constants.S_WALL_);
                break;
        }
    }

}
