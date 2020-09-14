#!/bin/bash

# The path of containing Defects4J bugs.
d4jData=/home/mrdrivingduck/Desktop/bugsDotJarData/

# The path of Defects4J git repository.
d4jPath=/home/mrdrivingduck/Desktop/defects4j/

# The fault localization metric used to calculate suspiciousness value of each code line.
metric=Ochiai

# The buggy project ID: e.g., Chart_1
# bugId=$1
# java -Xmx4g -cp "target/dependency/*" edu.lu.uni.serval.main.Main_Pos $d4jData $d4jPath $bugId $metric L

proj=ACCUMULO
bugs=("151_b007b22e")
for bug in ${bugs[@]}
do
    java -Xmx4g -cp "target/dependency/*" edu.lu.uni.serval.main.Main_Pos $d4jData $d4jPath ${proj}-${bug} $metric L
    echo ${proj}_${bug}
done
