@echo off
SET MYPATH=%~dp0
set WORKING_COPY=D:\github\prohr_app
REM atur alamat ini ke WorkingCopy
set PHP_FOLDER=%WORKING_COPY%\php_file
REM set PATH_GIT=C:\Program Files\Git\cmd
copy *.php "%PHP_FOLDER%"
cd %WORKING_COPY%
git add %PHP_FOLDER%\*.php
git commit -m "COMMITING PHP"
git push origin master && cd %PHP_FOLDER% && Uploader.bat && cd %MYPATH%


REM INSTALL GIT
REM https://git-scm.com/download/win untuk download git, 
REM tambahkan Path di environment Variables di user variables for ...., search aja "variable"
REM alamat git.exe biasanya di "C:\Program Files\Git\cmd\"