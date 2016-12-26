# protoc-gen-vectorizer ![](https://travis-ci.org/wush978/protoc-gen-vectorizer.svg?branch=master)

## Introduction

[Protocol Buffers](https://en.wikipedia.org/wiki/Protocol_Buffers) are widely used serialization format in real applications. 
And we usually need to develop applications based on these messages.

To develop a machine learning application, we need to convert these messages to [vector](https://en.wikipedia.org/wiki/Vector_(mathematics_and_physics)) 
before doing machine learning things.

This is a vectorizer generator plugin for the Google Protocol Buffers compiler
(`protoc`). The plugin can generate source code (java) which will convert the serialized message into 
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
  //'@categorical 0 1 2 3 4 5 6 7 8 9
  required int32 age = 2;
  //'@categorical
  optional string sex = 3;
}
```

Then, the plugin will generate related code for us to vectorize the message into vector (`Double[]`) properly.

## Build

After installing protocol buffer v3 or above, you can build this compiler plugin via

```
mkdir build
cd build
cmake ..
make
# make test for testing
```

Then executable `build/bin/ProtocGenVectorizer` generates the vectorization code based on the proto file.

### Docker

You can directly use the pre-build docker image from dockerhub.

## Usage

## Commands

### Docker

Suppose you are under a project root with maven standard directory layout:

```
src/main/java   Application/Library sources
src/main/proto  Proto files location with proper annotations
```

#### Generate Java Vectorization Code

Aftuer pulling the docker image from dockerhub, you can generate the vectorization code via:

```
docker run --volume=`pwd`:/data --user=`id -u $USER`:`id -u $USER` \
  wush978/protoc-gen-vectorizer /data/src/main/proto/<proto-filename> \
  --proto_path=/data/src/main/proto \
  --java_out=/data/src/main/java \
  --vec_java_out=/data/src/main/java
```

#### Configure Maven Dependency

Add following dependency to `pom.xml`:

```
<dependencies>
    <dependency>
        <groupId>com.github.wush978</groupId>
        <artifactId>protobuf-vectorizer</artifactId>
        <version>0.1.2</version>
    </dependency>
</dependencies>
```

and the following maven repository:

```
<repositories>
    <repository>
        <id>protoc-gen-vectorizer-mvn-repo</id>
        <url>https://raw.github.com/wush978/protoc-gen-vectorizer/mvn-repo/</url>
        <snapshots>
            <enabled>true</enabled>
            <updatePolicy>always</updatePolicy>
        </snapshots>
    </repository>
</repositories>
```

## API Documentation

For each annotation, the prefix should be: `//'`. This is inspired by [roxygen2](https://cran.r-project.org/web/packages/roxygen2/index.html).

### Field Annotation

- `@categorical`: This field is categorical.  The value will be converted to string before vectorization. 
    - (TODO: the explanation of categorical field)
    - For `optional` field, the missing data will be skipped.
    - For `repeated` field, each value will be converted to corresponding result.
- `@numerical`: This field is numeric. The value should be numeric or an error will be thrown. 
    - For `optional` field, the missing data will be 0, and an indicator variable (of existence) will be generated. 
    - For `repeated` field, the plugin will fail. Please transform the field to single value properly by `@user` and the chain rule.
- `@interaction [(string) symbol]`: This field is a part of interaction. Fields with the same interaction symbol will be combined to the interacted feature. Note that the `symbol` should be unique in each project.
- `@user [(string) function identity]`: This field will be transformed by the given static function.

#### Chain Rule

The user can annotated the same field with multiple symbols. For example:

```
//'@user com.wush978.github.Demo.a
//'@user com.wush978.github.Demo.b
//'@categorical
optional int32 age;
```

The vectorizer will put the `age` field as the argument of `com.wush978.github.Demo.a` first, then send the output to the argument of `com.wush978.github.Demo.b`. Finally, the output will be treated as categorical feature. Therefore, the order of annotation matters.

