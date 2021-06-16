### 记录近期工作
0.生成代码页面,以及生成限制 不可重复生成  ，以及回退生成(删除菜单以及代码)
formType                    formType             无
1          2    __    3             4        5              6        7            8        9         10                                                                                 
formType* itemType formType(*) sqlType* queryType(*)  queryExp* dictcode(*) itemName* itemDesc*  listShow listLength(*) isSort(*) isUnique(*) isMust(*)  
不显示      
文本 String  input       输入框             varchar(128)  like                                         1     40              0            0          0                                                                     
多行文本 String textarea   输入框             varchar(255) like                                          0     80              0           0            0        
富文本 String editor     输入框             text        like                                           0     80              0             0         0         
下拉 Integer #select    下拉框/单选框/复选框  char(2)      eq         *                                  1     40              1           0           0          
单选 Integer #radio     下拉框/单选框/复选框  char(2)     eq          *                                  1     40              1          0            0         
多选 String #checkbox   下拉框/单选框/复选框  varchar() eq|eq|eq      *                                  1     80              1           0           0         
日期 Date  #datapicker 日期框               DATETIME  bettwen                                          1     60              1          0            0         
数字 Integer/Double/float/deciaml input 数字框/数据范围框（根据是否是bettwen）  int(11)/double/float/decimal(10,2) eq/gt/ge/lt/le/bettewn                             1     40              1          0            0            (*)           
/根据组件默认/根据JAVA类默认/根据组件默认（根据组件默认）/根据组件默认/根据组件默认/根据组件默认/默认非/下拉自选/当前仅当数默认是

/字/描/组件
/JAVA/sql/是否可查(查表达式)/是否展列(列长)/必/排/唯/典[拉单复选]/合[数]（可查询的下拉选字典）
/字/描/组件/JAVA/sql
/是否可查(查表达式)/是否展列(列长)/必/排/唯/典[拉单复选]/合[数]（可查询的下拉选字典）




3.文件選擇跟上（）文件系统
文件存,;|  url网络路径

有一张File表
id     path filename               url                          source             公共字段
生成    /a/b/c/   filename.file    http://qiniuyun/a/b/c/filename.file                qiniuyun           
       /a/b/c    filename.fille    http://qiniuyun/a/b/c/filename.file                minior
       /a/b/c     fielname.file     http://192.168.1.1:8080/files/a/b/c/filename.file  local
       /a/b/c     filename.file    http://miniorurl/a/b/c/filename.file               minior                                     
删除图片  url->file_url    是否删除


5.rocketmq 如生成代码

4.redis
6.websocket







