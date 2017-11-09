package com.jc.android.baselib.ui.vision;

import android.view.View;


public class WeVisionEngine {
	
	public static WeAnimationComposer use(Vision v){
		return new WeAnimationComposer(v);
	}
	
	public static final class WeVisionStatus{
		
		private AbsBaseAnimation mAnimation;
		
		public WeVisionStatus(AbsBaseAnimation animation){
			this.mAnimation=animation;
		}
		
		public boolean isRunning(){
			return mAnimation.isRunning();
		}
		public boolean isEnd(){
			return mAnimation.isEnd();
		}
		public void stop(){
			mAnimation.stop();
		}
		public AbsBaseAnimation.WeVisionAnimatorListener getListener(){
			return mAnimation.getListener();
		}
	}
	
	public static final class WeAnimationComposer {

		private AbsBaseAnimation mAnimation;
		private long mDuration = AbsBaseAnimation.DURATION;
		private AbsBaseAnimation.WeVisionAnimatorListener mListener;

		public WeAnimationComposer(Vision v) {
			this.mAnimation = v.getAnimator();
		}

		public WeAnimationComposer duration(long timemills) {
			this.mDuration = timemills;
			return this;
		}

		public WeAnimationComposer addListener(AbsBaseAnimation.WeVisionAnimatorListener listener) {
			this.mListener = listener;
			return this;
		}

		public WeVisionStatus playOn(View target) {
			mAnimation.duration(mDuration);
			mAnimation.setView(target);
			mAnimation.addListener(mListener);
			mAnimation.start();
			return new WeVisionStatus(mAnimation);
		}
	}
}
