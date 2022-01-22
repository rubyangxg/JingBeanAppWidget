package com.whitefan.jdlite.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

public class SharedPreferencesUtils {

	/**
	 * 记录所有 Prefs
	 */
	private static Map<String, SharedPreferences> mPrefs = null;

	/**
	 * 记录所有 Prefs对应的Editor
	 */
	private static Map<String, SharedPreferences.Editor> mEditors = null;

	/**
	 * 根据指定的Key获取到的目标Prefs
	 */
	private static SharedPreferences tempPrefs = null;
	/**
	 * 根据指定的Key获取到的目标Editor
	 */
	private static SharedPreferences.Editor tempEditor = null;

	static {
		mPrefs = new HashMap<String, SharedPreferences>();
		mEditors = new HashMap<String, SharedPreferences.Editor>();
	}

	/**
	 * @Author wenJun
	 * @Description 根据传入的 CustomKeyboardView 获取对应的 Prefs
	 * @Date 2013-2-19
	 * @param context
	 * @param prefsKey
	 *            指定的Key 来源 Constants
	 * @return 获取到的 Prefs
	 */
	public static synchronized SharedPreferences getPrefs(Context context, String prefsKey) {
		tempPrefs = mPrefs.get(prefsKey);
		if (tempPrefs == null) {
			tempPrefs = context.getSharedPreferences(prefsKey, Context.MODE_PRIVATE);
			mPrefs.put(prefsKey, tempPrefs);
		}
		return tempPrefs;
	}

	/**
	 * @Author wenJun
	 * @Description 根据传入的 CustomKeyboardView 获取对应的 Editor
	 * @Date 2013-2-19
	 * @param context
	 * @param prefsKey
	 *            指定的Key 来源 Constants
	 * @return 获取到的 Editor
	 */
	public static synchronized SharedPreferences.Editor getEditor(Context context, String prefsKey) {
		tempEditor = mEditors.get(prefsKey);
		if (tempEditor == null) {
			tempEditor = getPrefs(context, prefsKey).edit();
			mEditors.put(prefsKey, tempEditor);
		}
		return tempEditor;
	}


}
