/*
 * CategoricalVectorization.h
 *
 *  Created on: Nov 14, 2016
 *      Author: wush
 */

#ifndef INCLUDE_CATEGORICALVECTORIZATION_H_
#define INCLUDE_CATEGORICALVECTORIZATION_H_

#include <cctype>
#include <string>
#include <google/protobuf/descriptor.h>
#include <FieldVectorization.h>

namespace vectorizer {

class CategoricalVectorization : public FieldVectorization {

public:

  CategoricalVectorization(const google::protobuf::FieldDescriptor *descriptor, Vectorization* inner)
  : FieldVectorization(descriptor, inner) { }

  virtual ~CategoricalVectorization() { }

  virtual void generate(std::stringstream& out) {

    out << "categorical(prefix + \"" << getDescriptor()->lowercase_name() << "\" + ";
    generate_result(out);
    out << ", builder);" << std::endl;

  }

private:

  void generate_result(std::stringstream& out) {
    out << "(";
    std::string name(getDescriptor()->camelcase_name());
    if (name.size() > 0) name[0] = toupper(name[0]);
    if (getInner().get() != nullptr) {
      getInner()->generate(out);
    } else {
      switch(getDescriptor()->type()) {
      case google::protobuf::FieldDescriptor::TYPE_DOUBLE :
      case google::protobuf::FieldDescriptor::TYPE_FLOAT :
        out << "Double.toString(src.get" << name << "())";
        break;
      case google::protobuf::FieldDescriptor::TYPE_INT64 :
      case google::protobuf::FieldDescriptor::TYPE_INT32 :
      case google::protobuf::FieldDescriptor::TYPE_UINT64 :
      case google::protobuf::FieldDescriptor::TYPE_FIXED64 :
      case google::protobuf::FieldDescriptor::TYPE_FIXED32 :
      case google::protobuf::FieldDescriptor::TYPE_UINT32 :
      case google::protobuf::FieldDescriptor::TYPE_SFIXED32 :
      case google::protobuf::FieldDescriptor::TYPE_SFIXED64 :
      case google::protobuf::FieldDescriptor::TYPE_SINT32 :
      case google::protobuf::FieldDescriptor::TYPE_SINT64 :
        out << "Integer.toString(src.get" << name << "())";
        break;
      case google::protobuf::FieldDescriptor::TYPE_BOOL :
        out << "Boolean.toString(src.get" << name << "())";
        break;
      case google::protobuf::FieldDescriptor::TYPE_STRING :
      case google::protobuf::FieldDescriptor::TYPE_ENUM :
        out << "src.get" << name << "()";
        break;
      default :
        throw std::invalid_argument("Unsupported field type");
      }
    }
    out << ")";
  }

};

} /* namespace vectorizer */

#endif /* INCLUDE_CATEGORICALVECTORIZATION_H_ */
