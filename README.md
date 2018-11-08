# Android学习与实践

# 文章
- [WPopup - 一个简单使用并且高度定制的Popupwindow](http://www.wanandroid.com/blog/show/2340)
- [Android优雅地处理按钮重复点击 AOP](https://www.jianshu.com/p/7b35eb8d0d3)
- [Android常用优秀开源框架](https://github.com/Ericsongyl/AOSF)
- [Android Jetpack组件](https://blog.csdn.net/Alexwll/article/details/83302173)
- [BRVAH 强大的RecyclerAdapter框架](https://www.jianshu.com/p/b343fcff51b0)


# 工具
## studio 插件 
- translation(翻译插件) 
- AndroidSourceViewer(跳转查看API)

## 文章
- [Mac下用Charles实现Android http/https抓包](https://blog.csdn.net/luochoudan/article/details/72801573)

# 日常笔记
## audioWave 音波View
自定义View属性声明获取，如果要把自定义View绘制在指定坐标，当前view画布要包含这个坐标，
不然在onDraw指定坐标绘画没有效果。可以在父布局中自己布局实现具体位置。

## fragment实现onBackPress
fragment onBackPress activity onbackPress分发到Fragment
实测fragment onBackPress实现策略，实测下来效果不理想
1. 通过FragmentManager.findFragmentById(id)确定当前Fragment是哪个Fragment，id为包裹Fragments的View id，
viewPager-fragments方式，获取的currentFragment并不是当前Fragment，而是预加载的最后一个Fragment。
2. 通过FragmentTransaction.add(id,Fragment)的方式添加Fragment，可以正常使用这套策略。
3. 主要是分发思想activity收到onBackPress遍历下面的Fragment，把事件分发下去，需要处理事件的通过的返回True拦截
事件。
        
## Fragment事务回退栈
- FragmentTransaction transaction = getFragmentManager().beginTransatcion();
- transaction.add() 添加一个Fragment
- transaction.remove() 移除 如果没有回退栈Fragment将被销毁
- transaction.replace() 用另一个Fragment替换当前，实际是remove 和 add的合体
- transaction.hide() 隐藏当前Fragment
- transaction.show() 显示之前隐藏
- transaction.detach() 销毁视图结构，实例不会被销毁。
- transaction.commit() 提交事务
- transcation.addToBackStack(String) 添加一个Fragment事务回退栈
- Fragment处理Fragment的事情，activity相当于Fragment的总线，可以通过回调的方式由Activity管理各个Fragment的创建。
- 参考[郭霖 Fragment解析](https://blog.csdn.net/lmj623565791/article/details/37992017)

## Fragment优化 懒加载
- 只加载当前滑至页面，取消FragmentViewPager destroyItem(),防止因为回收滑回来时再加载。优点：体验好 缺点：内存高。当
快速滑动时在onFragmentHind()回调中取消网络请求。

## Fragment详解
- 详见 com/bobLearn/fragment/fragment.md

## RecyclerView详解
- 详见 com/bobLearn/recyclerView/recyclerView.md