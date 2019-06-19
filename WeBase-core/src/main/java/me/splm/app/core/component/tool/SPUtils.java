package me.splm.app.core.component.tool;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class SPUtils implements IUtilsMarker {

    private SharedPreferences mPreference;

    private SPUtils() {
    }

    public void init(Context context,String fileName) {
        if (mPreference == null) {
            mPreference = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        }
    }

    public <E> void put(String key, @NonNull E defaultValue) {
        SharedPreferences.Editor editor = mPreference.edit();
        if (defaultValue instanceof Integer
                || defaultValue instanceof Long
                || defaultValue instanceof Float
                || defaultValue instanceof Double
                || defaultValue instanceof String) {
            editor.putString(key, String.valueOf(defaultValue));
        } else {
            //TODO 是否需要将对象序列后的值存储在这个地方
            //editor.putString(key, new JSONObject(defaultValue));
        }
        new SPCompat().apply(editor);
    }

    public <E> E get(String key, E defaultValue) {
        String value = mPreference.getString(key, String.valueOf(defaultValue));
        if (defaultValue instanceof Integer) {
            return (E) (Integer.valueOf(value));
        } else if (defaultValue instanceof Long) {
            return (E) Long.valueOf(value);
        } else if (defaultValue instanceof Double) {
            return (E) Double.valueOf(value);
        } else if (defaultValue instanceof Float) {
            return (E) Float.valueOf(value);
        } else if (defaultValue instanceof String) {
            return (E) value;
        } else {
            return null;//TODO 参照put方法，是否需要将对象序列后的值存储在这个地方
        }
    }

    public boolean isContains(String key) {
        return mPreference.contains(key);
    }

    public void remove(String key) {
        SharedPreferences.Editor editor = mPreference.edit();
        editor.remove(key);
        new SPCompat().apply(editor);
    }

    public Map<String, ?> getAll() {
        return mPreference.getAll();
    }

    public void clearAll() {
        SharedPreferences.Editor editor = mPreference.edit();
        editor.clear();
        new SPCompat().apply(editor);
    }

    private class SPCompat {
        private Method sApplyMethod = findApplyMethod();

        private Method findApplyMethod() {
            try {
                Class cls = SharedPreferences.Editor.class;
                return cls.getMethod("apply");
            } catch (NoSuchMethodException unused) {
                // fall through
            }
            return null;
        }

        public void apply(SharedPreferences.Editor editor) {
            if (sApplyMethod != null) {
                try {
                    sApplyMethod.invoke(editor);
                    return;
                } catch (InvocationTargetException unused) {
                    // fall through
                } catch (IllegalAccessException unused) {
                    // fall through
                }
            }
            editor.commit();
        }
    }
}
