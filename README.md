# SwingSupport

## —— 想要 JAVA Swing 开发更加美观、便捷

`test` 包中是一些测试及效果展示。您可以 pull 下整个项目，然后运行这些测试了解项目

`test.swg.SuitA`、`test.swg.SuitB` 是包含了主要的功能的测试

## 功能简介

下面是对已经比较完善的几个的类简介：

类名 | 功能
---|---
**Fader** | 实现组件前景色、背景色的各种淡入淡出
**Slider** | 实现组件的各种移动效果
**Dragger** | 使组件可被拖动，可在任何组件上拖动任何组件
**Imager** | 实现图像的旋转、翻转等
**Liner** | 向某个方向填充渐变的色彩，类似 css 的 liner-gradual
**PolygonPainter** | 绘制各种多边形、芒星

**Liner** 

![描述](/readme-resource/liner.jpg)

**Polygon Painter**

![描述](/readme-resource/polygon.jpg)

其他几个功能是动态的，可以在测试类中查看

## 包名介绍

包名 | 描述
---|---
**org** | 引入的其他项目，主要用于解析文件编码  
**pri** | 本项目的主体  
**pri.file** | 包含对文件相关的工具  
**pri.math** | 包含数学相关的工具  
**pri.swg** | 包含对窗体相关的工具（也是项目的原始意图）  
**pri.util** | 其他的一些工具  
**test** | 测试类（目前无UT，之后可能会补）

---

Java Swing 或许终究是不值得太多关注的东西，自己没事图个乐就好了