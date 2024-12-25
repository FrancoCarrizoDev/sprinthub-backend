# sprinthub-backend

### Initialize Project

1. Complete .env file
2. run docker-compose --env-file .env up command


show all topics
docker exec -it sprinthub-kafka kafka-topics --list --bootstrap-server localhost:9092

create topic

docker exec -it sprinthub-kafka kafka-topics --create --topic user.created --bootstrap-server localhost:9092

show message from topic

docker exec -it sprinthub-kafka kafka-console-consumer --topic user.created --from-beginning --bootstrap-server localhost:9092


