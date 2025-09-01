FROM ubuntu:latest
LABEL authors="rafel"

ENTRYPOINT ["top", "-b"]


FROM mysql:5.7
ENV MYSQL_ROOT_PASSWORD my-secret-pw
EXPOSE 3306