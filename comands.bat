@echo off
set MENSAJE="boorado del stage de dast"
set REMOTE=localgit
set BRANCH=DEV

echo Añadiendo cambios a staging
git add .

echo Commiteando cambios
git commit -m %MENSAJE%

echo Pushing a la rama %BRANCH%
git push %REMOTE% %BRANCH%


