#!/bin/sh
### BEGIN INIT INFO
# Provides:         startvision
# Required-Start:   $network
# Required-Stop:    $network
# Default-Start:    2 3 4 5
# Default-Stop;     0 1 6
# Short-Description: mjpg_streamer for webcam
# Description:  streams /dev/video0 through imgExplore to 
#               http://IP/?action=stream
### END INIT INFO

f_message()
{
    printf "[+] $1"
}

f_getpid()
{
    local pid=`ps -Af | grep $1 | grep -v grep | awk '{print $2}' | head -n 1`
    echo "$pid"
}

f_start()
{
    f_message "Starting vision4915 services"
    f_message "  installing website"
    rsync -a --delete imgServer.home /var/tmp

    f_message "  starting mjpg_streamer on port 80"
    LD_LIBRARY_PATH=/usr/local/lib \
        /usr/local/bin/mjpg_streamer \
        -b \
        -i "input_file.so -f /var/tmp/imgServer.home -n currentImage.jpg" \
        -o "output_http.so -w /var/tmp/imgServer.home -p 80"
    f_message "  mjpg_stream started"

    f_message "Starting imgExplore.py"
    su ubuntu -c "python ./imgExplore.py \
                    --algorithm 5 \
                    --stashinterval 1 \
                    --nodisplay \
                    --daemonize >> /var/tmp/imgExplore.log 2>&1"
    f_message "  imgExplore.py started"
}

f_stop()
{
    f_message "Stopping vision4915 services"
    pid=`f_getpid mjpg_streamer`
    if [ -n "$pid" ];
    then
        kill $pid
    fi
    # the next line is a bit of a big hammer.. we'd need a pid
    # file to be more surgical
    pid=`f_getpid imgExplore`
    if [ -n "$pid" ];
    then
        kill $pid
    fi
    f_message "  vision4915 stopped"
}

visdir="/home/ubuntu/2016-Stronghold/src/org/usfirst/frc/team4915/stronghold/vision"
if [ ! -d $visdir ] ; then
    visdir="/home/ubuntu/vision"
fi

cd $visdir/jetson/imgExplore2

case "$1" in
    start)
        f_start
        ;;

    stop)
        f_stop
        ;;

    restart)
        f_stop
        f_start
        ;;

    status)
        f_message 'vision4915 status....\n'
        pid=`f_getpid mjpg_streamer`
        if [ -n "$pid" ];
        then
            echo "mjpg_streamer is running with pid ${pid}, started with:"
            cat /proc/${pid}/cmdline; echo '\n'
        else
            echo  "Could not find mjpg_streamer running"
        fi
        pid=`f_getpid imgExplore` 
        if [ -n "$pid" ];
        then
            echo "imgExplore is running with pid ${pid}, started with:"
            cat /proc/${pid}/cmdline; echo '\n'
        else
            f_message "Could not find imgExplore running"
        fi
        ;;

    *)
        f_message "Usage: $0 {start|stop|status|restart}"
        exit 1
        ;;
esac
exit 0
