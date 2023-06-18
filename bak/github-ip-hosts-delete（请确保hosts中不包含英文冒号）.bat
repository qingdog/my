@echo off
setlocal enabledelayedexpansion

rem hosts文件路径
set "hosts_file=%WINDIR%\system32\drivers\etc\hosts"
rem 通过url更新
set "update_url=https://raw.hellogithub.com/hosts"

rem 根据内容标记符号
set "start_flag=0"

rem 将 hosts 文件中的内容复制到临时文件 temp.txt 中, > temp表示覆盖文件
type %hosts_file% > temp.txt
rem 清空文件
cd. > %hosts_file%
::type null > %hosts_file%
::echo # > C:\Windows\System32\drivers\etc\hosts
rem 先将上一次更新的删掉，保留原有域名ip映射
echo.
echo 从 %hosts_file% 读取文件中每一行
rem 使用 findstr /n 读取临时文件 temp.txt 的每一行，并在行前添加行号
for /f "delims=" %%i in ('findstr /n .* temp.txt') do (
	set "line=%%i"
	rem 删除行号
	set "line=!line:*:=!"
	rem 如果读到的行的前21个字符是 "# GitHub520 Host Start"，设置开始标记，停止写入hosts
	if "!line:~0,22!" == "# GitHub520 Host Start" (
		set "start_flag=1"
		echo 从该行开始删除：%%i
	)
	rem 如果没有达到开始标记，则将该行写入空的 hosts 文件中
	if not "!start_flag!" == "1" echo.!line! >> %hosts_file%
	rem 如果读到的行是 "# GitHub520 Host End"，设置结束标记，继续循环写入hosts
	::if "!line!" == "# GitHub520 Host End" set "start_flag=0"
	rem 如果读到的行包含 "# GitHub520 Host Start"，设置开始标记
	if not "!line:# GitHub520 Host End=!" == "!line!" (
		rem 如果当前行不等于空行
		if not "!line!" == "" (
			set "start_flag=0"
			echo 删除到最后一行：%%i
			echo.
		)
	)
)
del temp.txt
ipconfig -flushdns
pause