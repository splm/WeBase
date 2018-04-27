package me.splm.app.baselibdemo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.jc.android.baselib.imageloader.ImageLoader;
import com.jc.android.baselib.imageloader.ImageLoaderUtils;

import java.util.ArrayList;
import java.util.List;

import me.splm.app.auto.WeMainActivity_Beadle;
import me.splm.app.auto.WeSecondActivity_Plumber;
import me.splm.app.baselibdemo.TestModel.BookModel;
import me.splm.app.baselibdemo.TestModel.MainViewModel;
import me.splm.app.baselibdemo.databinding.ActivityMainBinding;
import me.splm.app.inject.annotation.WeInjectBeadle;
import me.splm.app.inject.annotation.WeInjectPorter;

@WeInjectPorter(layoutId = R.layout.activity_main)
public class MainActivity extends BaseActivity<ActivityMainBinding,MainViewModel> {

    private List<String> list=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        WeMainActivity_Beadle.getInstance().doSomeThingsBackground("haha");
    }

    @WeInjectBeadle(taskID = "id1",serial = "serial1",delay = 3000L)
    public void doSomeThingsBackground(String name){
        Log.e("**********", "doSomeThingsBackground: "+Thread.currentThread().getId());
    }

    private void init(){
        for(int i=0;i<15;i++){
            list.add(i+"个元素");
        }
        getBinding().open1ActBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookModel bookModel=new BookModel();
                bookModel.setName("bookName");
                bookModel.setPrice(50);
                bookModel.setTag(1);
                WeSecondActivity_Plumber.getInstance()
                        .setName("hello,nerd!")
                        .setValue(100)
                        .setBookModel(bookModel)
                        .start(MainActivity.this);

            }
        });
        /*IWorkshop workshop=new InformationDesk();
        ActivityCoreManager manager=workshop.catchManagerOfActivity();*/

        ImageLoader imageLoader=new ImageLoader.Builder().url("").imgView(null).build();
        ImageLoaderUtils.getInstance().loadImage(this,imageLoader);
    }
}
