package me.splm.app.core.logic.module;


import android.support.annotation.NonNull;

import me.splm.app.core.component.tool.UtilsFactory;

public interface IModel {
    UtilsFactory getUtilsFactory();

    <E> void writeValueToSP(String key, E value);

    <E> E getValueFromSP(String key, @NonNull E defaultValue);
}
