package com.jc.android.baselib.ui.vision;


import android.view.animation.Interpolator;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;

import java.util.List;

public class CustomAnimatorset extends Animator {

    private List<AnimatorSet> mAnimatorSetList;

    public CustomAnimatorset(List<AnimatorSet> list){
        this.mAnimatorSetList=list;
    }

    @Override
    public long getStartDelay() {
        return 0;
    }

    @Override
    public void setStartDelay(long startDelay) {

    }

    @Override
    public Animator setDuration(long duration) {
        return null;
    }

    @Override
    public long getDuration() {
        return 0;
    }

    @Override
    public void setInterpolator(Interpolator value) {

    }

    @Override
    public boolean isRunning() {
        return false;
    }

    public class CustomAnimatorsetListener implements  AnimatorListener{

        private boolean isAllDone;

        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {

        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    }

}
