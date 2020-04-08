#!/bin/bash

# The path of containing Defects4J bugs.
d4jData=/home/mrdrivingduck/d4jData/

# The path of Defects4J git repository.
d4jPath=/home/mrdrivingduck/defects4j/

# The fault localization metric used to calculate suspiciousness value of each code line.
metric=Ochiai

# The buggy project ID: e.g., Chart_1
# bugId=$1
# java -Xmx4g -cp "target/dependency/*" edu.lu.uni.serval.main.Main_Pos $d4jData $d4jPath $bugId $metric L


proj=Chart
for bug in $(seq 1 26)
do
    java -Xmx4g -cp "target/dependency/*" edu.lu.uni.serval.main.Main_Pos $d4jData $d4jPath ${proj}_${bug} $metric L
done