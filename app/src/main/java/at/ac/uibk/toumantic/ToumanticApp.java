package at.ac.uibk.toumantic;

import android.app.Application;

import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.SimpleFacebookConfiguration;

/**
 * Created by david on 6/28/16.
 */
public class ToumanticApp extends Application {
    private static final String APP_ID = "796899943783331";
    private static final String NAME_SPACE = "toumantic";

    @Override
    public void onCreate() {
        super.onCreate();
        Permission[] permissions = new Permission[]{
                Permission.USER_LIKES,
        };
        SimpleFacebookConfiguration configuration = new SimpleFacebookConfiguration.Builder()
                .setAppId(APP_ID)
                .setNamespace(NAME_SPACE)
                .setPermissions(permissions)
                .build();
        SimpleFacebook.setConfiguration(configuration);
    }
}
