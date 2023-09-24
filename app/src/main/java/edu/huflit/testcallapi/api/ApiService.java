package edu.huflit.testcallapi.api;

import android.database.Observable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import edu.huflit.testcallapi.model.Currency;
import edu.huflit.testcallapi.model.MyResponse;
import edu.huflit.testcallapi.model.ObjectData;
import edu.huflit.testcallapi.model.Post;
import edu.huflit.testcallapi.model.User;
import edu.huflit.testcallapi.model.UserIMG;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    //Ling API : http://apilayer.net/api/live?access_key=843d4d34ae72b3882e3db642c51e28e6&currencies=VND&source=USD&format=1
    // Link API post : https://jsonplaceholder.typicode.com/posts
    // Link API User :https://jsonplaceholder.typicode.com/posts?userId=1
    // Link API Dynamic : https://api.github.com/users/
    Gson gson= new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
     //ThemHeader mỗi khi call API
    Interceptor interceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Request.Builder builder= request.newBuilder();
            //Nếu muốn truyền thêm key thì copy dòng dưới này
            builder.addHeader("Authorization","KeyValueHere"); // giá trị token
            return  chain.proceed(builder.build());
        }
    };
    //log ra mỗi khi call API
    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor().setLevel((HttpLoggingInterceptor.Level.BODY));

    OkHttpClient.Builder okBuilder = new OkHttpClient.Builder()
            //interceptor có keyvalue
            //.addInterceptor(interceptor)
            //interceptor log
            .addInterceptor(loggingInterceptor)
            //trong 20s ko respond thi bao loi
            .readTimeout(20, java.util.concurrent.TimeUnit.SECONDS)
            .connectTimeout(20, TimeUnit.SECONDS);

    ApiService apiServiceAddHeader  = new Retrofit.Builder()
            .baseUrl("http://apilayer.net/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okBuilder.build())
            .build()
            .create(ApiService.class);
    //AndroidRx
    ApiService apiServiceRxandroid = new Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(okBuilder.build())
            .build()
            .create(ApiService.class);

    @GET("posts")
    Observable<List<ObjectData>> CallAPI();
    //log ra mỗi khi call
    ApiService apiService = new Retrofit.Builder()
            .baseUrl("http://apilayer.net/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

    ApiService apiServicePost = new Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);
    ApiService apiServiceGetUser = new Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);
    ApiService apiServiceDynamic = new Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);
    @GET("/users/{id}")
    Call<MyResponse> getReponse (@Path("id") int Id);
    @GET("posts")
    Call<List<User>> getUserList(@Query("userId") int userId);
    @GET("api/live")
    Call<Currency> convertUsdToVnd(@Query("access_key") String access_key,
                                   @Query("currencies") String currencies,
                                   @Query("source") String source,
                                   @Query("format") int format);

    //Co the truyen thang paramiter vao
    @GET("api/live?access_key=843d4d34ae72b3882e3db642c51e28e6&currencies=VND&source=USD&format=1")
    Call<Currency> convertUsdToVnd1();

    //vd ve truyen dong Link:Http://apilayer.net/api/group/1/users?sort=desc
    @GET("api/group/{id}/users")
    Call<Currency> getListUserFromGroup(@Path("id") int groupId,
                                        @Query("sort") String sort);
    //co nhieu cach goi get khach thong qua URL MANIPULATION trong trang https://square.github.io/retrofit/
    @POST("/posts")
    Call<Post>sendPost(@Body Post post);
    //Xai multipart thi phai co part va Request body
    @Multipart
    @POST("/posts")
    Call<UserIMG> registerAccount(@Part(Const.KEY_USERNAME)RequestBody username,
                                  @Part(Const.KEY_PASSWORD) RequestBody password,
                                  @Part MultipartBody.Part avt);
}
