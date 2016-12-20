/*
 * UserVectorization.h
 *
 *  Created on: 2016年12月20日
 *      Author: wush.wu
 */

#ifndef INCLUDE_USERVECTORIZATION_H_
#define INCLUDE_USERVECTORIZATION_H_

#include <sstream>
#include <FieldVectorization.h>

namespace vectorizer {

class UserVectorization : public FieldVectorization {

  const std::string fullName;

public:

  UserVectorization(const std::string& _fullName, const google::protobuf::FieldDescriptor *descriptor, Vectorization* inner)
  : FieldVectorization(descriptor, inner), fullName(_fullName) { }

  virtual ~UserVectorization() { }

  virtual void generateContent(std::stringstream& out) {
    out << fullName << "(" ;
    if (getInner().get() == nullptr) {
      out << getGetter();
    } else {
      getInner()->generate(out);
    }
    out << ")";
  }


};

}

#endif /* INCLUDE_USERVECTORIZATION_H_ */
