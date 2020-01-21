package grand.grandlib;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import grand.grandlib.constantsutils.Codes;

public abstract class Grand {
    /////
    public static void showDialog(AppCompatActivity activity){
        MovementManager.startDialogActivity(activity, Codes.GRAND_DIALOG, new Bundle());
    }
}
