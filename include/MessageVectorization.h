/*
 * MessageVectorization.h
 *
 *  Created on: Nov 14, 2016
 *      Author: wush
 */

#ifndef INCLUDE_MESSAGEVECTORIZATION_H_
#define INCLUDE_MESSAGEVECTORIZATION_H_

#include <vector>
#include <google/protobuf/compiler/code_generator.h>
#include <google/protobuf/descriptor.h>
#include "Vectorization.h"

namespace vectorizer {

class MessageVectorization {

  std::string* error;

  const google::protobuf::Descriptor *descriptor;

  std::vector< std::shared_ptr<Vectorization> > operators;

public:

  MessageVectorization(const google::protobuf::Descriptor *descriptor, std::string* error);

  virtual ~MessageVectorization() { }

  void generate(std::stringstream&);

};

} /* namespace vectorizer */

#endif /* INCLUDE_MESSAGEVECTORIZATION_H_ */
