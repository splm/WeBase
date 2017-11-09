package com.jc.android.baselib.ui.vision.animation.flip;

import android.view.View;

import com.jc.android.baselib.ui.vision.AbsBaseAnimation;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;


public class FlipOutYAnimation extends AbsBaseAnimation {

	@Override
	protected void animate(AnimatorSet set, View target, int index) {
		set.playSequentially(
				ObjectAnimator.ofFloat(target, "rotationY", 0, -15,90),
                ObjectAnimator.ofFloat(target, "alpha", 1, 0)
        );
	}

}
