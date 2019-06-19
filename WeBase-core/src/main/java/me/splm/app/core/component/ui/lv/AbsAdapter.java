package me.splm.app.core.component.ui.lv;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public abstract class AbsAdapter<T> extends BaseAdapter {

    protected Context mContext;
    private List<T> mDatas;
    private int itemLayoutId;
    public AbsAdapter(Context context, List<T> mDatas, int itemLayoutId) {
        this.mContext = context;
        this.mDatas = mDatas;
        this.itemLayoutId = itemLayoutId;
    }

    @Override
    public int getCount() {

        return mDatas.size();

    }

    @Override
    public T getItem(int position) {

        return mDatas.get(position);

    }

    @Override
    public long getItemId(int position) {

        return position;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = getViewHolder(position, convertView, parent);

        convert(viewHolder, getItem(position), position);

        return viewHolder.getmConvertView();

    }

    protected abstract void convert(ViewHolder viewHolder, T item, int position);

    private ViewHolder getViewHolder(int position, View convertView, ViewGroup parent) {

        return ViewHolder.get(mContext, convertView, parent, itemLayoutId,position);

    }

    public void insert(T t,int index){

        if(mDatas!=null){

            mDatas.add(index, t);

        }

        notifyDataSetChanged();

    }

    public void remove(T t){

        if(mDatas!=null){

            mDatas.remove(t);

        }

        notifyDataSetChanged();

    }

    public List<T> getList(){

        return mDatas;

    }

}
