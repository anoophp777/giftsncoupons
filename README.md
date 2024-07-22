# giftsncoupons

We need to create keyspace in Cassandra in order to run this application:

CREATE KEYSPACE spring_cassandra WITH replication = {'class' : 'SimpleStrategy', 'replication_factor' : 1};