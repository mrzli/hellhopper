/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2013 Goran Mrzljak
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.turbogerm.germlibrary.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;

// based on Game class from libgdx
public abstract class GameBase implements ApplicationListener {
    
    private static final float MAX_DELTA = 0.1f;
    private static final float UPDATE_RATE = 60.0f;
    private static final float UPDATE_STEP = 1.0f / UPDATE_RATE;
    
    private static final float UPDATE_STEP_MAX = UPDATE_STEP * 1.1f;
    
    private Screen mScreen;
    
    private float mDeltaAccumulator;
    
    public GameBase() {
        mDeltaAccumulator = 0.0f;
    }
    
    @Override
    public void dispose() {
        if (mScreen != null) {
            mScreen.hide();
        }
    }
    
    @Override
    public void pause() {
        if (mScreen != null) {
            mScreen.pause();
        }
    }
    
    @Override
    public void resume() {
        if (mScreen != null) {
            mScreen.resume();
        }
    }
    
    @Override
    public void render() {
        if (mScreen != null) {
            
            // long javaHeap = Gdx.app.getJavaHeap();
            // long nativeHeap = Gdx.app.getNativeHeap();
            // Logger.debug("Java Heap: %d; Native Heap: %d", javaHeap, nativeHeap);
            
            Screen screen = mScreen;
            
            float delta = Math.min(Gdx.graphics.getDeltaTime(), MAX_DELTA);
            
            mDeltaAccumulator += delta;
            while (mDeltaAccumulator > 0.0f) {
                float deltaStep = mDeltaAccumulator <= UPDATE_STEP_MAX ? mDeltaAccumulator : UPDATE_STEP;
                screen.update(deltaStep);
                mDeltaAccumulator -= deltaStep;
            }
            
            screen.render();
        }
    }
    
    @Override
    public void resize(int width, int height) {
        if (mScreen != null) {
            mScreen.resize(width, height);
        }
    }
    
    public void setScreen(Screen screen) {
        if (this.mScreen != null)
            this.mScreen.hide();
        this.mScreen = screen;
        if (this.mScreen != null) {
            this.mScreen.show();
            this.mScreen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
    }
    
    /** @return the currently active {@link Screen}. */
    public Screen getScreen() {
        return mScreen;
    }
}