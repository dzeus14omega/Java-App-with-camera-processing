//
// Created by hangd on 05/09/2020.
//

#include <jni.h>
#include <string>
#include <opencv2/core/core.hpp>
#include <opencv2/imgproc/imgproc.hpp>
#include <android/log.h>
#define TAG "NativeLib"

using namespace std;
using namespace cv;

extern "C" {
JNIEXPORT jstring JNICALL Java_com_example_opencvcppfinal_MainActivity_stringFromJNI(JNIEnv *env,jobject) {
    string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

}
