https://debezium.io/blog/2019/02/19/reliable-microservices-data-exchange-with-the-outbox-pattern/#a

https://debezium.io/documentation/reference/transformations/outbox-event-router.html

https://medium.com/@huseyinygl/outbox-pattern-implementation-using-debezium-and-google-protobuffers-58b2bd80cc6c



### build service image

```bash
$ ./gradlew order-service:jibDockerBuild
```

```bash
$ ./gradlew shipment-service:jibDockerBuild
```

### check db tables

```bash
$ docker exec -it exam-db \
mysql -uroot -proot -Dexam -e "show tables"
```  

### check kafka 

```bash
$ docker exec -it myKafka \
kafka-topics.sh --bootstrap-server localhost:9092 --list
```  



### check connect

```bash
$ curl localhost:8083/connector-plugins | jq
```

### run connector

```bash
curl -X POST -H "Content-Type: application/json" \
--data @docker/cdc-outbox.json \
http://localhost:8083/connectors | jq
```  

### check connector

```bash
$ curl localhost:8083/connectors/cdc-outbox/status | jq
```

### delete connector

```bash
$ curl -X DELETE localhost:8083/connectors/cdc-outbox | jq
```

### check topic

```bash
$ docker exec -it myKafka kafka-topics.sh \
--bootstrap-server localhost:9092 \
 --list
```

### create order

```bash
$ curl -X POST \
-H "Content-Type: application/json" \
localhost:8080/order \
--data '{
 "item" : "test2",
 "quantity" : 1,
 "totalPrice" : 101,
 "status" : "ENTERED"
}' | jq
```

### check table

```bash
$ docker exec -it exam-db \
mysql -uroot -proot -Dexam -e "select * from order_line"
```

```bash
$ docker exec -it exam-db \
mysql -uroot -proot -Dexam -e "select * from outbox"
```

```bash
$ docker exec -it exam-db \
mysql -uroot -proot -Dexam -e "select * from shipment"
```

```bash
$ docker exec -it exam-db \
mysql -uroot -proot -Dexam -e "select * from consumed_message"
```

### check outbox order topic

```bash
$ docker exec -it myKafka kafka-console-consumer.sh \
--bootstrap-server localhost:9092 \
--topic outbox.event.Order \
--property print.key=true \
--property print.headers=true \
--from-beginning 
```  



### update order

```bash
$ curl -X PUT \
-H "Content-Type: application/json" \
localhost:8080/order \
--data '{
  "orderId" : 1,
  "newStatus" : "CANCELLED"
}' | jq
```

### update shipment

```bash
$ curl -X PUT \
-H "Content-Type: application/json" \
localhost:8081/shipment \
--data '{
  "shipmentId" : 1,
  "newStatus" : "DONE"
}' | jq
```

### check outbox shipment topic


```bash
$ docker exec -it myKafka kafka-console-consumer.sh \
--bootstrap-server localhost:9092 \
--topic outbox.event.Shipment \
--property print.key=true \
--property print.headers=true \
--from-beginning 
```  