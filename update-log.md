# 更新记录

------

2019-03-04
WeAnnotation
更新：
1.解决源代码编译过程频繁报错的问题；
2.解决重复初始化的问题；
3.修复重复创建生成类的问题；
4.优化了生成类的创建规则，并加入Webase-mirror策略，结合新版Gradle编译速度更快；
5.处理一些细节。

--------------------

2018-04-16

WeAnnotation

更新：
1.集成快速定义数组的功能；
2.加入Typename,classname，class之间相互转换的功能；
3.更便捷的定义变量方法；
4.修复其他若干问题；


--------------------------

2018-03-09

WeAnnotation

更新：
1.修复了WeBlock中定义多个WeVar导致编译出错的问题；
2.调整生成类的名称_legend；



--------------------------


2017-11-24

WeAnnotation

更新：

1.优化了单例模式创建形式，代码不像以前那么复杂；
2.新增WeMethod对象的API；
3.加入TextUtils字符串辅助工具



--------------------------



2017-11-08

WeAnnotation

更新：

1.加入修饰符功能；

2.重写了构建方法体的方式，更贴近编程思路；



------



2017-10-31

WeAnnotation

更新：

1.加入注解控制线程的功能；

2.调整TreeRoot及其子类的API，使其更遵守开发规范；使其每一个职责更加明确；

3.优化了AbsGenerateJavaAction，加入设置生成Java类文件后缀名的抽象方法，使其子类有更好的约束；

4.增加logz日志打印功能的API；

5.修改了Config类中，生成Java类的标识，从"$$"改为"_"，例如：`WeMainActivity_Beadle`；



说明：

Q:更新5为什么要改？

A:在开发中，在XXAction中生成Java代码时调用其他已经生成的Java类，"$$"标识会被删除一个，不清楚问题所在，所以为了规避问题，将标识修改。

------

2017-10-27

WeAnnotation

更新：

1.修复了ForemanProcessor类有时崩溃的问题；

计划：

1.加入@Backgound线程处理注解，已经初步完成线程处理的工具类；

存在问题：

1.针对线程处理功能，思路就是：

- 线程场景有两种，一是单个线程（单任务），二是多个线程（多个任务），又分为串行与并行；
- 所有的线程均有线程池管理，大大解决资源消耗；所有线程处理后的返回值，暂时没有做处理；

------



2017-10-25

WeAnnotation

更新：

1.丰富了Log日志打印的数据，让打印的结果更为准确；

2.完善了Log日志初始化时的校验功能；使用者在没有对log工具初始化却调用了打印方法，会提示；

计划：

1.对日志工具的debug功能加入开关，支持gradle脚本控制；



------

2017-10-24

WeAnnotation

更新：

1.加入日志，此为开发者选项，普通开发者可以不予理睬，如果想对该框架进行维护或者定制，那么该功能会是一个很好的帮手，该日志用于程序调试，在实际开发中，由于processor的特殊性，不能通过`System.out`或者`log`的形式打印调试信息，给程序开发带来了较大的困难，因此加入了日志功能。

> 现在：
>
> 如果在`ForemanProcessor`子类中：
>
> ```java
> LOGGER.info("Hello,new shit nerd!");
> ```
>
> 如果在其他位置：
>
> - 需要如下声明，`superclass`为其父类，如果没有父类可以使用自身，然后调用如上方法即可。
>
> ```java
> Logger LOGGER= LoggerFactory.getLogger(?superclass?);
> ```



------

2017-10-13

WeAnnotation

更新：

1.优化了AbsGenerateAction中创建TargetClass构建方式；去掉了大量创建变量，方法，类等语句，让GenerateXXXAction类的职责更清晰，更轻便；

------

2017-9-30

WeAnnotation

更新：

1.加入是否全屏，是否去掉标题的样式设定，使用注解的形式进行设置；

> ```java
> @WeInjectPorter(noTitle = Wheather.YES,fullScreen = Wheather.YES)
> ```

------



2017-9-28

WeAnnotation

更新：

1.加入对baseActivityAPI的一些支持，便于继承的Activity调用；

预计：

1.完善使用说明；

理想：

以下内容都是基于注解完成的

~~1.全屏与标题栏的控制；~~

2.为控件绑定动画效果；

3.绑定事件；

4.指定运行的线程,@UIThread,@Background

------



2017-9-27

WeAnnotation

修改：

1.对AbsGenerateAction类中的方法进行了重构，去除了大量冗余重复的代码；

2.调整了XXXAction实例的创建形式；

3.调整了ActionTaskQueue的创建方式，使用Contruct(ActionTask...tasks)；

------



2017-9-26

WeAnnotation

修改：

1.优化了TreeTrunk职责不清的问题；

------



2017-9-25

WeAnnotation

修改：

1.对所有的常量进行整理，不再是随处可见了，有统一的管理类；

预计：

1.对TreeTrunk类的职责进行模糊化的设计，不再有执行的职责；

2.对TreeTrunk，AbsGenerateAction，ActionTask，TaskQueue进行规划，参考第一项，TreeTrunk只会负责对任务的执行，至于何种任务，不会关心；ActionTask类，可以理解为就是AbsGenerateAction子类的代理类；TaskQueue是承载Task的队列，一个TreeTrunk可能会有若干了任务（生成Java文件动作）需要执行

------



2017-9-22

WeAnnotation

修改：

1.之前遇到的棘手问题，已经彻底得到了解决，解决思路就是在生成Java文件的同时再生成一个同名的类用于保存当前目标类所标记的注解；

问题：

1.鉴于上述1中的情况，在未来，可能还会创建其他同名辅助性的文件，因此，觉得需要给RootClass做一个内容是JavaFile的任务队列；

------



2017-9-20

WeAnnotation

棘手问题：

1.如何调用生成的Java中的方法；

------



2017-9-19

Weannotation

修改：

1.调整了AbsGenerateJavaAction中的方法名，方法名与方法作用更贴切；

2.$$Porter动态生成的类，没有传递Activity对象；

存在问题：

2.无法动态触发IWoker接口的assist方法，主要原因就是因为注解的生命周期不同，导致不能正常获取；

------



2017-9-18
Weannotation
修改：
1.优化了WeInjectPorter注解的初始化操作，由原来需要指定三个对象（layoutId，binding对象和ViewModel对象）改为仅有一个对象（layoutId）

2.里程碑一样的解决了APT不能生成ActivityDataBinding对象的问题；

预计：

~~1.RootClass对象的Action职责过于清晰，根据软件设计原则需要进行优化；~~

------

2017-9-12
Weannotation
修改：

- 为RootClass对象加入管理方法模式

------



2017-9-11

Weannotation
修改：

- 调整了Weannotation的结构与类图，对于重新定义一个Weannotation模块，更容易操作；

------



2017-9-7
新增：
修改：

- 1.调整了processor的继承关系；

------



2017-9-6
Weannotation
新增：StartActivtiyForResult方法
修改：
1.修改了生成代码的注释；
2.修复了若干了小bug；

预计功能：

1.加入fragment之间的数据传递，并且需要与FragmentCoreManager兼容

2017-9-5

# 修复问题：

Weannotation

- 1.解决传递引用类型数据的问题；
- 2.解决传递基本类型数据的问题；

预计功能：
Weannotation

- 1.支持更复杂的对象传递，例如嵌套效果的List对象;

------



2017-9-3
新增：
1.WeVision动画库，集成了若干动画效果；