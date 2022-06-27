package com.pes.sokoban.Constants;

public class Constants {

    public static final String S_SKIN = "sokoban.json";

    public static final String S_ATLASCOMMON = "common.atlas";
    public static final String S_ATLASBERUSKA = "skin_beruska/beruska.atlas";
    public static final String S_ATLASROBI = "skin_robi/robi.atlas";
    public static final String S_ATLASTUCI = "skin_tuci/tuci.atlas";
    public static final String S_ATLASRYBKA = "skin_rybka/rybka.atlas";

    public static final String S_DIRBERUSKA = "skin_beruska/";
    public static final String S_DIRROBI = "skin_robi/";
    public static final String S_DIRTUCI = "skin_tuci/";
    public static final String S_DIRRYBKA = "skin_rybka/";

    public static final String S_BCKGRMAINP = "bckgrMainP";
    public static final String S_BCKGRMAINL = "bckgrMainL";
    public static final String S_BCKGRCONTAINER = "bckgrContainer";
    public static final String S_BCKGRPLAY = "bckgrPlay";

    public static final String S_SWSOLVED = "done";
    public static final String S_SWSOLVEDACTIVE = "done - pressed";
    public static final String S_SWINWORK = "run";
    public static final String S_SWINWORKACTIVE = "run - pressed";
    public static final String S_SWALL = "all levels";
    public static final String S_SWALLACTIVE = "all levels - pressed";
    public static final String S_SWPACKAGE = "download";
    public static final String S_SWPACKAGEACTIVE = "download - pressed";

    public static final String S_IMAGESKINTUCI = "skinTuci";
    public static final String S_IMAGESKINROBI = "skinRobi";
    public static final String S_IMAGESKINBERUSKA = "skinBeruska";
    public static final String S_IMAGESKINRYBKA = "skinRybka";

    public static final String S_MODEWALK = "steps_walk";
    public static final String S_MODEJUMP = "steps_jump";

    public static final String S_OSCREPPREV = "osbRepPrev";
    public static final String S_OSCREPNEXT = "osbRepNext";
    public static final String S_OSCREPFORWARD = "osbRepForward";
    public static final String S_OSCREPBACK = "osbRepBack";
    public static final String S_OSCREPPAUSE = "osbRepPause";
    public static final String S_OSCUP = "osbUp";
    public static final String S_OSCDOWN = "osbDown";
    public static final String S_OSCRIGHT = "osbRight";
    public static final String S_OSCLEFT = "osbLeft";
    public static final String S_OSCPREV = "osbPrev";
    public static final String S_OSCNEXT = "osbNext";

    public static final String S_SEMITRANSPARENT = "semitransparent";
    public static final String S_YOUTUBE = "YouTube";
    public static final String S_GETITONGOOGLEPLAY = "getitongoogleplay";

    public static final String S_BTNSUP = "s";
    public static final String S_BTNSDOWN = "s - stin";
    public static final String S_BTNO1UP = "o1";            // button settings
    public static final String S_BTNO1DOWN = "o1 - stin";
    public static final String S_BTNKUP = "k";
    public static final String S_BTNKDOWN = "k - stin";
    public static final String S_BTNO2UP = "o2";            // button help
    public static final String S_BTNO2DOWN = "o2 - stin";
    public static final String S_BTNBANUP = "ban";
    public static final String S_BTNBANDOWN = "ban - stin";
    public static final String S_BTNBANUPL = "banL";
    public static final String S_BTNBANDOWNL = "banL - stin";

    public static final String S_IMGHOME = "imgHome";

    // AUDIO
    public static final String S_SOUNDSKIN = "audio/SPRINGBP.WAV";
    public static final String S_SOUNDTAP = "audio/tap.wav";
    public static final String S_SOUNDWALK = "audio/walk.wav";
    public static final String S_SOUNDJUMP = "audio/jump.wav";
    public static final String S_SOUNDPUSH = "audio/push.wav";
    public static final String S_SOUNDWIN = "audio/win.wav";
    public static final String S_SOUNDOPENWINDOW = "audio/openWindow.wav";
    public static final String S_SOUNDDIALOG = "audio/dialog.wav";
    public static final String[] midiFiles = {"Broken arm","Casual afternoon","Icy garden","Journey forgoten","No one","Without time","Old friends","The road you use ...","We have to do something"};
    public static final Integer BASIC_SIZE = 400;

    // MENU MODE
    public static final int MM_PACKAGE = 1;
    public static final int MM_PLAYED = 2;
    public static final int MM_ALL = 3;
    public static final int MM_SOLVED = 4;

    // INFO MODE
    public static final int IM_HELP = 1;
    public static final int IM_COPYRIGHT = 2;
    public static final int IM_FULLVERSION = 3;
    public static final int IM_WIN = 4;
    public static final int IM_PREFERENCES = 5;
    public static final int IM_PACKAGES = 6;
    public static final int IM_LEVELINFO = 7;
    public static final int IM_SUMMARY = 8;
    public static final int IM_HELPSETTINGS = 9;
    public static final int IM_SKINS = 10;


    // SOKOBAN
    public static final int ID_EMPTY = 1;
    public static final int ID_GROUND = 2;
    public static final int ID_SOKOBAN = 4;
    public static final int ID_TARGET = 8;
    public static final int ID_BOX = 16;
    public static final int ID_BOXONTARGET = 32;
    public static final int ID_WALL = 64;

    //Up 1, Left 2, Right 4, Down 8
    public static final int ID_WALL_ = 0;
    public static final int ID_WALL_U = 1;
    public static final int ID_WALL_L = 2;
    public static final int ID_WALL_U_L = 3;
    public static final int ID_WALL_R = 4;
    public static final int ID_WALL_U_R = 5;
    public static final int ID_WALL_L_R = 6;
    public static final int ID_WALL_U_L_R = 7;
    public static final int ID_WALL_D = 8;
    public static final int ID_WALL_U_D = 9;
    public static final int ID_WALL_D_L = 10;
    public static final int ID_WALL_U_D_L = 11;
    public static final int ID_WALL_D_R = 12;
    public static final int ID_WALL_U_D_R = 13;
    public static final int ID_WALL_D_L_R = 14;
    public static final int ID_WALL_U_D_L_R = 15;

    public static final String S_WALL_ = "wall";
    public static final String S_WALL_U = "wall_u";
    public static final String S_WALL_L = "wall_l";
    public static final String S_WALL_U_L = "wall_u_l";
    public static final String S_WALL_R = "wall_r";
    public static final String S_WALL_U_R = "wall_u_r";
    public static final String S_WALL_L_R = "wall_l_r";
    public static final String S_WALL_U_L_R = "wall_u_l_r";
    public static final String S_WALL_D = "wall_d";
    public static final String S_WALL_U_D = "wall_u_d";
    public static final String S_WALL_D_L = "wall_d_l";
    public static final String S_WALL_U_D_L = "wall_u_d_l";
    public static final String S_WALL_D_R = "wall_d_r";
    public static final String S_WALL_U_D_R = "wall_u_d_r";
    public static final String S_WALL_D_L_R = "wall_d_l_r";
    public static final String S_WALL_U_D_L_R = "wall_u_d_l_r";

    public static final boolean OSC_PLAY = false;

    public static final char CHAR_WALL = '#';
    public static final char CHAR_TARGET = '.';
    public static final char CHAR_BOXONTARGET = '*';
    public static final char CHAR_SOKOBAN = '@';
    public static final char CHAR_SOKOBANONTARGET = '+';
    public static final char CHAR_GROUND = ' ';
    public static final char CHAR_BOX = '$';


    public static final String S_SOKOBANGROUND = "ground";
    public static final String S_SOKOBANEMPTY = "ground";
    public static final String S_SOKOBANWALL = "wall";
    public static final String S_SOKOBANTARGET = "target";
    public static final String S_SOKOBANBOX = "box";
    public static final String S_SOKOBANBOXONTARGET = "boxOnTarget";

    public static final String S_SLCDIRECTORY = "slc/";
    public static final String S_PLAYEDNAME = "slp/Played.slp";

    public static final int SPACE = 10;

    //
    public final static int MENU = 0;
    public final static int PLAY = 2;
    public final static int INFO = 3;

    public final static int MAXSTRLENGHT = 26;

    // SAMPLE APP CONSTANTS
    public static final String ACTIVITY_NUMBER = "activity_num";
    public static final String LOG_TAG = "iabv3";

    // PRODUCT & SUBSCRIPTION IDS
/*
    public static final String PRODUCT_ID = "com.pes.sokoban10.full";
    public static final String LICENSE_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAz3Ia+D38qPwvqTx0YR8eyVNoDsL1AD6cB5CXYaqPz0nkHo4PueUGPvnT05tQj5C7NjxHRIItGbB2idYf+/J/NQ5JxvEcAie08QTY8w5sEYwgVlcM6+rq6euruUOfzX8Qv+IkJ9Iqv+3SVISSRTF6q0PXpkViCxAVC+WqDMXCrwleYqJcU1W3qK9W0KJiBh4IjWtFuyFb2UTQ7yyAe58Q5qrclX7jkaLE9IyOnE1bvId6fpCb6g6tWpXqIlBJG+rZiqDLzMoj32gc7f2qzlTN65Jl1ngJHnRiuN/gO/iMJE/VG+oj+7Vwzv1nM8QF/TMTIh8etxHqilp2+k5UAk9FAQIDAQAB";
    public static final String MERCHANT_ID="08118349747632329075";
*/
}
