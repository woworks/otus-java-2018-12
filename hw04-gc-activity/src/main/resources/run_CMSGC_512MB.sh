#!/usr/bin/env bash

java -jar -XX:+UseConcMarkSweepGC -XX:+UseParNewGC -XX:+CMSParallelRemarkEnabled -XX:+UseCMSInitiatingOccupancyOnly -Xms512m -Xmx512m -XX:MaxMetaspaceSize=512m ././../../../target/hw04-gc-activity.jar