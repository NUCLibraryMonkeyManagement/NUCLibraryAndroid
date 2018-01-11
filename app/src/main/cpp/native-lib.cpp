#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring

JNICALL
Java_cn_edu_nuc_androidlab_nuclibraryandroid_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
