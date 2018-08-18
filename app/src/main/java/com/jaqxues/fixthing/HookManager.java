package com.jaqxues.fixthing;

import java.util.concurrent.atomic.AtomicBoolean;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

import static de.robv.android.xposed.XposedHelpers.findAndHookConstructor;
import static de.robv.android.xposed.XposedHelpers.getObjectField;
import static de.robv.android.xposed.XposedHelpers.setObjectField;

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

        // For 10.20.5.0:    var1="e", var2="f"
        // For 10.26.5.0:    var1="g", var2="h"
        final String var1 = "e";
        final String var2 = "f";
        findAndHookConstructor(
                "com.snapchat.android.app.feature.discover.model.ChannelPage",
                lpparam.classLoader,
                "com.snapchat.android.app.feature.discover.model.ChannelPage$a", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                if (getObjectField(param.args[0], var1) == null)
                    setObjectField(param.args[0], var1, 1000);
                if (getObjectField(param.args[0], var2) == null)
                    setObjectField(param.args[0], var2, 1000);
            }
        });
    }
}