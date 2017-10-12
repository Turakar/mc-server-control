#!/bin/bash

if screen -list | grep -q "minecraft-$1"; then
  screen -S minecraft-$1 -X stuff "stop^M"
fi

for i in {1..10}
do
  if ! screen -list | grep -q "minecraft-$1"; then
    exit 0
  fi
  sleep 1
done

exit 1
