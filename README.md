# MongoDB Troubleshooter

## Download jar

Download `mongodb-troubleshooter-1.0.0-***-jar-with-dependencies.jar`

https://repository-cyrille-leclerc.forge.cloudbees.com/snapshot/localdomain/localhost/mongodb-troubleshooter/1.0.0-SNAPSHOT/

## Tutorial

**Command line**

```
$ java -jar mongodb-troubleshooter-1.0.1-SNAPSHOT-jar-with-dependencies.jar -uri mongodb://localhost/local
```

**Result**

```
host=[localhost],username=null,database=local,collection=null


# MongoClient

Mongo{authority=MongoAuthority{type=Direct, serverAddresses=[localhost/127.0.0.1:27017], credentials={credentials={}}}, options=MongoOptions{description='null', connectionsPerHost=100, threadsAllowedToBlockForConnectionMultiplier=5, maxWaitTime=120000, connectTimeout=10000, socketTimeout=0, socketKeepAlive=false, autoConnectRetry=false, maxAutoConnectRetryTime=0, slaveOk=false, readPreference=primary, dbDecoderFactory=DefaultDBDecoder.DefaultFactory, dbEncoderFactory=DefaultDBEncoder.DefaultFactory, safe=false, w=0, wtimeout=0, fsync=false, j=false, socketFactory=javax.net.DefaultSocketFactory@1181a6d, cursorFinalizerEnabled=true, writeConcern=WriteConcern { "getlasterror" : 1} / (Continue Inserting on Errors? false), alwaysUseMBeans=false}}


# Databases

* local - default database


# Database

DB: local


## Collections

* startup_log - 2 entries
```

