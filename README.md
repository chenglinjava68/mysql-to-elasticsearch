# mysql-to-elasticsearch
import data from mysql into elasticsearch

this project use `Spring Boot` and `Spring jdbc` and `elasticsearch` 

## how to use

you need to extends `AbstractDataCollector` class, then override two method that defined in AbstractDataCollector class.

and you also need to edit `config.properties` file in resources folder to provider some information

default port is 8080, so you can :

- http://localhost:8080/data/queryAll show the data which you want to import into elasticsearch from mysql
- http://localhost:8080/trigger/import trigger to data import task
- http://localhost:8080/elasticsearch/queryAll show the data which has imported into the elasticsearch

