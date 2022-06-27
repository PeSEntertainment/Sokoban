package com.pes.sokoban.Interfaces;

import com.pes.sokoban.Global.Result;

public interface Platform {
    // MIDI PLAY
    void play();
    void pause();
    void stop();
    void setVolume(float volume);
    boolean isPlaying();
    void playNext();
    void change();
    void release();
    // SCREEN ORIENTATION
    void setOrientation(String string);
    // BILLING
//    boolean connect();
    void purchase();
    void getPurchased();
//    String getSku(String SKU);
}
