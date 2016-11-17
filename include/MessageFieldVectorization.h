/*
 * MessageFieldVectorization.h
 *
 *  Created on: 2016/11/17
 *      Author: gmobi-wush
 */

#ifndef INCLUDE_MESSAGEFIELDVECTORIZATION_H_
#define INCLUDE_MESSAGEFIELDVECTORIZATION_H_

#include <FieldVectorization.h>

namespace vectorizer {

class MessageFieldVectorization : public FieldVectorization {

public:

  MessageFieldVectorization(const google::protobuf::FieldDescriptor *descriptor)
  : FieldVectorization(descriptor, nullptr) { }

  virtual ~MessageFieldVectorization() { }

  virtual void generateContent(std::stringstream& out) {
    out << "append(builder, apply(" << getGetter() << ", interaction));" << std::endl;
  }

};

} /* namespace vectorizer */

#endif /* INCLUDE_MESSAGEFIELDVECTORIZATION_H_ */
