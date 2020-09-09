#!/bin/bash

# The path of containing Defects4J bugs.
d4jData=/home/mrdrivingduck/Desktop/quixbugs-experiment/

# The path of Defects4J git repository.
d4jPath=/home/mrdrivingduck/Desktop/quixbugs-experiment/

# The fault localization metric used to calculate suspiciousness value of each code line.
metric=Ochiai

# The buggy project ID: e.g., Chart_1
# bugId=$1
# java -Xmx4g -cp "target/dependency/*" edu.lu.uni.serval.main.Main_Pos $d4jData $d4jPath $bugId $metric L

projs=(FIND_FIRST_IN_SORTED)

for proj in ${projs[@]}
do
    java -Xmx4g -cp "target/dependency/*" edu.lu.uni.serval.main.Main_Pos $d4jData $d4jPath ${proj} $metric L
    echo ${proj}
done
