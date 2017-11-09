package com.jc.android.baselib.ui.vision.animation.attention;

import android.view.View;

import com.daimajia.easing.Glider;
import com.daimajia.easing.Skill;
import com.jc.android.baselib.ui.vision.AbsBaseAnimation;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;


public class DropOutAnimation extends AbsBaseAnimation {

	@Override
	protected void animate(AnimatorSet set,View target, int index) {
		int distance = target.getMeasuredWidth() + target.getMeasuredHeight();
		set.playTogether(
				ObjectAnimator.ofFloat(target, "alpha", 0, 1),
                Glider.glide(Skill.ElasticEaseOut, getDuration(), ObjectAnimator.ofFloat(target, "translationY", -distance, 0))
        );
	}
}
