package me.splm.app.baselibdemo;

import android.os.Bundle;

import me.splm.app.baselibdemo.TestModel.BookModel;


//@WeInjectPlumber
//@WeInjectPorter(R.layout.activity_second)
public class SecondActivity extends BaseActivity {

    //@WeInjectPlumber
    public String name;
    //@WeInjectPlumber
    public int value;
    //@WeInjectPlumber
    public BookModel bookModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
