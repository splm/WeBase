package me.splm.app.baselibdemo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.jc.android.baselib.manager.Config;
import com.jc.android.baselib.manager.IWorkshop;
import com.jc.android.baselib.manager.InformationDesk;
import com.jc.android.baselib.manager.LoaderObject;
import com.jc.android.baselib.manager.UILayerManager;

import java.util.ArrayList;
import java.util.List;

import me.splm.app.auto.WeMainActivity_Beadle;
import me.splm.app.auto.WeSecondActivity_Plumber;
import me.splm.app.baselibdemo.TestModel.BookModel;
import me.splm.app.baselibdemo.TestModel.MainViewModel;
import me.splm.app.baselibdemo.databinding.ActivityMainBinding;
import me.splm.app.inject.annotation.WeInjectBeadle;
import me.splm.app.inject.annotation.WeInjectPorter;
import me.splm.app.inject.processor.component.proxy.TreeTrunk;

@WeInjectPorter(layoutId = R.layout.activity_main)
public class MainActivity extends BaseActivity<ActivityMainBinding,MainViewModel> {

    private Button open_1_act_btn;
    private List<String> list=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        WeWorkersProxy.bind(this);
        init();
        WeMainActivity_Beadle.getInstance().doSomeThingsBackground("haha");
    }

    @WeInjectBeadle(taskID = "id1",serial = "serial1",delay = 3000L)
    public void doSomeThingsBackground(String name){
        Log.e("**********", "doSomeThingsBackground: "+Thread.currentThread().getId());
    }

    @WeInjectBeadle
    public void doSomeThingBackground(TreeTrunk treeTrunk){

    }

    private void init(){
        for(int i=0;i<15;i++){
            list.add(i+"个元素");
        }
        open_1_act_btn=(Button) findViewById(R.id.open_1_act_btn);
        open_1_act_btn.setOnClickListener(new View.OnClickListener() {
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
        TestUIConfig testUIConfig=new TestUIConfig();
        IWorkshop workshop=new InformationDesk();
        LoaderObject<UILayerManager> loader=workshop.enrollPermission(testUIConfig);
        Config config=loader.getConfig();
    }
}
