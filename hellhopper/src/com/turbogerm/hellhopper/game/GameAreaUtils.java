/*
 * Copyright (c) 2013 Goran Mrzljak
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package com.turbogerm.hellhopper.game;

import com.badlogic.gdx.math.Vector2;

public final class GameAreaUtils {
    
    public static final float METER_TO_PIXEL = 40.0f;
    public static final float PIXEL_TO_METER = 1.0f / METER_TO_PIXEL;
    
    // 'step' is vertical offset, 'offset' is horizontal offset in position grid
    public static final float STEP_HEIGHT = 1.0f;
    public static final float OFFSET_WIDTH = 0.25f;
    
    public static Vector2 getPosition(int startStep, float step, float offset) {
        float x = offset *  OFFSET_WIDTH;
        float y = (step + startStep) * STEP_HEIGHT;
        return new Vector2(x, y);
    }
}
