package interview.legacy.pinterest.api;



import interview.legacy.pinterest.model.DataList;
import interview.legacy.pinterest.model.Pin;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface PinService {

    @GET("me/pins/")
    Observable<DataList<Pin>> getPins(@Query("fields") String fields);

    @GET("boards/{id}/pins")
    Observable<DataList<Pin>> getBoardPins(@Path("id") Long id, @Query("fields") String fields);
}
