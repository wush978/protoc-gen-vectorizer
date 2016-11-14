/*
 * FileVectorization.h
 *
 *  Created on: Nov 14, 2016
 *      Author: wush
 */

#ifndef INCLUDE_FILEVECTORIZATION_H_
#define INCLUDE_FILEVECTORIZATION_H_

#include <map>
#include <google/protobuf/descriptor.h>
#include <MessageVectorization.h>

namespace vectorizer {

class FileVectorization {

  const google::protobuf::FileDescriptor* file;

  std::vector< std::shared_ptr<MessageVectorization> > operations;

public:

  FileVectorization(const google::protobuf::FileDescriptor* file, std::string* error);

  virtual ~FileVectorization() { }

  void generate(google::protobuf::compiler::GeneratorContext*);

  static void write(google::protobuf::io::ZeroCopyOutputStream* out, const std::string& data);

  static std::string getPackage(const google::protobuf::FileDescriptor* file);

};

} /* namespace vectorizer */

#endif /* INCLUDE_FILEVECTORIZATION_H_ */
