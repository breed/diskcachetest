#!/bin/bash
echo starting drive
sudo ./power.expect on > o 2> e
sleep 15
sudo hdparm -W $1 /dev/sdc
