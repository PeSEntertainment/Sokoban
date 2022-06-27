package com.pes.sokoban.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SplashScreen implements Screen{
    private SpriteBatch batch;
    private Texture ttrSplash;

public SplashScreen(){
        batch = new SpriteBatch();
        ttrSplash = new Texture("splash_screen.png");
    }

    @Override public void show() {
    }


    @Override public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        int x,y,w,h;

        if (Gdx.graphics.getWidth()<Gdx.graphics.getHeight()) { // PORTRAIT
            w = Gdx.graphics.getWidth()-80; h = ttrSplash.getHeight()*w/ttrSplash.getWidth();
            x = 40; y = (Gdx.graphics.getHeight()-h)/2;
        }
        else { // LANDSCAPE
            h = Gdx.graphics.getHeight()-100; w = ttrSplash.getWidth()*h/ttrSplash.getHeight();
            x = (Gdx.graphics.getWidth()-w)/2; y = 50;
        }

        batch.draw(ttrSplash,  x, y, w, h);
        batch.end();
    }

    @Override public void resize(int width, int height) {
    }

    @Override public void pause() { }

    @Override public void resume() { }

    @Override public void hide() {
    }

    @Override public void dispose() {
        ttrSplash.dispose();
        batch.dispose();
    }
}
