package fengyu.cn.library.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;


import java.util.List;

import fengyu.cn.library.R;


/**
 * Created by fys on 2015/7/17. FysXListView 扩展版 内置了listview 数据各种状态的提示；
 */
public class FUtimateListView<T> extends FrameLayout {

    // 内置FysXListView
    private FysXListView inSideListView;
    // 状态指示器
    private InitViewController statusiIdicatorView;
    // 跟布局View
    private View rootView;
    private BaseAdapter adapter;
    private static final int LoadMoreSize = 15;
    // 数据源
    private List<T> data;

    public FUtimateListView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rootView = inflater.inflate(R.layout.futimatelist_layout, this);
        inSideListView = (FysXListView) rootView
                .findViewById(R.id.inside_fyslistview);
        statusiIdicatorView = (InitViewController) rootView
                .findViewById(R.id.init_view_controller);
        statusiIdicatorView.setState(InitViewController.LOADING);
    }

    /**
     * 为内部持有的ListView set adapter 并改变 指示器状态
     *
     * @param adapter
     */
    public void setAdapter(BaseAdapter adapter) {

        if (adapter != null) {
            if (adapter.getCount() == 0) {
                statusiIdicatorView.setState(InitViewController.NODATA);
                // 隐藏Footer
                inSideListView.setHideFooter();
            } else if (adapter.getCount() > 0) {
                if (adapter.getCount() < 8)
                    inSideListView.setHideFooter();
                statusiIdicatorView.setState(InitViewController.OK);
            }
            this.adapter = adapter;
            inSideListView.setAdapter(adapter);
        }
    }

    /**
     * 上拉加载 NotifyDataSetChanged 对加入的数据 tempData 根据大小对判断listview能否可以继续加载更多
     *
     * @param tempData
     */
    public void loadMoreNotifyDataSetChanged(List<T> tempData) {

        if (adapter != null) {
            if (tempData != null) {

                if (tempData.size() > 0) {
                    data.addAll(tempData);
                }
                if (tempData.size() < LoadMoreSize) {
                    inSideListView.stopLoadMore(FysXListViewFooter.STATE_LOADFULL);
                } else {
                    inSideListView.stopLoadMore(FysXListViewFooter.STATE_NORMAL);
                }
                adapter.notifyDataSetChanged();
            }

        }else{
            throw new IllegalArgumentException("set adapter before call loadMoreNotifyDataSetChanged");
        }
    }

    /**
     * 下拉刷新 NotifyDataSetChanged
     *
     * @param data
     */
    public void refreshNotifyDataSetChanged(List<T> data) {

        if (data != null) {
            if (data.size() > 0) {
                this.data.clear();
                this.data.addAll(data);
                this.adapter.notifyDataSetChanged();
            }

        }

        inSideListView.stopRefresh();
    }

    public void notifyDataSetChanged() {
        adapter.notifyDataSetChanged();
    }

    /**
     * 获取数据源
     *
     * @return data;
     */
    public List<T> getDataSource() {
        return data;
    }

    /**
     * 设置数据源
     *
     * @param data
     */
    public void setDataSouce(List<T> data) {
        this.data = data;
    }

    /**
     * 获取内部持有的FysXListView
     *
     * @return
     */
    public FysXListView getListView() {
        return inSideListView;
    }

    /**
     * 强制更新指示器状态
     * status：
     * InitViewController.OK        数据加在完成
     * InitViewController.NODATA    没有数据
     * InitViewController.NETERROR  网络错误
     * InitViewController.LOADING   加在中
     *
     * @param status
     */
    public void changeIndicaterStatus(int status) {

        switch (status) {
            case InitViewController.OK:
                statusiIdicatorView.setState(InitViewController.OK);
                break;
            case InitViewController.NODATA:
                statusiIdicatorView.setState(InitViewController.NODATA);
                break;
            case InitViewController.NETERROR:
                statusiIdicatorView.setState(InitViewController.NETERROR);
                break;
            case InitViewController.LOADING:
                statusiIdicatorView.setState(InitViewController.LOADING);
                break;
        }

    }

    public void setIndicaterMode(int Mode) {

        statusiIdicatorView.setIndicaterMode(Mode);

    }

    /**
     * 设置能否上拉加载更多
     *
     * @param isEnable
     */
    public void setPullLoadEnable(boolean isEnable) {
        inSideListView.setPullLoadEnable(isEnable);
    }

    /**
     * 设置能否下拉刷新
     *
     * @param isEnable
     */
    public void setPullRefreshEnable(boolean isEnable) {
        inSideListView.setPullRefreshEnable(isEnable);
    }
}
