package com.pes.sokoban.Global;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.pes.sokoban.Constants.Constants;
import com.pes.sokoban.Constants.Languages;

public class Game {
// GLOBAL VARIABLES
    public static Array<Package> levelPackages;
    public static Array<com.pes.sokoban.Global.LevelPlayed> levelsPlayed;
    public static Package activePackage;
    public static Level activeLevel;
    public static Level lastLevel;
    public static Level infoLevel;
    public static int returnScreen, returnMode;

    public static float screenAspectRatio;
    public static Integer virtualWidth, virtualHeight;
    public static boolean portrait;
    public static int infoMode;

    public static Skin skin;
    public static Music music;
    public static Sound soundTap;
    public static AssetManager assetManager;
    public static TextureAtlas textureAtlasCommon;
    public static TextureAtlas textureAtlasSkin;

    public static Boolean FULLVERSION = false;

    public static Integer actualMIDI = 2;
    public static Float musicVolume = 0.2f;
    public static Boolean musicEnabled = false;
    public static Float soundVolume = 0.2f;
    public static Boolean soundEnabled = true;
    public static Float replayStepIn = 0.2f; // 1sec
    public static String screenOrientation = "sensor";
    public static String atlas = Constants.S_ATLASROBI;
    public static String dir = Constants.S_DIRROBI;
    public static Integer language = Languages.LANG_ENGLISH;
    public static Integer versionCode = 254827;


    public static Texture texThWall;
    public static Texture texThBox;
    public static Texture texThBoxOnTarget;
    public static Texture texThGround;
    public static Texture texThTarget;
    public static Texture texThEmpty;
    public static Texture texThSokoban;
}
