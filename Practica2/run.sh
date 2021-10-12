#!/bin/bash

javac -cp lucene-core-7.1.0.jar:lucene-analyzers-common-7.1.0.jar src/*.java
java -cp lucene-core-7.1.0.jar:.:lucene-analyzers-common-7.1.0.jar:. src.TextAnalyzer $*

rm src/*.class
