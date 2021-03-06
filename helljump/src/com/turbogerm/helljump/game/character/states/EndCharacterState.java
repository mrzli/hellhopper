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
package com.turbogerm.helljump.game.character.states;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.turbogerm.germlibrary.util.GameUtils;
import com.turbogerm.helljump.CameraData;
import com.turbogerm.helljump.game.character.GameCharacter;
import com.turbogerm.helljump.game.character.graphics.CharacterBodyGraphics;
import com.turbogerm.helljump.game.character.graphics.CharacterEyesGraphicsNormal;
import com.turbogerm.helljump.game.character.graphics.CharacterHeadGraphics;
import com.turbogerm.helljump.game.character.graphics.CharacterMouthGraphicsSmile;
import com.turbogerm.helljump.resources.ResourceNames;

final class EndCharacterState extends CharacterStateBase {
    
    private static final float FIRST_END_JUMP_POWER_MULTIPLIER = 1.0f;
    private static final float FIRST_END_JUMP_SPEED = GameCharacter.JUMP_SPEED * FIRST_END_JUMP_POWER_MULTIPLIER;
    
    private static final float END_RESTITUTION_MULTIPLIER = 1.0f / 1.5f;
    private static final float END_RESTITUTION_SPEED_DECREASE = 0.75f;
    
    private static final float CHARACTER_STOPPED_DURATION = 3.0f;
    
    private static final float MIN_SHEEP_SOUND_INTERVAL = 0.5f;
    private static final float MAX_SHEEP_SOUND_INTERVAL = 3.0f;
    
    private final Rectangle mCameraRect;
    
    private final CharacterBodyGraphics mCharacterBodyGraphics;
    private final CharacterHeadGraphics mCharacterHeadGraphics;
    private final CharacterEyesGraphicsNormal mCharacterEyesGraphics;
    private final CharacterMouthGraphicsSmile mCharacterMouthGraphics;
    
    private boolean mIsFirstEndJump;
    private boolean mIsCharacterStopped;
    private float mCharacterStoppedCountdown;
    
    private final Sound[] mSheepSounds;
    
    private float mNextSheepSoundInteval;
    private float mElapsedSinceLastSheepSound;
    
    public EndCharacterState(CharacterStateManager characterStateManager,
            CameraData cameraData, AssetManager assetManager) {
        
        super(characterStateManager);
        
        mCameraRect = cameraData.getNonOffsetedGameCameraRect();
        
        mCharacterBodyGraphics = new CharacterBodyGraphics(assetManager);
        mCharacterHeadGraphics = new CharacterHeadGraphics(assetManager);
        mCharacterEyesGraphics = new CharacterEyesGraphicsNormal(assetManager);
        mCharacterMouthGraphics = new CharacterMouthGraphicsSmile(assetManager);
        
        mSheepSounds = new Sound[ResourceNames.SOUND_SHEEP_COUNT];
        for (int i = 0; i < mSheepSounds.length; i++) {
            mSheepSounds[i] = assetManager.get(ResourceNames.getSoundSheep(i));
        }
    }
    
    @Override
    public void reset() {
        mCharacterBodyGraphics.reset();
        mCharacterHeadGraphics.reset();
        mCharacterEyesGraphics.reset();
        mCharacterMouthGraphics.reset();
        
        mIsFirstEndJump = true;
        mIsCharacterStopped = false;
        mCharacterStoppedCountdown = CHARACTER_STOPPED_DURATION;
        mNextSheepSoundInteval = MathUtils.random(MIN_SHEEP_SOUND_INTERVAL, MAX_SHEEP_SOUND_INTERVAL);
        mElapsedSinceLastSheepSound = 0.0f;
    }
    
    @Override
    public void update(CharacterStateUpdateData updateData) {
        
        if (mCharacterStoppedCountdown <= 0.0f) {
            changeState(CharacterStateManager.FINISHED_CHARACTER_STATE);
            return;
        }
        
        float delta = updateData.delta;
        
        if (!mIsCharacterStopped) {
            Vector2 position = updateData.characterPosition;
            Vector2 speed = updateData.characterSpeed;
            float riseHeight = updateData.riseHeight;
            
            if (position.y <= riseHeight) {
                position.y = riseHeight + GameUtils.EPSILON;
                if (mIsFirstEndJump) {
                    speed.y = FIRST_END_JUMP_SPEED;
                    mIsFirstEndJump = false;
                } else {
                    speed.y = Math.abs(speed.y * END_RESTITUTION_MULTIPLIER);
                    speed.y -= END_RESTITUTION_SPEED_DECREASE;
                    if (speed.y <= 0.0f) {
                        mIsCharacterStopped = true;
                    }
                }
            }
            
            if (!mIsCharacterStopped) {
                updatePositionAndSpeed(position, speed, updateData.horizontalSpeed,
                        mCameraRect.x, mCameraRect.width, delta);
            }
        } else {
            mCharacterStoppedCountdown -= delta;
        }
        
        mCharacterEyesGraphics.update(delta);
        
        mElapsedSinceLastSheepSound += delta;
        if (mElapsedSinceLastSheepSound >= mNextSheepSoundInteval) {
            mSheepSounds[MathUtils.random(ResourceNames.SOUND_SHEEP_COUNT - 1)].play();
            mNextSheepSoundInteval = MathUtils.random(MIN_SHEEP_SOUND_INTERVAL, MAX_SHEEP_SOUND_INTERVAL);
            mElapsedSinceLastSheepSound = 0.0f;
        }
    }
    
    @Override
    public void render(CharacterStateRenderData renderData) {
        SpriteBatch batch = renderData.batch;
        Vector2 position = renderData.characterPosition;
        
        mCharacterBodyGraphics.render(batch, position);
        mCharacterHeadGraphics.render(batch, position);
        mCharacterEyesGraphics.render(batch, position);
        mCharacterMouthGraphics.render(batch, position);
    }
}
