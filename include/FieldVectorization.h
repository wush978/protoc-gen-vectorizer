/*
 * FieldVectorization.h
 *
 *  Created on: 2016/11/17
 *      Author: gmobi-wush
 */

#ifndef INCLUDE_FIELDVECTORIZATION_H_
#define INCLUDE_FIELDVECTORIZATION_H_

#include <sstream>
#include <Vectorization.h>

namespace vectorizer {

class FieldVectorization: public Vectorization {

  const google::protobuf::FieldDescriptor *descriptor;

  bool repeated_getter;

  std::string getter;

  std::string haver;

  std::string counter;

public:

  FieldVectorization(const google::protobuf::FieldDescriptor *_descriptor, Vectorization* inner)
  : Vectorization(inner), descriptor(_descriptor), repeated_getter(false) {
  }

  virtual ~FieldVectorization() { }

  const google::protobuf::FieldDescriptor* getDescriptor() const {
    return descriptor;
  }

  void updateGetter(bool _repeated_getter) {
    repeated_getter = _repeated_getter;
    if (repeated_getter) {
//      getter.assign("src.get");
//      getter.append(getDescriptor()->camelcase_name());
//      if (getter.size() > 7) getter[7] = toupper(getter[7]);
//      getter.append("(i)");
      getter.assign(get("get"));
      getter.append("(i)");
    } else {
      getter.assign(get("get"));
      getter.append("()");
    }
  }

  const std::string& getGetter() {
    if (getter.length() == 0) {
      updateGetter(repeated_getter);
    }
    return getter;
  }

  const std::string& getHaver() {
    if (haver.length() == 0) {
//      haver.reserve(30);
//      haver.assign("src.has");
//      haver.append(getDescriptor()->camelcase_name());
//      if (haver.size() > 7) haver[7] = toupper(haver[7]);
//      haver.append("()");
      haver.assign(get("has"));
      haver.append("()");
    }
    return haver;
  }

  const std::string& getCounter() {
    if (counter.length() == 0) {
//      counter.reserve(30);
//      counter.assign("src.get");
//      counter.append(getDescriptor()->camelcase_name());
//      if (counter.size() > 7) counter[7] = toupper(counter[7]);
//      counter.append("Count()");
      counter.assign(get("get"));
      counter.append("Count()");
    }
    return counter;
  }

  // The subclass should implement "generateContent" instead of "generate"
  virtual void generateContent(std::stringstream& out) = 0;

  // label wrapper
  virtual void generate(std::stringstream& out) {
    switch(getDescriptor()->label()) {
    case google::protobuf::FieldDescriptor::LABEL_OPTIONAL :
      out << "if (" << getHaver() << ") {" << std::endl;
      generateContent(out);
      out << "}" << std::endl;
      break;
    case google::protobuf::FieldDescriptor::LABEL_REPEATED :
      out << "for(int i = 0;i < " << getCounter() << ";i++) {" << std::endl;
      updateGetter(true);
      generateContent(out);
      out << "}" << std::endl;
      break;
    case google::protobuf::FieldDescriptor::LABEL_REQUIRED :
      updateGetter(false);
      generateContent(out);
      break;
    }
  }

private:

  const std::string get(const std::string& prefix) {
    std::string result;
    result.reserve(30);
    result.assign("src.");
    result.append(prefix);
    result.append(getDescriptor()->camelcase_name());
    if (result.size() > 4 + prefix.size()) result[4 + prefix.size()] = toupper(result[4 + prefix.size()]);
    return result;
  }
};

} /* namespace vectorizer */

#endif /* INCLUDE_FIELDVECTORIZATION_H_ */
