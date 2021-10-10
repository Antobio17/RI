#!/bin/bash

javac -cp tika-app-1.24.1.jar src/*.java
java -cp tika-app-1.24.1.jar:. src.DocumentsParser $*

rm src/*.class
