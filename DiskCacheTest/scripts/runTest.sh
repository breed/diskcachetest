#!/bin/bash

BSIZE=2000
DELAY=0

if [ $# -ne 3 ]
then
	echo "$0 write_cache(0/1) barrier(0/1) sync(true/false) "
	exit 2
fi

cmd="java -jar DiskCacheTest.jar /mnt/foo $BSIZE $DELAY"

if [ $1 -eq 0 ]
then
	cmd="$cmd nocache"
fi

if [ $2 -eq 0 ]
then
	cmd="$cmd nobarrier"
fi

if [ $3 == "false" ]
then
	cmd="$cmd nosync"
fi

echo -n "$ITERATION:	"
eval "$cmd"
