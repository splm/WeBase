package me.splm.app.baselibdemo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


public class FourActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four);
    }

    public void sayHello(){
        Log.e("*************", "sayHello: ");
    }

    public String initView(@NonNull  String name){
        return null;
    }
}
