#!/usr/bin/python3

import os

root_dir = "/home/mrdrivingduck/Desktop/bugs-dot-jar"
data_dir = "/home/mrdrivingduck/Desktop/bugsDotJarData/"
git_dir = os.path.join(root_dir, ".git", "modules")
projects = os.listdir(git_dir)
os.system("mkdir " + data_dir)

project = "accumulo"

git_ref = open(os.path.join(git_dir, project, "packed-refs"))
for line in git_ref.readlines():
    line = line.strip('\n')
    line_split = line.split(' ')
    if len(line_split) >= 2 and line_split[1].startswith("refs/remotes/origin/bugs-dot-jar_"):
        version = line_split[1][33:]
        os.system("cd " + os.path.join(root_dir, project) + " && git checkout bugs-dot-jar_" + version)
        # os.system("mkdir " + os.path.join(data_dir, version))
        os.system("cp -r " + os.path.join(root_dir, project) + " " + os.path.join(data_dir, version))
git_ref.close()