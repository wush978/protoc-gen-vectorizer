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

  std::string package;

  std::vector< const google::protobuf::FileDescriptor* > files;

  std::vector< std::shared_ptr<MessageVectorization> > operations;

  FileVectorization(const std::string&);

  void generate(google::protobuf::compiler::GeneratorContext* context);

  static std::map<std::string, std::shared_ptr<FileVectorization> > file_indexes;

public:

  virtual ~FileVectorization() { }

  static void add_file(const google::protobuf::FileDescriptor*, std::string*);

  static void generateAll(google::protobuf::compiler::GeneratorContext*);

  static void write(google::protobuf::io::ZeroCopyOutputStream* out, const std::string& data);

  static std::string getPackage(const google::protobuf::FileDescriptor* file);

};

} /* namespace vectorizer */

#endif /* INCLUDE_FILEVECTORIZATION_H_ */
