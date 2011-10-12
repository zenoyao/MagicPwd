if [ -e "magicpwd.desktop" ]
then
echo "#!/usr/bin/env xdg-open" > magicpwd.desktop
else
echo "#!/usr/bin/env xdg-open" >> magicpwd.desktop
fi

echo "" >> magicpwd.desktop
echo "[Desktop Entry]" >> magicpwd.desktop
echo "Encoding=UTF-8" >> magicpwd.desktop
echo "Version=1.0" >> magicpwd.desktop
echo "Type=Application" >> magicpwd.desktop
echo "Terminal=false" >> magicpwd.desktop
echo "Icon[zh_CN]=$(pwd)/logo/logo.svg" >> magicpwd.desktop
echo "Exec=$(pwd)/magicpwd.jar" >> magicpwd.desktop
echo "Name[zh_CN]=魔方密码" >> magicpwd.desktop
echo "Comment[zh_CN]=开源的跨平台密码管理软件！" >> magicpwd.desktop
echo "Name=魔方密码" >> magicpwd.desktop
echo "Comment=开源的跨平台密码管理软件！" >> magicpwd.desktop
echo "Icon=$(pwd)/logo/logo.svg" >> magicpwd.desktop
echo "" >> magicpwd.desktop

if [ -d ~/desktop/ ]
then
mv magicpwd.desktop ~/desktop/magicpwd.desktop
chmod 777 ~/desktop/magicpwd.desktop
fi

if [ -d ~/桌面/ ]
then
mv magicpwd.desktop ~/桌面/magicpwd.desktop
chmod 777 ~/桌面/magicpwd.desktop
fi

echo "魔方密码安装成功！"