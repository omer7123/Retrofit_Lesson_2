package com.ripalay.retrofitlesson2;

import android.app.Application;

import com.ripalay.retrofitlesson2.data.remote.HerokuApi;
import com.ripalay.retrofitlesson2.data.remote.RetrofitClient;

public class App extends Application {
    private RetrofitClient retrofitClient;
    public static HerokuApi api;

    @Override
    public void onCreate() {
        super.onCreate();
        retrofitClient = new RetrofitClient();
        api = retrofitClient.provideApi();
    }
}
