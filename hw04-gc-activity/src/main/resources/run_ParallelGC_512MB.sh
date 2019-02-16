#!/usr/bin/env bash

java -jar -XX:+UseParallelGC -Xms512m -Xmx512m -XX:MaxMetaspaceSize=512m ././../../../target/hw04-gc-activity.jar