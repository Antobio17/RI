#!/bin/bash

javac -cp tika-app-1.24.1.jar documentsParser/*.java
java -cp tika-app-1.24.1.jar:. documentsParser.DocumentsParser $*

rm documentsParser/*.class
