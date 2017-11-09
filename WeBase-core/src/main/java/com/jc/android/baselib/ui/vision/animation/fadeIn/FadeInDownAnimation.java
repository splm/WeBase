package com.jc.android.baselib.ui.vision.animation.fadeIn;

import android.view.View;

import com.jc.android.baselib.ui.vision.AbsBaseAnimation;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;


public class FadeInDownAnimation extends AbsBaseAnimation {

	@Override
	protected void animate(AnimatorSet set, View target, int index) {
		set.playTogether(
				ObjectAnimator.ofFloat(target, "alpha", 0, 1),
				ObjectAnimator.ofFloat(target, "translationY", target.getMeasuredHeight(), 0));
	}

}
