package com.turbogerm.hellhopper.game;

import com.badlogic.gdx.math.Vector2;
import com.turbogerm.hellhopper.game.platforms.PlatformBase;

public final class PlatformToCharCollisionData {
    
    public boolean isCollision;
    public final Vector2 collisionPoint;
    public PlatformBase collisionPlatform;
    public boolean isEnabled;
    
    public PlatformToCharCollisionData() {
        collisionPoint = new Vector2();
    }
    
    public void reset() {
        isCollision = false;
    }
}
