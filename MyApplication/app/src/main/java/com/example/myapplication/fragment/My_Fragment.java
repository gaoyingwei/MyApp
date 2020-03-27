package com.example.myapplication.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.bigkoo.pickerview.TimePickerView;
import com.example.myapplication.R;
import com.example.myapplication.ui.ViewActivity;
import com.example.myapplication.util.ActionSheet;
import com.example.myapplication.util.AlertDialog;
import com.example.myapplication.util.ConstomDialog;
import com.example.myapplication.util.FiltrateBean;
import com.example.myapplication.util.FlowPopWindow;
import com.example.myapplication.util.LoadingDialog;
import com.example.myapplication.util.nav_bar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static android.app.Activity.RESULT_OK;

public class My_Fragment extends Fragment {
    private ActionSheet actionSheet;
    ImageView imageView;
    nav_bar nav_bar,nav_bar_birthday,nav_bar_name,nav_bar_tui,nav_bar_my;
    private SimpleDateFormat simpleDateFormat;
    private Date date;
    private String format;
    private Calendar calendar;
    TextView textView_username,textView_uvl;
    Dialog mShareDialog;
    private PopupWindow popupWindow;
    private View popupView = null;
    private EditText inputComment;
    private String nInputContentText;
    private TextView btn_submit;
    private RelativeLayout rl_input_container;
    private InputMethodManager mInputManager;
    //筛选框控件
    private FlowPopWindow flowPopWindow;
    //筛选框数据
    private List<FiltrateBean> dictList = new ArrayList<>();
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
        textView_username=view.findViewById(R.id.user_name);
        textView_uvl=view.findViewById(R.id.user_val);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initParam();
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
        textView_username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toAliPayScan(getActivity());

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

        textView_uvl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flowPopWindow = new FlowPopWindow(getActivity(), dictList);
                flowPopWindow.showAsDropDown(getActivity().getWindow().getDecorView(), Gravity.TOP, 0, 0);
                flowPopWindow.setOnConfirmClickListener(new FlowPopWindow.OnConfirmClickListener() {
                    @Override
                    public void onConfirmClick() {
                        StringBuilder sb = new StringBuilder();
                        for (FiltrateBean fb : dictList) {
                            List<FiltrateBean.Children> cdList = fb.getChildren();
                            for (int x = 0; x < cdList.size(); x++) {
                                FiltrateBean.Children children = cdList.get(x);
                                if (children.isSelected())
                                    sb.append(fb.getTypeName() + ":" + children.getValue() + "；");
                            }
                        }
                        if (!TextUtils.isEmpty(sb.toString()))
                            Toast.makeText(getActivity(), "111"+sb.toString(), Toast.LENGTH_LONG).show();
                    }
                });
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

    @SuppressLint("WrongConstant")
    public static void toWeChatScanDirect(Context context) {
        try {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI"));
            intent.putExtra("LauncherUI.From.Scaner.Shortcut", true);
            intent.setFlags(335544320);
            intent.setAction("android.intent.action.VIEW");
            context.startActivity(intent);
        } catch (Exception e) {
        }
    }
    public static void toAliPayScan(Context context)
    {
        try
        {
            Uri uri = Uri.parse("alipayqr://platformapi/startapp?saId=10000007");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);
        } catch (Exception e)
        {
            Toast.makeText(context, "打开失败，请检查是否安装了支付宝", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("WrongConstant")
    private void showPopupcomment() {
        if (popupView  == null){
            //加载评论框的资源文件
            popupView =  LayoutInflater.from(getActivity()).inflate(R.layout.comment_popupwindow, null);
        }
        inputComment = (EditText) popupView.findViewById(R.id.et_discuss);
        btn_submit = (Button) popupView.findViewById(R.id.btn_confirm);
        rl_input_container = (RelativeLayout)popupView.findViewById(R.id.rl_input_container);
        //利用Timer这个Api设置延迟显示软键盘，这里时间为200毫秒
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            public void run()
            {
                mInputManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                mInputManager.showSoftInput(inputComment, 0);
            }

        }, 200);
        if (popupWindow == null){
            popupWindow = new PopupWindow(popupView, RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT, false);

        }
        //popupWindow的常规设置，设置点击外部事件，背景色
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE)
                    popupWindow.dismiss();
                return false;

            }
        });

        // 设置弹出窗体需要软键盘，放在setSoftInputMode之前
        popupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        // 再设置模式，和Activity的一样，覆盖，调整大小。
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //设置popupwindow的显示位置，这里应该是显示在底部，即Bottom
        popupWindow.showAtLocation(popupView, Gravity.BOTTOM, 0, 0);

        popupWindow.update();

        //设置监听
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            // 在dismiss中恢复透明度
            @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
            public void onDismiss() {

                mInputManager.hideSoftInputFromWindow(inputComment.getWindowToken(), 0); //强制隐藏键盘


            }
        });
        //外部点击事件
        rl_input_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                mInputManager.hideSoftInputFromWindow(inputComment.getWindowToken(), 0); //强制隐藏键盘
                popupWindow.dismiss();

            }
        });
        //评论框内的发送按钮设置点击事件
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                nInputContentText = inputComment.getText().toString().trim();

                if (nInputContentText == null || "".equals(nInputContentText)) {
                    Toast.makeText(getActivity(), "请输入评论内容", Toast.LENGTH_SHORT).show();
                    return;
                }
                mInputManager.hideSoftInputFromWindow(inputComment.getWindowToken(),0);
                popupWindow.dismiss();

            }
        });
    }
    //这些是假数据，真实项目中直接接口获取添加进来，FiltrateBean对象可根据自己需求更改
    private void initParam() {
        String[] sexs = {"男", "女"};
        String[] colors = {"红色", "浅黄色", "橙子色", "鲜绿色", "青色", "天蓝色", "紫色", "黑曜石色", "白色", "五颜六色"};
        String[] company = {"阿里巴巴集团", "腾讯集团", "华为技术服务有限公司", "小米", "www.xiaomi.com"};

        FiltrateBean fb1 = new FiltrateBean();
        fb1.setTypeName("性别");
        List<FiltrateBean.Children> childrenList = new ArrayList<>();
        for (String sex : sexs) {
            FiltrateBean.Children cd = new FiltrateBean.Children();
            cd.setValue(sex);
            childrenList.add(cd);
        }
        fb1.setChildren(childrenList);

        FiltrateBean fb2 = new FiltrateBean();
        fb2.setTypeName("颜色");
        List<FiltrateBean.Children> childrenList2 = new ArrayList<>();
        for (String color : colors) {
            FiltrateBean.Children cd = new FiltrateBean.Children();
            cd.setValue(color);
            childrenList2.add(cd);
        }
        fb2.setChildren(childrenList2);

        FiltrateBean fb3 = new FiltrateBean();
        fb3.setTypeName("企业");
        List<FiltrateBean.Children> childrenList3 = new ArrayList<>();
        for (String s : company) {
            FiltrateBean.Children cd = new FiltrateBean.Children();
            cd.setValue(s);
            childrenList3.add(cd);
        }
        fb3.setChildren(childrenList3);

        dictList.add(fb1);
        dictList.add(fb2);
        dictList.add(fb3);
    }
}
