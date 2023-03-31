@echo off
@title ZeroMS
Color 0A
set path=jrex64\bin;%SystemRoot%\system32;%SystemRoot%;%SystemRoot%
set JRE_HOME=jrex64
set JAVA_HOME=jrex64
set CLASSPATH=.;ZeroMS\*;Lib\*;

java -server -Xmx2000m -Xms1000m -Xmn512m -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=1 -XX:ParallelGCThreads=8 -XX:CMSInitiatingOccupancyFraction=70 -XX:+UseCMSInitiatingOccupancyOnly -XX:+AggressiveOpts -XX:+UseFastAccessorMethods gui.ZeroMS_UI
pause 