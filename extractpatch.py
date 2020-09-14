#!/usr/bin/python3

import os

data_dir = "/home/mrdrivingduck/Desktop/bugsDotJarData/"
patch_dir = "/home/mrdrivingduck/Desktop/bugsDotJarPatches/"
os.system("mkdir " + patch_dir)
bugs = os.listdir(data_dir)

for bug in bugs:
    patch_src_path = os.path.join(data_dir, bug, ".bugs-dot-jar", "developer-patch.diff")
    patch_dest_path = os.path.join(patch_dir, bug + ".diff")
    os.system("cp " + patch_src_path + " " + patch_dest_path)
