## 链接
http://localhost:8083/playground
## 命令
```
curl -X POST localhost:8083/echo -d 'deadea'
```

## 集成skywalking
### OSX
```
-javaagent:/Users/akka/ShootHzj/skywalking/sw850/agent/skywalking-agent.jar -Dskywalking_config=/Users/akka/master/maven-demo/demo-graphql/src/main/resources/agent.config
```
### Windows
```
-javaagent:D:\ShootHzj\skywalking\sw850\agent\skywalking-agent.jar -Dskywalking_config=D:\master\maven-demo\demo-graphql\src\main\resources\agent.config
```