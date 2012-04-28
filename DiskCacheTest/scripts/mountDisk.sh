#!/bin/bash
sudo mount -o barrier=$1 /dev/sdc2 /mnt
sudo chmod a+w /mnt
