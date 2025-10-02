cmake_minimum_required(VERSION 3.30)

# When specifying an out-of-tree source a binary directory must be explicitly specified.
#add_subdirectory(../third_party/googletest ../build)

add_executable(image_test
    image/image.cpp
)
target_link_libraries(image_test image)

