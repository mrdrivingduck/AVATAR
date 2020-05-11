#!/bin/bash

# The path of containing Defects4J bugs.
d4jData=~/Desktop/d4jData/

# proj=Chart
# for bug in $(seq 1 26)
# do
#     cd ${d4jData}${proj}_${bug}
#     echo ${proj}_${bug}
#     ls | grep ^build$ | wc -l
#     ls | grep ^build-tests$ | wc -l
#     ls | grep ^source$ | wc -l
#     ls | grep ^tests$ | wc -l
# done

# proj=Cli
# for bug in $(seq 1 5; seq 7 40)
# do
#     cd ${d4jData}${proj}_${bug}
#     echo ${proj}_${bug}
#     cd src
#     ls | grep ^java$ | wc -l
#     ls | grep ^test$ | wc -l
#     cd ..
#     cd target
#     ls | grep ^classes$ | wc -l
#     ls | grep ^test-classes$ | wc -l
#     cd ..
# done

# proj=Closure
# for bug in $(seq 1 62; seq 64 92; seq 94 176)
# do
#     cd ${d4jData}${proj}_${bug}
#     echo ${proj}_${bug}
#     cd build
#     ls | grep ^test$ | wc -l
#     ls | grep ^classes$ | wc -l
#     cd ..
#     ls | grep ^src$ | wc -l
#     ls | grep ^test$ | wc -l
# done

# proj=Codec
# for bug in $(seq 1 18)
# do
#     cd ${d4jData}${proj}_${bug}
#     echo ${proj}_${bug}
#     cd target
#     ls | grep ^tests$ | wc -l
#     ls | grep ^classes$ | wc -l
#     cd ..
#     cd src
#     ls | grep ^java$ | wc -l
#     ls | grep ^test$ | wc -l
#     cd ..
    # cd src/main
    #     ls | grep ^java$ | wc -l
    # cd ../../
    # cd src/test
    #     ls | grep ^java$ | wc -l
    # cd ../../
# done

# proj=Collections
# for bug in $(seq 25 28)
# do
#     cd ${d4jData}${proj}_${bug}
#     echo ${proj}_${bug}
#     cd target
#     ls | grep ^tests$ | wc -l
#     ls | grep ^classes$ | wc -l
#     cd ../
#     cd src/main
#     ls | grep ^java$ | wc -l
#     cd ../../
#     cd src/test
#     ls | grep ^java$ | wc -l
#     cd ../../
# done

# proj=Compress
# for bug in $(seq 1 47)
# do
#     cd ${d4jData}${proj}_${bug}
#     echo ${proj}_${bug}
#     cd target
#         ls | grep ^test-classes$ | wc -l
#         ls | grep ^classes$ | wc -l
#     cd ../
#     cd src/main
#         ls | grep ^java$ | wc -l
#     cd ../../
#     cd src/test
#         ls | grep ^java$ | wc -l
#     cd ../../
# done

# proj=Csv
# for bug in $(seq 1 16)
# do
#     cd ${d4jData}${proj}_${bug}
#     echo ${proj}_${bug}
#     cd target
#         ls | grep ^test-classes$ | wc -l
#         ls | grep ^classes$ | wc -l
#     cd ../
#     cd src/main
#         ls | grep ^java$ | wc -l
#     cd ../../
#     cd src/test
#         ls | grep ^java$ | wc -l
#     cd ../../
# done

# proj=Gson
# for bug in $(seq 1 18)
# do
#     cd ${d4jData}${proj}_${bug}
#     echo ${proj}_${bug}
#     cd target
#         ls | grep ^test-classes$ | wc -l
#         ls | grep ^classes$ | wc -l
#     cd ../
#     cd gson/src/main
#         ls | grep ^java$ | wc -l
#     cd ../../../
#     cd gson/src/test
#         ls | grep ^java$ | wc -l
#     cd ../../../
# done

# proj=JacksonCore
# for bug in $(seq 1 26)
# do
#     cd ${d4jData}${proj}_${bug}
#     echo ${proj}_${bug}
#     cd target
#         ls | grep ^test-classes$ | wc -l
#         ls | grep ^classes$ | wc -l
#     cd ../
#     cd src/main
#         ls | grep ^java$ | wc -l
#     cd ../../
#     cd src/test
#         ls | grep ^java$ | wc -l
#     cd ../../
# done

# proj=JacksonDatabind
# for bug in $(seq 1 112)
# do
#     cd ${d4jData}${proj}_${bug}
#     echo ${proj}_${bug}
#     cd target
#         ls | grep ^test-classes$ | wc -l
#         ls | grep ^classes$ | wc -l
#     cd ../
#     cd src/main
#         ls | grep ^java$ | wc -l
#     cd ../../
#     cd src/test
#         ls | grep ^java$ | wc -l
#     cd ../../
# done

# proj=JacksonXml
# for bug in $(seq 1 6)
# do
#     cd ${d4jData}${proj}_${bug}
#     echo ${proj}_${bug}
#     cd target
#         ls | grep ^test-classes$ | wc -l
#         ls | grep ^classes$ | wc -l
#     cd ../
#     cd src/main
#         ls | grep ^java$ | wc -l
#     cd ../../
#     cd src/test
#         ls | grep ^java$ | wc -l
#     cd ../../
# done

# proj=Jsoup
# for bug in $(seq 1 93)
# do
#     cd ${d4jData}${proj}_${bug}
#     echo ${proj}_${bug}
#     cd target
#         ls | grep ^test-classes$ | wc -l
#         ls | grep ^classes$ | wc -l
#     cd ../
#     cd src/main
#         ls | grep ^java$ | wc -l
#     cd ../../
#     cd src/test
#         ls | grep ^java$ | wc -l
#     cd ../../
# done

# proj=JxPath
# for bug in $(seq 1 22)
# do
#     cd ${d4jData}${proj}_${bug}
#     echo ${proj}_${bug}
#     cd target
#         ls | grep ^test-classes$ | wc -l
#         ls | grep ^classes$ | wc -l
#     cd ../
#     cd src
#         ls | grep ^java$ | wc -l
#         ls | grep ^test$ | wc -l
#     cd ../
# done

# proj=Lang
# for bug in $(seq 1 1; seq 3 65)
# do
#     cd ${d4jData}${proj}_${bug}
#     echo ${proj}_${bug}
#     cd target
#         ls | grep ^tests$ | wc -l
#         ls | grep ^classes$ | wc -l
#     cd ../
#     cd src
#         ls | grep ^java$ | wc -l
#     cd ../
#     cd src
#         ls | grep ^test$ | wc -l
#     cd ../
# done

# proj=Math
# for bug in $(seq 1 106)
# do
#     cd ${d4jData}${proj}_${bug}
#     echo ${proj}_${bug}
#     cd target
#         ls | grep ^test-classes$ | wc -l
#         ls | grep ^classes$ | wc -l
#     cd ../
#     cd src
#         ls | grep ^java$ | wc -l
#         ls | grep ^test$ | wc -l
#     cd ../
# done

proj=Mockito
for bug in $(seq 1 38)
do
    cd ${d4jData}${proj}_${bug}
    echo ${proj}_${bug}
    cd build/classes
        ls | grep ^main$ | wc -l
        ls | grep ^test$ | wc -l
    cd ../../
    ls | grep ^src$ | wc -l
    ls | grep ^test$ | wc -l
done

# proj=Time
# for bug in $(seq 1 20; seq 22 27)
# do
#     cd ${d4jData}${proj}_${bug}
#     echo ${proj}_${bug}
#     cd build
#         ls | grep ^tests$ | wc -l
#         ls | grep ^classes$ | wc -l
#     cd ../
#     cd src/main
#         ls | grep ^java$ | wc -l
#     cd ../../
#     cd src/test
#         ls | grep ^java$ | wc -l
#     cd ../../
# done
