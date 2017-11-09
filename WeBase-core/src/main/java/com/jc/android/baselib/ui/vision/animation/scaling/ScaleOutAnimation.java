package com.jc.android.baselib.ui.vision.animation.scaling;

import android.view.View;

import com.jc.android.baselib.ui.vision.AbsBaseAnimation;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;


public class ScaleOutAnimation extends AbsBaseAnimation {

	@Override
	protected void animate(AnimatorSet set, View view, int index) {
		set.playTogether(
				ObjectAnimator.ofFloat(view,"scaleX",1f,1.05f,0.9f,0.3f,0),
				ObjectAnimator.ofFloat(view,"scaleY",1f,1.05f,0.9f,0.3f,0),
				ObjectAnimator.ofFloat(view,"alpha",1,0)
		);
	}
}
