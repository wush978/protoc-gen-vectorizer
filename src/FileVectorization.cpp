/*
 * FileVectorization.cpp
 *
 *  Created on: Nov 14, 2016
 *      Author: wush
 */

#include <iostream>
#include <sstream>
#include <algorithm>
#include <google/protobuf/descriptor.pb.h>
#include <google/protobuf/io/zero_copy_stream.h>
#include <FileVectorization.h>

namespace vectorizer {

std::map<std::string, std::shared_ptr<FileVectorization> > FileVectorization::file_indexes;

static void addMessageType(const google::protobuf::Descriptor *descriptor, std::string* error, std::vector< std::shared_ptr<MessageVectorization> >& operations) {
  operations.emplace_back(new MessageVectorization(descriptor, error));
  if (error->length() > 0) return;
  for(int i = 0;i < descriptor->nested_type_count();i++) {
    addMessageType(descriptor->nested_type(i), error, operations);
    if (error->length() > 0) return;
  }
}

FileVectorization::FileVectorization(const std::string& _package) : package(_package), files(), operations() { }

void FileVectorization::add_file(const google::protobuf::FileDescriptor* file, std::string* error) {
  std::string package(getPackage(file));
  auto pFV = file_indexes.find(package);
  if (pFV == file_indexes.end()) {
    file_indexes.emplace(std::make_pair(package, std::shared_ptr<FileVectorization>(new FileVectorization(package))));
    pFV = file_indexes.find(package);
  }
  pFV->second->files.push_back(file);
  for (int i = 0; i < file->message_type_count(); i++) {
    // check nested type recursively
    addMessageType(file->message_type(i), error, pFV->second->operations);
    if (error->length() > 0) return;
  }
}

std::string FileVectorization::getPackage(const google::protobuf::FileDescriptor* file) {
  std::string retval;
  const google::protobuf::FileOptions& options(file->options());
  if (options.has_java_package()) {
    retval.assign(options.java_package());
  } else {
    retval.assign(file->package());
  }
  return retval;
}

void FileVectorization::generate(google::protobuf::compiler::GeneratorContext* context) {
  std::string file_name(package);
  std::replace(file_name.begin(), file_name.end(), '.', '/');
  file_name.append("/Vectorizer.java");
  std::shared_ptr<google::protobuf::io::ZeroCopyOutputStream> out(context->Open(file_name));
  std::stringstream ss;
  ss << "package " << package << ";" << std::endl;
  ss << std::endl;
  ss << "class Vectorizer extends com.github.wush978.vectorizer.BaseVectorizer {" << std::endl;
  for(const google::protobuf::FileDescriptor* file : files) {
    const google::protobuf::FileOptions& options(file->options());
    for(std::shared_ptr<MessageVectorization>& pMV : operations) {
      if (pMV.get() == nullptr) continue;
      pMV->generate(ss);
    }
  }
  ss << "}" << std::endl;
  write(out.get(), ss.str());
}

void FileVectorization::generateAll(google::protobuf::compiler::GeneratorContext* context) {
  for(auto& entry : file_indexes) {
    entry.second->generate(context);
  }
}

void FileVectorization::write(google::protobuf::io::ZeroCopyOutputStream* out, const std::string& data) {
  const char *start = &data[0], *begin = &data[0];
  char *buffer;
  int size;
  while(true) {
    out->Next((void**) &buffer, &size);
    int size_left = data.size() - (start - begin);
    int bytes = std::min(size, size_left);
    memcpy(buffer, start, bytes);
    if (size_left <= size) {
      out->BackUp(size - size_left);
      break;
    } else {
      start += bytes;
    }
  }
}

}
