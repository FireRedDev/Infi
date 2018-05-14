#!/bin/bash
PATH=$PATH:$(npm bin)
set -x
# Production build
ng build --prod
# merge ngsw-manifest and copy to dist

# Serve
cd dist
start chrome http://localhost:8084/
http-server

