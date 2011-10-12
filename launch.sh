#! /bin/sh


# We detect the java executable to use according to the following algorithm:
#
# 1. If it is located in JAVA_HOME, then we use that; or
# 2. If the one used by the IzPack installer is available then use that, otherwise
# 3. Use the java that is in the command path.
# 
if [ -d "$JAVA_HOME" -a -x "$JAVA_HOME/bin/java" ]; then
	JAVACMD="$JAVA_HOME/bin/java"
else
	JAVACMD=java
fi

# Are we running within Cygwin on some version of Windows or on Mac OS X?
cygwin=false;
macosx=false;
case "`uname -s`" in
	CYGWIN*) 
		cygwin=true 
		;;
	Darwin*) 
		macosx=true
		;;
esac

# MagicPwd home.
if $macosx ; then
	MAGICPWD_HOME='./Contents/Resources/Java'
else 
	MAGICPWD_HOME='./'
fi

# MagicPwd home in Unix format.
if $cygwin ; then
	UNIX_STYLE_HOME=`cygpath "$MAGICPWD_HOME"`
else
	UNIX_STYLE_HOME="$MAGICPWD_HOME"
fi

cd "$UNIX_STYLE_HOME"

# Check to see if the JVM meets the minimum required to run MagicPwd and inform the user if not and skip 
# launch.  versioncheck.jar is a special jar file which has been compiled with javac version 1.2.2, which 
# should be able to be run by that version or higher. The arguments to JavaVersionChecker below specify the 
# minimum acceptable version (first arg) and any other acceptable subsequent versions.  <MAJOR>.<MINOR> should 
# be all that is necessary for the version form. 
$JAVACMD -cp "$UNIX_STYLE_HOME/lib/versioncheck.jar" JavaVersionChecker 1.6 1.7
if [ "$?" = "1" ]; then
	exit
fi

# First entry in classpath is the MagicPwd application.
TMP_CP="$UNIX_STYLE_HOME/magicpwd.jar"

# Then add all library jars to the classpath.
for a in "$UNIX_STYLE_HOME"/lib/*; do
	TMP_CP="$TMP_CP":"$a"
done

# Set the update app's classpath to use jars in download area first, then the installed jars
UPDATE_CP=$TMP_CP
for a in "$UNIX_STYLE_HOME"/update/downloads/core/*; do
	UPDATE_CP="$a":"$UPDATE_CP"
done


# Now add the system classpath to the classpath. If running
# Cygwin we also need to change the classpath to Windows format.
if $cygwin ; then
	TMP_CP=`cygpath -w -p $TMP_CP`
	UPDATE_CP=`cygpath -w -p $UPDATE_CP`
	TMP_CP=$TMP_CP';'$CLASSPATH
	UPDATE_CP=$UPDATE_CP';'$CLASSPATH
else
	TMP_CP=$TMP_CP:$CLASSPATH
	UPDATE_CP=$UPDATE_CP:$CLASSPATH
fi

if $macosx ; then
	# Define mac-specific system properties if running on Mac OS X
	MACOSX_UPDATER_PROPS="-Dapple.laf.useScreenMenuBar=true -Dcom.apple.mrj.application.apple.menu.about.name=MagicPwd"
	MACOSX_MAGICPWD_PROPS="-Dapple.laf.useScreenMenuBar=true -Dcom.apple.mrj.application.apple.menu.about.name=MagicPwd"
	NATIVE_LAF_PROP="--native-laf"
fi

if $macosx ; then
	# macosx provides unknown args to the script, causing MagicPwd to bail..
	SCRIPT_ARGS=""
else
	SCRIPT_ARGS="$1 $2 $3 $4 $5 $6 $7 $8 $9"
fi

# Launch MagicPwd application
$JAVACMD -Xmx256m -cp "$TMP_CP" $MACOSX_MAGICPWD_PROPS com.magicpwd.MagicPwd --log-config-file "$UNIX_STYLE_HOME"/log4j.properties --magicpwd-home "$UNIX_STYLE_HOME" $NATIVE_LAF_PROP $SCRIPT_ARGS
