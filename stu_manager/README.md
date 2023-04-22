## sed
```bash
sed -i "/# GitHub520 Host Start/Q" c:/Windows/System32/drivers/etc/hosts
```
* -i 参数表示直接在原文件上进行修改。
* 其中, "/# GitHub520 Host Start/Q"是一个sed的匹配模式。
* 在这个命令中，sed 在文件c:/Windows/System32/drivers/etc/hosts中查找 "# GitHub520 Host Start" 这行并且删除这行及后面所有的行。
