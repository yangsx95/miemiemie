# 单节点kafka

## 基础操作

创建topic:

```console
docker exec kafka \
kafka-topics.sh \
--create --topic crane \
--partitions 3 \
--zookeeper zookeeper:2181 \
--replication-factor 1
```

查看topic：

```console
docker exec kafka \
kafka-topics.sh --list \
--zookeeper zookeeper:2181
```

创建生产者：

```console
docker exec -it kafka \
kafka-console-producer.sh \
--topic crane \
--broker-list kafka:9092
```

创建消费者：

```console
docker exec kafka \
kafka-console-consumer.sh \
--topic crane \
--bootstrap-server kafka:9092
```

## kafka客户端工具

<https://www.kafkatool.com/>
