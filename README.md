
使用说明
====
## Step 1. Add the JitPack repository to your build file
``` Gradle
allprojects {
		repositories {
			...
			maven { 
			url 'https://jitpack.io'
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
	         implementation 'com.github.kenxiong0113:MyBaseSDK:v1.0.（）'
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
## 打赏（不要理，我就是写一个图片表格试试）
| ![](https://github.com/kenxiong0113/BaseLibrary/blob/master/img/ZhiFuBao.jpg)|![](https://github.com/kenxiong0113/BaseLibrary/blob/master/img/weichat.png)|
--------- | -------------





