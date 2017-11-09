package com.jc.android.baselib.ui.vision.animation.attention;

import android.view.View;

import com.jc.android.baselib.ui.vision.AbsBaseAnimation;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;


public class ShakeHarderAnimation extends AbsBaseAnimation {

	@Override
	protected void animate(AnimatorSet set, View target, int index) {
		float width = target.getMeasuredWidth();
        float one = (float)(width/25.0);
		set.playTogether(
				ObjectAnimator.ofFloat(target, "translationX", 0 * one, -25 * one, 20 * one, -15 * one, 10 * one, -5 * one, 0 * one,0),
                ObjectAnimator.ofFloat(target, "rotation", 0, -5, 3, -3, 2, -1,0)
        );
	}

}
