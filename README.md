# 背单词app实验

基于Android平台的移动端英语背单词软件主要包括词库管理、网络单词、学习/复习、设置、用户管理、数据备份等功能模块

## 工具与虚拟机

* `Android Developer Tools`
* `Device` >= 3.7 FWVGA slider(480×853:hdpi)
* `Target` >= Android 4.3 - API Level 18
* `Size` >= 200 MiB

## 克隆项目

直接下载 ZIP 文件：您可以在 GitHub 项目页面的右上角找到一个 "Download" 按钮，可以使用它来下载项目的 ZIP 压缩文件。下载后，您可以解压缩文件，获得项目的副本。

## 运行

1. 克隆项目到文件夹
2. 在Eclipse中点击`File` `Import` `Android` `Existing Android Code Into Workspace` Next
3. 找到bdc
4. - [x] Copy projects into workspace
6. Finish

### 导入词库功能运行

1. 启动模拟器
2. 素材下的REBrowse.apk、danci.txt文件复制到adt/sdk/platfrom-tools文件夹。
3. `cmd`在终端cd到platfrom-tools输入
4. adb install REBrowse.apk
5. adb push danci.txt /sdcard/
6. 未成功请重新启动虚拟机。
可以使用 `adb devices` 命令来列出已连接的设备
如果有设备连接并处于正常状态，输出可能类似于：
    List of devices attached
    ABCDEFG1234	device
这表示已连接一个设备，序列号为 "ABCDEFG1234"，并且设备处于正常状态。
如果没有设备连接，输出可能只显示标题，没有设备信息：
    List of devices attached
