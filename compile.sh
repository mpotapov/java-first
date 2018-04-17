#!/bin/sh
find src/Aircraft/*.java src/Weather/*.java > sources.txt
javac -d . -sourcepath ./src @sources.txt
echo "\n\033[22;32mDone\033[0m\n"