Set oFso = CreateObject("Scripting.FileSystemObject")
strCurPath = oFso.GetParentFolderName(Wscript.ScriptFullName)

Set WshShell = WScript.CreateObject("WScript.Shell")
StrPath = "" & WshShell.Environment("user").Item("CLASSPATH")
if InStr(LCase(StrPath), LCase(strCurPath)) < 1 Then
WshShell.Environment("user").Item("CLASSPATH") = strCurPath & "\lib\;" & StrPath
End If

StrDesktop = WshShell.SpecialFolders("Desktop")

Set oLnk = WshShell.CreateShortcut(StrDesktop & "\魔方密码.lnk")
oLnk.TargetPath = strCurPath & "\magicpwd.jar"
oLnk.WindowStyle = 1
oLnk.Hotkey = "Ctrl+Alt+M"
oLnk.IconLocation = strCurPath & "\logo\logo.ico, 0"
oLnk.Description = "开源的跨平台密码管理软件！"
oLnk.WorkingDirectory = strCurPath & "\"
oLnk.Save

Set oUrl = WshShell.CreateShortcut(StrDesktop & "\魔方密码首页.url")
oUrl.TargetPath = "http://magicpwd.com/"
oUrl.Save

oVar = MsgBox ("魔方密码安装成功！", 0, "友情提示")