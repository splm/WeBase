package com.jc.android.baselib.ui.vision;

import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.view.ViewHelper;

import java.util.ArrayList;
import java.util.List;

public abstract class AbsBaseAnimation {
	
	public static final long DURATION=1000;
	public static final long DELAY=100;
	/**State of running**/
	private static final int RUNNING=1;
	/**state of stopped**/
	private static final int STOPPED=0;

	/**current animation plays state.**/
	private int mPlayStatus;//0 stop,1 running,
	/**current animation wheather is running.**/
	private boolean isRunning;
	private WeVisionAnimatorListener mListener;

	private long mDuration;
	private List<AnimatorSet> mAnimatorSetQueue=new ArrayList<>();

	public AbsBaseAnimation duration(long timemills){
		this.mDuration=timemills;
		return this;
	}
	protected abstract void animate(AnimatorSet set,View target,int index);

	/** Measure the target view. */
	private void measureTarget(View target){
		int height=MeasureSpec.makeMeasureSpec(0,MeasureSpec.UNSPECIFIED);
		int width=MeasureSpec.makeMeasureSpec(0,MeasureSpec.UNSPECIFIED);
		target.measure(width,height);
	}
	
	public void setView(View target){//1
		resetEffection(target);
		prepare(target);
	}

	protected void prepare(View target){//2
		treeViews(target);
	}

	private void treeViews(View target){//3
		if(target instanceof ViewGroup){
			ViewGroup group=(ViewGroup)target;
			int count=group.getChildCount();
			if(count>0){
				for (int i = 0; i < count; i++) {
					View child = group.getChildAt(i);
					confirmAnimation(child,i);
				}
			}
			return;
		}else{
			confirmAnimation(target,0);
		}
	}

	/** Make sure to set animations on the target view which has measured.*/
	private void confirmAnimation(View target,int index){//4
		measureTarget(target);
		AnimatorSet set=new AnimatorSet();
		mAnimatorSetQueue.add(set);
		composeAnimator(set,target,index);
		//animate(target, index);//5
	}

	/** Start the animation of current collection. */
	public void start(){
		int i=0;
		for(AnimatorSet set:mAnimatorSetQueue){
			set.setDuration(mDuration);
			set.setStartDelay(delays(i));
			set.start();//6
			i++;
		}
		AnimatorSet firstSet=mAnimatorSetQueue.get(0);
		if(firstSet.isStarted()){
			//When use start() method,it means current animation has already run,so field isRunning is True,although delay is a nonzero,but animation is not running.
			isRunning=true;
		}
		if(firstSet.isRunning()){//Current animation is running,and delay time has passed.
			mPlayStatus=RUNNING;
		}
	}

	/**
	 * stop all animations
	 */
	public void stop(){
		List<AnimatorSet> list=getAnimatorQueue();
		for(AnimatorSet set:list){
			if(set.isRunning()){
				set.end();
			}
		}
		stopStatus();
	}

	private void stopStatus(){
		isRunning=false;
		mPlayStatus=STOPPED;
	}

	private void composeAnimator(AnimatorSet set,View view, int index){
		animate(set,view,index);
	}

	/** reset all animations.
	 * @param  target view
	 * */
	protected void resetEffection(View target){
		ViewHelper.setAlpha(target, 1);
        ViewHelper.setScaleX(target, 1);
        ViewHelper.setScaleY(target, 1);
        ViewHelper.setTranslationX(target, 0);
        ViewHelper.setTranslationY(target, 0);
        ViewHelper.setRotation(target, 0);
        ViewHelper.setRotationY(target, 0);
        ViewHelper.setRotationX(target, 0);
        ViewHelper.setPivotX(target, target.getMeasuredWidth() / 2.0f);
        ViewHelper.setPivotY(target, target.getMeasuredHeight() / 2.0f);
	}
	
	protected long delays(int index){
		return index * DELAY;
	}
	
	protected long getDuration(){
		return this.mDuration;
	}

	public void addListener(final WeVisionAnimatorListener listener){
		this.mListener=listener;
		List<AnimatorSet> queue=getAnimatorQueue();
		int size=queue.size();
		if(size==0){
			return;
		}
		AnimatorSet firstSet=queue.get(0);
		AnimatorSet lastSet=queue.get(size-1);
		if(listener!=null){
			firstSet.addListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationStart(Animator animation) {
					super.onAnimationStart(animation);
					listener.onStart(animation);
				}
			});
			lastSet.addListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(Animator animation) {
					super.onAnimationEnd(animation);
					listener.onEnd();
					stopStatus();
				}
			});
		}
	}
	public WeVisionAnimatorListener getListener(){
		return this.mListener;
	}
	public boolean isRunning(){
		return (mPlayStatus==RUNNING||isRunning);
	}
	public boolean isEnd(){
		return mPlayStatus==STOPPED;
	}

	protected  List<AnimatorSet> getAnimatorQueue(){
		return this.mAnimatorSetQueue;
	}
	public interface WeVisionAnimatorListener{
		void onStart(Animator animator);
		void onEnd();
	}
}
