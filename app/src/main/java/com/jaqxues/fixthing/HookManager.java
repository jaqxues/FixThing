package com.jaqxues.fixthing;

import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

import static de.robv.android.xposed.XposedHelpers.callStaticMethod;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import static de.robv.android.xposed.XposedHelpers.findClass;

/**
 * This file was created by Jacques (jaqxues) in the Project FixThing.<br/>
 * Date: 18.08.2018 - Time 21:55.
 */

public class HookManager implements IXposedHookLoadPackage {
    private AtomicBoolean hooked = new AtomicBoolean();

    @Override
    public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
        if (!lpparam.packageName.equals("com.snapchat.android")
                || hooked.getAndSet(true))
            return;

        final Class nxz = findClass("nxz", lpparam.classLoader);
        findAndHookMethod(nxz, "a", String.class, new XC_MethodReplacement() {
            @Override
            protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                String str = ((String) param.args[0]).toUpperCase(Locale.ENGLISH);
                if (str.equals("HLS_VIDEO"))
                    return callStaticMethod(nxz, "valueOf", "VIDEO");
                return callStaticMethod(nxz, "valueOf", str);
            }
        });
    }
}