package learn.bob.com.androidlearn.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import java.util.List;

/**
 * Create by cly on 18/10/11
 */
public class BackHandlerHelper {

    /**
     * 将back事件分发给 FragmentManager 中管理的子Fragment，如果该 FragmentManager 中的所有Fragment都
     * 没有处理back事件，则尝试 FragmentManager.popBackStack()
     */
    public static boolean handleBackPress(FragmentManager fragmentManager, int id) {
        List<Fragment> fragments = fragmentManager.getFragments();
        Fragment currentFragment = fragmentManager.findFragmentById(id);
        if (fragments == null) return false;

        for (int i = fragments.size() - 1; i >= 0; i--) {
            Fragment child = fragments.get(i);
            if (isFragmentBackHandler(child, currentFragment)) {
                return true;
            }
        }

        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
            return true;
        }
        return false;
    }

    /**
     * 判断Fragment是否处理的返回
     *
     * @param child
     * @param currentFragment
     * @return
     */
    private static boolean isFragmentBackHandler(Fragment child, Fragment currentFragment) {
        return child != null
                && child.isVisible()
                && child instanceof FragmentBackHandler
                && ((FragmentBackHandler) child).onBackPressed(currentFragment);
    }

    public static boolean handleBackPress(Fragment fragment, int id) {
        return handleBackPress(fragment.getChildFragmentManager(), id);
    }

    public static boolean handleBackPress(FragmentActivity fragmentActivity, int id) {
        return handleBackPress(fragmentActivity.getSupportFragmentManager(), id);
    }
}