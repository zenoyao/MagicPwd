@echo off

IF "%JAVA_HOME%"=="" SET LOCAL_JAVA=java
IF NOT "%JAVA_HOME%"=="" SET LOCAL_JAVA=%JAVA_HOME%\bin\java

set basedir=%~f0
:strip
set removed=%basedir:~-1%
set basedir=%basedir:~0,-1%
if NOT "%removed%"=="\" goto strip
set MAGICPWD_HOME=%basedir%

"%LOCAL_JAVA%w" -cp "%MAGICPWD_HOME%\lib\versioncheck.jar" JavaVersionChecker 1.6 1.7
if ErrorLevel 1 goto ExitForWrongJavaVersion

if not exist "%MAGICPWD_HOME%\update\changeList.xml" goto launchmagicpwd
SET TMP_CP="%MAGICPWD_HOME%\update\downloads\core\magicpwd.jar"
if not exist %TMP_CP% goto launchmagicpwd
dir /b "%MAGICPWD_HOME%\update\downloads\core\*.*" > %TEMP%\update-lib.tmp
FOR /F %%I IN (%TEMP%\update-lib.tmp) DO CALL "%MAGICPWD_HOME%\addpath.bat" "%MAGICPWD_HOME%\update\downloads\core\%%I"
SET UPDATE_CP=%TMP_CP%
SET UPDATE_PARMS=--log-config-file "%MAGICPWD_HOME%\update-log4j.properties" --magicpwd-home "%MAGICPWD_HOME%" %1 %2 %3 %4 %5 %6 %7 %8 %9
@"%LOCAL_JAVA%w" -cp %UPDATE_CP% -Dlog4j.defaultInitOverride=true -Dprompt=true net.sourceforge.magicpwd_sql.client.update.gui.installer.PreLaunchUpdateApplication %UPDATE_PARAMS%

:launchmagicpwd
@rem build MagicPwd's classpath
set TMP_CP="%MAGICPWD_HOME%\magicpwd.jar"
dir /b /s "%MAGICPWD_HOME%\lib\*.*" > %TEMP%\magicpwd-lib.tmp
FOR /F %%I IN (%TEMP%\magicpwd-lib.tmp) DO CALL "%MAGICPWD_HOME%\env\addpath.bat" "%%I"
SET MAGICPWD_CP=%TMP_CP%;%CLASSPATH%
echo "MAGICPWD_CP=%MAGICPWD_CP%"

SET TMP_PARMS=--log-config-file "%MAGICPWD_HOME%\log4j.properties" --magicpwd-home "%MAGICPWD_HOME%" %1 %2 %3 %4 %5 %6 %7 %8 %9

@rem Run with a command window.
@rem "%LOCAL_JAVA%" -cp %TMP_CP% com.magicpwd.MagicPwd %TMP_PARMS%

@rem To add translation working directories to your classpath edit and uncomment this line:
@rem start "MagicPwd SQL Client" /B "%LOCAL_JAVA%w" -Xmx256m -cp %TMP_CP%;<your working dir here> com.magicpwd.MagicPwd %TMP_PARMS%

@rem -Dsun.java2d.noddraw=true prevents performance problems on Win32 systems. 

@rem To change the language edit and uncomment this line:
@rem start "MagicPwd SQL Client" /B "%LOCAL_JAVA%w" -Xmx256m -Dsun.java2d.noddraw=true -cp %TMP_CP%;<your working dir here> -Duser.language=<your language here> com.magicpwd.MagicPwd %TMP_PARMS%

@rem Run with no command window. This may not work with older versions of Windows. Use the command above then.
start "MagicPwd" /B "%LOCAL_JAVA%w" -Xmx256m -Dsun.java2d.noddraw=true -cp %MAGICPWD_CP% com.magicpwd.MagicPwd %TMP_PARMS%

@rem Run the executable jar file with or without a cmd window. However the
@rem classes from the %CLASSPATH% environment variable will not be available.
@rem "%LOCAL_JAVA%" -jar "%MAGICPWD_HOME%\magicpwd.jar" %TMP_PARMS%
@rem start "MagicPwd SQL Client" /B "%LOCAL_JAVA%w" -Dsun.java2d.noddraw=true -jar "%MAGICPWD_HOME%\magicpwd.jar" %TMP_PARMS%

:ExitForWrongJavaVersion
exit
