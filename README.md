# giftsncoupons

Note: Postman collections are attached in the repo.

Do check out the video demo attached in the zip file.

Run the dependent applications by running

```
docker compose up -
```

We need to create keyspace in Cassandra in order to run this application:
```
CREATE KEYSPACE spring_cassandra WITH replication = {'class' : 'SimpleStrategy', 'replication_factor' : 1};
```

# External Calls

External calls to LogiDeli are mocked using wiremocks.
