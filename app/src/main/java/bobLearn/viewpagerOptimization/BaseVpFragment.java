package bobLearn.viewpagerOptimization;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Create by cly on 18/10/16
 * <p>
 * 当前滑动优化策略：Fragment懒加载，300ms内滑过则不加载本页数据。优点：占用内存低 缺点：fragment因为销毁，滑动还会重新创建
 * * 2、取消viewpagerAdapter destroyItem, 优点：fragment只会创建一次，缺点：占用内存高
 */
public abstract class BaseVpFragment extends Fragment {
    private boolean isFragmentVisible;
    private boolean isFirstVisible;
    protected boolean isInflate = true;
    private View rootView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVariable();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        isInflate = true;
        checkLoadData();
        return createView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = view;
            if (getUserVisibleHint()) {
                if (isFirstVisible) {
                    onFragmentFirstVisible();
                    isFirstVisible = false;
                }
                isFirstVisible = true;
                checkLoadData();
            }
        }
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (rootView == null) return;
        if (isFragmentVisible && isVisibleToUser) {
            onFragmentFirstVisible();
            isFirstVisible = false;
        }
        if (isVisibleToUser) {
            isFirstVisible = true;
            checkLoadData();
            return;
        }
        if (isFragmentVisible) {
            isFragmentVisible = false;
            onFragmentHind();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        initVariable();
    }

    private void initVariable() {
        isFirstVisible = true;
        isFragmentVisible = false;
        rootView = null;
    }

    private void checkLoadData() {
        if (isInflate && isFragmentVisible) loadData();
    }

    protected void onFragmentFirstVisible() {
    }

    protected abstract View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    protected abstract void onFragmentHind();

    protected abstract void loadData();
}
