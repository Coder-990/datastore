FROM mysql

ENV MYSQL_ROOT_PASSWORD root
ADD datastock.sql /docker-entrypoint-initdb.d

EXPOSE 3308