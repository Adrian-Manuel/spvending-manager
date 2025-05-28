@echo off
set TAG=v1.0.0
set MENSAJE="prueba CI/CD"
set REMOTE=localgit
set BRANCH=DEV

echo Eliminando tag local %TAG%
git tag -d %TAG%

echo Eliminando tag remoto %TAG%
git push %REMOTE% --delete %TAG%

echo Creando tag local %TAG%
git tag %TAG%