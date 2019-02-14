package com.bobLearn.threadpool;

import android.os.Handler.Callback;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

public class FundWXThreadImpl implements FundWXThread {

    private boolean mStarted = false;
    private FundWXThreadPriority mPriority = FundWXThreadPriority.NORMAL;
    private String mGroupName = null;
    private Callable<?> mCallable;

    protected FundWXThreadImpl() {

    }

    @Override
    public void start(Runnable task) {

        if (!mStarted) {
            mCallable = Executors.callable(task);
            FundWXRunnerController.getThreadRunner().runTask(mCallable, null, mGroupName, mPriority);
            mStarted = true;
        }
    }

    @Override
    public void start(Callable<?> task, Callback callback) {
        if (!mStarted) {
            mCallable = task;
            FundWXRunnerController.getThreadRunner().runTask(task, callback, mGroupName, mPriority);
            mStarted = true;
        }
    }

    @Override
    public void cancel(boolean mayInterruptIfRunning) {
        FundWXRunnerController.getThreadRunner().cancelTask(mCallable, mayInterruptIfRunning);
    }

    @Override
    public void setPriority(FundWXThreadPriority priority) {
        mPriority = priority;
    }

    @Override
    public FundWXThread addThread2Group(String groupName) {
        if (!mStarted)
            mGroupName = groupName;
        return this;
    }

    @Override
    public void removeThreadFromGroup(String groupName) {
        if (!mStarted)
            mGroupName = null;
    }

    @Override
    public void cancelGroupThread(boolean force) {
        FundWXRunnerController.getThreadRunner().cancelGroup(mGroupName, force);
    }

    @Override
    public void pauseGroupThread() {
        FundWXRunnerController.getThreadRunner().pauseGroup(mGroupName);
    }

    @Override
    public void resumeGroupThread() {
        FundWXRunnerController.getThreadRunner().resumeGroup(mGroupName);
    }

    @Override
    public void setGroupConcurrents(int concurrents) {
        FundWXRunnerController.getThreadRunner().setGroupConcurrents(mGroupName, concurrents);
    }


    @Override
    public boolean isCancelled() {
        return FundWXRunnerController.getThreadRunner().isTaskCancelled(mCallable);
    }

    @Override
    public String getGroupName() {
        return mGroupName;
    }

    @Override
    public FundWXThreadPriority getPriority() {
        return mPriority;
    }
}
