FROM node:16

# RUN npm run build -- --output-path=./dist/out --prod
WORKDIR /app
RUN npm install -g typescript@latest
RUN npm install -g @angular/cli@latest
COPY package*.json ./
RUN npm install
COPY . ./

EXPOSE 4200 49153
# CMD [ "ng", "serve", "--host", "0.0.0.0", "--verbose"]
CMD [ "npm", "run", "start"]
