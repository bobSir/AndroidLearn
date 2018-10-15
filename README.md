#Android学习与实践
    自定义View属性声明获取，如果要把自定义View绘制在指定坐标，当前view画布要包含这个坐标，
    不然在onDraw指定坐标绘画没有效果。可以在父布局中自己布局实现具体位置。
    
    fragment onBackPress activity onbackPress分发到Fragment
    实测fragment onBackPress实现策略，实测下来效果不理想
        1. 通过FragmentManager.findFragmentById(id)确定当前Fragment是哪个Fragment，id为包裹Fragments的View id，
        viewPager-fragments方式，获取的currentFragment并不是当前Fragment，而是预加载的最后一个Fragment。
        2. 通过FragmentTransaction.add(id,Fragment)的方式添加Fragment，可以正常使用这套策略。
        3. 主要是分发思想activity收到onBackPress遍历下面的Fragment，把事件分发下去，需要处理事件的通过的返回True拦截
        事件。