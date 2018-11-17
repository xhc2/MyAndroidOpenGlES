cmake_minimum_required(VERSION 3.4.1)

include_directories(src/main/cpp/include)
add_library( native-lib

             SHARED

             # Provides a relative path to your source file(s).
             src/main/cpp/native-lib.cpp
             src/main/cpp/myopengl.cpp
             src/main/cpp/MyOpenGlUtils.cpp
             src/main/cpp/Matrix.cpp
             src/main/cpp/opengl_table.cpp
             src/main/cpp/MyThread.cpp
             )


find_library(
              log-lib
              log )


target_link_libraries(
                       native-lib
                       GLESv2
                        OpenSLES
                        EGL
                       android
                       log
                       ${log-lib} )