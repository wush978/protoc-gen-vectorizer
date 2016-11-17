/*
 * MessageFieldVectorization.h
 *
 *  Created on: 2016/11/17
 *      Author: gmobi-wush
 */

#ifndef INCLUDE_MESSAGEFIELDVECTORIZATION_H_
#define INCLUDE_MESSAGEFIELDVECTORIZATION_H_

#include <Vectorization.h>

namespace vectorizer {

class MessageFieldVectorization: public Vectorization {

  const google::protobuf::FieldDescriptor *descriptor;

public:

  MessageFieldVectorization() { }

  virtual ~MessageFieldVectorization() { }

};

} /* namespace vectorizer */

#endif /* INCLUDE_MESSAGEFIELDVECTORIZATION_H_ */
