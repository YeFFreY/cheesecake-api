@echo off

IF [%1] == [] GOTO bye
IF [%2] == [] GOTO bye

set other=%2 %3 %4 %5
%HOMEDRIVE%%HOMEPATH%\code\tools\liquibase-4.5.0\liquibase.bat --defaultsFile=liquibase-%1.properties %other%

exit /b


:bye
ECHO Please provide db environment: dev, tst,...
Echo then provide update, dropAll, rollbackCount X