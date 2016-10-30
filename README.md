# protoc-gen-vectorizer (Draft)

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

## Usage

## API Documentation

For each annotation, the prefix should be: `//'`. This is inspired by [roxygen2](https://cran.r-project.org/web/packages/roxygen2/index.html).

### File Annotation

- `@hash [(int32) size]`: The size of the vector.

### Field Annotation



- `@categorical [(optional string) level1] [(optional string) level2] ...`: This field is categorical.  The value will be converted to string before vectorization. 
    - (TODO: the explanation of categorical field)
    - For `optional` field, the missing data will be skipped.
    - For `repeated` field, each value will be converted to corresponding result.
- `@numerical`: This field is numeric. The value should be numeric or an error will be thrown. 
    - For `optional` field, the missing data will be 0, and an indicator variable (of existence) will be generated. 
    - For `repeated` field, the plugin will fail. Please transform the field to single value properly by `@user` and the chain rule.
- `@bin [(duoble) denominator]`: This field will be devided by denominator and replaced by the quotient.
    - For `optional` field, the missing data will be skipped.
    - For `repeated` field, the repeated
- `@split [(string) delimiter]`: This field wlil be splitted by the given delimiter.
- `@interaction [(string) symbol]`: This field is a part of interaction. Fields with the same interaction symbol will be combined to the interacted feature.
- `@user [(string) function identity, (string) returned type]`: This field will be transformed by the given static function.
    - For `required` field, the plugin will generate `ReturnedType ClassName.FunctionName(T value)`
    - For `optional` field, the plugin will generate `com.google.common.base.Optional[ReturnedType] ClassName.Function(com.google.common.base.Optional[T] value)`.
    -  (TODO) For `repeated` field, the plugin will generate ??.

#### Chain Rule

The user can annotated the same field with multiple symbols. For example:

```
//'@bin 10
//'@categorical
optional int32 age;
```

The vectorizer will apply `@bin 10` first, then send the output to `@categorical`. Therefore, the order of annotation matters.
