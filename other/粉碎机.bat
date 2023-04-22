:说明
:使用 goto loop 和 shift 命令循环遍历所有传递给批处理文件的参数。
:使用 "del" 和 "rd" 命令删除文件和文件夹。
:使用 "2>nul" 可以禁止命令输出错误消息。
:使用 "if exist" 命令检查文件或文件夹是否仍然存在，如果是，则提示删除失败。
:使用 %~1 代替 %1 可以自动处理用户输入的文件路径中的引号。
:这样就可以批量删除多个文件啦，如果要删除的文件在同一个文件夹内，可以在文件夹路径后面加上 * 号，比如 C:\test\* 这样就可以删除test文件夹下所有文件。
@echo off

rem Check if any file or folder is specified
if "%~1" == "" (
    echo Please specify the file or folder name to delete
    echo.
    pause
    exit /b
)

rem Loop through all the passed arguments
:loop
if "%~1" == "" (
    goto end_loop
)

rem Delete the current file or folder
del /f /q "%~1" 2>nul
rd /s /q "%~1" 2>nul

rem Check if the file or folder still exists
if exist "%~1" (
    echo Failed to delete the file or folder: %~1
    echo.
) else (
    echo Successfully deleted the file or folder: %~1
    echo.
)

shift
goto loop

:end_loop
:pause
