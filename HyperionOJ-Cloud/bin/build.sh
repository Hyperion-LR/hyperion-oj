#!/bin/bash

# shellcheck disable=SC2164
cd ../HyperionOJ-Cloud

git pull

mvn clean

mvn complete

mvn install

mv ~/HyperionOJ/HyperionOJ-Cloud/hyperionoj-web/target/hyperionoj-web-1.0.0.jar ~/HyperionOJ-WEB/server/


