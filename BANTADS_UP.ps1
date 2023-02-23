#Sobe tudo os containers
docker-compose -f ".bantads_docker\docker-compose.yml" up -d --build

#Muda pra pasta do Front e o executa
cd frontend && ng serve
