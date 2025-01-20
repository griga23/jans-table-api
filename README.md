# jans-table-api
Jan's example to use Table API!

Build docker image 
==================
WARNING: you might need to change the docker source image (depends on your target platform): FROM --platform=linux/amd64 confluentinc/cp-flink:1.19.1-cp1

WARNING: use Java 11 to compile the source code

mvn install -Dmaven.test.skip=true

docker build -t griga/jans-table-api .

docker image push griga/jans-table-api 


Deploy docker image to CPF 
==========================
export CONFLUENT_CMF_URL=http://localhost:8080

confluent flink environment list

confluent flink environment create jans-env --kubernetes-namespace default

confluent flink application list --environment jans-env

confluent flink application create --environment jans-env deploy_example.yaml

confluent flink application web-ui-forward jans-table-api --environment jans-env


Deploy with Minio
=================
WARNING: you need to have Minio client installed locally 

kubectl port-forward pod/minio 9000 9090 -n minio-dev

mc alias set dev-minio http://localhost:9000 minioadmin minioadmin

mc mb dev-minio/flink

mc cp target/flink-tableapi-test-1.0-SNAPSHOT.jar dev-minio/flink/jans-table-api-jar-with-dependencies.jar

mc ls dev-minio/flink

confluent flink application create --environment jans-env deploy_example_minio.json

confluent flink application web-ui-forward table-api-minio --environment jans-env

