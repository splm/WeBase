Weannotation设计思路
无意当中看到某人编写的基于注解传值的AndJump项目，该项目只需要在变量上加入特定注解即可在目标activity之间接收变量值，
原来是每次页面跳转都需要写intent，现在可以彻底告别了，数据究竟是怎么传递的？作者并没有给出答案，或许也是我技术不行，没有找到目标代码，因此带着这种疑惑，
自己重新实现了没有找到的核心部分。

- 明确Java注解几种运行周期和作用域；
- 了解JavaPoet的是基础语法和原理；
- 了解注解处理器的玩法；
- apt.


Weannotation
1.传递引用类型数据的时候，需要保证传递的对象能够被序列化;
2.每次在创建新的activity的时候，填写完注释@Weinject，需要重新Rebuild一下，否则We开头的辅助类，是不会生成的。