cmake_minimum_required (VERSION 3.1)

project(ProtocGenVectorizer)

set(CMAKE_MODULE_PATH ${CMAKE_MODULE_PATH} "${CMAKE_SOURCE_DIR}/cmake/Modules/")
set(CMAKE_CXX_STANDARD 11)

enable_testing()
add_subdirectory(src)
add_subdirectory(test)

# copy testing protos
file(COPY test/project DESTINATION ${PROJECT_BINARY_DIR}/test)

find_package (Maven)

find_package(Protobuf REQUIRED)
add_test(TestVectorizerGenerator test/TestVectorizerGenerator)
add_test(NAME CompileProtobufVectorizer COMMAND ${MAVEN_EXECUTABLE} test package install -Dmaven.repo.local=${PROJECT_BINARY_DIR}/.m2 WORKING_DIRECTORY ${PROJECT_BINARY_DIR}/test/project/protobuf-vectorizer)
add_test(NAME CompileGenratedCode COMMAND ${MAVEN_EXECUTABLE} clean compile test -Dmaven.repo.local=${PROJECT_BINARY_DIR}/.m2 WORKING_DIRECTORY ${PROJECT_BINARY_DIR}/test/project/test-vectorizer)
 