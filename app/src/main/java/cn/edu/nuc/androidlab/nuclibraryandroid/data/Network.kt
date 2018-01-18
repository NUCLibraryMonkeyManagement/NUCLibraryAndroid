package cn.edu.nuc.androidlab.nuclibraryandroid.data

import cn.edu.nuc.androidlab.nuclibraryandroid.bean.Classroom
import cn.edu.nuc.androidlab.nuclibraryandroid.bean.UserResult
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

/**
 * Network
 *
 * Created by MurphySL on 2018/1/15.
 */
class Network private constructor(){
    companion object {
        private val DEFAULT_TIMEOUT = 5L
        private val BASE_URL = ""

        private lateinit var retrofit : Retrofit

        private var api : CommonApi? = null

        @JvmStatic
        val instance: Network by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            Network()
        }
    }

    fun getCommonApi() : CommonApi =
            if(api == null) configRetrofit(CommonApi::class.java) else api!!

    private fun configClient() : OkHttpClient =
            OkHttpClient.Builder()
                    .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    .build()

    private fun <T> configRetrofit(service : Class<T>) : T =
            Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(configClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
                    .create(service)

}

interface CommonApi{
    @GET("/user/{studentID}")
    fun getUser(@Path("studentID")studentID : Int) : Observable<UserResult>

    @POST("/user/{studentID}")
    fun updateUser(@Path("studentID")studentID: Int, @Query("password")password : String) : Observable<UserResult>

    @POST("/user")
    fun insertUser(@Path("studentID")studentID: Int) : Observable<UserResult>

    fun getClassrooms() : Observable<Classroom>

}