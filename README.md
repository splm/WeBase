`此文件会持续更新`

-------------------

相关文档：

- [更新日志](update-log.md)
- [设计思路](design.md)
- [WeCode的使用](WeCodeManual.md)

参考资料：

- [JCodeModel](https://www.ibm.com/developerworks/cn/java/j-lo-codemodel/)
- [AndroidAnnotation](http://www.jianshu.com/p/7179769fec6f)
- [JavaPoet](http://blog.csdn.net/crazy1235/article/details/51876192)
- [Callable&Future](http://blog.csdn.net/ghsau/article/details/7451464)

-------------------------------





# 功能介绍

`此项目是`[WeInject2](https://github.com/splm/WeInject2)`的整合与升级，因此WeInject2会放弃维护`

> WeBase，是一个避免开发者反复编写重复代码的工具，所有常用的代码可以自己根据WeCodeManual编写自己的规则，然后交由`WeAnnotation-processor`生成代码。

> `注意：使用本项目请结合Goodle Databinding框架使用。如果不想基于这个，那么需要修改Weannotation-processor中的代码`

> 闲话：主要是以技术探讨交流，也参考了大量开源框架的设计思路和功能实现，真的很佩服这些大佬们的智慧，WeBase的很多功能都参考了他们的智慧结晶，这里再次膜拜大佬，感谢你们对技术做出的贡献。
>
> - 为什么要开发这个东西？
>   - 答：之前对注解这个东西并不是很陌生，也一直在用，但当时只停留在运行时，也知道效率是其弱点。直到看到一个大神写的界面路由功能，只需要简单的注解就完美的实现了冗长的跳转代码，十分好奇，可惜当时并没有看到相关实现源码，因为核心的东西并没有公布，所以本着对技术的好奇开始研究这段功能强大但又来历不明的代码。
> - 这个东西能不能作为一种模块集成到项目中？
>   - 答：虽然我个人在公司的一些演示Demo中使用了WeBase，用着感觉也算顺手（毕竟自己一笔一划写的），但当中可能还存在很多不合理，不健壮的“坑”，导致这样那样的问题，得不偿失，所以不推荐拿来主义，其次是效率问题，考虑过增量，在编译过程会检查文件的内容变更情况，从而判断是否需要重新创建辅助的Java文件，但经过多次尝试发现一旦Java类不被创建，就意味着其不会被编译器知道，导致类丢失的问题，也看过Gradle关于增量编译的一些资料，但始终没有找到合适的解决方案，这个问题也是一个心病，有了解的朋友，希望可以Fork，一起研究。
> - 这个东西现在有哪些功能？
>   - 答：WeBase一直在修改，一是代码混乱，二是不停的抽象，所以功能一直没有向前推进。支持Activity中的控件及其子View的初始化工作；支持简单动画的设定；支持线程创建调用；支持一些核心管理类的管理（App中的各项设定，比如SharePreference字段名称，网络模块的HostUrl等，这些配置好比一份一份的文档，摆在IWorkShop这个接口桌子上，想要拿哪份文档，无论你在哪里，来到这张桌上找就行）
> - 一句话概括这个东西的原理
>   - 答：自动创建一个完成了部分功能的代码块，别的类主动去调用。

1.**WeVision**：动画效果类，一行代码实现复杂特效；[更多功能可以参考](https://github.com/splm/WeVision)

> ```java
> WeVisionStatus status=WeVisionEngine.use(Vision.FadeInLeft).playOn(targetView);
> ```

2.**WeInject**：

###### 数据传递

> 这个功能现在废弃，想基于WeAnnotation做一个路由功能
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



-----------------------



###### 初始化代码精简

- b)根除每创建一个Activity都需要添加重复的代码，避免出现无尽的初始化代码；

> 之前：
>
> ```java
> public class FourActivity extends BaseActivity {
>         @Override
>         protected void onCreate(Bundle savedInstanceState) {
>             super.onCreate(savedInstanceState);
>             setContentView(R.layout.activity_four);
>             Button btn=findViewById(R.id.btn_from_four);
>         }
>     }
> ```
>
>

> 现在：
>
> ```java
> @WeInjectPorter(R.layout.activity_main)
> public class SecondActivity extends BaseActivity {
> 
>     @WeInjectPorter(viewID = R.id.open_1_act_btn)
>     public Button mButton;
>     @WeInjectPorter(viewID = R.id.open_2_act_btn)
>     public Button mloadBtn;
> 
>     @Override
>     protected void onCreate(Bundle savedInstanceState) {
>         super.onCreate(savedInstanceState);
>     }
> }
> ```
>

`说明：使用思路基本和ButterKnife一样，都是为了避免大量的重复代码出现，同时也尽量将开发的精力转移到核心业务上。`

------------------------



###### 线程的简化

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

###### 动态生成Java类文件的语法简化

- 如果需要自定义注解插件或者有自动构建Java类的需求可以使用，详细参考[WeCode的使用](WeCodeManual.md)

--------------------------



###详细使用

如何开始

- 1.创建一个BaseActivity继承，并在OnCreate()中加入

```java
WeWorkersProxy.bind(this);
```

或者直接继承`me.splm.app.core.logic.view.BaseActivity`

- 设置Activity布局

```java
@WeInjectPorter(R.layout.activity_main)
public class SecondActivity
```

- 初始化View控件

```java
@WeInjectPorter(viewID = R.id.open_1_act_btn)
public Button mButton;
```

- 初始化子View控件

```java
@WeInjectPorter(viewID = R.id.layout_1_vs)
public ViewStub mStubView;
@WeInjectPorter(parentView = "mStubView",viewID = R.id.open_2_act_btn)
public Button mButton2;
```

`注意写在子View变量上的注解ParentView一定要与父View的变量名对应，并且一定要在当前OnCreate()加入：`

```java
WeMainActivity_Porter.getInstance().initOtherView();
```

`如果没有WeXXXX_Porter类，请先ReBuild`

