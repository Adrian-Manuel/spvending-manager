@echo off
set TAG=v1.0.0
set MENSAJE="prueba CI/CD"
set REMOTE=localgit
set BRANCH=DEV
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

echo Pushing a la rama %BRANCH%
git push %REMOTE% %BRANCH%

echo Listo. Se ha creado y subido la versión %TAG%.
pause


