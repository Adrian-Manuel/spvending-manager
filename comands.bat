Write-Host "Eliminando etiqueta local v1.0.0"
git tag -d v1.0.0
Write-Host "Eliminando etiqueta remota v1.0.0 en localgit"
git push localgit --delete v1.0.0
Write-Host "Agregando todos los cambios"
git add .
Write-Host "Commiteando los cambios"
git commit -m "ci en gitlab registry container"
Write-Host "Subiendo a localgit main"
git push localgit main
Write-Host "Creando etiqueta local v1.0.0"
git tag v1.0.0
Write-Host "Subiendo etiqueta v1.0.0 a localgit"
git push localgit v1.0.0
Write-Host "Proceso completado."
Read-Host -Prompt "Presiona Enter para salir"