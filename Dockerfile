FROM confluentinc/cp-flink:1.19.1-cp1
RUN mkdir -p $FLINK_HOME/usrlib
COPY target/flink-tableapi-test-1.0-SNAPSHOT.jar $FLINK_HOME/usrlib/jans-table-api-jar-with-dependencies.jar