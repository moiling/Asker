# Asker
Q&amp;A app for school. <br />
Android Client of [Asker Backend](https://github.com/moiling/Asker_Backend). <br />
Using my image crop lib [Picrop](https://github.com/moiling/PiCrop). <br />

![image](https://raw.githubusercontent.com/moiling/Asker/master/pic/icon.png)

## Project Structure
```
config                                        – 保存各种常量配置文件
event                                         – 事件通知类，如登录事件
model                                         – MVP中的数据层
    | - bean                                  – 所有数据所对应的JavaBean对象
    | - network                               – 和网络请求有关的对象
              | - service                     – Retrofit中请求的注解定义类
              | - setting                     – Retrofit的各种配置
                       | - annoatation        – Json和Xml的注解配置
                       | - func               – 对Rxjava的func进行封装
    | - subscriber                            – Rxjava的订阅者封装
presenter                                     – MVP中的管理层，处理业务逻辑
ui                                            – MVP中的视图层
    | - activity                              – 所有Activity
    | - adapte                                – 所有适配器
             | - itemview                     – 一些特殊的列表元素封装
    | - component                             – 自定义视图组件
    | - fragment                              – 所有Fragment
    | - viewholder                            – 所有RecyclerView的ViewHolder封装
    | - vu                                    – MVP中视图层的接口，用于解耦
utils                                         – 所有工具类
```

## Details
### 1.	首页 - 最新问题列表
正常状态如上图，包括上方菜单栏，问题列表区域和右下角的提问浮动按钮<br />
滑动状态如下图，其中菜单栏与提问浮动按钮隐藏，给用户更大的阅读区域<br />
滑到底部状态如右下图，底部显示没有更多提示用户<br />
问题列表中显示每个问题的标签、题目、发布时间、发布者、内容、收藏数和回复数，点击收藏按钮即可收藏，已收藏的题目按钮会变成红色提示<br />
点击提问按钮若已登录进入提问界面，若未登录则进入登录页面<br />
![image](https://raw.githubusercontent.com/moiling/Asker/master/pic/1.png)<br />
![image](https://raw.githubusercontent.com/moiling/Asker/master/pic/2.png)<br />

### 2.	搜索
单击上方菜单栏右边的“放大镜”按钮，进入搜索状态。状态如下图所示。<br />
右边为状态退出按钮，单击则返回正常状态<br />
中间为搜索框，输入待搜索的信息<br />
右边“X”按钮为清空搜索框文字按钮，单击则可清空文本框中的文字<br />
最右边的箭头按钮为搜索按钮，单击则会搜索输入框中的信息，将结果显示在内容区域<br />
![image](https://raw.githubusercontent.com/moiling/Asker/master/pic/3.png)<br />

### 3.	侧滑菜单
![image](https://raw.githubusercontent.com/moiling/Asker/master/pic/4.png)<br />
![image](https://raw.githubusercontent.com/moiling/Asker/master/pic/5.png)<br />
通过主页右边的菜单按钮或屏幕左侧滑动可以进入，如上图所示侧滑菜单中包括三部分<br />
1．	上方用户信息展示<br />
若已登录，如上图上包括头像与用户名，单击可以进入用户信息编辑界面<br />
若未登录，如上图下，显示登录提示，单击可以进入用户登录界面<br />
2．	中间问题分类，包括最新、搜索、收藏和我的，单击后主页内容区域变成对应内容<br />
3．	下方更多区域，点击关于和提问分别跳转到关于页面和提问页面<br />

### 4.	问题详细与回答列表
点击主页中的任意问题即可跳转到问题详细与回答列表页面<br />
主页中的问题字数只能显示三行，剩余用省略号省略，此页面不做省略，完整显示，其余和主页中的问题一样<br />
下方显示回答列表，每个回答显示回答者类型和昵称、时间、内容和支持数<br />
点击每个回答最左边的上/下箭头选择赞成/反对，回答结果按支持数排序<br />
右下方为回答按钮，若已登录单击进入回答页面，若未登录进入登录页面<br />
![image](https://raw.githubusercontent.com/moiling/Asker/master/pic/6.png)<br />

### 5.	提问/回答
### 6.	收藏/我的问题
![image](https://raw.githubusercontent.com/moiling/Asker/master/pic/7.png)<br />
![image](https://raw.githubusercontent.com/moiling/Asker/master/pic/8.png)<br />

### 7.	登录/注册界面
输入账号密码后单击“登录或注册按钮”<br />
若账号存在，密码正确则登录成功，跳转到主页面<br />
若账号存在，密码错误则提示密码错误<br />
若账号不存在，则弹出下图右所示对话框，选择注册类型进行注册，注册成功后自动登录跳转到主页面<br />
![image](https://raw.githubusercontent.com/moiling/Asker/master/pic/9.png)<br />
![image](https://raw.githubusercontent.com/moiling/Asker/master/pic/10.png)<br />

### 8.	个人信息详细界面
该界面分为教师与学生两种，内容分为公共信息和教师/学生专有信息，退出登录按钮<br />
整个界面可以上下滑动，下图为了看到整体效果使用了长截图方式与真实效果有偏差<br />
单击每一项信息均可以更改，单击右上方的保存按钮即可保存<br />
单击头像即可选择手机中的图片上传为头像<br />
单击退出按钮即可回到登录页面切换账号<br />
下图左边为学生信息，右边为教师信息<br />
![image](https://raw.githubusercontent.com/moiling/Asker/master/pic/11.png)<br />
![image](https://raw.githubusercontent.com/moiling/Asker/master/pic/12.png)<br />

### 9.	修改头像
单击用户头像则会跳转到相册选择照片，跳转到剪裁页面进行剪裁，完毕后上传头像到服务器（注：服务器太小，所以限定图片大小很严格，剪裁后的图片不能大于1MB，否则上传失败）<br />
操作过程如下图所示
![image](https://raw.githubusercontent.com/moiling/Asker/master/pic/13.png)<br />

### 10.	修改信息
![image](https://raw.githubusercontent.com/moiling/Asker/master/pic/15.png)<br />

### 11.	无内容提示
如果没有内容，页面会有如下图所示的对应提示<br />
若没有登录去查看收藏或我的问题则会显示登录提示，单击则可跳转到登录界面<br />
![image](https://raw.githubusercontent.com/moiling/Asker/master/pic/16.png)<br />
