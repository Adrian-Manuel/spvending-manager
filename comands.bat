@echo off
set TAG=v1.0.0
set MENSAJE="despliegue para pruebas en staging"
set REMOTE=localgit
echo Eliminando tag local %TAG%
git tag -d %TAG%

echo Eliminando tag remoto %TAG%
git push %REMOTE% --delete %TAG%

echo Añadiendo cambios a staging
git add .

echo Commiteando cambios
git commit -m %MENSAJE%

echo Creando tag local %TAG%
git tag %TAG%

echo Pushing a la rama main
git push %REMOTE% main

echo Pushing tag remoto %TAG%
git push %REMOTE% %TAG%

echo Listo. Se ha creado y subido la versión %TAG%.
pause


