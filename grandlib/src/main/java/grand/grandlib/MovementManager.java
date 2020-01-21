package grand.grandlib;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import net.grand.R;

import grand.grandlib.constantsutils.Codes;
import grand.grandlib.constantsutils.Params;
import timber.log.Timber;


public abstract class MovementManager {


    //---------Fragments----------//
    private static final int CONTAINER_ID = R.id.fl_home_container;

    public static void popAllFragments(Context context) {
        FragmentManager fm = ((FragmentActivity) context).getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
    }

    public static void addFragment(Context context, Fragment fragment, String backStackText) {
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().add(CONTAINER_ID, fragment);
        if (!backStackText.equals("")) {
            fragmentTransaction.addToBackStack(backStackText);
        }
        fragmentTransaction.commit();
    }


    public static void replaceFragment(Context context, Fragment fragment, String backStackText) {
        try {
            FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().replace(CONTAINER_ID, fragment);
            if (!backStackText.equals("")) {
                fragmentTransaction.addToBackStack(backStackText);
            }
            fragmentTransaction.commit();
        } catch (Exception e) {
            Timber.e(e);
        }
    }

    public static void replaceFragment(Context context, Fragment fragment, String backStackText, Bundle bundle) {
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().replace(CONTAINER_ID, fragment);
        if (!backStackText.equals("")) {
            fragmentTransaction.addToBackStack(backStackText);
        }
        fragmentTransaction.commit();
    }


    public static void popLastFragment(Context context) {
        ((FragmentActivity) context).getSupportFragmentManager().popBackStack();
    }


    //-----------Activities-----------------//

    public static void startActivityFragment(Context context, Class<?> activity, int page) {
        Intent intent = new Intent(context, activity);
        intent.putExtra(Params.INTENT_PAGE, page);
        context.startActivity(intent);
    }

    public static void startActivityFragment(Context context, Class<?> activity, int page, Bundle bundle) {
        Intent intent = new Intent(context, activity);
        intent.putExtra(Params.INTENT_PAGE, page);
        intent.putExtra(Params.BUNDLE_PAGE, bundle);
        context.startActivity(intent);
    }

    public static void setResult(Activity activity, int requestCode, Bundle bundle) {
        Intent intent = new Intent();
        intent.putExtras(bundle);
        activity.setResult(requestCode, intent);
        activity.finish();
    }

    public static void setResult(Activity activity, int requestCode, Intent intent) {
        activity.setResult(requestCode, intent);
        activity.finish();
    }

    public static void startActivityForResult(Activity currentActivity, Class<? extends Activity> destination, int requestCode,
                                              int codePage) {
        Intent intent = new Intent(currentActivity, destination);
        intent.putExtra(Params.INTENT_PAGE, codePage);
        currentActivity.startActivityForResult(intent, requestCode);
    }

    public static void startActivityForResult(Fragment fragment, Class<? extends Activity> destination, int requestCode,
                                              int codePage) {
        Intent intent = new Intent(fragment.requireActivity(), destination);
        intent.putExtra(Params.INTENT_PAGE, codePage);
        fragment.startActivityForResult(intent, requestCode);
    }

    public static void startActivityForResult(Fragment fragment, Class<? extends Activity> destination, int requestCode,
                                              int codePage, Bundle bundle) {
        Intent intent = new Intent(fragment.requireActivity(), destination);
        intent.putExtra(Params.INTENT_PAGE, codePage);
        intent.putExtra(Params.BUNDLE_PAGE, bundle);
        fragment.startActivityForResult(intent, requestCode);
    }


    public static void startDialogActivity(FragmentActivity context, int page, Bundle bundle) {
        Intent intent = new Intent(context, DialogActivity.class);
        intent.putExtra(Params.INTENT_PAGE, page);
        intent.putExtra(Params.BUNDLE_PAGE, bundle);
        context.startActivityForResult(intent, Codes.DIALOG_REQUEST_CODE);
    }




    public static void startWebPage(Context context, String page) {
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(page)));
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public static void openGPSSetting(Fragment fragment) {
        Intent callGPSSettingIntent = new Intent(
                android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        fragment.startActivityForResult(callGPSSettingIntent, Codes.GPS_SETTINGS_REQ_CODE);
    }
    public static void openGPSSetting(Activity activity) {
        Intent callGPSSettingIntent = new Intent(
                android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        activity.startActivityForResult(callGPSSettingIntent, Codes.GPS_SETTINGS_REQ_CODE);
    }

}
