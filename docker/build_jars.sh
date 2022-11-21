#!/bin/sh

set -e
set -x

echo "Current location: $(pwd)"
(cd .. && mvn clean install -DskipTests)
ls -alh ../

#rm -rf jars/*
echo "Move jars"
mv ../target/gerimedica-1.0-SNAPSHOT.jar jars/gerimedica.jar
