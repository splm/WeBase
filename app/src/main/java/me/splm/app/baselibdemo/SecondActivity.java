package me.splm.app.baselibdemo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import me.splm.app.inject.annotation.WeInjectPlumber;
import me.splm.app.inject.annotation.WeInjectPorter;

import me.splm.app.baselibdemo.TestModel.BookModel;
import me.splm.app.baselibdemo.databinding.ActivitySecondBinding;


@WeInjectPlumber
@WeInjectPorter(layoutId =R.layout.activity_second)
public class SecondActivity extends BaseActivity<ActivitySecondBinding, MainViewModel> {

    @WeInjectPlumber
    public String name;
    @WeInjectPlumber
    public int value;
    @WeInjectPlumber
    public BookModel bookModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        long begin=System.currentTimeMillis();
        super.onCreate(savedInstanceState);
        getBinding().open2ActBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(SecondActivity.this, ThirdActivity.class);
                startActivity(intent);
//                Toast.makeText(SecondActivity.this,"Click the button from SecActivity",Toast.LENGTH_SHORT).show();
            }
        });
        long end=System.currentTimeMillis();
        Log.e("---------", "sec==="+(end-begin)+"s");
    }
}
