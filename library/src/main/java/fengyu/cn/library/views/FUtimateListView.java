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
 * Created by fys on 2015/7/17. FysXListView ��չ�� ������listview ���ݸ���״̬����ʾ��
 */
public class FUtimateListView<T> extends FrameLayout {

    // ����FysXListView
    private FysXListView inSideListView;
    // ״ָ̬ʾ��
    private InitViewController statusiIdicatorView;
    // ������View
    private View rootView;
    private BaseAdapter adapter;
    private static final int LoadMoreSize = 15;
    // ����Դ
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
     * Ϊ�ڲ����е�ListView set adapter ���ı� ָʾ��״̬
     *
     * @param adapter
     */
    public void setAdapter(BaseAdapter adapter) {

        if (adapter != null) {
            if (adapter.getCount() == 0) {
                statusiIdicatorView.setState(InitViewController.NODATA);
                // ����Footer
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
     * �������� NotifyDataSetChanged �Լ�������� tempData ���ݴ�С���ж�listview�ܷ���Լ������ظ���
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
     * ����ˢ�� NotifyDataSetChanged
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
     * ��ȡ����Դ
     *
     * @return data;
     */
    public List<T> getDataSource() {
        return data;
    }

    /**
     * ��������Դ
     *
     * @param data
     */
    public void setDataSouce(List<T> data) {
        this.data = data;
    }

    /**
     * ��ȡ�ڲ����е�FysXListView
     *
     * @return
     */
    public FysXListView getListView() {
        return inSideListView;
    }

    /**
     * ǿ�Ƹ���ָʾ��״̬
     * status��
     * InitViewController.OK        ���ݼ������
     * InitViewController.NODATA    û������
     * InitViewController.NETERROR  �������
     * InitViewController.LOADING   ������
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
     * �����ܷ��������ظ���
     *
     * @param isEnable
     */
    public void setPullLoadEnable(boolean isEnable) {
        inSideListView.setPullLoadEnable(isEnable);
    }

    /**
     * �����ܷ�����ˢ��
     *
     * @param isEnable
     */
    public void setPullRefreshEnable(boolean isEnable) {
        inSideListView.setPullRefreshEnable(isEnable);
    }
}
