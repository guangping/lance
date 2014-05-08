#!/bin/bash
cd `dirname $0`
BIN_DIR=`pwd`
cd ..
DEPLOY_DIR=`pwd`
CLASSES_DIR=$DEPLOY_DIR/classes
CONFIG_DIR=$DEPLOY_DIR/config
LIB_DIR=$DEPLOY_DIR/lib
LIB_JARS=`ls $LIB_DIR|grep .jar|awk '{print "'$LIB_DIR'/"$0}'|tr "\n" ":"`
LOGS_FILE=``



LOGS_DIR=""
if [ -n "$LOGS_FILE" ]; then
    LOGS_DIR=`dirname $LOGS_FILE`
else
    LOGS_DIR=$DEPLOY_DIR/logs
fi
if [ ! -d $LOGS_DIR ]; then
    mkdir $LOGS_DIR
fi
STDOUT_FILE=$LOGS_DIR/stdout.log

PIDS=`ps -f | grep java | grep "$DEPLOY_DIR" |awk '{print $2}'`
if [ -z "$PIDS" ]; then
	nohup java -classpath $LIB_JARS:$CLASSES_DIR:$CONFIG_DIR com.framework.file.discover.Main > $STDOUT_FILE 2>&1 &
	echo "OK!"
	PIDS=`ps -f | grep java | grep "$DEPLOY_DIR" | awk '{print $2}'`
	echo "PID: $PIDS"
	echo "wssmall is start!"
else 
    echo "wssmall aleready start!"	
fi



