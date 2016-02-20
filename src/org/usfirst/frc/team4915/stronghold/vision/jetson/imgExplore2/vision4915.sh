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
    printf "[+] $1\n"
}

f_getpid()
{
    local pid=`ps -Af | grep $1 | grep -v grep | awk '{print $2}' | head -n 1`
    echo "$pid"
}

f_start()
{
    f_message "Starting vision4915 mjpg services on port 80"
    rsync -a --delete imgServer.home /var/tmp

    # nb: mjpg streamer requires quotes around -i and -o lines
    #     because they embody private parameters.
    # nb: messages are logged to /var/log/syslog
    # more details here:
    # http://rpm.pbone.net/index.php3/stat/45/idpl/23775320/numer/1/nazwa/mjpg_streamer 
    www=/var/tmp/imgServer.home
    /usr/local/bin/mjpg_streamer \
        -b \
        -i "/usr/local/lib/input_file.so -f $www -n currentImage.jpg" \
        -o "/usr/local/lib/output_http.so -w $www -p 80" \
    f_message "  mjpg_stream started"
}

f_stop()
{
    f_message "Stopping vision4915 services"
    pid=`f_getpid mjpg_streamer`
    if [ -n "$pid" ];
    then
        kill $pid
    fi
}

visdir="/home/ubuntu/vision"

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
        f_message 'vision4915 status....'
        pid=`f_getpid bin/mjpg_streamer`
        if [ -n "$pid" ];
        then
            f_message  "  -mjpg_streamer pid ${pid}, started with:"
            cat /proc/${pid}/cmdline; 
            printf "\n"
        else
            f_message  "  -could not find mjpg_streamer running"
        fi
        pid=`f_getpid ./imgExplore` 
        if [ -n "$pid" ];
        then
            f_message "  -imgExplore pid ${pid}, started with:"
            cat /proc/${pid}/cmdline 
            printf "\n"
        else
            f_message "  -could not find imgExplore running"
        fi
        ;;

    *)
        f_message "Usage: $0 {start|stop|status|restart}"
        exit 1
        ;;
esac
exit 0
