/*
 * MessageVectorization.cpp
 *
 *  Created on: Nov 14, 2016
 *      Author: wush
 */

#include <cstdlib>
#include <iostream>
#include <sstream>
#include <map>
#include <algorithm>
#include <boost/algorithm/string.hpp>
#include <google/protobuf/descriptor.pb.h>
#include <MessageVectorization.h>
#include <FileVectorization.h>
#include <MessageFieldVectorization.h>
#include <CategoricalVectorization.h>
#include <NumericalVectorization.h>
#include <InteractionIndex.h>

namespace vectorizer {

Vectorization* MessageVectorization::getVectorization(const google::protobuf::FieldDescriptor *descriptor, std::string* error) {
  if (descriptor->type() == google::protobuf::FieldDescriptor::TYPE_MESSAGE) {
    return new MessageFieldVectorization(descriptor);
  }
  google::protobuf::SourceLocation source_location;
  if (!descriptor->GetSourceLocation(&source_location)) {
    error->append("Failed to get source location for field: ");
    error->append(descriptor->full_name());
    return new Vectorization();
  }
  const std::string& leading(source_location.leading_comments);
  std::vector<std::string> lines;
  boost::split(lines, leading, boost::is_any_of("\n"));
  Vectorization* retval = nullptr;
  for(std::string& line : lines) {
    boost::trim(line);
    if (line.size() < 2) continue;
    if (line[0] != '\'') continue;
    if (line[1] != '@') continue;
    line = line.substr(2, line.size());
    std::vector<std::string> tokens;
    boost::split(tokens, line, boost::is_any_of(" "));
    tokens.erase(std::remove_if(tokens.begin(), tokens.end(), [](std::string& s) {
      return s.size() == 0;
    }), tokens.end());

    if (tokens.size() < 1) continue;
    if (tokens[0].compare("categorical") == 0) {
      retval = new CategoricalVectorization(descriptor, retval);
    } else if (tokens[0].compare("numerical") == 0) {
      retval = new NumericalVectorization(descriptor, retval);
    } else if (tokens[0].compare("interaction") == 0) {
      if (tokens.size() != 2) {
        error->append("//'@interaction <interaction-id>");
        return new Vectorization();
      }
      InteractionIndex::getInstance().set_index(tokens[1], retval);
    } else  {
      std::cerr << "Unknown annotation: " << tokens[0] << std::endl;
    }
  }
  return retval;
}

MessageVectorization::MessageVectorization(const google::protobuf::Descriptor *_descriptor, std::string* _error)
: error(_error) , descriptor(_descriptor), operators(){
  for(int i = 0;i < descriptor->field_count();i++) {
    operators.emplace_back(getVectorization(descriptor->field(i), error));
    if (error->size() > 0) return;
  }
}

static void getMessageName(const google::protobuf::Descriptor *descriptor, std::string& message_name) {
  auto containing_type = descriptor->containing_type();
  if (containing_type == nullptr) {
    const google::protobuf::FileDescriptor* file = descriptor->file();
    const google::protobuf::FileOptions& options(file->options());
    message_name.assign(FileVectorization::getPackage(file));
    message_name.append(".");
    if (options.has_java_outer_classname()) {
      message_name.append(options.java_outer_classname());
      message_name.append(".");
    } else {
      message_name.append(descriptor->name());
      message_name.append("OuterClass");
      message_name.append(".");
    }
  } else {
    getMessageName(containing_type, message_name);
    message_name.append(".");
  }
  message_name.append(descriptor->name());
}


void MessageVectorization::generate(std::stringstream& out) {
  std::string message_name;
  getMessageName(descriptor, message_name);
  out << "public static com.github.wush978.vectorizer.Vector.SparseVector.Builder apply(" << message_name << " src, java.util.Map<String, com.github.wush978.vectorizer.Interaction> interaction) {" << std::endl;
  out << "com.github.wush978.vectorizer.Vector.SparseVector.Builder builder = com.github.wush978.vectorizer.Vector.SparseVector.newBuilder();" << std::endl;
  out << "String prefix = src.getClass().getCanonicalName() + \".\";" << std::endl;
  for(std::shared_ptr<Vectorization>& pV : operators) {
    if (pV.get() == nullptr) continue;
    pV->generate(out);
  }
  out << "return builder;" << std::endl;
  out << "}" << std::endl;
  out << "public static com.github.wush978.vectorizer.Vector.SparseVector.Builder apply(" << message_name << " src) {" << std::endl;
  out << "java.util.Map<String, com.github.wush978.vectorizer.Interaction> interaction = new java.util.HashMap();" << std::endl;

  const auto& index(InteractionIndex::getInstance().getInteractionIndex());
  for(auto index_iterator = index.begin();index_iterator != index.end();index_iterator++) {
    out << "interaction.put(\"" << index_iterator->first << "\", com.github.wush978.vectorizer.Interaction.<String, String>of());" << std::endl;
  }

  out << "com.github.wush978.vectorizer.Vector.SparseVector.Builder builder = apply(src, interaction);" << std::endl;
  out << "apply(interaction, builder);" << std::endl;
  out << "return builder;" << std::endl;
  out << "}" << std::endl;
}

}


