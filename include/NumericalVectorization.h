/*
 * NumericalVectorization.h
 *
 *  Created on: Nov 15, 2016
 *      Author: wush
 */

#ifndef INCLUDE_NUMERICALVECTORIZATION_H_
#define INCLUDE_NUMERICALVECTORIZATION_H_

#include <string>
#include <FieldVectorization.h>

namespace vectorizer {

class NumericalVectorization : public FieldVectorization {

public:

  NumericalVectorization(const google::protobuf::FieldDescriptor *descriptor, Vectorization* inner)
  : FieldVectorization(descriptor, inner) { }

  virtual ~NumericalVectorization() { }

  virtual void generateContent(std::stringstream& out) {
    Vectorization::generate(out);
    // TODO
  }

};

} /* namespace vectorizer */




#endif /* INCLUDE_NUMERICALVECTORIZATION_H_ */
