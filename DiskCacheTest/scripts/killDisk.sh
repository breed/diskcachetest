#!/bin/bash
exec &> killDisk.out
echo killing drive
sudo ./power.expect off 
sleep 20
