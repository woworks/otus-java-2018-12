#!/usr/bin/env bash

java -jar -XX:+UseSerialGC -Xms512m -Xmx512m -XX:MaxMetaspaceSize=512m ././../../../target/hw04-gc-activity.jar