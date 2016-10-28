#!/bin/sh  
#============ get the file name ===========  

cd ..
path=`pwd`
home=${path}
folder=${path}"/lib"
main_jar=":converter.jar"
all_jar=""  
dataDir=""
config_file=${path}"/conf/setup.xml"
echo
for file in ${folder}/*; do  
    temp_file=`basename $file`  
    all_jar="${all_jar}:${folder}/${temp_file}"
done
main_class="org.easyArch.converter.startup.Converter"
cd "bin"
if [ -n "$1" ];then
    dataDir=$1
else
    dataDir=${path}"/data/bill"
fi
if [ -n "$2" ];then
	java -cp $all_jar$main_jar -Deasyproxy.home="${home}" $main_class $1 $2
else
	java -cp $all_jar$main_jar -Deasyproxy.home="${home}" $main_class $1 ${config_file}
fi
