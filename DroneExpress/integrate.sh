#!/bin/bash

mvn clean package;

if [ -f droneexpress-optimizer/target/droneexpress-optimizer.war ]; then
   echo "=== Tests ok ! ===";
   echo "=== Time to push... ===";
   mvn clean

   git push origin master

else
   echo "=== Integration failure. Try again ;) ====";
fi
