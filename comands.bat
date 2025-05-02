git tag -d v1.0.1
git push fedoragit --delete v1.0.1
git add .
git commit -m "prueba de ci cd"
git push fedoragit main
git tag v1.0.1
git push fedoragit v1.0.1
