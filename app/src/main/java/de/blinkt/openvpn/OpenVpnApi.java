package de.blinkt.openvpn;

import android.content.Context;
import android.content.Intent;
import android.net.VpnService;
import android.os.Build;
import android.os.RemoteException;
import android.text.TextUtils;

import com.cybernetvpn.cybernetvpn.R;

import java.io.IOException;
import java.io.StringReader;

import de.blinkt.openvpn.core.ConfigParser;
import de.blinkt.openvpn.core.ProfileManager;
import de.blinkt.openvpn.core.VPNLaunchHelper;

public class OpenVpnApi {

    public static void startVpn(Context context, String inlineConfig, String userName, String pw) throws RemoteException {
        if (TextUtils.isEmpty(inlineConfig)) throw new RemoteException("config is empty");
        Intent intent = VpnService.prepare(context);
        if (intent != null) {
            intent = new Intent(context, VpnAuthActivity.class);
            intent.putExtra(VpnAuthActivity.KEY_CONFIG, inlineConfig);
            if (!TextUtils.isEmpty(userName)) {
                intent.putExtra(VpnAuthActivity.KEY_USERNAME, userName);
            }
            if (!TextUtils.isEmpty(pw)) {
                intent.putExtra(VpnAuthActivity.KEY_PASSWORD, pw);
            }
            context.startActivity(intent);
        } else {
            startVpnInternal(context, inlineConfig, userName, pw);
        }
    }

    static void startVpnInternal(Context context, String inlineConfig, String userName, String pw) throws RemoteException {
        ConfigParser cp = new ConfigParser();
        try {
            cp.parseConfig(new StringReader(inlineConfig));
            VpnProfile vp = cp.convertProfile();
            vp.mName = Build.MODEL;
            if (vp.checkProfile(context) != R.string.no_error_found){
                throw new RemoteException(context.getString(vp.checkProfile(context)));
            }
            vp.mProfileCreator = context.getPackageName();
            vp.mUsername = "password";
            vp.mPassword = "password";
            ProfileManager.setTemporaryProfile(vp);
            VPNLaunchHelper.startOpenVpn(vp, context);
        } catch (IOException | ConfigParser.ConfigParseError e) {
            throw new RemoteException(e.getMessage());
        }
    }
}
