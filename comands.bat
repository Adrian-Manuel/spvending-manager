
git tag -d v1.0.0

git push localgit --delete v1.0.0

git add .

git commit -m "ci en gitlab registry container"

git push localgit main

git tag v1.0.0

git push localgit v1.0.0
