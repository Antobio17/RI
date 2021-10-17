#!/bin/bash

javac -cp tika-app-1.24.1.jar:lucene-core-7.1.0.jar:lucene-analyzers-common-7.1.0.jar src/*.java
java -cp tika-app-1.24.1.jar:.:lucene-core-7.1.0.jar:.:lucene-analyzers-common-7.1.0.jar:. src.TextAnalyzer $*

rm src/*.class
