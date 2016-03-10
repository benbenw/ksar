#!/bin/sh

DIRNAME=`dirname $0`

# Setup the JVM
if [ "x$JAVA_HOME" != "x" ]; then
    JAVA="$JAVA_HOME/bin/java"
else
    JAVA="java"
fi

if [ ! -f "$DIRNAME/ksar.jar" ] ; then
    echo "Unable to find ksar.jar"
    exit 1;
fi

exec $JAVA $JAVA_OPT -jar $DIRNAME/ksar.jar $@
