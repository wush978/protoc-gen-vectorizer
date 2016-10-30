cmake_minimum_required (VERSION 3.1)

project(ProtocGenVectorizer)

set(CMAKE_MODULE_PATH ${CMAKE_MODULE_PATH} "${CMAKE_SOURCE_DIR}/cmake/Modules/")
set(CMAKE_CXX_STANDARD 11)

add_subdirectory(src)
add_subdirectory(test)

enable_testing()
add_test(TestVectorizerGenerator test/TestVectorizerGenerator)


 