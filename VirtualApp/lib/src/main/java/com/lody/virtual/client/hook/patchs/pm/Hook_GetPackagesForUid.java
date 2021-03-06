package com.lody.virtual.client.hook.patchs.pm;

import android.os.Process;

import com.lody.virtual.client.core.AppSandBox;
import com.lody.virtual.client.hook.base.Hook;

import java.lang.reflect.Method;
import java.util.Set;

/**
 * @author Lody
 *
 *
 * @see android.content.pm.IPackageManager#getPackagesForUid(int)
 */
/* package */ class Hook_GetPackagesForUid extends Hook<PackageManagerPatch> {
	/**
	 * 这个构造器必须有,用于依赖注入.
	 *
	 * @param patchObject
	 *            注入对象
	 */
	public Hook_GetPackagesForUid(PackageManagerPatch patchObject) {
		super(patchObject);
	}

	@Override
	public String getName() {
		return "getPackagesForUid";
	}

	@Override
	public Object onHook(Object who, Method method, Object... args) throws Throwable {
		int uid = (int) args[0];
		if (uid == Process.myUid()) {
			Set<String> pkg = AppSandBox.getInstalledPackages();
			pkg.add("com.android.vending");
			return pkg.toArray(new String[pkg.size()]);
		}
		 return method.invoke(who, args);
	}

	@Override
	public boolean isEnable() {
		return isAppProcess();
	}
}
