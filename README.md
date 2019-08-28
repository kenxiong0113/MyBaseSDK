
版本说明(最新1.1.5)
===
## （1.1.5）
## 说明 新增个性化toast

===
## （1.1.4）
## 说明：兼容1.1.3
## 添加了glide 清除缓存工具


## (1.1.3）
## 1、新版本集成了权限管理的sdk 有用到（ api 'com.hjq:xxpermissions:x.x'）的需在主模块中去除，避免重复
## 2、集成了蒲公英APP版本更新功能，详情使用方法请查看蒲公英官方文档，本sdk 关键使用请参考UpdateAppVersionActivity.java


使用说明
====
## Step 1. Add the JitPack repository to your build file
``` Gradle
allprojects {
		repositories {
			...
			maven { 
			url 'https://jitpack.io'
			// 1.1.3 新集成了蒲公英版本更新，crash 捕获的功能 必须添加下面这行
			maven { url "https://raw.githubusercontent.com/Pgyer/mvn_repo_pgyer/master" }
			}
		}
	}
```
## Step 2. Add the dependency（注意使用版本）	
``` Gradle

android {
	...
	  compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
}

dependencies {
//****************************必须保留start******************
		implementation fileTree(dir: 'libs', include: ['*.jar'])
   		testImplementation 'junit:junit:4.12'
    		androidTestImplementation 'com.android.support.test:runner:1.0.2'
    		androidTestImplementation 'com.android.support.test.espresso:espresso-core:3.0.2'
//****************************必须保留end******************
// 项目使用butterknife 必须添加这行
    annotationProcessor 'com.jakewharton:butterknife-compiler:8.4.0'
	         implementation 'com.github.kenxiong0113:MyBaseSDK:x.x.x'
	}
```
## Step 3.初始化
``` Java
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
//        初始化base_utils_class
        BaseLibrary.initBaseLibrary(this);
//        设置通知栏应用logo
       BaseLibrary.setAppIcon(R.mipmap.ic_launcher);

    }
}
```
## Step 4.设置actionBar
``` Xml
    <!-- Base application theme. -->
    <style name="AppTheme" parent="Theme.AppCompat.Light.DarkActionBar">
        <!-- Customize your theme here. -->
        ...
      	<item name="windowActionBar">false</item>
        <item name="windowNoTitle">true</item>
    </style>
```

## 使用
#### 1.继承 BaseToolBarActivity，重写实现父类必要方法，示例：

``` Java
public class MainActivity extends BaseToolBarActivity {
    private static final String TAG = "MainActivity";
    @BindView(R.id.et_scan)
    EditText etScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initBase() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {
       
    }

    @Override
    protected void initData() {

    }
}
```
#### 2.版本更新示例(请查看UpdateAppVersionActivity.java)：

``` java
    //关键代码：
      UpdateAppVersionUtils.getInstance().checkAppVersion(this);
```


## 打赏（不要理，我就是写一个图片表格试试）
| ![](https://github.com/kenxiong0113/BaseLibrary/blob/master/img/ZhiFuBao.jpg)|![](https://github.com/kenxiong0113/BaseLibrary/blob/master/img/weichat.png)|
--------- | -------------





