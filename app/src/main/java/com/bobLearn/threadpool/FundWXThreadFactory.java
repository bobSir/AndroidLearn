package com.bobLearn.threadpool;

public class FundWXThreadFactory {

    private static class InnerThreadImpl extends FundWXThreadImpl {

        public InnerThreadImpl() {
            super();
        }

        public InnerThreadImpl(FundWXThreadPriority priority) {
            super();
            this.setPriority(priority);
        }
    }

    /**
     * 获取一个默认优先级的线程处理
     *
     * @return
     */
    public static FundWXThread newThread() {
        FundWXThreadImpl thread = new InnerThreadImpl();
        return thread;
    }

    /**
     * 获取一个特定优先级的线程处理
     *
     * @return
     */
    public static FundWXThread newThread(FundWXThreadPriority priority) {
        FundWXThread thread = new InnerThreadImpl(priority);
        return thread;
    }

}
