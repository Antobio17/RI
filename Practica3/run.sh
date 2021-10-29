#!/bin/bash

javac -cp tika-app-1.24.1.jar:lucene-core-8.1.0.jar:lucene-analyzers-common-8.1.0.jar:lucene-backward-codecs-8.1.0.jar:opencsv-3.8.jar:commons-lang3-3.12.0.jar:commons-beanutils-1.9.4.jar src/*.java
java -cp tika-app-1.24.1.jar:.:lucene-core-8.1.0.jar:.:lucene-analyzers-common-8.1.0.jar:.:lucene-backward-codecs-8.1.0.jar:.:opencsv-3.8.jar:.:commons-lang3-3.12.0.jar:.:commons-beanutils-1.9.4.jar:. src.InformationRetrievalSystem $*

rm src/*.class
