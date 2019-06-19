package me.splm.app.core.component.ui.lv;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ViewHolder {
    private SparseArray<View> mViews;

    private View mConvertView;

    public ViewHolder(Context context, ViewGroup parent, int layoutId, int position){

        mViews=new SparseArray<View>();

        mConvertView= LayoutInflater.from(context).inflate(layoutId, parent, false);

        mConvertView.setTag(this);

    }

    public static ViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId, int position){

        if(convertView==null){

            return new ViewHolder(context, parent, layoutId, position);

        }

        return (ViewHolder)convertView.getTag();

    }

    public <T extends View> T getView(int viewId){

        View view=mViews.get(viewId);

        if(view==null){

            view=mConvertView.findViewById(viewId);

            mViews.put(viewId, view);

        }

        return (T)view;

    }

    public View getmConvertView() {

        return mConvertView;

    }
}
