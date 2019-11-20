FROM node:12.13.0-alpine
COPY . app/orders-vix
WORKDIR /app/orders-vix
RUN yarn
ENTRYPOINT yarn start
EXPOSE 4000
