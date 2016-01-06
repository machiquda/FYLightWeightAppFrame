package fengyu.cn.library.views;

import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 *ListView Adapter 基类
 */
abstract public class BaseListAdapter<T> extends BaseAdapter implements
        View.OnClickListener {


    protected LayoutInflater mInflater;

    Context mContext;
    //dataSource数据源
    List<T> mList;
    public static Application mAppliacation;

    public BaseListAdapter(Context context, List<T> list) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        mList = list;


    }

    /**
     * 返回绑定的数据
     * @return  List &lt T &gt dataSource
     */
    public List<T> getData() {
        return mList;
    }


    /**
     *批量添加
     * @param  list   List &lt T &gt
     */
    public void addData(List<T> list) {
        if (list != null) {
            if (mList == null) {
                mList = new ArrayList<T>();
            }
            mList.addAll(list);
        }
    }

    public void remove(int position) {
        if (mList != null && mList.size() > position) {
            mList.remove(position);
        }
    }


    /**
     * 单条添加
     * @param data
     * @param position
     */
    public void addDataAt(T data, int position) {
        mList.add(position, data);
    }

    /**
     * 批量添加
     * @param dataList
     * @param position
     */
    public void addDataAt(List<T> dataList, int position) {
        mList.addAll(position, dataList);
    }

    public void clear() {
        if (mList != null) {
            mList.clear();
        }
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public T getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    abstract public View getView(int position, View convertView,
                                 ViewGroup parent);

    @Override
    public void onClick(View v) {

    }

}
