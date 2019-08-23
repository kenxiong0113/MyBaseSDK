package com.cimcitech.base_utils_class.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;


import com.cimcitech.base_utils_class.base.BaseLibrary;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.cimcitech.base_utils_class.base.BaseToolBarActivity.REQUEST_CAMERA_CODE;


/**
 * 打开 相机
 * */
public class OpenCameraUtils {

    private static final String OUTPUTPATH = "/Pictures/best/images/";
    private static final String COMPRESSPATH = "/Pictures/best/images/zip/";

    private Activity mActivity;
    private static OpenCameraUtils sInstance;
    public static OpenCameraUtils getInstance(){
        if (sInstance == null) {
            synchronized (OpenCameraUtils.class) {
                if (sInstance == null) {

                    sInstance = new OpenCameraUtils();
                }
            }
        }
        return sInstance;
    }
     private OpenCameraUtils(){ }

     /**
      * 检查权限
      * */
     public void checkPer(Activity activity){
         this.mActivity = activity;
         List<String> perList = new ArrayList<>();
         if (ContextCompat.checkSelfPermission(BaseLibrary.mContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
             perList.add(Manifest.permission.CAMERA);
         }

         if (ContextCompat.checkSelfPermission(BaseLibrary.mContext, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
             perList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
         }

         if (ContextCompat.checkSelfPermission(BaseLibrary.mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
             perList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
         }

         if (perList.size() == 0) {
             openAlbum();
         } else {
             ActivityCompat.requestPermissions(activity, perList.toArray(new String[perList.size()]), REQUEST_CAMERA_CODE);
         }

     }


    public void openAlbum() {
        makePath();
        PictureSelector.create(mActivity)
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .maxSelectNum(1)// 最大图片选择数量 int
                .previewImage(true)// 是否可预览图片 true or false
                .isCamera(true)// 是否显示拍照按钮 true or false
                .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                .setOutputCameraPath(OUTPUTPATH)// 自定义拍照保存路径,可不填
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    private void makePath() {
        File outPutPath = new File(Environment.getExternalStorageDirectory().getPath() + OUTPUTPATH);
        File compressPath = new File(Environment.getExternalStorageDirectory().getPath() + COMPRESSPATH);
        if (!outPutPath.exists()) {
            outPutPath.mkdir();
        }
        if (!compressPath.exists()) {
            compressPath.mkdir();
        }
    }


    public Uri getUri(String path) {
        Uri uri;
        File file = new File(path);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            uri = Uri.fromFile(file);
        } else {
            String authorities = BaseLibrary.packageName+".fileProvider";
            uri = FileProvider.getUriForFile(BaseLibrary.mContext, authorities, file);
        }
        return uri;
    }

}
