/**
 * Copyright 2014, 2015, 2016 Elvis Stansvik
 * Copyright 2016 Wush Wu
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 *   Redistributions of source code must retain the above copyright notice,
 *   this list of conditions and the following disclaimer.
 *
 *   Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * Many implementations are modified from <https://github.com/estan/protoc-gen-doc>
 */

#include <VectorizerGenerator.h>
#include <unordered_map>
#include <google/protobuf/descriptor.h>

namespace gp = google::protobuf;

namespace vectorizer {

VectorizerGenerator::VectorizerGenerator() {
}

VectorizerGenerator::~VectorizerGenerator() {
}

/**
 * Extract annotations from a message or field
 */
template<typename Descriptor>
void extractAnnotation(const Descriptor *descriptor,
    std::string *error) {
//
//  gp::SourceLocation sourceLocation;
//
//  std::string debug = descriptor->DebugString();
//
//  std::string leading = sourceLocation.leading_comments;
//
//  std::string trailing = sourceLocation.trailing_comments;

  //TODO

}

static void addField(const gp::FieldDescriptor *descriptor, std::string* error) {
}

static void addMessage(const gp::Descriptor *descriptor, std::string* error) {

//  Message message;
//  message["message_name"] = descriptor->name();
//  message["message_long_name"] = longName(descriptor);
//  message["message_full_name"] = descriptor->full_name();
//  message["message_annotation"] = extractAnnotation(descriptor, error);
//
  // Add fields
//  Fields fields;
  for(int i = 0;i < descriptor->field_count();i++) {
    addField(descriptor->field(i), error);
  }
//  message["message_fields"] = fields;
//  messages->emplace_back(std::move(message));
}

static void addFile(const gp::FileDescriptor *file, std::string* error) {

//  FileInfo fileInfo;
//
//  fileInfo["file_name"] = file->name();
//  fileInfo["file_package"] = file->package();
//
//  Messages messages;
//
  for (int i = 0; i < file->message_type_count(); i++) {
    addMessage(file->message_type(i), error);
  }
//  fileInfo["file_messages"] = messages;
//
//  fileInfos->emplace_back(std::move(fileInfo));
//
}

static bool render(gp::compiler::GeneratorContext *context, std::string *error) {
  return true;
}

bool VectorizerGenerator::Generate(const google::protobuf::FileDescriptor* file,
    const std::string& parameter,
    google::protobuf::compiler::GeneratorContext* generator_context,
    std::string* error) const {
    static std::shared_ptr< std::vector<const gp::FileDescriptor *> > pParsedFiles(nullptr);
    if (pParsedFiles.get() == nullptr) {
      pParsedFiles.reset(new std::vector<const gp::FileDescriptor *>());
      generator_context->ListParsedFiles(pParsedFiles.get());
    }
    bool isLast = file == pParsedFiles->back();

    addFile(file, error);
    if (error->size() > 0) return false;

    if (isLast) {
      if (!render(generator_context, error)) {
        return false;
      }
    }
    return true;
}

}
