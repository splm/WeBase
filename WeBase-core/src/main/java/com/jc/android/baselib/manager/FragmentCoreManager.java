package com.jc.android.baselib.manager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class FragmentCoreManager implements IManagerMarker {
    private FragmentManager mManager;
    private int mContainerId = -1;

    public FragmentCoreManager(){}

    public FragmentCoreManager(int containerId, FragmentManager manager) {
        init(containerId,manager);
    }

    public void init(int containerId, FragmentManager manager) {
        this.mManager = manager;
        this.mContainerId = containerId;
        checkInitParamsStatus();
    }

    /**
     * check init params's status
     */
    private void checkInitParamsStatus(){
        if(mContainerId<0){
            throw new IllegalArgumentException("The arguments of the container's resource id is not right!");
        }
        if(mManager==null){
            throw new NullPointerException("You didn't define FragmentManager Object!");
        }
    }

    /**
     * 得到当前POP栈中所有的碎片的名字
     *
     * @return List<String>
     */
    public List<String> getAllPopStackFragments() {
        checkInitParamsStatus();
        int count = mManager.getBackStackEntryCount();
        List<String> list = new ArrayList<>(count);
        Log.e("**********", count + "-------------");
        for (int i = 0; i < count; i++) {
            FragmentManager.BackStackEntry backStackEntry = mManager.getBackStackEntryAt(i);
            String name = backStackEntry.getName();
            Log.e("************", name);
            list.add(name);
        }
        return list;
    }

    public int getFragmentCounts() {
        checkInitParamsStatus();
        return mManager.getBackStackEntryCount();
    }

    private Fragment openNewFragment(String partName, String aliasName, Class<? extends Fragment> clazz) {
        checkInitParamsStatus();
        try {
            Fragment fragment = (Fragment) clazz.newInstance();
            FragmentManager fragmentManager = mManager;
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            Fragment fragmentContainer = fragmentManager.findFragmentById(mContainerId);
            if (fragmentContainer != null) {
                transaction.hide(fragmentContainer);
            }
            transaction.add(mContainerId, fragment, aliasName);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);//为启动的Fragment加入动画
            transaction.addToBackStack(aliasName);
            transaction.commitAllowingStateLoss();
            return fragment;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void back() {
        mManager.popBackStack();
    }

    /**
     * 获取当前POP栈中栈顶元素的名字
     *
     * @return
     */
    public String getNameOfFirstInStack() {
        return getAllPopStackFragments().get(0);
    }

    /**
     * 获取当前POP栈中栈底元素的名字
     *
     * @return
     */
    public String getNameOfLastInStack() {
        int size = getAllPopStackFragments().size();
        return getAllPopStackFragments().get(size - 1);
    }

    /**
     * 清空pop栈中除栈底以外的所有fragment
     */
    public void clearWithoutTop() {
        checkInitParamsStatus();
        mManager.popBackStackImmediate(getNameOfFirstInStack(), 0);
    }

    /**
     * 清空pop栈中所有的fragment
     */
    public void clear() {
        checkInitParamsStatus();
        mManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public Fragment showPages(String partName, String aliasName, Class<? extends Fragment> clazz, boolean isPop) {
        Fragment fragment = null;
        if (mManager != null) {
            fragment = mManager.findFragmentByTag(aliasName);
        }
        if (fragment == null) {
            fragment = openNewFragment(partName, aliasName, clazz);
        } else {
            if (isPop) {
                mManager.popBackStackImmediate(aliasName, 0);
                return fragment;
            }
            List<Fragment> list = mManager.getFragments();
            FragmentTransaction transaction = mManager.beginTransaction();
            for (Fragment f : list) {
                transaction.hide(f);
            }
            if (fragment.isAdded()) {
                transaction.show(fragment).commitAllowingStateLoss();
            }
        }
        return fragment;
    }

    public Fragment showPages(String partName, String aliasName, Class<? extends Fragment> clazz) {
        return this.showPages(partName, aliasName, clazz, false);
    }

    public Fragment showPages(String partName, String aliasName, Class<? extends Fragment> clazz, Bundle bundle) {
        Fragment fragment = showPages(partName, aliasName, clazz, false);
        fragment.setArguments(bundle);
        return fragment;
    }
}
