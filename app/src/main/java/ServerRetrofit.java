import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ServerRetrofit {

    @FormUrlEncoded
    @POST("user_channel.php")
    Call<ResponseBody> Channel_load(String id);
}
