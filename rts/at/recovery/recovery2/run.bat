@echo off

echo "Running recovery2 quickstart"

mvn -f fail clean compile exec:java
IF %ERRORLEVEL% EQU 0 exit -1
echo "Recovering failed service - this could take up to 2 minutes"
mvn -f recover compile exec:java
IF %ERRORLEVEL% NEQ 0 exit -1
echo "Service recovery example succeeded"
