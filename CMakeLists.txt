cmake_minimum_required (VERSION 3.1)

project(ProtocGenVectorizer)

set(CMAKE_MODULE_PATH ${CMAKE_MODULE_PATH} "${CMAKE_SOURCE_DIR}/cmake/Modules/")
set(CMAKE_CXX_STANDARD 11)

enable_testing()
add_subdirectory(src)

# copy testing protos
file(COPY test/project DESTINATION ${PROJECT_BINARY_DIR}/test)
file(COPY test/proto DESTINATION ${PROJECT_BINARY_DIR}/test)
file(DOWNLOAD https://raw.githubusercontent.com/wush978/protoc-gen-vectorizer/java/src/main/proto/person.proto test/proto/person.proto)

find_package (Maven)
find_package(Protobuf REQUIRED)
# add_test(NAME InstallProtobufVectorizer COMMAND ${MAVEN_EXECUTABLE} test package install -Dmaven.repo.local=${PROJECT_BINARY_DIR}/.m2 WORKING_DIRECTORY ${PROJECT_BINARY_DIR}/test/project/protobuf-vectorizer)
add_test(NAME TestPersonVectorizerGenerator COMMAND ${PROJECT_BINARY_DIR}/bin/ProtocGenVectorizer test/proto/person.proto --vec_java_out=test/project/test-vectorizer/src/main/java WORKING_DIRECTORY ${PROJECT_BINARY_DIR})
add_test(NAME TestImportVectorizerGenerator COMMAND ${PROJECT_BINARY_DIR}/bin/ProtocGenVectorizer test/proto/a.proto test/proto/b.proto --proto_path=test/proto --java_out=test/project/test-vectorizer/src/main/java --vec_java_out=test/project/test-vectorizer/src/main/java WORKING_DIRECTORY ${PROJECT_BINARY_DIR})
add_test(NAME TestOpenRTBVectorizerGenerator COMMAND ${PROJECT_BINARY_DIR}/bin/ProtocGenVectorizer test/project/test-vectorizer/src/main/proto/openrtb.proto --java_out=test/project/test-vectorizer/src/main/java --vec_java_out=test/project/test-vectorizer/src/main/java WORKING_DIRECTORY ${PROJECT_BINARY_DIR})
add_test(NAME TestGenratedCode COMMAND ${MAVEN_EXECUTABLE} clean compile test -U -Dmaven.repo.local=${PROJECT_BINARY_DIR}/.m2 WORKING_DIRECTORY ${PROJECT_BINARY_DIR}/test/project/test-vectorizer)
