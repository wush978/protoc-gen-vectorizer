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
#include <InteractionIndex.h>

namespace vectorizer {

class CategoricalVectorization : public FieldVectorization {

public:

  CategoricalVectorization(const google::protobuf::FieldDescriptor *descriptor, Vectorization* inner)
  : FieldVectorization(descriptor, inner) { }

  virtual ~CategoricalVectorization() { }

  virtual void generateContent(std::stringstream& out) {

    out << "categorical(prefix + \"" << getDescriptor()->lowercase_name() << "\", ";
//    generateResult(out);
    if (getInner().get() == nullptr) {
      out << getGetter();
    } else {
      out << "(";
      getInner()->generate(out);
      out << ")";
    }
    out << ", builder);" << std::endl;

    // interaction
    const auto& reverse_index(InteractionIndex::getInstance().getInteractionReverseIndex());
    auto p = reverse_index.find(this);
    if (p != reverse_index.end()) {
      for(std::shared_ptr<Interaction> interaction : p->second) {
        if (interaction->getFields()[0] == this) {
          generateInteraction(*interaction.get(), true, out);
        } else if (interaction->getFields()[1] == this){
          generateInteraction(*interaction.get(), false, out);
        } else {
          throw std::logic_error("Failed to find Vectorization*");
        }
      }
    }

  }

private:

  void generateInteraction(const Interaction& interaction, bool isFirst, std::stringstream& out) {
    out << "interaction.get(\"" << interaction.getName() << "\").add" << (isFirst ? "A" : "B") << "Categorical(";
    out << getGetter();
    out << ").add" << (isFirst ? "A" : "B") << "CategoricalValue(" << getGetter() << ");" << std::endl;
  }

//  void generateResult(std::stringstream& out) {
//    out << "(";
//    std::string name(getDescriptor()->camelcase_name());
//    if (name.size() > 0) name[0] = toupper(name[0]);
//    if (getInner().get() != nullptr) {
//      getInner()->generate(out);
//    } else {
//      switch(getDescriptor()->type()) {
//      case google::protobuf::FieldDescriptor::TYPE_DOUBLE :
//      case google::protobuf::FieldDescriptor::TYPE_FLOAT :
//        out << getGetter();
//        break;
//      case google::protobuf::FieldDescriptor::TYPE_INT64 :
//      case google::protobuf::FieldDescriptor::TYPE_INT32 :
//      case google::protobuf::FieldDescriptor::TYPE_UINT64 :
//      case google::protobuf::FieldDescriptor::TYPE_FIXED64 :
//      case google::protobuf::FieldDescriptor::TYPE_FIXED32 :
//      case google::protobuf::FieldDescriptor::TYPE_UINT32 :
//      case google::protobuf::FieldDescriptor::TYPE_SFIXED32 :
//      case google::protobuf::FieldDescriptor::TYPE_SFIXED64 :
//      case google::protobuf::FieldDescriptor::TYPE_SINT32 :
//      case google::protobuf::FieldDescriptor::TYPE_SINT64 :
//        out << "Integer.toString(" << getGetter() << ")";
//        break;
//      case google::protobuf::FieldDescriptor::TYPE_BOOL :
//        out << "Boolean.toString(" << getGetter() << ")";
//        break;
//      case google::protobuf::FieldDescriptor::TYPE_STRING :
//          out << getGetter();
//          break;
//      case google::protobuf::FieldDescriptor::TYPE_ENUM :
//          out << getGetter() << ".toString()";
//          break;
//      default :
//        throw std::invalid_argument("Unsupported field type");
//      }
//    }
//    out << ")";
//  }

};

} /* namespace vectorizer */

#endif /* INCLUDE_CATEGORICALVECTORIZATION_H_ */
