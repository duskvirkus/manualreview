#!/bin/bash

mvn exec:java -Dexec.mainClass=sketch.App  -Dexec.args="-i ./in -o ./out"