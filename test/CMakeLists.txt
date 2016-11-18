# build executables for testing
set(EXECUTABLE_OUTPUT_PATH ${PROJECT_BINARY_DIR}/test)
find_package(Protobuf REQUIRED)
include_directories(${PROTOBUF_INCLUDE_DIRS} ${Boost_INCLUDE_DIRS} ${CMAKE_CURRENT_SOURCE_DIR}/../include)
add_executable (TestPersonVectorizerGenerator VectorizerGenerator_testPerson.cpp)
target_link_libraries (TestPersonVectorizerGenerator ProtocGenVectorizer ${PROTOBUF_LIBRARIES} ${PROTOBUF_PROTOC_LIBRARIES})

add_executable (TestOpenRTBVectorizerGenerator VectorizerGenerator_testOpenRTB.cpp)
target_link_libraries (TestOpenRTBVectorizerGenerator ProtocGenVectorizer ${PROTOBUF_LIBRARIES} ${PROTOBUF_PROTOC_LIBRARIES})
