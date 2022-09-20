FROM node:18-alpine3.15 as react-build
WORKDIR /portfolio-app
COPY package.json .
RUN npm install
COPY . .
RUN npm run build

FROM nginx:1.23.1-alpine
COPY --from=react-build /portfolio-app/build /usr/share/nginx/html