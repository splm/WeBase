package me.splm.app.baselibdemo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.Toast;

import me.splm.app.auto.WeMainActivity_Beadle;
import me.splm.app.auto.WeMainActivity_Porter;
import me.splm.app.core.component.http.RequestServer;
import me.splm.app.core.component.manager.ConfigFilesManager;
import me.splm.app.core.component.manager.HttpRequestConfig;
import me.splm.app.core.component.manager.IWorkShop;
import me.splm.app.core.component.manager.InformationDesk;
import me.splm.app.core.component.manager.SharePreferenceConfig;
import me.splm.app.inject.annotation.WeInjectBeadle;
import me.splm.app.inject.annotation.WeInjectPorter;
import me.splm.app.wenetjudger.processor.NetManager;


@WeInjectPorter(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @WeInjectPorter(viewID = R.id.open_1_act_btn)
    public Button mButton;
    @WeInjectPorter(viewID = R.id.open_2_act_btn)
    public Button mloadBtn;
    //@WeInjectPorter(viewID = R.id.layout_1_vs)
    public ViewStub mViewStub;

    public View mStubView;

    //@WeInjectPorter(parentView = "mStubView",viewID = R.id.open_2_act_btn)
    public Button mButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        WeMainActivity_Porter.getInstance().initOtherView();
        NetManager.getDefault().registerObserver(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetManager.getDefault().unRegisterObserver(this);
        NetManager.getDefault().unRegisterAllObserver();
    }

    @WeInjectBeadle(taskID = "id2", serial = "serial1", delay = 3000L)
    public void doSomeThingsBackground(String name) {
        Log.e("**********", "doSomeThingsBackground: " + Thread.currentThread().getId());
    }

    public void test() {
        WeMainActivity_Beadle.getInstance().doSomeThingsBackground("haha");
        Toast.makeText(MainActivity.this, "hello", Toast.LENGTH_SHORT).show();
    }

    private void init() {
        //mStubView=mViewStub.inflate();
        IWorkShop workshop = new InformationDesk();
        final ConfigFilesManager localFilesManager = workshop.catchManagerOfConfigFile();
        final SharePreferenceConfig config = localFilesManager.getSharePreferenceConfig();
        final HttpRequestConfig httpRequestConfig = localFilesManager.getHttpRequestConfig();
        config.putValueWhenNew("key_3", httpRequestConfig.getHostUrl());
        RequestServer.init(httpRequestConfig);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                config.putValue("key_3", "http://www.baidu.com/api");
            }
        });
        mloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("**********", "onClick: " + config.getValue("key_3", "tmp"));
            }
        });
        /*getBinding().open1ActBtn.setOnClickListener(new View.OnClickListener() {
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
        });*/
        /*IWorkShop workshop=new InformationDesk();
        TestUIConfig testUIConfig=new TestUIConfig();
        testUIConfig.read(workshop).getConfig().get("");*/
        /*ImageLoader imageLoader=new ImageLoader.Builder().url("").imgView(null).build();
        ImageLoaderUtils.getInstance().loadImage(this,imageLoader);*/
    }
}
