set VERSION=v1.0.0
git tag -d %VERSION%
git push fedoragit --delete %VERSION%
git add .
git commit -m "prueba de ci cd"
git push fedoragit main
git tag %VERSION%
git push fedoragit %VERSION%
