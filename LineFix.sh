#!/bin/bash

# The path of containing Defects4J bugs.
d4jData=/home/mrdrivingduck/Desktop/d4jData/

# The path of Defects4J git repository.
d4jPath=/home/mrdrivingduck/Desktop/defects4j/

# The fault localization metric used to calculate suspiciousness value of each code line.
metric=Ochiai

# The buggy project ID: e.g., Chart_1
# bugId=$1
# java -Xmx4g -cp "target/dependency/*" edu.lu.uni.serval.main.Main_Pos $d4jData $d4jPath $bugId $metric L


proj=Chart
for bug in $(seq 1 26)
do
    # java -Xmx4g -cp "target/dependency/*" edu.lu.uni.serval.main.Main_Pos $d4jData $d4jPath ${proj}_${bug} $metric L
    echo ${proj}_${bug}
done

proj=Cli
for bug in $(seq 1 5; seq 7 40)
do
    # java -Xmx4g -cp "target/dependency/*" edu.lu.uni.serval.main.Main_Pos $d4jData $d4jPath ${proj}_${bug} $metric L
    echo ${proj}_${bug}
done

proj=Closure
for bug in $(seq 1 62; seq 64 92; seq 94 176)
do
    # java -Xmx4g -cp "target/dependency/*" edu.lu.uni.serval.main.Main_Pos $d4jData $d4jPath ${proj}_${bug} $metric L
    echo ${proj}_${bug}
done

proj=Codec
for bug in $(seq 1 18)
do
    # java -Xmx4g -cp "target/dependency/*" edu.lu.uni.serval.main.Main_Pos $d4jData $d4jPath ${proj}_${bug} $metric L
    echo ${proj}_${bug}
done

proj=Collections
for bug in $(seq 25 28)
do
    # java -Xmx4g -cp "target/dependency/*" edu.lu.uni.serval.main.Main_Pos $d4jData $d4jPath ${proj}_${bug} $metric L
    echo ${proj}_${bug}
done

proj=Compress
for bug in $(seq 1 47)
do
    # java -Xmx4g -cp "target/dependency/*" edu.lu.uni.serval.main.Main_Pos $d4jData $d4jPath ${proj}_${bug} $metric L
    echo ${proj}_${bug}
done

proj=Csv
for bug in $(seq 1 16)
do
    # java -Xmx4g -cp "target/dependency/*" edu.lu.uni.serval.main.Main_Pos $d4jData $d4jPath ${proj}_${bug} $metric L
    echo ${proj}_${bug}
done

proj=Gson
for bug in $(seq 1 18)
do
    # java -Xmx4g -cp "target/dependency/*" edu.lu.uni.serval.main.Main_Pos $d4jData $d4jPath ${proj}_${bug} $metric L
    echo ${proj}_${bug}
done

proj=JacksonCore
for bug in $(seq 1 26)
do
    # java -Xmx4g -cp "target/dependency/*" edu.lu.uni.serval.main.Main_Pos $d4jData $d4jPath ${proj}_${bug} $metric L
    echo ${proj}_${bug}
done

proj=JacksonDatabind
for bug in $(seq 1 112)
do
    # java -Xmx4g -cp "target/dependency/*" edu.lu.uni.serval.main.Main_Pos $d4jData $d4jPath ${proj}_${bug} $metric L
    echo ${proj}_${bug}
done

proj=JacksonXml
for bug in $(seq 1 6)
do
    # java -Xmx4g -cp "target/dependency/*" edu.lu.uni.serval.main.Main_Pos $d4jData $d4jPath ${proj}_${bug} $metric L
    echo ${proj}_${bug}
done

proj=Jsoup
for bug in $(seq 1 93)
do
    # java -Xmx4g -cp "target/dependency/*" edu.lu.uni.serval.main.Main_Pos $d4jData $d4jPath ${proj}_${bug} $metric L
    echo ${proj}_${bug}
done

proj=JxPath
for bug in $(seq 1 22)
do
    # java -Xmx4g -cp "target/dependency/*" edu.lu.uni.serval.main.Main_Pos $d4jData $d4jPath ${proj}_${bug} $metric L
    echo ${proj}_${bug}
done

proj=Lang
for bug in $(seq 1 1; seq 3 65)
do
    # java -Xmx4g -cp "target/dependency/*" edu.lu.uni.serval.main.Main_Pos $d4jData $d4jPath ${proj}_${bug} $metric L
    echo ${proj}_${bug}
done

proj=Math
for bug in $(seq 1 106)
do
    # java -Xmx4g -cp "target/dependency/*" edu.lu.uni.serval.main.Main_Pos $d4jData $d4jPath ${proj}_${bug} $metric L
    echo ${proj}_${bug}
done

proj=Mockito
for bug in $(seq 1 38)
do
    # java -Xmx4g -cp "target/dependency/*" edu.lu.uni.serval.main.Main_Pos $d4jData $d4jPath ${proj}_${bug} $metric L
    echo ${proj}_${bug}
done

proj=Time
for bug in $(seq 1 20; seq 22 27)
do
    # java -Xmx4g -cp "target/dependency/*" edu.lu.uni.serval.main.Main_Pos $d4jData $d4jPath ${proj}_${bug} $metric L
    echo ${proj}_${bug}
done
