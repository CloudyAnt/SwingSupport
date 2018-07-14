# SwingSupport

## —— 致力于使 JAVA Swing 开发更加美观、便捷

`test` 包中是一些测试及效果展示。您可以 pull 下整个项目，然后运行这些测试了解项目。
主要的测试类有 `Test_SuitA`、`Test_SuitB` 、`Test_FadeFrame` 等

下面是对已经比较完善的几个的类简介：

类名 | 描述
---|---
`Fader` | 原名 *ColorOperator*，实现组件前景色、背景色的各种淡入淡出
`Slider` | “滑行者”，实现组件的各种移动效果
`Dragger` | “拖曳者”，让组件可被拖动。嗯，可在任何组件上拖动任何组件
`Imager` | “绘图者”，实现图像的旋转、翻转等
`Liner` | “绘线者”，向某个方向填充渐变的色彩，类似 css 的 liner-gradual

除了以上几个成熟的，还有其他的一些规划，目前忙于找工作、实习等等，加上之前的事，已经且还将要停滞很长时间。有志同道合者，Welcome for Working-Together

以下是各种包的含义

包名 | 描述
---|---
`org` | 引入的其他项目，用于主要用于解析文件编码  
`pri` | 本项目的主体  
`pri.file` | 包含对文件相关的工具  
`pri.math` | 包含数学相关的工具  
`pri.swg` | 包含对窗体相关的工具（也是项目的原始意图）  
`pri.util` | 其他的一些工具  