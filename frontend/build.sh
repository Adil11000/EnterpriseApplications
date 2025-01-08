#!/usr/bin/env bash

rm ../src/main/resources/static/* -rf
npm run build
cp dist/* ../src/main/resources/static -r
