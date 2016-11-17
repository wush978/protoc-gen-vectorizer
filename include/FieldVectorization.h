/*
 * FieldVectorization.h
 *
 *  Created on: 2016/11/17
 *      Author: gmobi-wush
 */

#ifndef INCLUDE_FIELDVECTORIZATION_H_
#define INCLUDE_FIELDVECTORIZATION_H_

#include <Vectorization.h>

namespace vectorizer {

class FieldVectorization: public Vectorization {

  const google::protobuf::FieldDescriptor *descriptor;

public:

  FieldVectorization(const google::protobuf::FieldDescriptor *_descriptor, Vectorization* inner)
  : Vectorization(inner), descriptor(_descriptor) { }

  virtual ~FieldVectorization() { }

  const google::protobuf::FieldDescriptor* getDescriptor() {
    return descriptor;
  }
};

} /* namespace vectorizer */

#endif /* INCLUDE_FIELDVECTORIZATION_H_ */
