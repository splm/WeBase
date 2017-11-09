package com.jc.android.baselib.ui.vision.animation.flip;

import android.view.View;

import com.jc.android.baselib.ui.vision.AbsBaseAnimation;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;


public class FlipInXAnimation extends AbsBaseAnimation {

	@Override
	protected void animate(AnimatorSet set, View target, int index) {
		set.playTogether(
				ObjectAnimator.ofFloat(target, "rotationX", 90, -15, 15, 0),
                ObjectAnimator.ofFloat(target, "alpha", 0.25f, 0.5f, 0.75f, 1)
        );
	}

}
