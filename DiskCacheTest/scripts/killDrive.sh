#!/bin/bash
echo killing drive
sudo umount /mnt
sudo ./power.expect off > o 2> e
sleep 20
