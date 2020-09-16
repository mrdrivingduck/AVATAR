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

proj=FLINK
bugs=("2817_5dfc897b")
for bug in ${bugs[@]}
do
    java -Xmx4g -cp "target/dependency/*" edu.lu.uni.serval.main.Main_Pos $d4jData $d4jPath ${proj}-${bug} $metric L
    echo ${proj}_${bug}
done

proj=LOG4J2
bugs=("114_afcf92eb" "127_029e79da" "813_0bea17d7" "1061_86d8944f" "1062_4cf831b6" "1153_8acedb4e")
for bug in ${bugs[@]}
do
    java -Xmx4g -cp "target/dependency/*" edu.lu.uni.serval.main.Main_Pos $d4jData $d4jPath ${proj}-${bug} $metric L
    echo ${proj}_${bug}
done

proj=MATH
bugs=("309_0596e314" "329_6dd3724b" "344_a0b4b4b7" "482_6d6649ef" "631_ebc61de9" "781_3c4cb189" "929_cedf0d27" "1204_a56d4998" "1252_09fe956a" "1283_9e0c5ad4")
for bug in ${bugs[@]}
do
    java -Xmx4g -cp "target/dependency/*" edu.lu.uni.serval.main.Main_Pos $d4jData $d4jPath ${proj}-${bug} $metric L
    echo ${proj}_${bug}
done

proj=OAK
bugs=("738_8ed779dc" "1297_73cc2442" "2219_f2740ce1" "2288_57bd2dc5" "3089_ba38c380" "3367_06812d25" "3517_24f7f60a" "3792_94110f21" "4170_2a489d05")
for bug in ${bugs[@]}
do
    java -Xmx4g -cp "target/dependency/*" edu.lu.uni.serval.main.Main_Pos $d4jData $d4jPath ${proj}-${bug} $metric L
    echo ${proj}_${bug}
done