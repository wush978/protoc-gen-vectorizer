/*
 * VectorizerGenerator_testOpenRTB.cpp
 *
 *  Created on: 2016年11月18日
 *      Author: wush.wu
 */

#include <google/protobuf/compiler/command_line_interface.h>
#include <google/protobuf/compiler/java/java_generator.h>
#include <VectorizerGenerator.h>

using google::protobuf::compiler::CommandLineInterface;

int main(int argc, char* argv[]) {

  // openrtb
  int fargc = 4;
  const char* const fargv[] = {
    "",
    "test/project/test-vectorizer/src/main/proto/openrtb.proto",
    "--java_out=test/project/test-vectorizer/src/main/java",
    "--vec_java_out=test/project/test-vectorizer/src/main/java"
  };

  CommandLineInterface cli;

  // Support generation of vectorizer
  vectorizer::VectorizerGenerator generator;
  google::protobuf::compiler::java::JavaGenerator java_generator;
  cli.RegisterGenerator("--java_out", &java_generator, "Generate Vectorizer file");
  cli.RegisterGenerator("--vec_java_out", &generator, "Generate Vectorizer file");

  int code = cli.Run(fargc, fargv);
  if (code != 0) return code;

}


