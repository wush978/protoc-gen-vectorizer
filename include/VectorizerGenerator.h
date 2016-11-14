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

#ifndef VECTORIZERGENERATOR_H_
#define VECTORIZERGENERATOR_H_

#include <vector>
#include <map>
#include <boost/variant.hpp>
#include <google/protobuf/compiler/code_generator.h>

namespace vectorizer {

class VectorizerGenerator: public google::protobuf::compiler::CodeGenerator {

public:

  VectorizerGenerator();

  virtual ~VectorizerGenerator();

  virtual bool Generate(const google::protobuf::FileDescriptor* file,
                          const std::string& parameter,
                          google::protobuf::compiler::GeneratorContext* generator_context,
                          std::string* error) const;

};

}

#endif /* VECTORIZERGENERATOR_H_ */
