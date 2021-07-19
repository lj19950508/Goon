### 记录近期工作

修改fws 库相关

6.websocket 做这个消息通知 一个网页放一个socket 还是多个的问题  spa 可以放一个 但是 mpa要放多个 不然页面通信不了
4.redis

文件存,;|  url网络路径
3.文件選擇跟上（）文件系统 https://plugins.krajee.com/file-basic-usage-demo
有一张File表
id     path filename               url                          source             公共字段
生成    /a/b/c/   filename.file    http://qiniuyun/a/b/c/filename.file                qiniuyun           
/a/b/c    filename.fille    http://qiniuyun/a/b/c/filename.file                minior
/a/b/c     fielname.file     http://192.168.1.1:8080/files/a/b/c/filename.file  local
/a/b/c     filename.file    http://miniorurl/a/b/c/filename.file               minior                                     
删除图片  url->file_url    是否删除


文件实现 上传 预览即可

