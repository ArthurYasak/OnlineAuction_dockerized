FROM postgres:15

WORKDIR /OnlineAuction

COPY AuctionBaseQuery.sql /docker-entrypoint-initdb.d/AuctionBaseQuery.sql
EXPOSE 5432