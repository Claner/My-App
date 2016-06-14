package com.chat.ui.fragment;

import android.app.ProgressDialog;
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
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chat.entity.HeaderHelper;
import com.chat.entity.UploadAvatarHelper;
import com.chat.ui.activity.R;
import com.chat.ui.activity.SettingActivity;
import com.chat.utils.MyOkHttpManager;
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
 * Created by Clanner on 2016/5/26.
 */
public class FragmentFour extends BaseFragment implements View.OnClickListener {

    private static final int IMAGE_REQUEST_CODE = 0;//相册
    private static final int CAMERA_REQUEST_CODE = 1;//相机
    private static final int RESIZE_REQUEST_CODE = 2;
    private final String IMAGE_TYPE = "image/*";
    private String cookieString;
    private String path;
    private File uploadFile = null;
    private String baseUrl = "http://tpwhcm.com/TDetc/";
    private String dataUrl = "http://tpwhcm.com/Home/User/getBaseInfo";
    private String url = "http://tpwhcm.com/TDetc/Home/User/uploadAvatar.html";
    private String[] item_list = {"本地相册", "拍照"};
    private Gson gson;
    private Handler handler;

    //控件
    private ImageView avatar;
    private TextView tv_name;
    private TextView tv_phone;
    //    private ProgressBar progressBar;
    private ProgressDialog progressDialog;

    public static FragmentFour newInstance() {
        return new FragmentFour();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_four;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        avatar = (ImageView) view.findViewById(R.id.image_avatar);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_phone = (TextView) view.findViewById(R.id.tv_phone);
//        progressBar = (ProgressBar) view.findViewById(R.id.four_progressbar);
        avatar.setOnClickListener(this);
        view.findViewById(R.id.btn_setting).setOnClickListener(this);
        handler = new Handler();
        gson = new Gson();
        getUserData();
    }

    /**
     * 获取用户数据
     */
    private void getUserData() {

        MyOkHttpManager.getInstance().setContext(getHoldingActivity().getApplicationContext());
        MyOkHttpManager.Get(dataUrl, new MyOkHttpManager.ResultCallback() {
            @Override
            public void onError(IOException e) {
                Log.d("Hello", "失败");
            }

            @Override
            public void onResponse(final Response response) throws IOException {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        HeaderHelper helper = null;
                        try {
                            helper = gson.fromJson(response.body().string(), HeaderHelper.class);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        int code = helper.getCode();
                        if (code == 20000) {
                            String path = baseUrl + helper.getResponse().getHead_path();
                            String name = helper.getResponse().getName();
                            String phone = helper.getResponse().getPhone();
                            Picasso.with(getHoldingActivity().getApplicationContext()).load(path).into(avatar);
                            tv_name.setText(name);
                            tv_phone.setText(phone);
                        }
                    }
                });
            }
        });

//        cookieString = getCookie(getHoldingActivity().getApplicationContext(), "cookie");
//        Log.d("Hello", cookieString);
//        OkHttpClient client = MyOkHttpManager.getInstance().getClient(getHoldingActivity().getApplicationContext());
//        Request request = new Request.Builder().url(dataUrl).header("cookie", cookieString).build();
//        Call call = client.newCall(request);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.d("Hello", "失败");
//            }
//
//            @Override
//            public void onResponse(Call call, final Response response) throws IOException {
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        HeaderHelper helper = null;
//                        try {
//                            helper = gson.fromJson(response.body().string(), HeaderHelper.class);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        int code = helper.getCode();
//                        if (code == 20000) {
//                            String path = baseUrl + helper.getResponse().getHead_path();
//                            String name = helper.getResponse().getName();
//                            String phone = helper.getResponse().getPhone();
//                            Picasso.with(getHoldingActivity().getApplicationContext()).load(path).into(avatar);
//                            tv_name.setText(name);
//                            tv_phone.setText(phone);
//                        }
//                    }
//                });
//            }
//        });
    }

    /**
     * 获取Cookie
     */
    public String getCookie(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences("cookie", Context.MODE_PRIVATE);
        return preferences.getString(key, "");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_avatar:
                showDialog();
                break;
            case R.id.btn_setting:
                Setting();
                break;
        }
    }

    private void Setting() {
//        addFragment(SettingFragment.newInstance());
        Intent intent = new Intent(getHoldingActivity().getApplicationContext(), SettingActivity.class);
        startActivity(intent);
        getHoldingActivity().finish();
    }

    /**
     * 显示对话框
     */
    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getHoldingActivity());
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
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != getHoldingActivity().RESULT_OK) {
            return;
        } else {
            switch (requestCode) {
                case IMAGE_REQUEST_CODE:
                    resizeImage(data.getData(), IMAGE_REQUEST_CODE);
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
                    resizeImage(getImageUri(), CAMERA_REQUEST_CODE);
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

    private Uri getImageUri() {
        String filePath = Environment.getExternalStorageDirectory().getPath();
        filePath = filePath + "/" + "avatar.jpg";
        return Uri.fromFile(new File(filePath));
    }

    /**
     * 裁剪图片
     *
     * @param uri
     */
    public void resizeImage(Uri uri, int i) {
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

        if (i == IMAGE_REQUEST_CODE) {
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor mCursor = getHoldingActivity().getApplicationContext().getContentResolver().query(uri, proj, null, null, null);
            int col_index = 0;
            if (mCursor != null) {
                col_index = mCursor.getColumnIndex(MediaStore.Images.Media.DATA);
            }
            mCursor.moveToFirst();
            String filePath = mCursor.getString(col_index);
            uploadFile = new File(filePath);
            mCursor.close();
        } else if (i == CAMERA_REQUEST_CODE) {
            String filePath_craema = intent.getData().getPath();
            uploadFile = new File(filePath_craema);
        }

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
            avatar.setImageBitmap(photo);
            PostImage();
        }
    }

    /**
     * 上传头像
     */
    private void PostImage() {
        handler.post(new Runnable() {
            @Override
            public void run() {
//                progressBar.setVisibility(View.VISIBLE);
                progressDialog = ProgressDialog.show(getHoldingActivity(), null, "正在上传头像...");
            }
        });
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

        cookieString = getCookie(getHoldingActivity().getApplicationContext(), "cookie");
        Log.d("cookieString", cookieString);
        RequestBody fileBody = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("image", "header.jpg", RequestBody.create(null, uploadFile)).build();//new File(IMAGE_FILE_NAME)
        Request request = new Request.Builder().url(url).header("cookie", cookieString).post(fileBody).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("Hello", "失败");
                handler.post(new Runnable() {
                    @Override
                    public void run() {
//                        progressBar.setVisibility(View.GONE);
                    }
                });
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
                                Toast.makeText(getHoldingActivity().getApplicationContext(), "上传成功", Toast.LENGTH_SHORT).show();
                            }
//                            progressBar.setVisibility(View.GONE);
                            progressDialog.dismiss();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}
