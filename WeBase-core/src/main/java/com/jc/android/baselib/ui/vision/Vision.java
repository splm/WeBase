package com.jc.android.baselib.ui.vision;


import com.jc.android.baselib.ui.vision.animation.attention.DropOutAnimation;
import com.jc.android.baselib.ui.vision.animation.attention.FlashAnimation;
import com.jc.android.baselib.ui.vision.animation.attention.ShakeAnimation;
import com.jc.android.baselib.ui.vision.animation.attention.ShakeHarderAnimation;
import com.jc.android.baselib.ui.vision.animation.attention.TakeOffAnimation;
import com.jc.android.baselib.ui.vision.animation.bounce.BounceInAnimation;
import com.jc.android.baselib.ui.vision.animation.bounce.BounceInDownAnimation;
import com.jc.android.baselib.ui.vision.animation.bounce.BounceInLeftAnimation;
import com.jc.android.baselib.ui.vision.animation.bounce.BounceInRightAnimation;
import com.jc.android.baselib.ui.vision.animation.bounce.BounceInUpAnimation;
import com.jc.android.baselib.ui.vision.animation.fadeIn.FadeInDownAnimation;
import com.jc.android.baselib.ui.vision.animation.fadeIn.FadeInLeftAnimation;
import com.jc.android.baselib.ui.vision.animation.fadeIn.FadeInRightAnimation;
import com.jc.android.baselib.ui.vision.animation.fadeIn.FadeInUpAnimation;
import com.jc.android.baselib.ui.vision.animation.flip.FlipInXAnimation;
import com.jc.android.baselib.ui.vision.animation.flip.FlipInYAnimation;
import com.jc.android.baselib.ui.vision.animation.flip.FlipOutXAnimation;
import com.jc.android.baselib.ui.vision.animation.flip.FlipOutYAnimation;
import com.jc.android.baselib.ui.vision.animation.scaling.ScaleInAnimation;
import com.jc.android.baselib.ui.vision.animation.scaling.ScaleOutAnimation;

public enum Vision {
	
	FadeInLeft(FadeInLeftAnimation.class),
	FadeInRight(FadeInRightAnimation.class),
	FadeInUp(FadeInUpAnimation.class),
	FadeInDown(FadeInDownAnimation.class),
	
	Flash(FlashAnimation.class),
	TakeOff(TakeOffAnimation.class),
	
	FlipInX(FlipInXAnimation.class),
	FlipOutX(FlipOutXAnimation.class),
	FlipInY(FlipInYAnimation.class),
	FlipOutY(FlipOutYAnimation.class),
	
	DropOut(DropOutAnimation.class),
	Shake(ShakeAnimation.class),
	ShakeHarder(ShakeHarderAnimation.class),
	
	BouncingIn(BounceInAnimation.class),
	BouncingInDown(BounceInDownAnimation.class),
	BouncingInLeft(BounceInLeftAnimation.class),
	BouncingInRight(BounceInRightAnimation.class),
	BouncingInUp(BounceInUpAnimation.class),
	
	ScaleIn(ScaleInAnimation.class),
	ScaleOut(ScaleOutAnimation.class);
	
	private Class<? extends AbsBaseAnimation> animatorClazz;

    private Vision(Class<? extends AbsBaseAnimation> clazz) {
        animatorClazz = clazz;
    }
    
    public AbsBaseAnimation getAnimator() {
        try {
            return (AbsBaseAnimation) animatorClazz.newInstance();
        } catch (Exception e) {
            throw new Error("Could not init WeVision instance!");
        }
    }
}
