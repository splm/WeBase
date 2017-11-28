`参考资料`

[JavaPoet基础](http://www.jianshu.com/p/95f12f72f69a)



# WeCodeManual的设计思路

> WeBase框架中，当你使用其注解来完成开发工作时，APT会根据根据注解解释器自动按照一定规则自动生成代码，所指的一定规则，就是利用JavaPoet工具要求的语法，编写相应的功能。

**下面展示一下JavaPoet的语法及实际用法：**

- MethodSpec 代表一个构造函数或方法声明。
- TypeSpec 代表一个类，接口，或者枚举声明。
- FieldSpec 代表一个成员变量，一个字段声明。
- JavaFile包含一个顶级类的Java文件。

比如我们想用JavaPoet自动生成以下代码，该怎么办？

![code1](http://img.blog.csdn.net/20160519234032780)

JavaPoet的语法就该如下：

```java
private void generateHelloworld() throws IOException{
        MethodSpec main = MethodSpec.methodBuilder("main") //main代表方法名
                    .addModifiers(Modifier.PUBLIC,Modifier.STATIC)//Modifier 修饰的关键字
                .addParameter(String[].class, "args") //添加string[]类型的名为args的参数
                    .addStatement("$T.out.println($S)", System.class,"Hello World")//添加代码，这里$T和$S后面会讲，这里其实就是添加了System,out.println("Hello World");
                .build();
                TypeSpec typeSpec = TypeSpec.classBuilder("HelloWorld")//HelloWorld是类名
                .addModifiers(Modifier.FINAL,Modifier.PUBLIC)
                .addMethod(main)  //在类中添加方法
                .build();
        JavaFile javaFile = JavaFile.builder("com.example.helloworld", typeSpec)
                .build();
        javaFile.writeTo(System.out);

```

这里不详细解释语法的含义，只是简单说明一下JavaPoet的过程：

- 1.定义全局变量（如果没有可以忽略）；
- 2.定义方法1，方法2，直至方法n；
- 3.定义类对象，并将步骤1,2元素打包在一起；
- 4.统一将前者写入文件。

从上面的几个步骤，我们可以大致明白，Javapoet的流程，基本就是从类的最小单元写起，由小及大，最后将它们打包，统一写入文件。我在没有引入WeCodeManual之前，也一直使用JavaPoet这种写法，但在做优化的时候，觉得这种功能写法虽然原生态，但觉得不符合我们日常编程习惯，因为我们在写代码的时候，都是先创建Java文件，写变量，写方法，也就是将上面步骤反过来，所以我基于Javapoet封装了一套我个人用着很习惯也很喜欢的功能，WeCodeManual。其主要作用就是之前提到的，更贴合日常开发习惯。

```java
//先创建WeCodeModel模型对象
WeCodeModel weCodeModel =renewCodeModel();//new WeCodeModel();也可以
//创建一个名字叫做"HelloWorld"的Java类；
WeClass weClass = weCodeModel.createClass("HelloWorld");
//创建名字为main的方法，修饰符为public，static，有多个修饰符可以+
WeMethod main = weClass.declareMethod(WeMod.PUBLIC+WeMod.STATIC, "main");
//创建代码块，方法体中的内容
CodeBlock block=CodeBlock.builder().add("System.out.println("+params+");").build();
//为main方法指定方法内容
main.addBody(block);
//创建
weCodeModel.build();
```

从代码行数来看，没有比JavaPoet原生的简单多少，但主要是创建流程有了变化，而且这种变化我觉得很友好，都是按部就班，容错率也会增加。

## 关于WeCodeManual其他用法

- Java类绑定接口

```java
weClass.addInterface(IWorkersProxy.class);
```

- 继承

```java
weClass.addExtends(WorkersProxy.class);
```

- 该类为单例模式

```java
weClass.canBeSingleton();
```

- 创建全局变量

  - 方法1：

    ```java
    //参数:修饰符，变量包名，类名，变量名，比如想要创建me.splm.app.kook.CityCrackpot类型的变量
    //那么变量包名就是me.splm.app.kook，类名：CityCrackpot，变量名：随意
    WeVar mActivity=weClass.declareVar(WeMod.PUBLIC, pName, cName, MACTIVITYNAME);
    ```

  - 方法2：

    ```java
    WeVar weVar=new WeVar(WeMod.PRIVIATE,pName, cName,MACTIVITYNAME);
    weClass.declareVar(weVar);
    ```

  同样，访问修饰符可以通过“+”来一次绑定多个。

  关于WeVar这个类的另外一个用法就是：

  ```java
  WeVar layoutIdOfIllusion=new WeVar(id);
  ```

  这种用法其实对于代码生成来讲，没有太多的实际意义，主要是用来进行传值作用

  ```java
  PorterFieldModelProxy porterFieldModelProxy=new PorterFieldModelProxy();
  porterFieldModelProxy.setValueOfLayoutIdValueModel(new PorterFieldModel(layoutIdOfIllusion));
  PorterCodeAssistant assistant=new PorterCodeAssistant(porterFieldModelProxy);
  ```

  这就是一个Wevar传递值得一个应用，主要体现在`GeneratePorterAction`中，为了将Processor从注解中获取到的值传递给`PorterCodeAssistant`，当你想自定义注解功能的时候，可以使用，普通情况下不要关注这个问题。

- 创建方法

```java
WeVar object = new WeVar("java.lang", "Object", "object");
WeMethod assist = weClass.declareMethod(WeMod.PUBLIC, "assist");
assist.addParameters(object);//多个方法参数，后面继续追加即可，obj1,obj2,obj3
assist.addAnnotation(Override.class);//为方法加入注解
assist.addBody(codeblock);
assist.reference(initView, object);//assist调用别的方法，第二个为前者方法参数
```

​	如果一次想引用多个方法，可以参考`public void references(WeMethodMarker weMethodMarker)`引用顺序即为添加顺序