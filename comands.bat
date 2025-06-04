@echo off
set MENSAJE="se modifico el dockerfile para que no subiera todo el codigo fuente a la imagen y por tanto ocupara menos espacio"
set REMOTE=localgit
set BRANCH=DEV

echo Añadiendo cambios a staging
git add .

echo Commiteando cambios
git commit -m %MENSAJE%

echo Pushing a la rama %BRANCH%
git push %REMOTE% %BRANCH%


