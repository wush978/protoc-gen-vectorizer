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
#include <CategoricalVectorization.h>
#include <NumericalVectorization.h>

namespace vectorizer {

static Vectorization* getVectorization(const google::protobuf::FieldDescriptor *descriptor, std::string* error) {
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
    } else  {
      error->append("Unknown annotation: ");
      error->append(tokens[0]);
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



void MessageVectorization::generate(std::stringstream& out) {
  const google::protobuf::FileDescriptor* file = descriptor->file();
  const google::protobuf::FileOptions& options(file->options());
  std::string message_name(FileVectorization::getPackage(file));
  message_name.append(".");
  if (options.has_java_outer_classname()) {
    message_name.append(options.java_outer_classname());
    message_name.append(".");
  } else {
    message_name.append(descriptor->name());
    message_name.append("OuterClass");
    message_name.append(".");
  }
  message_name.append(descriptor->name());
  out << "public static com.github.wush978.vectorizer.Vector.SparseVector apply(" << message_name << " src) {" << std::endl;
  out << "com.github.wush978.vectorizer.Vector.SparseVector.Builder builder = com.github.wush978.vectorizer.Vector.SparseVector.newBuilder();" << std::endl;
  out << "String prefix = src.getClass().getCanonicalName() + \".\";" << std::endl;
  for(std::shared_ptr<Vectorization>& pV : operators) {
    if (pV.get() == nullptr) continue;
    pV->generate(out);
  }
  out << "return builder.build();" << std::endl;
  out << "}" << std::endl;
}

}


