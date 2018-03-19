@echo off
cd %PHP_FOLDER%
if exist ftpcmd.bat (
	del ftpcmd.bat)
echo open ftp.urbancare.id> ftpcmd.bat
echo urbancare@urbancare.id>> ftpcmd.bat
echo J$ml~P4V,M~L>> ftpcmd.bat
echo mput *.php>> ftpcmd.bat
echo quit>> ftpcmd.bat
ftp -i -s:ftpcmd.bat
del ftpcmd.bat