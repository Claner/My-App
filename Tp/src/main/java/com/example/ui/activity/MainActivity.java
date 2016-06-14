package com.example.ui.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ui.activity.com.example.entity.UploadAvatarHelper;
import com.example.ui.activity.com.example.entity.UserDataHelper;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Clanner on 2016/5/21.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String IMAGE_FILE_NAME = "header.jpg";
    private static final int IMAGE_REQUEST_CODE = 0;//相册
    private static final int CAMERA_REQUEST_CODE = 1;//相机
    private static final int RESIZE_REQUEST_CODE = 2;
    private final String IMAGE_TYPE = "image/*";
    private String[] item_list = {"本地相册", "拍照"};

    private String baseUrl = "http://tpwhcm.com/TDetc/";
    private String url = "http://tpwhcm.com/TDetc/Home/User/uploadAvatar.html";
    private String dataUrl = "http://tpwhcm.com/Home/User/getBaseInfo";
    private String cookieString;
    private File uploadFile = null;
    private Gson gson;
    private Handler handler;
    private String path;

    //控件
    private ImageView image;
    private Button postImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        getUserData();
    }

    private void initView() {
        image = (ImageView) findViewById(R.id.image_user);
        postImage = (Button) findViewById(R.id.btn_postimage);
        gson = new Gson();
        handler = new Handler(Looper.myLooper());
        image.setOnClickListener(this);
        postImage.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_user:
                showDialog();
                break;
            case R.id.btn_postimage:
                PostImage();
                break;
        }
    }

    /**
     * 上传头像
     */
    private void PostImage() {
        OkHttpClient client = new OkHttpClient.Builder().cookieJar(new CookieJar() {
            private final HashMap<HttpUrl, List<Cookie>> cookieStore = new HashMap<>();

            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                cookieStore.put(url, cookies);
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {
                List<Cookie> cookies = cookieStore.get(url.host());
                return cookies != null ? cookies : new ArrayList<Cookie>();
            }
        }).build();

        cookieString = getCookie(getApplicationContext(), "cookie");
        Log.d("cookieString", cookieString);
        if (uploadFile != null) {
            RequestBody fileBody = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("image", "header.jpg", RequestBody.create(null, uploadFile)).build();//new File(IMAGE_FILE_NAME)
            Request request = new Request.Builder().url(url).header("cookie", cookieString).post(fileBody).build();
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d("Hello", "失败");
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                UploadAvatarHelper helper = gson.fromJson(response.body().string(), UploadAvatarHelper.class);
                                path = helper.getResponse();
                                Log.d("path", path);
                                int code = helper.getCode();
                                if (code == 20000) {
                                    Toast.makeText(MainActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            });
        } else {
            Toast.makeText(MainActivity.this, "请先设置头像", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 显示对话框
     */
    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(item_list, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                switch (which) {
                    case 0:
                        getImageFromAlbum();
                        break;
                    case 1:
                        getImageFromCamera();
                        break;
                }
            }
        });
        AlertDialog dialog = builder.create();//获取dialog
        dialog.show();//显示对话框
    }

    /**
     * 从相册选择头像
     */
    private void getImageFromAlbum() {
        Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
        getAlbum.setType(IMAGE_TYPE);
        startActivityForResult(getAlbum, IMAGE_REQUEST_CODE);
    }

    /**
     * 通过相机拍照获取头像
     */
    private void getImageFromCamera() {
        if (isSdcardExisting()) {
            Intent cameraIntent = new Intent("android.media.action.IMAGE_CAPTURE");
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, getImageUri());
            cameraIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
            startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
        } else {
            Toast.makeText(MainActivity.this, "请插入sd卡", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        } else {
            switch (requestCode) {
                case IMAGE_REQUEST_CODE:
                    resizeImage(data.getData());
//                    try {
//                        Uri imgUri = data.getData();
//                        Bitmap targetBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imgUri);
//                        int imgWidth = image.getMeasuredWidth();
//                        int imgHeight = image.getMeasuredHeight();
//                        Bitmap bitmap = Bitmap.createScaledBitmap(targetBitmap, imgWidth, imgHeight, false);
//                        String[] proj = {MediaStore.Images.Media.DATA};
//                        Cursor mCursor = getContentResolver().query(imgUri, proj, null, null, null);
//                        int col_index = 0;
//                        if (mCursor != null) {
//                            col_index = mCursor.getColumnIndex(MediaStore.Images.Media.DATA);
//                        }
//                        mCursor.moveToFirst();
//                        String filePath = mCursor.getString(col_index);
//                        image.setImageBitmap(bitmap);
//                        uploadFile = new File(filePath);
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                    break;
                case CAMERA_REQUEST_CODE:
                    if (isSdcardExisting()) {
                        resizeImage(getImageUri());
                    } else {
                        Toast.makeText(MainActivity.this, "未找到存储卡，无法存储照片！",
                                Toast.LENGTH_LONG).show();
                    }
                    break;
                case RESIZE_REQUEST_CODE:
                    if (data != null) {
                        showResizeImage(data);
                    }
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 判断sd卡是否存在
     *
     * @return
     */
    private boolean isSdcardExisting() {
        final String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            Log.d("Hello", "yes");
            return true;
        } else {
            Log.d("Hello", "no");
            return false;
        }
    }

    /**
     * 裁剪图片
     *
     * @param uri
     */
    public void resizeImage(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        Log.d("Uri", String.valueOf(uri));
        intent.setDataAndType(uri, IMAGE_TYPE);
        intent.putExtra("crop", "true");
        //宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        //裁剪图片的宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor mCursor = getContentResolver().query(uri, proj, null, null, null);
        int col_index = 0;
        if (mCursor != null) {
            col_index = mCursor.getColumnIndex(MediaStore.Images.Media.DATA);
        }
        mCursor.moveToFirst();
        String filePath = mCursor.getString(col_index);
        uploadFile = new File(filePath);
        //       uploadFile = new File(IMAGE_FILE_NAME);
        startActivityForResult(intent, RESIZE_REQUEST_CODE);
    }

    /**
     * 显示裁剪后的图片
     *
     * @param data
     */
    public void showResizeImage(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            Log.d("extras", String.valueOf(extras));
//            Drawable drawable = new BitmapDrawable(photo);
//            Log.d("Drawable", String.valueOf(drawable));
//            image.setImageDrawable(drawable);
            image.setImageBitmap(photo);

        }
    }

    private Uri getImageUri() {
        String filePath = Environment.getExternalStorageDirectory().getPath();
        filePath = filePath + "/" + IMAGE_FILE_NAME;
        return Uri.fromFile(new File(filePath));
    }

    /**
     * 获取Cookie
     */
    public String getCookie(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences("cookie", MODE_PRIVATE);
        return preferences.getString(key, "");
    }

    private void getUserData() {
        cookieString = getCookie(getApplicationContext(), "cookie");
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(dataUrl).header("cookie", cookieString).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("Hello", "失败");
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        UserDataHelper helper = null;
                        try {
                            helper = gson.fromJson(response.body().string(), UserDataHelper.class);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        int code = helper.getCode();
                        if (code == 20000) {
                            String path = baseUrl + helper.getResponse().getHead_path();
                            Picasso.with(MainActivity.this).load(path).into(image);
                        }
                    }
                });
            }
        });
    }

}
