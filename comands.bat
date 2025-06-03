@echo off
set MENSAJE="arreglo de error: pedia autorizacion para el endpoint de refreshToken"
set REMOTE=localgit
set BRANCH=DEV

echo Añadiendo cambios a staging
git add .

echo Commiteando cambios
git commit -m %MENSAJE%

echo Pushing a la rama %BRANCH%
git push %REMOTE% %BRANCH%


