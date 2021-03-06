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
package com.turbogerm.helljump.game.items;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.turbogerm.germlibrary.util.GameUtils;
import com.turbogerm.helljump.HellJump;
import com.turbogerm.helljump.dataaccess.ItemData;
import com.turbogerm.helljump.game.GameAreaUtils;
import com.turbogerm.helljump.resources.ResourceNames;

public abstract class ItemBase {
    
    public static final int FART_EFFECT = 0;
    public static final int SHIELD_EFFECT = 1;
    public static final int HIGH_JUMP_EFFECT = 2;
    public static final int EXTRA_LIFE_EFFECT = 3;
    public static final int SCORE_EFFECT = 4;
    public static final int SIGNET_EFFECT = 5;
    
    private static final int EXISTING_STATE = 0;
    private static final int TEXT_STATE = 1;
    private static final int GONE_STATE = 2;
    
    private static final float TEXT_COUNTDOWN_DURATION = 3.0f;
    
    protected final Sprite mSprite;
    private final Vector2 mInitialPosition;
    private final Vector2 mOffsetFromPlatform;
    
    protected final Vector2 mPosition;
    protected final Vector2 mSize;
    protected final float mRadius;
    
    private int mItemState;
    
    private float mTextCountdown;
    
    private String mPickedUpText;
    private boolean mIsPickedUpTextBoundsDirty;
    private final Vector2 mPickedUpTextBounds;
    
    public ItemBase(ItemData itemData, String imageName, int startStep, AssetManager assetManager) {
        
        mInitialPosition = itemData.getPosition(startStep);
        mOffsetFromPlatform = new Vector2();
        
        mPosition = new Vector2(mInitialPosition);
        
        TextureAtlas atlas = assetManager.get(ResourceNames.ITEMS_ATLAS);
        mSprite = atlas.createSprite(imageName);
        GameUtils.multiplySpriteSize(mSprite, GameAreaUtils.PIXEL_TO_METER);
        GameUtils.setSpriteOriginCenter(mSprite);
        
        mSize = new Vector2(mSprite.getWidth(), mSprite.getHeight());
        mRadius = mSize.x / 2.0f;
        
        mItemState = EXISTING_STATE;
        
        mTextCountdown = TEXT_COUNTDOWN_DURATION;
        
        mPickedUpTextBounds = new Vector2();
    }
    
    public final void update(float delta) {
        if (mItemState == EXISTING_STATE) {
            updateImpl(delta);
        } else if (mItemState == TEXT_STATE) {
            mTextCountdown -= delta;
            if (mTextCountdown <= 0.0f) {
                mTextCountdown = 0.0f;
                mItemState = GONE_STATE;
            }
        }
    }
    
    protected void updateImpl(float delta) {
    }
    
    public final void render(SpriteBatch batch) {
        if (mItemState == EXISTING_STATE) {
            mSprite.draw(batch);
        }
    }
    
    public final void renderText(SpriteBatch batch, float visibleAreaPosition, BitmapFont itemFont) {
        if (mItemState == TEXT_STATE) {
            if (mIsPickedUpTextBoundsDirty) {
                TextBounds textBounds = itemFont.getBounds(mPickedUpText);
                mPickedUpTextBounds.set(textBounds.width, textBounds.height);
                mIsPickedUpTextBoundsDirty = false;
            }
            float alpha = mTextCountdown / TEXT_COUNTDOWN_DURATION;
            Color c = itemFont.getColor();
            itemFont.setColor(c.r, c.g, c.b, alpha);
            float textX = (mPosition.x + mSize.x / 2.0f) * GameAreaUtils.METER_TO_PIXEL -
                    mPickedUpTextBounds.x / 2.0f;
            textX = MathUtils.clamp(textX, 0.0f, HellJump.VIEWPORT_WIDTH - mPickedUpTextBounds.x);
            float textY = (mPosition.y + mSize.y / 2.0f - visibleAreaPosition) * GameAreaUtils.METER_TO_PIXEL +
                    mPickedUpTextBounds.y / 2.0f;
            itemFont.draw(batch, mPickedUpText, textX, textY);
        }
    }
    
    public void setOffsetFromPlatform(Vector2 platformInitialPosition) {
        mOffsetFromPlatform.set(
                mInitialPosition.x - platformInitialPosition.x,
                mInitialPosition.y - platformInitialPosition.y);
    }
    
    public final void updatePosition(Vector2 platformPosition) {
        mPosition.set(
                platformPosition.x + mOffsetFromPlatform.x,
                platformPosition.y + mOffsetFromPlatform.y);
        
        updatePositionImpl();
    }
    
    protected abstract void updatePositionImpl();
    
    public void pickUp() {
        mItemState = TEXT_STATE;
    }
    
    public boolean isCollision(Rectangle rect) {
        return false;
    }
    
    public boolean isExisting() {
        return mItemState == EXISTING_STATE;
    }
    
    public abstract int getEffect();
    
    public abstract Object getValue();
    
    public void setPickedUpText(String pickedUpText) {
        mPickedUpText = pickedUpText;
        mIsPickedUpTextBoundsDirty = true;
    }
}
