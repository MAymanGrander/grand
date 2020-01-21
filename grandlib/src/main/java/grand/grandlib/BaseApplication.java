package grand.grandlib;

import android.app.Application;
import org.jetbrains.annotations.NotNull;
import timber.log.Timber;

public class BaseApplication extends Application {

    private static BaseApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        initTimber();
    }

    public static synchronized BaseApplication getInstance() {
        return mInstance;
    }

    private void initTimber() {
        Timber.plant(new Timber.DebugTree() {
            @Override
            protected String createStackElementTag(@NotNull StackTraceElement element) {
                return super.createStackElementTag(element) + " line: " + element.getLineNumber();
            }
        });
    }

}
