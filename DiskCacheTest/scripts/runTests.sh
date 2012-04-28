#!/bin/bash
export ITERATION=0
for (( ITERATION=0; ITERATION<100; ITERATION++ ))
do
	./runTest.sh 1 1 true
	./runTest.sh 1 0 true
	./runTest.sh 0 1 true
	./runTest.sh 0 0 true
	./runTest.sh 1 1 false
done
