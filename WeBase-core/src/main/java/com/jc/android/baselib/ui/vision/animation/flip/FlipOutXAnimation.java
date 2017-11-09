package com.jc.android.baselib.ui.vision.animation.flip;

import android.view.View;

import com.daimajia.easing.Glider;
import com.daimajia.easing.Skill;
import com.jc.android.baselib.ui.vision.AbsBaseAnimation;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;


public class FlipOutXAnimation extends AbsBaseAnimation {

	@Override
	protected void animate(AnimatorSet set, View target, int index) {
		set.playSequentially(
				Glider.glide(Skill.BackEaseIn,getDuration(),ObjectAnimator.ofFloat(target, "rotationX", 0, 90)),
                ObjectAnimator.ofFloat(target, "alpha", 1, 0)
        );
	}

}
