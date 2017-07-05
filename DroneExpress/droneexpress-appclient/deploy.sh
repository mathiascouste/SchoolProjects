#!/bin/sh

path_to_servicemix=$HOME"/Software/servicemix/"
jar_name="drone-express-1.0.jar"

rm $path_to_servicemix"deploy/"$jar_name
cp  "target/"$jar_name $path_to_servicemix"deploy/"

$path_to_servicemix"bin/stop"
$path_to_servicemix"bin/servicemix"

