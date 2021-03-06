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
package com.turbogerm.helljump.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.turbogerm.germlibrary.controls.CustomButtonAction;
import com.turbogerm.germlibrary.controls.CustomImageButton;
import com.turbogerm.helljump.HellJump;
import com.turbogerm.helljump.resources.ResourceNames;
import com.turbogerm.helljump.screens.general.ScreenBackground;

public final class CreditsScreen extends ScreenBase {
    
    private static final float BUTTON_X = 105.0f;
    private static final float BUTTON_Y = 20.0f;
    
    private final ScreenBackground mScreenBackground;
    
    public CreditsScreen(HellJump game) {
        super(game);
        
        mGuiStage.addListener(getStageInputListener(this));
        
        mScreenBackground = new ScreenBackground(mCameraData, mAssetManager);
        
        float offsetFromTop = addTitleLabel();
        addTextLabel(offsetFromTop);
        
        TextureAtlas atlas = mAssetManager.get(ResourceNames.GRAPHICS_GUI_ATLAS);
        
        CustomImageButton button = new CustomImageButton(
                BUTTON_X, BUTTON_Y,
                atlas,
                ResourceNames.GUI_BUTTON_BACK_UP_IMAGE_NAME,
                ResourceNames.GUI_BUTTON_BACK_DOWN_IMAGE_NAME,
                getBackAction(),
                mAssetManager);
        button.addToStage(mGuiStage);
    }
    
    @Override
    public void show() {
        super.show();
        
        mScreenBackground.reset();
    }
    
    @Override
    protected void updateImpl(float delta) {
        mScreenBackground.update(delta);
    }
    
    @Override
    public void renderImpl() {
        mBatch.begin();
        mScreenBackground.render(mBatch);
        mBatch.end();
    }
    
    private float addTitleLabel() {
        LabelStyle labelStyle = new LabelStyle(mGuiSkin.get(LabelStyle.class));
        labelStyle.font = mGuiSkin.getFont("xxxl-font");
        
        String text = "Credits";
        TextBounds textBounds = labelStyle.font.getBounds(text);
        
        final float padding = 10.0f;
        final float labelHeight = textBounds.height + 2.0f * padding;
        
        Label label = new Label(text, mGuiSkin);
        label.setBounds(0.0f, HellJump.VIEWPORT_HEIGHT - labelHeight,
                HellJump.VIEWPORT_WIDTH, labelHeight);
        label.setStyle(labelStyle);
        label.setAlignment(Align.center);
        mGuiStage.addActor(label);
        
        return labelHeight;
    }
    
    private void addTextLabel(float offsetFromTop) {
        LabelStyle labelStyle = new LabelStyle(mGuiSkin.get(LabelStyle.class));
        labelStyle.font = mGuiSkin.getFont("medium-font");
        
        FileHandle creditsFileHandle = Gdx.files.internal(ResourceNames.CREDITS);
        String creditsText = creditsFileHandle.readString();
        
        TextBounds textBounds = labelStyle.font.getMultiLineBounds(creditsText);
        final float padding = 5.0f;
        final float labelHeight = textBounds.height + padding;
        
        Label textLabel = new Label(creditsText, mGuiSkin);
        textLabel.setBounds(padding, HellJump.VIEWPORT_HEIGHT - offsetFromTop - labelHeight,
                HellJump.VIEWPORT_WIDTH, labelHeight);
        textLabel.setStyle(labelStyle);
        textLabel.setAlignment(Align.left);
        mGuiStage.addActor(textLabel);
    }
    
    private static InputListener getStageInputListener(final CreditsScreen screen) {
        
        return new InputListener() {
            
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Keys.ESCAPE || keycode == Keys.BACK) {
                    screen.mGame.setScreen(HellJump.MAIN_MENU_SCREEN_NAME);
                    return true;
                }
                
                return false;
            }
        };
    }
    
    private CustomButtonAction getBackAction() {
        return new CustomButtonAction() {
            
            @Override
            public void invoke() {
                mGame.setScreen(HellJump.MAIN_MENU_SCREEN_NAME);
            }
        };
    }
}
