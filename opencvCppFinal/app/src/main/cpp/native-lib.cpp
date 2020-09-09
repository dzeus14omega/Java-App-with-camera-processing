#include <jni.h>
#include <string>
#include <opencv2/core/core.hpp>
#include <opencv2/imgproc/imgproc.hpp>
#include <android/log.h>
#define TAG "NativeLib"

using namespace std;
using namespace cv;

/*
extern "C" {
    JNIEXPORT jstring JNICALL Java_com_example_opencvcppfinal_MainActivity_stringFromJNI(JNIEnv *env,jobject) {
        string hello = "Hello from C++";
        return env->NewStringUTF(hello.c_str());
    }

}



extern "C"
JNIEXPORT jint JNICALL
Java_com_example_opencvcppfinal_MainActivity_sum(JNIEnv *e, jobject thiz, jintArray arr) {
    // TODO: implement sum()
    jint length = e -> GetArrayLength(arr);
    jint convertedArr[length];
    e->GetIntArrayRegion(arr,0,length, convertedArr);
    jint result;
    for (jint i=0;i<length;i++){
        result += convertedArr[i];
    }
    return result;
}*/

extern "C"
JNIEXPORT void JNICALL
Java_com_example_opencvcppfinal_MainActivity_adaptiveThresholdFromJNI(JNIEnv *env, jobject thiz,
                                                                      jlong matAddr) {
    // TODO: implement adaptiveThresholdFromJNI()
    Mat &mat = *(Mat *) matAddr;
    clock_t begin = clock();
    adaptiveThreshold(mat, mat, 255, ADAPTIVE_THRESH_MEAN_C, THRESH_BINARY_INV, 21, 5);
    // log computation time to Android Logcat
    double totalTime = double(clock() - begin) / CLOCKS_PER_SEC;
    __android_log_print(ANDROID_LOG_INFO, TAG, "adaptiveThreshold computation time = %f seconds\n",totalTime);
}