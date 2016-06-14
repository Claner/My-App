package com.chat.ui.view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chat.entity.FolderBean;
import com.chat.ui.activity.R;
import com.chat.utils.ImageLoader;

import java.util.List;

/**
 * Created by Clanner on 2016/6/5.
 */
public class ListImageDirPopupWindow extends PopupWindow {
    public OnDirSelectedLinstener mLinstener;
    private int mWidth;
    private int mHeight;
    private View mConvertView;
    private ListView mListView;
    private List<FolderBean> mDatas;

    public ListImageDirPopupWindow(Context context, List<FolderBean> datas) {
        calWidthAndHeight(context);

        mConvertView = LayoutInflater.from(context).inflate(R.layout.popup_window, null);
        mDatas = datas;
        //设置布局
        setContentView(mConvertView);
        //设置宽度
        setWidth(mWidth);
        //设置高度
        setHeight(mHeight);

        //设置可触摸
        setFocusable(true);
        //设置可点击
        setTouchable(true);
        //设置可点击外部
        setOutsideTouchable(true);
        //设置可点击消失
        setBackgroundDrawable(new BitmapDrawable());

        setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    dismiss();
                    return true;
                }
                return false;
            }
        });

        initViews(context);
        initEvent();
    }

    public void setOnDirSelectedLinstener(OnDirSelectedLinstener mLinstener) {
        this.mLinstener = mLinstener;
    }

    private void initViews(Context context) {
        mListView = (ListView) mConvertView.findViewById(R.id.list_dir);
        mListView.setAdapter(new ListDirAdapter(context, mDatas));
    }

    private void initEvent() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (mLinstener != null) {
                    mLinstener.onSelected(mDatas.get(position));
                }
            }
        });
    }

    /**
     * 计算popupwindow的宽度和高度
     */
    private void calWidthAndHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);

        mWidth = outMetrics.widthPixels;
        mHeight = (int) (outMetrics.heightPixels * 0.7);
    }

    public interface OnDirSelectedLinstener {
        void onSelected(FolderBean folderBean);
    }

    private class ListDirAdapter extends ArrayAdapter<FolderBean> {

        private LayoutInflater mInflater;

        public ListDirAdapter(Context context, List<FolderBean> objects) {
            super(context, 0, objects);

            mInflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.item_popup, parent, false);
                viewHolder.mImg = (ImageView) convertView.findViewById(R.id.dir_item_image);
                viewHolder.mDirName = (TextView) convertView.findViewById(R.id.dir_item_name);
                viewHolder.mDirCount = (TextView) convertView.findViewById(R.id.dir_item_count);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            FolderBean bean = getItem(position);
            //重置
            viewHolder.mImg.setImageResource(R.drawable.pictures_no);
            Log.d("Hello", bean.getFirstImagePath());
            ImageLoader.getInstance(3, ImageLoader.Type.LIFO).loadaImage(bean.getFirstImagePath(), viewHolder.mImg);
            viewHolder.mDirName.setText(bean.getName() + "");
            viewHolder.mDirCount.setText(bean.getCount() + "");
            return convertView;
        }

        private class ViewHolder {
            ImageView mImg;
            TextView mDirName;
            TextView mDirCount;
        }
    }
}
