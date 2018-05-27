package ar.com.fitlandia.fitlandia.utils;

public class ApiUtils {
    private ApiUtils() {}

    public static final String BASE_URL = "https://dadm-fitlandia-api.herokuapp.com/";

    public static APIService getAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }

}
