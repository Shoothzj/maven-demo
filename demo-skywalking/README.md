## OSX运行agent
### http
```
-javaagent:/Users/akka/ShootHzj/skywalking/sw850/agent/skywalking-agent.jar -Dskywalking_config=/Users/akka/master/maven-demo/demo-skywalking/src/main/resources/http/agent.config -DSW_LOGGING_DIR=/Users/akka/master/maven-demo/demo-skywalking
```
### pulsar producer
```
-javaagent:/Users/akka/ShootHzj/skywalking/sw850/agent/skywalking-agent.jar -Dskywalking_config=/Users/akka/master/maven-demo/demo-skywalking/src/main/resources/pulsar/producer/agent.config -DSW_LOGGING_DIR=/Users/akka/master/maven-demo/demo-skywalking
```
### pulsar consumer
```
-javaagent:/Users/akka/ShootHzj/skywalking/sw850/agent/skywalking-agent.jar -Dskywalking_config=/Users/akka/master/maven-demo/demo-skywalking/src/main/resources/pulsar/consumer/agent.config -DSW_LOGGING_DIR=/Users/akka/master/maven-demo/demo-skywalking
```
### kafka producer
```
-javaagent:/Users/akka/ShootHzj/skywalking/sw850/agent/skywalking-agent.jar -Dskywalking_config=/Users/akka/master/maven-demo/demo-skywalking/src/main/resources/kafka/producer/agent.config -DSW_LOGGING_DIR=/Users/akka/master/maven-demo/demo-skywalking
```
### kafka consumer
```
-javaagent:/Users/akka/ShootHzj/skywalking/sw850/agent/skywalking-agent.jar -Dskywalking_config=/Users/akka/master/maven-demo/demo-skywalking/src/main/resources/kafka/consumer/agent.config -DSW_LOGGING_DIR=/Users/akka/master/maven-demo/demo-skywalking
```
## WindowsAgent
### http
```
-javaagent:D:\ShootHzj\skywalking\sw850\agent\skywalking-agent.jar -Dskywalking_config=D:\master\maven-demo\demo-skywalking\src\main\resources\http\agent.config -DSW_LOGGING_DIR=D:\master\maven-demo\demo-skywalking
```
### pulsar producer
```
-javaagent:D:\ShootHzj\skywalking\sw850\agent\skywalking-agent.jar -Dskywalking_config=D:\master\maven-demo\demo-skywalking\src\main\resources\pulsar\producer\agent.config -DSW_LOGGING_DIR=D:\master\maven-demo\demo-skywalking
```
### pulsar consumer
```
-javaagent:D:\ShootHzj\skywalking\sw850\agent\skywalking-agent.jar -Dskywalking_config=D:\master\maven-demo\demo-skywalking\src\main\resources\pulsar\consumer\agent.config -DSW_LOGGING_DIR=D:\master\maven-demo\demo-skywalking
```
### kafka producer
```
-javaagent:D:\ShootHzj\skywalking\sw850\agent\skywalking-agent.jar -Dskywalking_config=D:\master\maven-demo\demo-skywalking\src\main\resources\kafka\producer\agent.config -DSW_LOGGING_DIR=D:\master\maven-demo\demo-skywalking
```
### kafka consumer
```
-javaagent:D:\ShootHzj\skywalking\sw850\agent\skywalking-agent.jar -Dskywalking_config=D:\master\maven-demo\demo-skywalking\src\main\resources\kafka\consumer\agent.config -DSW_LOGGING_DIR=D:\master\maven-demo\demo-skywalking
```
### integrate pckp
```
-javaagent:D:\ShootHzj\skywalking\sw850\agent\skywalking-agent.jar -Dskywalking_config=D:\master\maven-demo\demo-skywalking\src\main\resources\integrate\pckp\agent.config -DSW_LOGGING_DIR=D:\master\maven-demo\demo-skywalking
```

## command
### HTTP
```bash
curl -H 'Content-Type:application/json' 'http://localhost:8081' -d '{"msg":"hello"}'
curl -H 'Content-Type:application/json' 'http://localhost:8081' -d '{"msg":"hello", "msgType":"KAFKA"}'
```

### continue test
```bash
while :; do clear; curl -H 'Content-Type:application/json' 'http://localhost:8081/msg' -d '{"msg":"hello", "msgType":"KAFKA"}'; sleep 2; done
while :; do clear; curl -H 'Content-Type:application/json' 'http://localhost:8081/msg' -d '{"msg":"hello", "msgType":"PULSAR"}'; sleep 2; done
```
### close all
```bash
ps -ef|grep -i skywalking/sw850|grep -v grep|awk '{print $2}'|xargs kill -15
```