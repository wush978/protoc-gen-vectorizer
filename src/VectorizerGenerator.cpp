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
#include <FileVectorization.h>
#include <InteractionIndex.h>

namespace gp = google::protobuf;

namespace vectorizer {

VectorizerGenerator::VectorizerGenerator() : operations() { }

VectorizerGenerator::~VectorizerGenerator() { }


bool VectorizerGenerator::Generate(const google::protobuf::FileDescriptor* file,
    const std::string& parameter,
    gp::compiler::GeneratorContext* generator_context,
    std::string* error) const {
    static std::shared_ptr< std::vector<const gp::FileDescriptor *> > pParsedFiles(nullptr);
    if (pParsedFiles.get() == nullptr) {
      pParsedFiles.reset(new std::vector<const gp::FileDescriptor *>());
      generator_context->ListParsedFiles(pParsedFiles.get());
    }
    bool isLast = file == pParsedFiles->back();

    operations.emplace_back(new FileVectorization(file, error));
    if (error->size() > 0) return false;

    if (isLast) {
      // check interaction
      const auto& index(InteractionIndex::getInstance().getInteractionIndex());
      for(auto index_iterator = index.begin();index_iterator != index.end();index_iterator++) {
        if (index_iterator->second->getFields().size() != 2) {
          error->append("interaction: ");
          error->append(index_iterator->first);
          error->append(" does not contain two fields");
          return false;
        }
      }
      for(std::shared_ptr<FileVectorization>& pFV : operations) {
        pFV->generate(generator_context);
      }
    }
    return true;
}

}
