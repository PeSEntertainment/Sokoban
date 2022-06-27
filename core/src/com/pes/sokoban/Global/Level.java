package com.pes.sokoban.Global;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.pes.sokoban.Constants.Constants;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.Date;

import static com.pes.sokoban.Global.Game.texThBox;
import static com.pes.sokoban.Global.Game.texThBoxOnTarget;
import static com.pes.sokoban.Global.Game.texThEmpty;
import static com.pes.sokoban.Global.Game.texThGround;
import static com.pes.sokoban.Global.Game.texThSokoban;
import static com.pes.sokoban.Global.Game.texThTarget;
import static com.pes.sokoban.Global.Game.texThWall;

public class Level {
    public Package lPackage;
    public String id;
    public Integer width;
    public Integer height;
    public Boolean inPlay;
    public String moves;
    public Integer time;
    public Integer nextMove;
    public Boolean solved;
    public String solved_moves;
    public int solved_time;
    public Date solved_date;
    public int desk[][];
    public int task[][];
    public int situation[][];
    public Texture textureThumbnail;
    public int thWidth, thHeight;

    public Level(Package aPackage){
        lPackage = aPackage;
        id="no id info";
        inPlay=false;
        solved  = false;
        moves = "";
        solved_moves = "";
        solved_time = 0;
        nextMove = 0;
        time = 0;
        thWidth = thHeight = 0;
    }

    public String getInfoText(int iWrap){
        String text;
        text = "Title\n"+ lPackage.title +
                "\n"+width.toString() + " x " + height.toString()+
                "\n\nCopyright\n"+lPackage.copyright+
                "\n\nEmail\n"+lPackage.email+
                "\nUrl\n"+lPackage.url;

        if (solved) {
            Integer lenght = solved_moves.length();
            text = text + "\nSolved in "+lenght.toString()+" moves";
        }
        return text;
    }

    public void setToStart(){
        for (int j=0; j<height; j++) for (int i=0; i<width; i++) situation[i][j]=task[i][j];
        nextMove = 0;
    }

    public void setLevel(Level aLevel){
        id = aLevel.id;
        width = aLevel.width;
        height = aLevel.height;
    }

    public void eventuallyAddInformation(LevelPlayed aLevelPlayed){
        //Todo Title se spravne nenacte - je rozdil title z Level.slc a Played.slc
        if(id.equals(aLevelPlayed.id)&& width.equals(aLevelPlayed.width) && height==aLevelPlayed.height) {
            solved = aLevelPlayed.solved;
            solved_moves = aLevelPlayed.solved_moves;
            solved_time = aLevelPlayed.solved_time;
            moves = aLevelPlayed.moves;
            time = aLevelPlayed.time;
            if (!solved) inPlay = true;
        }
    }

    public void fillFromPlayed(LevelPlayed aLevelPlayed){
        solved_moves = aLevelPlayed.moves;
        solved = aLevelPlayed.solved;
        inPlay = true;
    }

    public Boolean isJustSolved(){
        boolean done;
        done = true;
        int j = 0;
        while ((j<height) && done) {
            int i = 0;
            while ((i < width) && done) {
                if (desk[i][j] == Constants.ID_TARGET) {
                    if (situation[i][j] != Constants.ID_BOX) {
                        done = false;
                    }
                }
                i++;
            }
            j++;
        }
        return done;
    }

    public void updateAfterSolution(){
        if (!solved) {
            solved_moves = moves;
            solved_time = time;
        }
        else {
            if (moves.length()<solved_moves.length()) {
                solved_moves = moves;
                solved_time = time;
            }
        }
        solved = true;
        inPlay = false;
        nextMove = 0;
        time = 0;
    }

    public String getId(){
        int index = id.lastIndexOf("/");
        String ret = id.substring(index + 1);
        ret = ret.substring(0, ret.length()-4);
        return ret;
    }

    public void setInPlayFromSolved(){
        inPlay = true;
        time = solved_time;
    }

    public Boolean load(Node nNode){
        boolean wasWall;
        boolean loadOK = true;
        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
            Element eElement = (Element) nNode;
            id = eElement.getAttribute("Id");
            width = Integer.parseInt(eElement.getAttribute("Width"));
            height = Integer.parseInt(eElement.getAttribute("Height"));
            desk = new int[width][height];
            situation = new int[width][height];
            task = new int[width][height];
            int j = height-1;
            for (int k=0; k<height; k++) {
                wasWall = false;
                String pureRow = eElement.getElementsByTagName("L").item(k).getTextContent();
                for (int i = 0; i < pureRow.length(); i++) {
                    desk[i][j] = Constants.ID_EMPTY;
                    task[i][j] = Constants.ID_EMPTY;
                    char charString = pureRow.toCharArray()[i];
                    switch (charString) {
                        case Constants.CHAR_WALL:
                            wasWall = true;
                            desk[i][j] = Constants.ID_WALL;
                            break;
                        case Constants.CHAR_TARGET:
                            desk[i][j] = Constants.ID_TARGET;
                            break;
                        case Constants.CHAR_BOX:
                            task[i][j] = Constants.ID_BOX;
                            desk[i][j] = Constants.ID_GROUND;
                            break;
                        case Constants.CHAR_BOXONTARGET:
                            task[i][j] = Constants.ID_BOX;
                            desk[i][j] = Constants.ID_TARGET;
                            break;
                        case Constants.CHAR_GROUND:
                            if (wasWall) desk[i][j] = Constants.ID_GROUND;
                            break;
                        case Constants.CHAR_SOKOBAN:
                            desk[i][j] = Constants.ID_GROUND;
                            task[i][j] = Constants.ID_SOKOBAN;
                            break;
                        case Constants.CHAR_SOKOBANONTARGET:
                            task[i][j] = Constants.ID_SOKOBAN;
                            desk[i][j] = Constants.ID_TARGET;
                            break;

                    }
                }
                for (int i = pureRow.length(); i < width; i++) {
                    desk[i][j] = Constants.ID_EMPTY;
                    task[i][j] = Constants.ID_EMPTY;
                }
                j--;
            }
        }
        return loadOK;
    }

    public void recalcWalls() {
        int wallType;
        //stred
        for (int j=0; j<height; j++) {
            for (int i = 0; i < width; i++) {
                if (desk[i][j] >= Constants.ID_WALL) {
                    wallType = 0;
                    if (j<height-1) if (desk[i][j+1] >= Constants.ID_WALL) wallType += 1;
                    if (i>1) if (desk[i-1][j] >= Constants.ID_WALL) wallType += 2;
                    if (i<width-1)if (desk[i+1][j] >= Constants.ID_WALL) wallType += 4;
                    if (j>1) if (desk[i][j-1] >= Constants.ID_WALL) wallType += 8;
                    desk[i][j] += wallType;
                }
            }
        }
    }

    public void removeArtefacts() {
        boolean empty;
        int k;
        for (int i = 0; i < width; i++) {
            k = 0; empty = true;
            while (k < height && empty) {
                if (desk[i][k] == Constants.ID_WALL) empty = false;
                else desk[i][k] = Constants.ID_EMPTY;
                k++;
            }
            k = height;empty = true;
            while (k > 0 && empty) {
                k--;
                if (desk[i][k] == Constants.ID_WALL) empty = false;
                else desk[i][k] = Constants.ID_EMPTY;
            }
        }
    }

    public void createNiceThumbnail(){

        TextureData dataTexBox = texThBox.getTextureData();
        if (!dataTexBox.isPrepared()) dataTexBox.prepare();
        Pixmap pixThBox = dataTexBox.consumePixmap();

        TextureData dataTexWall = texThWall.getTextureData();
        if (!dataTexWall.isPrepared()) dataTexWall.prepare();
        Pixmap pixThWall = dataTexWall.consumePixmap();

        TextureData dataTexGround = texThGround.getTextureData();
        if (!dataTexGround.isPrepared()) dataTexGround.prepare();
        Pixmap pixThGround = dataTexGround.consumePixmap();

        TextureData dataTexTarget = texThTarget.getTextureData();
        if (!dataTexTarget.isPrepared()) dataTexTarget.prepare();
        Pixmap pixThTarget = dataTexTarget.consumePixmap();

        TextureData dataTexBoxOnTarget = texThBoxOnTarget.getTextureData();
        if (!dataTexBoxOnTarget.isPrepared()) dataTexBoxOnTarget.prepare();
        Pixmap pixThBoxOnTarget = dataTexBoxOnTarget.consumePixmap();

        TextureData dataTexEmpty = texThEmpty.getTextureData();
        if (!dataTexEmpty.isPrepared()) dataTexEmpty.prepare();
        Pixmap pixThEmpty = dataTexEmpty.consumePixmap();

        TextureData dataTexSokoban = texThSokoban.getTextureData();
        if (!dataTexSokoban.isPrepared()) dataTexSokoban.prepare();
        Pixmap pixThSokoban = dataTexSokoban.consumePixmap();

        int elemSize = 16;
        thWidth = width * elemSize; thHeight = height * elemSize;

        Pixmap pixmap = new Pixmap(thWidth, thHeight, Pixmap.Format.RGBA8888);
        for (int j=0; j<height; j++) {
            for (int i = 0; i < width; i++) {
                switch (desk[i][height-1-j]) {
                    case Constants.ID_EMPTY:
                        pixmap.drawPixmap(pixThEmpty, i * elemSize, j * elemSize);
                        break;
                    case Constants.ID_GROUND:
                        pixmap.drawPixmap(pixThGround, i * elemSize, j * elemSize);
                        break;
                    case Constants.ID_TARGET:
                        pixmap.drawPixmap(pixThTarget, i * elemSize, j * elemSize);
                        break;
                    default:
                        if (desk[i][height-1-j]>=Constants.ID_WALL) pixmap.drawPixmap(pixThWall, i * elemSize, j * elemSize);
                        break;
                }
                switch (task[i][height-1-j]) {
                    case Constants.ID_BOX:
                        if (desk[i][height-1-j]==Constants.ID_TARGET) {
                            pixmap.drawPixmap(pixThBoxOnTarget, i * elemSize, j * elemSize);
                        }
                        else pixmap.drawPixmap(pixThBox, i * elemSize, j * elemSize);
                        break;
                    case Constants.ID_SOKOBAN:
                        pixmap.drawPixmap(pixThSokoban, i * elemSize, j * elemSize);
                        break;
                }
          }
        }
        textureThumbnail = new Texture(pixmap);
        pixmap.dispose();
    }

}
