@echo off
set VERSION=v1.0.1
set MENSAJE="prueba de ci cd"
set REMOTE=localgit
echo Eliminando tag local %VERSION%
git tag -d %VERSION%

echo Eliminando tag remoto %VERSION%
git push %REMOTE% --delete %VERSION%

echo Añadiendo cambios a staging
git add .

echo Commiteando cambios
git commit -m %MENSAJE%

echo Pushing a la rama main
git push %REMOTE% main

echo Creando tag local %VERSION%
git tag %VERSION%

echo Pushing tag remoto %VERSION%
git push %REMOTE% %VERSION%

echo Listo. Se ha creado y subido la versión %VERSION%.
pause
