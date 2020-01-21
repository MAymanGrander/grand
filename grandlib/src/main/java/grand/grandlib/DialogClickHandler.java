package grand.grandlib;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import net.grand.R;

public class DialogClickHandler {
    private DialogActivity dialogActivity;
    public DialogClickHandler(FragmentActivity activity) {
        this.dialogActivity = (DialogActivity) activity;
    }

    public void onOuterViewClick(){
           dialogActivity.finish();
    }

    public void onCallClcik(){
        makeCall(dialogActivity, dialogActivity.getString(R.string.lib_grand_phone));
    }

    public void onWhatsAppClick(){
        whatsAppMsg(dialogActivity,  dialogActivity.getString(R.string.lib_grand_phone));
    }

    public static void makeCall(Activity context, String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phone));
        context.startActivity(intent);
    }

    public void whatsAppMsg(Activity context, String phone){
        //NOTE : please use with country code first 2digits without plus signed
        if (phone.contains("+"))
            phone.replace("+","");
        try {
            String msg = "Hello, Grand";
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=" + phone + "&text=" + msg)));
        }catch (Exception e){
            //whatsapp app not install
            Toast.makeText(context, "whtasapp not installed", Toast.LENGTH_LONG).show();
        }
    }

}
