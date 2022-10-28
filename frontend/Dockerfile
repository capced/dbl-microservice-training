#Build Stage Start
FROM node:13.3-alpine as builder

RUN apk add --update --no-cache \
    python \
    make \
    g++

#Specify a working directory
WORKDIR '/'

#Run Stage Start
FROM nginx
RUN apt-get update && apt-get install -y curl \
 && rm -rf /var/lib/apt/lists/*
COPY nginx.conf /etc/nginx/nginx.conf

#Copy production build files from builder phase to nginx
COPY dist/* /usr/share/nginx/html/
RUN ls -l /usr/share/nginx/html/
EXPOSE 80
