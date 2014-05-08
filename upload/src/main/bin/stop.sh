#!/bin/bash
cd `dirname $0`
BIN_DIR=`pwd`
cd ..
DEPLOY_DIR=`pwd`

PIDS=`ps -f | grep java | grep "$DEPLOY_DIR" |awk '{print $2}'`
if [ -z "$PIDS" ]; then
    echo "ERROR: The wssmall does not started!"
    exit 1
fi


echo -e "Stopping the wssmall ...\c"
for PID in $PIDS ; do
    kill -9 $PID 
done

echo "OK!"
echo "PID: $PIDS"
