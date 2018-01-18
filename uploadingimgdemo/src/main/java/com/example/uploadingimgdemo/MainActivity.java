package com.example.uploadingimgdemo;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class MainActivity extends AppCompatActivity {
    private SimpleDraweeView simple_drawee_view;
    private PopupWindow window;
    private String path;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_main);
        simple_drawee_view = (SimpleDraweeView) findViewById(R.id.simple_drawee_view);
        path = Environment.getExternalStorageDirectory() + "/head.jpg";

        Observable<UserBean> userInfo = userBean("3381");
        userInfo
                //需要在io子线程联网
                .subscribeOn(Schedulers.io())
                //需要在主线程更新UI
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<UserBean>() {
                    @Override
                    public void accept(UserBean nicknameBean) throws Exception {
                        UserBean.DataBean data = nicknameBean.getData();
                        uri = Uri.parse(data.getIcon());
                        simple_drawee_view.setImageURI(uri);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("MainActivity", throwable.toString());
                    }
                });

        simple_drawee_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopwindow();
            }
        });
    }

    /**
     * 请求用户信息
     *
     * @param uid
     * @return
     */
    public Observable<UserBean> userBean(String uid) {
        Log.e("RxJava", "LoginModel");
        RetiofitVpi iRetiofitVip = RetiofitUtils.getInstance().create(RetiofitVpi.class);
        return iRetiofitVip.userBean(uid);
    }

    /**
     * 文件
     */
    public void uploadFiles(File file) {
        //代理模式生成对应server的实例化对象
        RetiofitVpi server = RetiofitUtils.getInstance().create(RetiofitVpi.class);
        //创建RequwstBody对象
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        //使用RxJava方式调度任务并监听
        server.uploadFile("3381", requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<FileBean>() {
                    @Override
                    public void accept(FileBean nicknameBean) throws Exception {
                        if (nicknameBean != null) {
                            Log.e("MainActivity", nicknameBean.getMsg());
                        } else {
                            Log.e("MainActivity", "**********");
                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("MainActivity", throwable.toString());
                    }
                });
    }

    /**
     * popwindow
     */
    private void showPopwindow() {
        // 利用layoutInflater获得View
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout, null);

        // 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()

        window = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);

        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        window.setBackgroundDrawable(dw);

        // 设置popWindow的显示和消失动画
        //window.setAnimationStyle(R.style.mypopwindow_anim_style);
        // 在底部显示
        window.showAtLocation(MainActivity.this.findViewById(R.id.simple_drawee_view), Gravity.BOTTOM, 0, 0);

        // 这里检验popWindow里的button是否可以点击
        Button first = (Button) view.findViewById(R.id.first);//相机
        Button third = (Button) view.findViewById(R.id.third);//取消
        Button second = (Button) view.findViewById(R.id.second);//相册
        //相机
        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 这个出捕获图片的常量值
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // 设置图片输出位置; 输出到制定的uri路径上;
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(path)));
                // 设置请求码
                startActivityForResult(intent, 100);
                window.dismiss();
            }
        });
        //相册
        second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 101);
                window.dismiss();
            }
        });
        //取消
        third.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "取消", Toast.LENGTH_SHORT).show();
                window.dismiss();
            }
        });
        // popWindow消失监听方法
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                System.out.println("popWindow消失");
            }
        });
    }

    /**
     * 接收回传值
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("resultCode:---", resultCode + "");
        if (requestCode == 100 && resultCode == RESULT_OK) {
//          iv.setImageURI(Uri.fromFile(new File(path)));
//            crop(Uri.fromFile(new File(path)));
            uploadFiles(new File(path));
        } else if (requestCode == 101 && resultCode == RESULT_OK) {
            // 获取data中的数据
            Uri uri = data.getData();
//         iv.setImageURI(uri);
//            crop(uri);
            String[] proj = {MediaStore.Images.Media.DATA};

            Cursor actualimagecursor = managedQuery(uri, proj, null, null, null);

            int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

            actualimagecursor.moveToFirst();

            String img_path = actualimagecursor.getString(actual_image_column_index);

            File file = new File(img_path);
            Log.e("MainActivity", "URL:" + uri.toString());
            uploadFiles(file);
        } else if (requestCode == 102 && resultCode == RESULT_OK) {
            //这个参数data,是固定写法
            File file = new File(path);
            Bitmap bitmap = data.getParcelableExtra("data");
            try {
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                bos.flush();
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //上传
            uploadFiles(file);
        }
    }

    /**
     * 剪切
     */
    public void crop(Uri uri) {
        // 隐式启动, 里面传的是action
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);
        intent.putExtra("outputFormat", "JPEG");// 图片格式
        intent.putExtra("noFaceDetection", false);// 取消人脸识别
        // 设置剪裁后是否返回数据
        intent.putExtra("return-data", true);
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
        startActivityForResult(intent, 102);

    }
}
