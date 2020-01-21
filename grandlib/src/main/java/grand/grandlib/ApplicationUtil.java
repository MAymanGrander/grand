package grand.grandlib;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Environment;
import android.text.format.DateFormat;
import android.util.Base64;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;

import net.grand.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import timber.log.Timber;

public abstract class ApplicationUtil {

    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }


    public static int getActionBarHeight(Activity activity) {
        // Calculate ActionBar height
        int actionBarHeight = 0;
        TypedValue tv = new TypedValue();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if (activity.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv,
                    true))
                actionBarHeight = TypedValue.complexToDimensionPixelSize(
                        tv.data, activity.getResources().getDisplayMetrics());
        } else {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,
                    activity.getResources().getDisplayMetrics());
        }
        return actionBarHeight;
    }

    public static Bitmap drawableResToBitmapWithDimensions(@DrawableRes int drawableResId, int newWidthDim, int newHeightDim) {
        Drawable d = BaseApplication.getInstance().getResources().getDrawable(drawableResId);
        if (d instanceof BitmapDrawable) {
            Timber.e("BitmapDrawable");
            return ((BitmapDrawable) d).getBitmap();
        }
        if (d instanceof GradientDrawable) {
            Timber.e("GradientDrawable");
            Bitmap b = BitmapFactory.decodeResource(BaseApplication.getInstance().getResources(), drawableResId);
            int width = dimenToInt(newWidthDim);
            int height = dimenToInt(newHeightDim);
            Bitmap resizedBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

            float scaleX = width / (float) b.getWidth();
            float scaleY = height / (float) b.getHeight();
            float pivotX = 0;
            float pivotY = 0;

            Matrix scaleMatrix = new Matrix();
            scaleMatrix.setScale(scaleX, scaleY, pivotX, pivotY);

            Canvas canvas = new Canvas(resizedBitmap);
            canvas.setMatrix(scaleMatrix);
            canvas.drawBitmap(b, 0, 0, new Paint(Paint.FILTER_BITMAP_FLAG));

            return resizedBitmap;
        }
        Timber.e("Bitmap.Config.ARGB_8888");
        Bitmap bit = BitmapFactory.decodeResource(BaseApplication.getInstance().getResources(), drawableResId);
        return bit.copy(Bitmap.Config.ARGB_8888, true);
    }

    public static Bitmap getBitmapFromVectorDrawable(@DrawableRes int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(BaseApplication.getInstance().getApplicationContext(), drawableId);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = (DrawableCompat.wrap(drawable)).mutate();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    public static int dimenToInt(int dimenRes) {
        return (int) BaseApplication.getInstance().getResources().getDimension(dimenRes);
    }

    public static void getScreenSize(Activity context) {
        Display display = context.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        Timber.d("width: " + width + "px");
        Timber.d("height" + height + "px");
    }

    public static View getRootView(Activity activity) {
        return activity.getWindow().getDecorView().getRootView();
    }

    public static void strikeThrough(TextView textView) {
        textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }

    public static void underlined(TextView textView) {
        textView.setPaintFlags(textView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

    public static void setElevation(View view, int elevation) {
        ViewCompat.setElevation(view, elevation);
    }






    public static String removeLastAndFirstChar(String str) {
        if (str != null && str.trim().length() > 0) {
            str = str.trim().substring(0, str.trim().length() - 1);
            str = str.trim().substring(1);
            return str.replace(" ","");
        }
        return null;
    }

    public static String getString(int resId) {
        return BaseApplication.getInstance().getString(resId);
    }


    public static int getColorInt(@NonNull String stringColor) {
        try {
            return Color.parseColor(stringColor);
        } catch (Exception e) {
            Timber.e(e);
            return Color.parseColor("#FFFFFF");
        }
    }

    public static int getColorRes(int colorRes) {
        return BaseApplication.getInstance().getResources().getColor(colorRes);
    }





    public static void playAudio(MediaPlayer mediaPlayer, String url) {
        mediaPlayer.setAudioAttributes(new AudioAttributes
                .Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build()
        );
        if(mediaPlayer.isPlaying()){
            Timber.e("isplaying");
            mediaPlayer.stop();
            mediaPlayer.reset();
        }
        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.setOnPreparedListener(mediaPlayer1 -> {
                mediaPlayer1.start();
            });
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnCompletionListener(mediaPlayer12 -> {
                Timber.e("on complete");
            });
        } catch (IOException | IllegalArgumentException e) {
            Timber.e(e);
        }
    }

    public void copyToClipboard(Context context, String text) {
        try {
            ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("promo code", text);
            Objects.requireNonNull(clipboard).setPrimaryClip(clip);
        } catch (Exception e) {
            Timber.e(e);
        }
    }

    public static void setTransparent(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, true, activity);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            );
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, false, activity);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
            activity.getWindow().setNavigationBarColor(Color.TRANSPARENT);

            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    private static void setWindowFlag(final int bits, boolean on, Activity activity) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }


    public static Bitmap stringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0,
                    encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    public static String bitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos); //quality is 0...100
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    public static void takeScreenshot(Activity activity) {
        View rootView = ApplicationUtil.getRootView(activity);
        rootView.setDrawingCacheEnabled(true);
        saveBitmap(rootView.getDrawingCache(), activity);
    }

    private static void saveBitmap(Bitmap bitmap, Activity activity) {
        Date currentDate = Calendar.getInstance().getTime();
        String now = DateFormat.format("yyyyMMdd/hh:mm:ss", currentDate).toString();
        String rootPath = Environment.getExternalStorageState();
        if (rootPath.contains("-")) {
            rootPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString() + "/Camera";
        } else {
            rootPath = Environment.getExternalStorageState();
        }
        File mediaStorageDir = new File(rootPath, "EM App");
        Timber.e("root path: " + mediaStorageDir.getAbsolutePath());
        if (!mediaStorageDir.exists()) {
            if (mediaStorageDir.mkdirs()) {
                createImage(mediaStorageDir, activity, now, bitmap);
            } else {
                Timber.e("failed to create EM App directory");
            }
        } else {
            createImage(mediaStorageDir, activity, now, bitmap);
        }
    }

    private static void createImage(File rootDir, Activity activity, String dateNow, Bitmap bitmap) {
        String imagePath = "/em" + dateNow + "screenshot.jpg";
        File file = new File(rootDir, imagePath);
        Timber.e("image path: " + file.getAbsolutePath());

        if (file.exists()) file.delete();
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
            fos.flush();
            fos.close();
            Toast.makeText(activity, getString(R.string.lib_image_saved), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Timber.e(e.getMessage(), e);
        }
    }

    public static void loadImageFromStorage(String path, ImageView imageView) {
        try {
            File f = new File(path);
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            imageView.setImageBitmap(b);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
