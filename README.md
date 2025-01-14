# jans-table-api
Jan's example to use Table API!

Build docker image example
==========================
mvn install -Dmaven.test.skip=true

docker build -t griga/jans-table-api .

docker image push griga/jans-table-api 


Deploy docker image to CPF example
==================================
confluent flink application create --environment jans-env deploy_example.yaml

confluent flink application web-ui-forward jans-table-api --environment jans-env
