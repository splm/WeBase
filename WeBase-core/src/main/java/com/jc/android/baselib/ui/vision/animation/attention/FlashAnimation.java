package com.jc.android.baselib.ui.vision.animation.attention;

import android.view.View;

import com.jc.android.baselib.ui.vision.AbsBaseAnimation;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;


public class FlashAnimation extends AbsBaseAnimation {

	@Override
	protected void animate(AnimatorSet set,View target, int index) {
		set.playTogether(
				ObjectAnimator.ofFloat(target,"alpha",1,0,1,0,1)
        );
	}

}
