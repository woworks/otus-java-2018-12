#!/usr/bin/env bash

java -jar -XX:+UseG1GC -Xms512m -Xmx512m -XX:MaxMetaspaceSize=512m ././../../../target/hw04-gc-activity.jar