### 记录发生的BUG
关于 savegroup下 unique的逻辑
1.新增 size为0 
2.修改 size为0（修改了值）， size为1 （不变）

当没有id时 size为0
当有id 时           id+name =1时 通过  id+name=0时通过
//id
