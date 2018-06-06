#### Databing学习笔记

#### 启用Databinding

```Java
android {
    dataBinding {
    enabled = true
    }
}
```

#### 修改布局文件
```xml
<?xml version="1.0" encoding="utf-8"?>

<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">

    <data>
        <variable
            name="user"
            type="cn.i27house.databindingsample.sample1.User" />
    </data>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical">

    </LinearLayout>
</layout>
```

我们可以看到，上面使用`<data></data>`标签定义了一个变量，然后使用`variable`标签指明了变量名以及类的路径，如果我们想使用静态方法，那么可以使用`import`方法导入类，然后就可以直接用啦。

#### 生成binding对象

+ 生成规则，驼峰命名法，以下划线隔开

```Java
ActivitySample1Binding mBinding =DataBindingUtil.setContentView(this,R.layout.activity_sample1);
```

#### 快速生成Databinding对象

有时候当我们修改了布局文件里面的id或者新创建了一个布局文件，Android Studio往往不会自动生成，或者当生成了，但是Android Studio提示一个红色，看着很不爽，解决方法除了重启Android Studio以外，还有运行Databinding的Gradle脚本 ,位置Android Studio右侧Gradle    Task->other-> dataBindingGenBaseClassesDebug


#### 表达式

+ 可以使用大多数的一元、二元、三目运算符、还可以使用instanceof

+ 特殊运算符 ?? 

```java
android:text="@{user.displayName ?? user.lastName}"
```

等价于

```java
android:text="@{user.displayName != null ? user.displayName : user.lastName}"
```

+ 表达式中使用字符串

在xml中，如果我们想使用字符串，需要使用``(反引号),如下

```xml
android:text="@{user.firstName instanceof String ? `1`:`2`}"
```

或者外面使用单引号，里面使用双引号。(**这种貌似不能放汉字！！！**)

```xml
android:text='@{user.firstName instanceof String ? "1":"2"}'
```

+ 在表达式中使用函数

如果是在java.lang包下面的我们可以直接使用，比如

```
android:text="@{String.valueOf(8888)}"
```

如果是非java.lang包下面的，我们需要在variable标签中进行定义才可以使用

#### 表达式链

比如下面的这些
```xml
<ImageView android:visibility=“@{isVisible ? View.VISIBLE : View.GONE}”/>
<TextView android:visibility=“@{isVisible  ? View.VISIBLE : View.GONE}”/>
<CheckBox android:visibility="@{isVisible  ? View.VISIBLE : View.GONE}"/>
```

我们如果使用表达式链可以写成如下形式，需要注意的是，**这里的id也要遵循驼峰命名法，比如如果id为photo_list，那么我们使用的时候是photoList**

```xml
<ImageView aandroid:id="@+id/image" android:visibility="@{isVisible ? View.VISIBLE:View.GONE}"/>
<TextView android:visibility="@{image.visibility}"/>
<CheckBox android:visibility="@{image.visibility}"/>
```

#### 隐式更新

其实隐式更新是表达式链的另一种表现形式，就是不仅仅可以使用同一个属性。

```xml
<CheckBox android:id=”@+id/seeAds“/>
<ImageView android:visibility=“@{seeAds.checked ? View.VISIBLE : View.GONE}”/>
```

#### 资源数据
在xml中我们可以根据变量动态设置数值，比如这样

```xml
android:padding="@{large? @dimen/largePadding : @dimen/smallPadding}"
```
或者这样,占位符都tm用上了

```xml
android:text="@{@string/nameFormat(firstName, lastName)}"
```


**Databing是空安全的，如果我们设置一些null值，则运行中不会显示**

#### 函数绑定
 + 绑定标准函数，比如onClick

我们需要定义一个对象，函数签名必须和onClick一致，然后在Xml使用

```
android:onClick="@{handler::jump}"
```

java类中定义的函数

```java
public void jump(View v) {
}
```

+ 绑定任意函数

绑定任意函数我们只需要使用(v) ->handler.jump(v)，当然，我们也可以省略掉v，或者也可以多添加几个参数。

```java
android:onClick=“@{(view)->presenter.save(view, item)}”
android:onClick=“@{()->presenter.save(item)}”
android:onFocusChange=“@{(v, fcs)->presenter.refresh(item)}”
```

**自定义View使用->形式貌似会出现问题**

#### include标签的绑定

首先被include进来的东西需要使用layout标签，以及data标签定义好需要使用的。这里我**故意定义name为user2**

```xml
<layout xmlns:android="http://schemas.android.com/apk/res/android">
    <data>
        <variable
            name="user2"
            type="cn.i27house.databindingsample.sample1.User"/>
    </data>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical">

        <TextView
            android:text="@{user2.xml()}"
            android:layout_width="match_parent"
            android:layout_height="wrap_content" />
    </LinearLayout>
</layout>
```

然后将对象传递给include的布局

可以看到，**bind后面跟的是上面定义的user2，然后值为本layout文件中的user**

```android
<include layout="@layout/activity_sample1_include" bind:user2="@{user}"/>
```

#### ViewStub标签的绑定

ViewStub标签的绑定与include标签一模一样，但是绑定的时机有点不同，ViewStub只有在inflate以后才可用，所以我们在使用ViewStub的时候需要先inflate。

Databinding使用的代理去管理绑定，当我们inflate以后才进行绑定。

```xml
mBinding.viewStub.getViewStub().inflate();

<ViewStub
	android:id="@+id/viewStub"
	android:layout_width="wrap_content"
	android:layout_height="wrap_content"
	android:layout="@layout/activity_sample1_include"
	bind:user2="@{user}"/>
```

#### 数据绑定

+ Observable

通过上面的操作，我们已经将Model绑定到了View上面，不过每一次刷新View都需要手动调用Databinding的setXXX方法，现在我们直接通过Model类的set方法去改变View该咋弄捏。

其他的都不需要改变，只需要修改Model类

```java
public class User extends BaseObservable {

    private String firstName;

    private String lastName;

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Bindable
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        notifyPropertyChanged(BR.firstName);
    }

    @Bindable
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        notifyPropertyChanged(BR.lastName);
    }

    public String xml() {
        return "xml" + firstName;
    }
}
```

如上，首先让Model类继承BaseObservable ，然后**在get方法上面使用 @Bindable注解进行绑定**，然后在set方法中使用 notifyPropertyChanged(BR.firstName);去刷新View。或者使用notifyChange()刷新全部

**如果BR类无法自动提示，那么在app\build\generated\source\apt\debug\包名\BR.java中可以找到生成的BR，然后写绝对路径就可以，如果没有生成，只能说明你写错了啊！！！**


**BR的生成规则 是setXXX后面的那一串XXX，并且使用驼峰命名法**

+ ObservableField

我们除了继承Observable以外，我们还可以使用ObservableField<T> ,或者使用具体类ObservableInt、--Short、--Byte等基础的数据类型，ps:**没有ObservableString**。

还有特殊的ObservableList<T>或者ObservableMap<T>等。

在使用ObservableField方式的时候有一个大坑，就是**不能使用getXXX函数**，需要将字段设置为public，不然不会刷新View。

```java
/**
 * 不能有get  不然无效
 */
public class User {

    public ObservableField<String> firstName;

    public ObservableField<String> lastName;

    public User(String firstName, String lastName) {
        this.firstName = new ObservableField<>(firstName);
        this.lastName = new ObservableField<>(lastName);
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }
}
```


+ 数据改变监听

有时候我们想当数据改变的时候做点自己想做的事，我们可以使用addOnPropertyChangedCallback来监听数据改变，如下，**只有当数据改变的时候才会回调，因为源码内部有判断数据是否与上次相同**

```Java
mUser.firstName.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
    @Override
    public void onPropertyChanged(Observable sender, int propertyId) {
        Log.i("123", "onPropertyChanged: " + mUser.firstName.get());
    }
});
```

#### Databinding与RecycleView一同使用

首先我们知道，数据和View绑定主要是发生在ViewHolder中的，所以我们定义一个通用ViewHolder来保存Databinding实例

```java
public class BindingViewHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder {

    private T mBinding;

    public BindingViewHolder(T binding) {
        super(binding.getRoot());
        mBinding = binding;
    }

    public T getBinding() {
        return mBinding;
    }
}
```

然后在`onCreateViewHolder`中进行Databinding的创建

```java
ViewDataBinding inflate = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),layoutId, parent, false);
BindingViewHolder holder = new BindingViewHolder(inflate);
```

最后在`onBindViewHolder`中进行数据绑定

```java
User user = mUsers.get(position);
holder.getBinding().setVariable(BR.item, user);
holder.getBinding().executePendingBindings();
```

**注意**
1、我们使用的`setVariable`刷新的数据，然后调用`executePendingBindings`让更改马上应用。
2、对于多个item布局，我们可以**在每一个item的布局中定义同一个name的Variable，然后使用setVariable去刷新View**,如上所示。


#### 自动set绑定

如果一个类有setXXX方法，那么当我们使用app:XXX="@{xxxx}"的时候，Databinding会自动帮我们调用这个set方法。

```xml
<TextView xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    app:text='@{"3213"}' />
```
如上代码，会被自动调用TextView#setText("3213")

上面这一点我们可以用来给自定义View使用

#### BindingAdapter

有时候我们不想复写一个View，然后给他添加setXXX方法，那么我们该怎么弄呢。

我们可以自定义一个类，然后使用@BindingAdapter注解方法即可。

比如下面，我们就定义了一个属性`bind:netImage`用来加载网络图片到ImageView中

```java
public class BindAdapter {

    @BindingAdapter({"bind:netImage"})
    public static void loadUrl(ImageView img, String url) {
        Glide.with(img.getContext()).load(url).into(img);
    }
}
```

**注意**
1、函数声明为**public static**，不可以使用其他的，可选返回值，不过一般不推荐。
2、方法数要和注解里面的参数匹配，第一个为View类型，多个注解参数使用逗号隔开

如下。

```java
@BindingAdapter({"bind:imageUrl", "bind:error"})
public static void loadImage(ImageView view, String url, Drawable error) {
   Picasso.with(view.getContext()).load(url).error(error).into(view);
}
```

使用:
先声明命名空间，然后直接使用就可以了，注意要用**@{}**将参数包裹，不然会报错的。

```xml
<ImageView
    xmlns:bind="http://schemas.android.com/apk/res-auto"
    bind:netImage="@{`https://upload-images.jianshu.io/upload_images/595349-6ffdbb24d4945fa4.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/700`}"
    android:layout_width="150dp"
    android:layout_height="150dp"
    android:src="@mipmap/ic_launcher_round" />
```

注:不一定需要使用bind NameSpace,你也可以使用app:netImage来使用，不过如果使用一样的名字bind，那么编译器会给与补全提示而已。


对于绑定的参数，我们也可以获取其旧值，比如上面的demo，我们也可以这样。

```java
public class BindAdapter {

    @BindingAdapter({"bind:netImage"})
    public static void loadUrl(ImageView img, String oldUrl, String newUrl) {
        if (!newUrl.equals(oldUrl)) {
            Glide.with(img.getContext()).load(newUrl).into(img);
        }
    }
}
```

**注意**
1、参数类型是一样的。
2、前面的一个参数是旧的，后面的是新的。


#### BindingConversion

当我们使用@{}表达式的时候，有可能里面的值不能转换为需要显示的。如下

```xml
<variable
    name="date"
    type="java.util.Date" />

<TextView
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:text="@{date}"/>
```

很明显的，Date不能设置给android:text，因为需要一个String，这个时候我们就可以自定义一个转换器去转换。如下，将Date转换为String。同样的得定义为public static

```java
public class BindConversion {

    @BindingConversion
    public static String conversion(Date date){
        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd");
        return time.format(date);
    }
}
```

其实系统有时候已经帮我们转换了一下，还是上面的例子

```xml
<TextView
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:text="@{@string/app_name}" />
```
我们使用的一个资源id，但是系统其实会帮我们自动弄成String


####  使用占位符

有时候我们想使用占位符，我们同样可以这样

```xml
<TextView
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:text='@{String.format("%s / %s",loginbean.userName,loginbean.userPwd)}' />
```

或者可以这样,直接使用xml中的

```xml
<string name="login_format">%1$s / %2$s</string>
```

```xml
<TextView
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:text="@{@string/login_format(loginbean.userName,loginbean.userPwd)}" />
```


#### 双向绑定

通过上面所学的东西，我们可以在当bean对象变化的时候刷新到View上，如果我们想当View上发生变化的时候自动改变bean对象改怎么做呢？答案就是双向绑定。

双向绑定的时候也是非常简单的。

```xml
<EditText
    android:id="@+id/loginUserName"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:hint="请输入用户名"
    android:text="@={loginbean.userName}" />
```

可以看到，我们仅仅使用了**@={}**而已，当然了，我们的**bean对象也必须能更新View**，就是上面数据绑定一节中介绍的内容。

```java
public class LoginBean extends BaseObservable {

    private String mUserName;

    private String mUserPwd;

    public LoginBean() {
    }

    @Bindable
    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
        notifyPropertyChanged(BR.userName);
        // notifyPropertyChanged(BR.);
    }

    @Bindable
    public String getUserPwd() {
        return mUserPwd;
    }

    public void setUserPwd(String userPwd) {
        mUserPwd = userPwd;
        //notifyChange();
        notifyPropertyChanged(BR.userPwd);
    }

    @Override
    public String toString() {
        return "LoginBean{" +
                "mUserName='" + mUserName + '\'' +
                ", mUserPwd='" + mUserPwd + '\'' +
                '}';
    }
}
```

使用双向绑定的时候需要注意死循环，比如你监听一个EditText的数据改变，当长度过长的重新设置EditText的文字，这个时候又会回调你，所以需要进行判断，不然可能会死循环。

> 参考链接：[官方文档](https://developer.android.com/topic/libraries/data-binding/)、[掘金博客](https://juejin.im/post/578b944a128fe10063ad6c05)









