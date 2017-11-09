`此文件会持续更新`

-------------------

[更新日志](update-log.md)



-------------------------------



# 功能介绍

> WeBase，这是一个快速开发的框架，涵盖了基础配置管理，UI特效，功能组件，线程管理，文件上载下载等，尽最大限度的帮助开发者以最少的代码来完成复杂重复的问题，提升工作效率。

1.**WeManager**：用于管理全局模块管理器的类：UI模块，网络模块，文件，基础配置等。此类的出现是为了避免各个模块的配置信息分布在项目四处，不好维护，因为都由统一的入口处理请求，再由唯一的接口分发到对应子模块；

> 相关API待完善

2.**WeVision**：动画效果类，一行代码实现复杂特效；

> ```java
> WeVisionStatus status=WeVisionEngine.use(Vision.FadeInLeft).playOn(targetView);
> ```
>
> 

3.**WeInject**：

- a)全新的activity之间传递数据模式，避免出现无尽的`new Intent()`；

> 之前：
>
> ```java
> Intent intent=new Intent();
> intent.setClass(MainActivity.this,SecActivity.class);
> intent.startActivity(intent);
> ```

> 现在：
>
> ```java
> WeSecondActivity_Plumber.getInstance().setName("Hello,nerd").startForResult(this,0x11);
> ```

- b)根除每创建一个Activity都需要添加重复的代码，避免出现无尽的初始化代码；

> 之前：
>
> ```java
> public class FourActivity extends BaseActivity<ActivitySecondBinding, MainViewModel> {
>         @Override
>         protected void onCreate(Bundle savedInstanceState) {
>             super.onCreate(savedInstanceState);
>             ActivitySecondBinding binding= DataBindingUtil.setContentView(this,R.layout.activity_second);
>             binding.setViewModel(new MainViewModel());
>             setBinding(binding);
>         }
>     }
> ```
>
> 

> 现在：
>
> ```java
> @WeInjectPlumber
> @WeInjectPorter(layoutId = R.layout.activity_second)
> public class SecondActivity extends BaseActivity<ActivitySecondBinding, MainViewModel> {
>
>     @WeInjectPlumber
>     public String name;
>     @WeInjectPlumber
>     public int value;
>     @WeInjectPlumber
>     public BookModel bookModel;
>
>     @Override
>     protected void onCreate(Bundle savedInstanceState) {
>         super.onCreate(savedInstanceState);
>     }
> }
> ```
>

`说明：基本彻底取消了三行重复的代码，这三行代码在每次创建一个activity的时候都需要加入，而且顺序不能改变，如果一旦不慎更改，就会出现问题，因此对刚接触数据绑定框架的开发者来说容错率不高，所以有了这个框架，可以大大减少不必要的麻烦，提高开发效率。`

- c)线程的使用

> 之前：
>
> ```java
> 1.Thread threa=new Thread();
> 2.实现Runnable接口
> //代码不赘述
> ```
>
> 现在：
>
> ```java
> @WeInjectBeadle
> public void doSomeThingsBackground(String name){
>      doSomething();
> }
> ```

.....



------

# 性能

AndroidMoitor执行onCreate()

> +130ms

System.currentTimeMillis()

> +1.8ms

------

