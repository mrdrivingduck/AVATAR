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

projs=(BITCOUNT BREADTH_FIRST_SEARCH BUCKETSORT DEPTH_FIRST_SEARCH DETECT_CYCLE FIND_FIRST_IN_SORTED \
FIND_IN_SORTED FLATTEN GCD GET_FACTORS HANOI IS_VALID_PARENTHESIZATION KHEAPSORT KNAPSACK KTH LCS_LENGTH \
LEVENSHTEIN LIS LONGEST_COMMON_SUBSEQUENCE MAX_SUBLIST_SUM MERGESORT NEXT_PALINDROME NEXT_PERMUTATION \
PASCAL POSSIBLE_CHANGE POWERSET QUICKSORT REVERSE_LINKED_LIST RPN_EVAL SHORTEST_PATH_LENGTH \
SHORTEST_PATH_LENGTHS SHORTEST_PATHS SIEVE SQRT SUBSEQUENCES TO_BASE TOPOLOGICAL_ORDERING WRAP)

for proj in ${projs[@]}
do
    java -Xmx4g -cp "target/dependency/*" edu.lu.uni.serval.main.Main_Pos $d4jData $d4jPath ${proj} $metric L
    echo ${proj}
done
