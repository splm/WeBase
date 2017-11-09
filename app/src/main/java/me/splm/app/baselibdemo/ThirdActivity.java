package me.splm.app.baselibdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;


public class ThirdActivity extends AppCompatActivity {
    private Button open_3_act_btn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        long begin=System.currentTimeMillis();
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_third);
        initView();
        long end=System.currentTimeMillis();
        Log.e("---------", "third==="+(end-begin)+"s");
    }
    private void initView(){
        open_3_act_btn=(Button) findViewById(R.id.open_3_act_btn);
        open_3_act_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(ThirdActivity.this,FourActivity.class);
                startActivity(intent);
            }
        });
    }
}
