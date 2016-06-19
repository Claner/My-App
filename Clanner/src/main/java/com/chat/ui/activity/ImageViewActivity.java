package com.chat.ui.activity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chat.entity.FolderBean;
import com.chat.ui.view.ListImageDirPopupWindow;
import com.chat.utils.adapter.ImageAdapter;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Clanner on 2016/6/4.
 */
public class ImageViewActivity extends AppCompatActivity {

    private static final int DATA_LOADED = 0x110;
    //控件
    private GridView mGridView;
    private RelativeLayout mBottomLy;
    private TextView mDirName;
    private TextView mDirCount;
    private ProgressDialog mProgressDialog;
    private ImageAdapter mImageAdapter;
    private ListImageDirPopupWindow mDirPopupWindow;

    //
    private List<String> mImgs;
    private List<FolderBean> mFolderBeans = new ArrayList<FolderBean>();
    private File mCurrentDir;
    private int mMaxCount;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == DATA_LOADED) {
                mProgressDialog.dismiss();
                //绑定数据到View中
                data2View();
                initDirPopWindow();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageview);

        initView();
        initDatas();
        initEvent();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        mGridView = (GridView) findViewById(R.id.gridView);
        mBottomLy = (RelativeLayout) findViewById(R.id.bottom_ly);
        mDirName = (TextView) findViewById(R.id.dir_name);
        mDirCount = (TextView) findViewById(R.id.dir_count);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("图片加载器");
    }

    private void initDirPopWindow() {
        mDirPopupWindow = new ListImageDirPopupWindow(this, mFolderBeans);
        mDirPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lightOn();
            }
        });

        mDirPopupWindow.setOnDirSelectedLinstener(new ListImageDirPopupWindow.OnDirSelectedLinstener() {
            @Override
            public void onSelected(FolderBean folderBean) {
                mCurrentDir = new File(folderBean.getDir());
                mImgs = Arrays.asList(mCurrentDir.list(new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String filename) {
                        if (filename.endsWith(".jpg") || filename.endsWith(".jpeg") || filename.endsWith(".png")) {
                            return true;
                        }
                        return false;
                    }
                }));
                mImageAdapter = new ImageAdapter(ImageViewActivity.this, mImgs, mCurrentDir.getAbsolutePath());
                mGridView.setAdapter(mImageAdapter);
                mDirCount.setText(mImgs.size() + "");
                mDirName.setText(folderBean.getName());
                mDirPopupWindow.dismiss();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 内容区域变亮
     */
    private void lightOn() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 1.0f;
        getWindow().setAttributes(lp);
    }

    /**
     * 内容区域变暗
     */
    private void lightOff() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = .3f;
        getWindow().setAttributes(lp);
    }

    protected void data2View() {
        if (mCurrentDir == null) {
            Toast.makeText(ImageViewActivity.this, "未扫描到任何图片", Toast.LENGTH_SHORT).show();
            return;
        }
        mImgs = Arrays.asList(mCurrentDir.list());
        mImageAdapter = new ImageAdapter(this, mImgs, mCurrentDir.getAbsolutePath());
        mGridView.setAdapter(mImageAdapter);

        mDirCount.setText(mMaxCount + "");
        mDirName.setText(mCurrentDir.getName());
    }

    /**
     * 利用ContentProvider扫描手机中的所有图片
     */
    private void initDatas() {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(ImageViewActivity.this, "当前存储卡不可用", Toast.LENGTH_SHORT).show();
            return;
        }
        //显示进度条
        mProgressDialog = ProgressDialog.show(this, null, "正在加载...");

        new Thread() {
            @Override
            public void run() {
                //如果要查询图片，Uri地址为：MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                Uri mImgUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver cr = ImageViewActivity.this.getContentResolver();

                /**
                 *只查询jpeg和png的图片
                 * MediaStore.Images.Media.DATE_MODIFIED
                 * 按修改日期排序
                 */
                Cursor cursor = cr.query(mImgUri, null, MediaStore.Images.Media.MIME_TYPE + "= ? or " + MediaStore.Images.Media.MIME_TYPE + "= ?"
                        , new String[]{"image/jpeg", "image/png"}, MediaStore.Images.Media.DATE_MODIFIED);

                Set<String> mDirPaths = new HashSet<String>();

                while (cursor.moveToNext()) {
                    String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                    File parentFile = new File(path).getParentFile();
                    if (parentFile == null) {
                        continue;
                    }
                    String dirPath = parentFile.getAbsolutePath();
                    FolderBean folderBean = null;

                    if (mDirPaths.contains(dirPath)) {
                        continue;
                    } else {
                        mDirPaths.add(dirPath);
                        folderBean = new FolderBean();
                        folderBean.setDir(dirPath);
                        folderBean.setFirstImagePath(path);
                    }
                    if (parentFile.list() == null) {
                        continue;
                    }

                    int picSize = parentFile.list(new FilenameFilter() {
                        @Override
                        public boolean accept(File dir, String filename) {
                            if (filename.endsWith(".jpg") || filename.endsWith(".jpeg") || filename.endsWith(".png")) {
                                return true;
                            }
                            return false;
                        }
                    }).length;
                    folderBean.setCount(picSize);
                    mFolderBeans.add(folderBean);

                    if (picSize > mMaxCount) {
                        mMaxCount = picSize;
                        mCurrentDir = parentFile;
                    }
                }
                cursor.close();
                //扫描完成，释放临时变量的内存
                mDirPaths = null;
                //通知Handler扫描图片完成
                mHandler.sendEmptyMessage(DATA_LOADED);
            }
        }.start();
    }

    private void initEvent() {
        mBottomLy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDirPopupWindow.setAnimationStyle(R.style.dir_popupwindow_anim);
                mDirPopupWindow.showAsDropDown(mBottomLy, 0, 0);
                lightOff();
            }
        });

        /**
         * ContentResolver query的参数详解
         *
         * 1：uri，rui是什么呢？好吧，上面我们提到了Android提供内容的叫Provider，那么在Android中怎么区分各个Provider？有提供联系人的，有提供图片的等等。
         * 所以就需要有一个唯一的标识来标识这个Provider，Uri就是这个标识，
         * android.provider.ContactsContract.Contacts.CONTENT_URI就是提供联系人的内容提供者，可惜这个内容提供者提供的数据很少。
         *
         * 2：projection
         * 这个参数告诉Provider要返回的内容（列Column），比如Contacts Provider提供了联系人的ID和联系人的NAME等内容，
         * 用null表示返回Provider的所有内容（列Column）。
         *
         * 3：selection，设置条件，相当于SQL语句中的where。null表示不进行筛选。
         *
         * 4：selectionArgs，这个参数是要配合第三个参数使用的，如果你在第三个参数里面有？，那么你在selectionArgs写的数据就会替换掉？，
         *
         * 5：sortOrder，按照什么进行排序，相当于SQL语句中的Order by。如果想要结果按照ID的降序排列：
         */
    }

}
