#!/bin/sh

url='http://localhost:8080/api/v1/sucursales'

curl -v -I -H "Accept: application/json" $url
curl -s $url | jq '.' --tab
