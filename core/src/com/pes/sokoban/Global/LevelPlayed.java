package com.pes.sokoban.Global;


import static com.pes.sokoban.Global.Game.levelsPlayed;

public class LevelPlayed {
    public String title;
    public String copyright;
    public String id;
    public Integer width;
    public Integer height;
    public Boolean solved;
    public String solved_moves;
    public Integer solved_time;
    public String moves;
    public Integer time;

    public LevelPlayed(String aTitle, String aCopyright, String aId, int aWidth, int aHeight, Boolean aSolved, String aSolvedMoves,
                       Integer aSolvedTime, String aMoves, Integer aTime) {
        title = aTitle;
        copyright = aCopyright;
        id = aId;
        width = aWidth;
        height = aHeight;
        solved = aSolved;
        solved_moves = aSolvedMoves;
        solved_time = aSolvedTime;
        moves = aMoves;
        time = aTime;
    }

    public LevelPlayed(Level aLevel){
        updateLevelPlayed(aLevel);
    }

    public void updateLevelPlayed(Level aLevel){
        title = aLevel.lPackage.title;
        copyright = aLevel.lPackage.copyright;
        id = aLevel.id;
        width = aLevel.width;
        height = aLevel.height;
        solved = aLevel.solved;
        solved_moves = aLevel.solved_moves;
        solved_time = aLevel.solved_time;
        moves = aLevel.moves;
        time = aLevel.time;
    }

    public Boolean isEqual(Level aLevel){

//        boolean equal = false;
//        if(title.equals(aLevel.lPackage.title)&&copyright.equals(aLevel.lPackage.copyright)&&id.equals(aLevel.id)&& width.equals(aLevel.width) && height.equals(aLevel.height)) equal = true;
        return (title.equals(aLevel.lPackage.title)&&copyright.equals(aLevel.lPackage.copyright)&&id.equals(aLevel.id)&& width.equals(aLevel.width) && height.equals(aLevel.height));
//        return equal;
    }

    public String getRow(){
        String ret = "<Level Title=\"" + title +
                "\" Copyright=\"" + copyright +
                "\" Id=\"" + id +
                "\" Width=\"" + width.toString() +
                "\" Height=\"" + height.toString() +
                "\" Solved=\"" + solved.toString() +
                "\" SolvedMoves=\"" + solved_moves +
                "\" SolvedTime=\"" + solved_time.toString() +
                "\" Moves=\"" + moves +
                "\" Time=\"" + time.toString() +
                "\">\n";
        return ret;
    }
}
