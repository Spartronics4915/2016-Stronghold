#!/bin/sh

f_getpid()
{
    local pid=`ps -Af | grep $1 | grep -v grep | awk '{print $2}' | head -n 1`
    echo "$pid"
}

f_getinterval()
{
    echo "1.0 / $1" | bc -l
}

pid=`f_getpid ./imgExplore.py`
if [ -n "$pid" ];
then
    kill $pid
fi

exploredir="/home/ubuntu/vision/jetson/imgExplore2"
cd $exploredir

python ./imgExplore.py \
        --algorithm 5 \
        --stashinterval `f_getinterval $1` \
        --nodisplay \
        --daemonize >> /var/tmp/imgExplore.log 2>&1
