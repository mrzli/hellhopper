package com.turbogerm.helljump.game.platforms.features;

import com.badlogic.gdx.assets.AssetManager;
import com.turbogerm.germlibrary.util.ExceptionThrower;
import com.turbogerm.helljump.dataaccess.PlatformFeatureData;

public final class PlatformFeatureFactory {
    
public static PlatformFeatureBase create(PlatformFeatureData featureData, AssetManager assetManager) {
        
        String featureType = featureData.getFeatureType();
        if (PlatformFeatureData.JUMP_BOOST_FEATURE.equals(featureType)) {
            return new JumpBoostPlatformFeature(featureData, assetManager);
        } else if (PlatformFeatureData.FLAME_FEATURE.equals(featureType)) {
            return new FlamePlatformFeature(featureData, assetManager);
        } else if (PlatformFeatureData.VISIBLE_ON_JUMP_FEATURE.equals(featureType)) {
            return new VisibleOnJumpFeature(featureData, assetManager);
        } else {
            ExceptionThrower.throwException("Invalid platform feature type: %s", featureType);
            return null;
        }
    }
}