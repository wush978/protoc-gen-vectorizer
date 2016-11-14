/*
 * VectorizerGenerator_test.cpp
 *
 *  Created on: Oct 30, 2016
 *      Author: wush
 */

#include <google/protobuf/compiler/command_line_interface.h>
#include <VectorizerGenerator.h>

using google::protobuf::compiler::CommandLineInterface;

int main(int argc, char* argv[]) {
  int fargc = 3;
  const char* const fargv[] = {
    "",
    "test/project/protobuf-vectorizer/src/main/proto/person.proto",
    "--vec_java_out=test/project/test-vectorizer/src/main/java/"
  };

  CommandLineInterface cli;

  // Support generation of vectorizer
  vectorizer::VectorizerGenerator generator;
  cli.RegisterGenerator("--vec_java_out", &generator, "Generate Vectorizer file");

  return cli.Run(fargc, fargv);
}
