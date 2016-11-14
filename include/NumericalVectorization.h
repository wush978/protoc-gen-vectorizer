/*
 * NumericalVectorization.h
 *
 *  Created on: Nov 15, 2016
 *      Author: wush
 */

#ifndef INCLUDE_NUMERICALVECTORIZATION_H_
#define INCLUDE_NUMERICALVECTORIZATION_H_

#include <string>
#include "Vectorization.h"

namespace vectorizer {

class NumericalVectorization : public Vectorization {

  const google::protobuf::FieldDescriptor *descriptor;

public:

  NumericalVectorization(const google::protobuf::FieldDescriptor *_descriptor, Vectorization* inner)
  : Vectorization(inner), descriptor(_descriptor) { }

  virtual ~NumericalVectorization() { }

  virtual void generate(std::stringstream& out) {
    Vectorization::generate(out);
    // TODO
  }

};

} /* namespace vectorizer */




#endif /* INCLUDE_NUMERICALVECTORIZATION_H_ */
