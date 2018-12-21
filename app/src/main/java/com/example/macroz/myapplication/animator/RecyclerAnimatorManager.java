package com.example.macroz.myapplication.animator;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;


/**
 * 类描述:   RecyclerView 中的动画管理 , 执行 增 删等动画,
 * 动画效果可自定义 ,有一套默认动画效果,如无特殊需求可以不配 ,动画实现参考
 * 设置Add动画             {@link #setAddAnimator(AbsItemAnimator)}
 * 设置Remove动画          {@link #setRemoveAnimator(AbsItemAnimator)}
 * 设置Change动画          {@link #setChangeAnimator(AbsItemAnimator)}
 * 设置Move动画            {@link #setMoveAnimator(AbsItemAnimator)}
 * 自动定位设置             {@link #setAutoLocate(boolean)}
 * 创建人:   macroz
 * 创建时间: 2018/11/26 下午4:04
 * 修改人:   macroz
 * 修改时间: 2018/11/26 下午4:04
 * 修改备注:
 */
public class RecyclerAnimatorManager {

    private RecyclerView mRecyclerView;
    private boolean autoLocateEnable;
    private RecyclerItemAnimator animator;
    private StartSnapHelper mHelper;

    /**
     * @param recyclerView
     */
    public RecyclerAnimatorManager(@NonNull RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        mHelper = new StartSnapHelper();
        //添加 add 动画
        animator = new RecyclerItemAnimator();
        animator.setAddAnimator(new ScaleAddAnimator());
        mRecyclerView.setItemAnimator(animator);
    }

    /**
     * @param setAutoLocate RecyclerView 自动定位的开关
     */
    public void setAutoLocate(boolean setAutoLocate) {
        //避免重复添加
        if (mRecyclerView == null || setAutoLocate == autoLocateEnable) {
            return;
        }
        if (setAutoLocate) {
            //这里确保下listener==null 避免snapHelp 绑定View的时候 crash
            mRecyclerView.setOnFlingListener(null);
            mHelper.attachToRecyclerView(mRecyclerView);
            autoLocateEnable = true;
        } else {
            mHelper.attachToRecyclerView(null);
            autoLocateEnable = false;
        }
    }

    /**
     * 返回当前RecyclerView 是否在fling状态(仅用于设置自动定位 {@link #setAutoLocate(boolean)}后fling状态判断)
     *
     * @return
     */
    public boolean isFling() {
        return mHelper != null && mHelper.isFling();
    }

    /**
     * 设置 Add动画
     *
     * @param addAnimator
     */
    public void setAddAnimator(AbsItemAnimator addAnimator) {
        if (animator != null) {
            animator.setAddAnimator(addAnimator);
        }
    }

    /**
     * 设置 Remove 动画
     *
     * @param removeAnimator
     */
    public void setRemoveAnimator(AbsItemAnimator removeAnimator) {
        if (animator != null) {
            animator.setRemoveAnimator(removeAnimator);
        }
    }

    /**
     * 设置 Change动画
     *
     * @param changeAnimator
     */
    public void setChangeAnimator(AbsItemAnimator changeAnimator) {
        if (animator != null) {
            animator.setChangeAnimator(changeAnimator);
        }
    }

    /**
     * 设置 Move动画
     *
     * @param moveAnimator
     */
    public void setMoveAnimator(AbsItemAnimator moveAnimator) {
        if (animator != null) {
            animator.setMoveAnimator(moveAnimator);
        }
    }

//
//    void postAction(Action action) {
//        if (action == null) {
//            return;
//        }
//        AnimatorProcessor.getIns().enqueue(action);
//    }
//
//    /**
//     * 定位到指定位置
//     *
//     * @param pos
//     */
//    public void postLocateAction(int pos) {
//        postAction(new LocateAction(mRecyclerView, mHelper, pos));
//    }
//
//    /**
//     * pos 增加一个Item的动画
//     *
//     * @param pos
//     */
//    public void postAddAction(int pos, IListBean bean) {
//        postAction(new AddAction(mRecyclerView, null, bean, pos));
//    }
//
//    /**
//     * pos  删除一个Item动画 (注： 这里仅执行动画,数据管理交给外面)
//     *
//     * @param pos
//     */
//    public void postDeleteAction(int pos) {
//        postAction(new DeleteAction(mRecyclerView, null, null, pos));
//    }
//
//
//    void postLocateAction(FollowDataManager dataManager, IListBean bean) {
//        postAction(new LocateAction(mRecyclerView, dataManager, mHelper, bean));
//        NTLog.d(FollowControler.TAG, "RecyclerAnimatorManager  post a LocateAction   ");
//    }
//
//    void postLocateAction(FollowDataManager dataManager, IListBean bean, int delay) {
//        postAction(new LocateAction(mRecyclerView, dataManager, mHelper, bean, delay));
//        NTLog.d(FollowControler.TAG, "RecyclerAnimatorManager  post a LocateAction   ");
//    }
//
//    void postRecAddAction(FollowDataManager dataManager, IListBean bean) {
//        if (dataManager == null || dataManager.isFull()) {
//            //最多展示100条数据,之后不再添加新数据
//            return;
//        }
//        postAction(new RecAddAction(mRecyclerView, dataManager, bean, 0));
//        NTLog.d(FollowControler.TAG, "RecyclerAnimatorManager  post  an RecAddAction   ");
//    }
//
//    void postRecDeleteAction(FollowDataManager dataManager, IListBean bean) {
//        postAction(new RecDeleteAction(mRecyclerView, dataManager, bean));
//        NTLog.d(FollowControler.TAG, "RecyclerAnimatorManager  post   a RecDeleteAction   ");
//    }
//
//
    static abstract class Action {
//        protected RecyclerView mRecyclerView;
        protected long mRunTime;
//        protected FollowDataManager mDataManager;
//        protected IListBean mData;
        protected Runnable mCallback = new Runnable() {
            @Override
            public void run() {
                runAction();
            }
        };
//
//        public Action(RecyclerView recyclerView, FollowDataManager dataManager, IListBean bean) {
//            mRecyclerView = recyclerView;
//            mDataManager = dataManager;
//            this.mData = bean;
//            mRunTime = System.currentTimeMillis();
//        }
//
        public long getRunTime() {
            return mRunTime;
        }

        public Runnable getCallback() {
            return mCallback;
        }

        protected abstract void runAction();
    }
//
//
//    /**
//     * 推荐新用户的 Add 动画
//     */
//    private static class RecAddAction extends Action {
//
//
//        public RecAddAction(RecyclerView recyclerView, FollowDataManager dataManager, IListBean bean) {
//            this(recyclerView, dataManager, bean, 0);
//
//        }
//
//        public RecAddAction(RecyclerView recyclerView, FollowDataManager dataManager, IListBean bean, int delay) {
//            super(recyclerView, dataManager, bean);
//            mRunTime += delay;
//        }
//
//
//        @Override
//        protected void runAction() {
//            NTLog.d(FollowControler.TAG, "FollowAnimatorManager  run AddAction ");
//            if (mDataManager == null) {
//                return;
//            }
//            IListBean cache = mDataManager.getOneCache(mData);
//            int addPos = mDataManager.indexOfData(mData) + 1;
//            if (cache != null) {
//                NTLog.d(FollowControler.TAG, "FollowAnimatorManager  AddAction  insert cache data to " + addPos);
//                mDataManager.insertDataTo(addPos, cache);
//                if (mRecyclerView.getAdapter() instanceof BaseRecyclerViewAdapter) {
//                    BaseRecyclerViewAdapter adapter = (BaseRecyclerViewAdapter) mRecyclerView.getAdapter();
//                    adapter.insertItemAndNotify(addPos, cache);
//
//                    if (cache instanceof NewsItemBean.ReadAgent && mDataManager.getBinderCallback() instanceof FollowCardBinderCallBack) {
//                        FollowCardBinderCallBack callBack = (FollowCardBinderCallBack) mDataManager.getBinderCallback();
//                        NewsItemBean.ReadAgent itemBean = (NewsItemBean.ReadAgent) cache;
//                        NRGalaxyEvents.sendUserRecomPopEvent(callBack.getGFrom((NewsItemBean.ReadAgent) mData),
//                                CommonGalaxy.getGalaxyColumnName(),
//                                addPos,
//                                callBack.getFollowId(itemBean),
//                                String.valueOf(callBack.getUserType(itemBean)),
//                                callBack.getTid(itemBean),
//                                callBack.getEname(itemBean));
//                    }
//                }
//            }
//        }
//    }
//
//    /**
//     * pos 位置插入一条新数据 (数据封装在Adapter,这里不单独处理)
//     */
//    private static class AddAction extends Action {
//        int pos = -1;
//
//        public AddAction(RecyclerView recyclerView, FollowDataManager dataManager, IListBean bean, int pos) {
//            super(recyclerView, dataManager, bean);
//            this.pos = pos;
//        }
//
//        @Override
//        protected void runAction() {
//            /**
//             * 当前位置 插入新数据
//             */
//            if (mRecyclerView.getAdapter() instanceof BaseRecyclerViewAdapter) {
//                BaseRecyclerViewAdapter adapter = (BaseRecyclerViewAdapter) mRecyclerView.getAdapter();
//                adapter.insertItemAndNotify(pos, mData);
//            }
//        }
//    }
//
//
//    /**
//     * 横滑推荐新用户  删除动画
//     */
//    private static class RecDeleteAction extends Action {
//
//        public RecDeleteAction(RecyclerView recyclerView, FollowDataManager dataManager, IListBean bean) {
//            super(recyclerView, dataManager, bean);
//        }
//
//        @Override
//        protected void runAction() {
//            int pos = mDataManager.indexOfData(mData);
//            if (mDataManager.deleteDataFrom(pos) != null) {
//                NTLog.d(FollowControler.TAG, "RecyclerAnimatorManager run Delete Action at " + pos);
//                if (mRecyclerView.getAdapter() instanceof BaseRecyclerViewAdapter) {
//                    BaseRecyclerViewAdapter adapter = (BaseRecyclerViewAdapter) mRecyclerView.getAdapter();
//                    adapter.removeItemAndNotify(pos);
//                }
//            }
//        }
//    }
//
//
//    /**
//     * 删除pos 位置item (数据封装在Adapter,这里不单独处理)
//     */
//    private static class DeleteAction extends Action {
//        int pos = -1;
//
//        public DeleteAction(RecyclerView recyclerView, FollowDataManager dataManager, IListBean bean, int pos) {
//            super(recyclerView, dataManager, bean);
//            this.pos = pos;
//        }
//
//        @Override
//        protected void runAction() {
//            if (mRecyclerView.getAdapter() instanceof BaseRecyclerViewAdapter) {
//                BaseRecyclerViewAdapter adapter = (BaseRecyclerViewAdapter) mRecyclerView.getAdapter();
//                adapter.removeItemAndNotify(pos);
//            }
//        }
//    }
//
//    /**
//     * 定位操作
//     */
//    private static class LocateAction extends Action {
//        private StartSnapHelper mScrollHelper;
//        private int pos;
//
//        LocateAction(RecyclerView recyclerView, StartSnapHelper scrollHelper, int pos) {
//            super(recyclerView, null, null);
//            mScrollHelper = scrollHelper;
//            this.pos = pos;
//        }
//
//        LocateAction(RecyclerView recyclerView, FollowDataManager dataManager, StartSnapHelper scrollHelper, IListBean bean) {
//            this(recyclerView, dataManager, scrollHelper, bean, 0);
//        }
//
//
//        LocateAction(RecyclerView recyclerView, FollowDataManager dataManager, StartSnapHelper scrollHelper, IListBean bean, int delay) {
//            super(recyclerView, dataManager, bean);
//            mScrollHelper = scrollHelper;
//            mRunTime += delay;
//            pos = mDataManager.indexOfData(bean);
//        }
//
//        @Override
//        protected void runAction() {
//            if (mRecyclerView == null) {
//                return;
//            }
//            if (mRecyclerView.getLayoutManager() instanceof LinearLayoutManager) {
//                LinearLayoutManager lm = (LinearLayoutManager) mRecyclerView.getLayoutManager();
//                if (pos < 0 || pos >= lm.getItemCount()) {
//                    return;
//                }
//                mScrollHelper.LocateToPosition(pos);
//                NTLog.d(FollowControler.TAG, "RecyclerAnimatorManager run Locate Action at " + pos);
//            }
//        }
//    }
}
