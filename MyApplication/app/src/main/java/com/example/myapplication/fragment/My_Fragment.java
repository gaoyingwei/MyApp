package com.example.myapplication.fragment;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.bigkoo.pickerview.TimePickerView;
import com.example.myapplication.R;
import com.example.myapplication.util.ActionSheet;
import com.example.myapplication.util.AlertDialog;
import com.example.myapplication.util.ConstomDialog;
import com.example.myapplication.util.LoadingDialog;
import com.example.myapplication.util.nav_bar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

public class My_Fragment extends Fragment {
    private ActionSheet actionSheet;
    ImageView imageView;
    nav_bar nav_bar,nav_bar_birthday,nav_bar_name,nav_bar_tui,nav_bar_my;
    private SimpleDateFormat simpleDateFormat;
    private Date date;
    private String format;
    private Calendar calendar;
    Dialog mShareDialog;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //不显示系统的标题栏,
        getActivity().getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN );
        View view=inflater.inflate(R.layout.my_fragment,null);
        imageView=view.findViewById(R.id.h_head);
        nav_bar=view.findViewById(R.id.shoucang);
        nav_bar_name=view.findViewById(R.id.lishi);
        nav_bar_tui=view.findViewById(R.id.tui);
        nav_bar_birthday=view.findViewById(R.id.version);
        nav_bar_my=view.findViewById(R.id.my);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LoadingDialog loadingDialog = new LoadingDialog(getActivity(),"加载中...",R.mipmap.ic_dialog_loading);
        loadingDialog.show();
        loadingDialog.setCanceledOnTouchOutside(true);
        // HH:mm:ss
        simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
//获取当前时间
        date = new Date(System.currentTimeMillis());
        format = simpleDateFormat.format(date);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSheet();
            }
        });

        nav_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSheetsex();

            }
        });

        nav_bar_birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time();
            }
        });

        nav_bar_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //实例化自定义对话框
                final ConstomDialog mdialog = new ConstomDialog(getActivity());
                mdialog.show();
                mdialog.setMyCallBack(new ConstomDialog.MyCallBack() {
                    @Override
                    public void call(String connect) {
                        mdialog.dismiss();
                        nav_bar_name.setName(connect);
                    }
                });
            }
        });

        nav_bar_tui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog adialog = new AlertDialog(getActivity());
                adialog.show();
            }
        });

        nav_bar_my.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               initShareDialog();
               mShareDialog.show();
            }
        });
    }
    private void showSheet() {
        actionSheet=new ActionSheet.DialogBuilder(getActivity())
                .addSheet("相机", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(getActivity(), "点击了相机！", Toast.LENGTH_SHORT).show();
                        //动态权限：点击相机时获取相机权限
                        DongTaiShare();
                        //从相机获取图片
                        getPicFromCamera();
                        actionSheet.dismiss();
                    }
                })
                .addSheet("相册", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(getActivity(), "点击了相册！", Toast.LENGTH_SHORT).show();
                        //从相册获取图片
                        getPicFromAlbm();
                        actionSheet.dismiss();
                    }
                })
                .addCancelListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(), "取消", Toast.LENGTH_SHORT).show();
                        actionSheet.dismiss();
                    }
                })
                .create();
    }
    private void showSheetsex() {
        actionSheet=new ActionSheet.DialogBuilder(getActivity())
                .addSheet("男", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        actionSheet.dismiss();
                        nav_bar.setName("男");
                    }
                })
                .addSheet("女", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        actionSheet.dismiss();
                        nav_bar.setName("女");
                    }
                })
                .addCancelListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(), "取消", Toast.LENGTH_SHORT).show();
                        actionSheet.dismiss();
                    }
                })
                .create();
    }
    public void time(){
        TimePickerView pvTime = new TimePickerView.Builder(getActivity(), new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                nav_bar_birthday.setName(getTime(date));
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                //    .setContentSize(18)//滚轮文字大小
                //    .setTitleSize(20)//标题文字大小
                //    //.setTitleText("Title")//标题文字
                        .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                    //    .isCyclic(true)//是否循环滚动
                    //    //.setTitleColor(Color.BLACK)//标题文字颜色
                    //    .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                    //    .setCancelColor(Color.BLUE)//取消按钮文字颜色
                    //    //.setTitleBgColor(0xFF666666)//标题背景颜色 Night mode
                    //    .setBgColor(0xFF333333)//滚轮背景颜色 Night mode
                    ////    .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                       // .setRangDate(,calendar.setTime(date))//起始终止年月日设定
                    //    //.setLabel("年","月","日","时","分","秒")//默认设置为年月日时分秒
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                //.isDialog(true)//是否显示为对话框样式
                .build();

        pvTime.show();
    }
    private String getTime(Date date) {//可根据需要自行截取数据显示
        //"YYYY-MM-DD HH:MM:SS"        "yyyy-MM-dd"
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }


    /**
     * 初始化分享弹出框
     */
    private void initShareDialog() {
        mShareDialog = new Dialog(getActivity(), R.style.dialog_bottom_full);
        mShareDialog.setCanceledOnTouchOutside(true);
        mShareDialog.setCancelable(true);
        Window window = mShareDialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.share_animation);
        View view = View.inflate(getActivity(), R.layout.dialog_custom_layout, null);
        view.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mShareDialog != null && mShareDialog.isShowing()) {
                    mShareDialog.dismiss();
                }
            }
        });
        window.setContentView(view);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);//设置横向全屏
    }

    //动态申请权限
    //添加动态权限
    private void DongTaiShare() {
        if (Build.VERSION.SDK_INT >= 23) {
            String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE, Manifest.permission.READ_LOGS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.SET_DEBUG_APP, Manifest.permission.SYSTEM_ALERT_WINDOW, Manifest.permission.GET_ACCOUNTS, Manifest.permission.WRITE_APN_SETTINGS, Manifest.permission.CAMERA};
            ActivityCompat.requestPermissions(getActivity(), mPermissionList, 123);
        }
    }
    //调用系统相机
    private void getPicFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 1);
    }

    //调用相册
    private void getPicFromAlbm() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, 2);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode) {
            // 调用相机后返回
            case 1:
                if (resultCode == RESULT_OK) {
                    final Bitmap photo = intent.getParcelableExtra("data");
                    //给头像设置你相机拍的照片
                    //imageView.setImageBitmap(photo);

                }
                break;
            //调用相册后返回
            case 2:
                if (resultCode == RESULT_OK) {
                    Uri uri = intent.getData();
                    //cropPhoto(uri);//裁剪图片
                }
                break;
            //调用剪裁后返回
            case 3:
                Bundle bundle = intent.getExtras();
                if (bundle != null) {
                    //在这里获得了剪裁后的Bitmap对象，可以用于上传
                    Bitmap image = bundle.getParcelable("data");
                    //设置到ImageView上
                    imageView.setImageBitmap(image);
                    //也可以进行一些保存、压缩等操作后上传
//                    String path = saveImage("userHeader", image);
//                    File file = new File(path);
                    /*
                     *这里可以做上传文件的额操作
                     */
                }
                break;
        }
    }
    /**
     * 裁剪图片
     */
    private void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }
    /**
     * 保存图片到本地
     *
     * @param name
     * @param bmp
     * @return
     */
    public String saveImage(String name, Bitmap bmp) {
        File appDir = new File(Environment.getExternalStorageDirectory().getPath());
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = name + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            return file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
