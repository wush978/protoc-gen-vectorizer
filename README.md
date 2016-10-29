# protoc-gen-vectorizer (Draft)

[Protocol Buffers](https://en.wikipedia.org/wiki/Protocol_Buffers) are widely used serialization format in real applications. 
And we usually need to develop applications based on these messages.

To develop a machine learning application, we need to convert these messages to [vector](https://en.wikipedia.org/wiki/Vector_(mathematics_and_physics)) 
before doing machine learning things.

This is a vectorizer generator plugin for the Google Protocol Buffers compiler
(`protoc`). The plugin can generate source code which will convert the serialized message into 
mathematical vector. You only need to comment your schema files(`.proto`) properly.

For example, here is a protobuf schema:

```proto
message Person {
  required string name = 1;
  required int32 age = 2;
  optional string sex = 3;
}
```

If we want to build a machine learning application to predict the potential value of the user, 
we could convert the message to a vector space in the following way:

```java
Person person;
Double[] vector = new Double[11];
if (person.getAge() >= 100) {
  vector[8] = 1;
} else if (person.getAge() < 10) {
  vector[age % 10 - 1] = 1;
}
if (person.hasSex()) {
  if (person.getSex().compareTo("male") == 0) {
    vector[9] = 1;
  else {
    vector[10] = 1;
  }
}
```

There are three details here:

- The name will be dropped.
- The age will be assigned to one of ten groups (0~9, 10~19, ..., 80~89, and 90+).
- The sex will be assigned to one of three groups (missing, male, and femail).

This kind of **glue code** introduces techical debt to the system ([Sculley et al., 2015](http://papers.nips.cc/paper/5656-hidden-technical-debt-in-machine-learning-systems)).

With this plugin, we could comment the field as follow:

```proto
message Person {
  // we won't predict the value based on name
  required string name = 1;
  //'@bin 10
  required int32 age = 2;
  //'@equal 
  optional string sex = 3;
}
```

Then, the plugin will generate related code for us to vectorize the message into vector (`Double[]`) properly.
