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

#include <google/protobuf/compiler/command_line_interface.h>
#include <google/protobuf/compiler/java/java_generator.h>
#include <VectorizerGenerator.h>

using namespace google::protobuf::compiler;

int main(int argc, char* argv[]) {
  CommandLineInterface cli;

  // Support generation of java code
  java::JavaGenerator java_generator;
  cli.RegisterGenerator("--java_out", &java_generator, "Generate java source file.");

  // Support generation of vectorizer
  vectorizer::VectorizerGenerator generator;
  cli.RegisterGenerator("--vec_java_out", &generator, "Generate vectorization of java file");

  return cli.Run(argc, argv);
}
