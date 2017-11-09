`此文件会持续更新`

-------------------

相关文档：

- [更新日志](update-log.md)
- [设计思路](design.md)

参考资料：

- [JCodeModel](https://www.ibm.com/developerworks/cn/java/j-lo-codemodel/)
- [AndroidAnnotation](http://www.jianshu.com/p/7179769fec6f)
- [JavaPoet](http://blog.csdn.net/crazy1235/article/details/51876192)
- [Callable&Future](http://blog.csdn.net/ghsau/article/details/7451464)

-------------------------------





# 功能介绍

`此项目是`[WeInject2](https://github.com/splm/WeInject2)`的整合与升级，因此WeInject2会放弃维护`

> WeBase，这是一个快速开发的框架，涵盖了基础配置管理，UI特效，功能组件，线程管理，文件上载下载等，尽最大限度的帮助开发者以最少的代码来完成复杂重复的问题，提升工作效率。
>
> `注意：使用本项目请结合Goodle Databinding框架使用`

1.**WeManager**：用于管理全局模块管理器的类：UI模块，网络模块，文件，基础配置等。此类的出现是为了避免各个模块的配置信息分布在项目四处，不好维护，因为都由统一的入口处理请求，再由唯一的接口分发到对应子模块；

> 相关API待完善

2.**WeVision**：动画效果类，一行代码实现复杂特效；[更多功能可以参考](https://github.com/splm/WeVision)

> ```java
> WeVisionStatus status=WeVisionEngine.use(Vision.FadeInLeft).playOn(targetView);
> ```
>
> 

3.**WeInject**：

###### 数据传递

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

- d)自动构建Java类语法

> 之前
>
> ```java
> private void generateHelloworld() throws IOException{
>         MethodSpec main = MethodSpec.methodBuilder("main") //main代表方法名
>                     .addModifiers(Modifier.PUBLIC,Modifier.STATIC)//Modifier 修饰的关键字
>                 .addParameter(String[].class, "args") //添加string[]类型的名为args的参数
>                     .addStatement("$T.out.println($S)", System.class,"Hello World")//添加代码，这里$T和$S后面会讲，这里其实就是添加了System,out.println("Hello World");
>                 .build();
>                 TypeSpec typeSpec = TypeSpec.classBuilder("HelloWorld")//HelloWorld是类名
>                 .addModifiers(Modifier.FINAL,Modifier.PUBLIC)
>                 .addMethod(main)  //在类中添加方法
>                 .build();
>         JavaFile javaFile = JavaFile.builder("com.example.helloworld", typeSpec)
>                 .build();
>         javaFile.writeTo(System.out);
>     }
> ```

`上面代码片段来自网络`



> 现在：
>
> ```java
> WeVar className = new WeVar(WeMod.PRIVATE+WeMod.STATIC,TARGET_PACKAGE_NAME,"We" + cName + writeSuffix(),"instance");
> WeCodeModel weCodeModel = new WeCodeModel();//创建CodeModel代码构造器实例
> WeClass weClass = weCodeModel.createClass("We" + cName + writeSuffix());//创建类
> weClass.addInterface(IWorkersProxy.class);//实现接口
> weClass.declareVar(WeMod.PUBLIC, p1st.getPackageName(), p1st.getSimpleName(), MDATABINGNAME);//定义全局变量
> weClass.canBeSingleton(className);//创建一个单例模式
> ```

说明：引入这个功能，更多的是为了让生成Java类与Java代码的过程更加人性化，更贴近编程思维，在使用JavaPoet生成Java代码时，需要先构建类中的最小单元变量，方法体，方法签名，然后再将这些整合到类中，而我们通常的编写习惯是创建类文件，声明类，变量，方法，方法签名。



.....



------

# 性能

AndroidMoitor执行onCreate()

> +130ms

System.currentTimeMillis()

> +1.8ms

------

