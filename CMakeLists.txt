cmake_minimum_required (VERSION 3.1)

project(ProtocGenVectorizer)

set(CMAKE_MODULE_PATH ${CMAKE_MODULE_PATH} "${CMAKE_SOURCE_DIR}/cmake/Modules/")
set(CMAKE_CXX_STANDARD 11)

enable_testing()
add_subdirectory(src)
add_subdirectory(test)

# copy testing protos
file(COPY test/proto DESTINATION ${PROJECT_BINARY_DIR}/test)
file(COPY test/project DESTINATION ${PROJECT_BINARY_DIR}/test)

find_package (Maven)

add_test(TestVectorizerGenerator test/TestVectorizerGenerator test/proto/person.proto --vec_java_out=test/project/src/main/java/)
add_test(CompileGenratedCode bash -c "cd test/project && mvn compile")
 