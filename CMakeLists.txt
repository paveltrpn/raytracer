cmake_minimum_required(VERSION 3.28)

set(CMAKE_C_COMPILER clang)
set(CMAKE_CXX_COMPILER clang++)
add_compile_options(-stdlib=libc++ -std=c++26)
add_link_options(-stdlib=libc++)
set(CMAKE_BUILD_TYPE Debug)

project (rt)

set(CMAKE_CXX_STANDARD 26)
set(CMAKE_EXPORT_COMPILE_COMMANDS ON )

include_directories("modules")
include_directories("third_party/json/include")

# Module network
# ================================
add_library(network)
target_sources(network
  PUBLIC
    FILE_SET CXX_MODULES FILES
    rt/network/network.cpp
    rt/network/server.cpp
)
target_link_libraries(network)

