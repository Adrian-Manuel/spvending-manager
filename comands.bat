@echo off
set MENSAJE="Se modifico la clase utilcookie para agregar soporte de samesite"
set REMOTE=localgit
set BRANCH=DEV

echo Añadiendo cambios a staging
git add .

echo Commiteando cambios
git commit -m %MENSAJE%

echo Pushing a la rama %BRANCH%
git push %REMOTE% %BRANCH%


