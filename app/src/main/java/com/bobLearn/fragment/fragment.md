
# Fragment

## 基本概念
Fragment简称碎片,android.app.fragment Android 3.0提出。support.v4.app.fragment 兼容低版本，最低兼容到Android1.6。
support是不断更新的，建议使用support库中的Fragment。使用support库中的Fragment，Activity必须要集成FragmentActivity。
- Fragment是依赖于Activity的，不能独立存在。
- 一个Activity里可以有多个Fragment，一个Fragment可以被多个Activity重用。
- Activity运行时动态地添加或删除Fragment。
- Nested Fragment: Fragment 内部嵌套Fragment，通过getChildFragmentManager()能够获得管理子Fragment的FragmentManager，
 在子Fragment中可以通过getParentFragment()获取父Fragment。
### 核心类
- Fragment: fragment基类，任何创建的Fragment都需要继承该类。(一般用support包)
- FragmentManager: 管理和维护Fragment，他是抽象类，具体实现类是FragmentManagerImpl。
- FragmentTransaction: 对Fragment的添加，删除等操作都需要通过事务方式进行，他是抽象类，具体实现类是BackStackRecord。
### 创建Fragment
- 继承Fragment onCreateView() 返回View.inflater.inflate(id,container,false).
- 传入参数，通过setArguments(Bundle bundle) 
- getActivity(), 建议再onAttach()将context强转为Activity。
### 添加
- 静态添加 xml
- 动态添加 方式很多，灵活，建议使用。 Fragment并不是一个View，和Activity是用一层次的。
```java
 getSupportFragmentManager().beginTransaction()
                 .add(R.id.container, new AFragment(), AFragment.TAG)
                 .commit();
```
- 设置tag，可以通过getSupportFragmentManager().findFragmentByTag(AFragment.TAG)查找Fragment，
getSupportFragmentManager().findFragmentById(R.id.container)
- 在一次事务中可以多次操作，add().remove().replace()。commit()是异步的，内部通过mManger.enqueueAction()加入处理
队列，同步方法为commitNow()。 如果commit()操作在onSaveInstanceState()之后，就会抛出异常。commitAllowingStateLoss()
方法不会抛出异常。不要把Fragment事务放在异步线程的回调中.
- addBackStack() FragmentManager添加回退栈。事务添加到回退栈，点击返回，回滚Fragment事务(如果事务是add()，那么回退操作就是remove())
getActivity.onBackPressed()
## 生命周期
![fragment生命周期](http://img.my.csdn.net/uploads/201211/29/1354170699_6619.png)
- onAttach()：Fragment和Activity相关联时调用。可以通过该方法获取Activity引用，还可以通过getArguments()获取参数。
- onCreate()：Fragment被创建时调用。
- onCreateView()：创建Fragment的布局。
- onActivityCreated()：当Activity完成onCreate()时调用。
- onStart()：当Fragment可见时调用。
- onResume()：当Fragment可见且可交互时调用。
- onPause()：当Fragment不可交互但可见时调用。
- onStop()：当Fragment不可见时调用。
- onDestroyView()：当Fragment的UI从视图结构中移除时调用。
- onDestroy()：销毁Fragment时调用。
- onDetach()：当Fragment和Activity解除关联时调用。

- 看代码
- Fragment的onAttach()->onCreate()->onCreateView()->onActivityCreated()->onStart()都是在Activity的onStart()
中调用的。
- AFragment启BFragment addToBackStack() replace() add()区别
- 看代码

- FragmentTransaction 基本方法

- show(): 不调用任何生命周期方法。前提是要显示的Fragment已经添加到容器中。只是Fragment UI setVisibility为true.
- hide(): 同上
- add()
- remove()
- replace()：旧的Fragment调用remove() 新的Fragment调用add()
- addToBackStack: 添加回退栈

## Fragment实现原理 Back Stack

- 源码
### Back Stack
- addToBackStack(@Nullable String name): 添加回退栈 传入标识当前Fragment tag.
- popBackStack(): 将回退栈的栈顶弹出，并回退该事务.
- popBackStack(String name,int flag): name->tag flag可以为0或者FragmentManager.POP_BACK_STACK_INCLUSIVE，0
表示之弹出该元素以上的所有的元素，POP_BACK_STACK_INCLUSIVE表示弹出包含该元素以上的所有元素。
- 看代码

- [fragment-bugly](https://mp.weixin.qq.com/s/dUuGSVhWinAnN9uMiBaXgw)



 