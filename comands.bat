@echo off
set VERSION=v1.0.0
set MENSAJE="prueba de ci cd"

echo Eliminando tag local %VERSION%
git tag -d %VERSION%

echo Eliminando tag remoto %VERSION%
git push fedoragit --delete %VERSION%

echo Añadiendo cambios a staging
git add .

echo Commiteando cambios
git commit -m %MENSAJE%

echo Pushing a la rama main
git push fedoragit main

echo Creando tag local %VERSION%
git tag %VERSION%

echo Pushing tag remoto %VERSION%
git push fedoragit %VERSION%

echo Listo. Se ha creado y subido la versión %VERSION%.
pause
