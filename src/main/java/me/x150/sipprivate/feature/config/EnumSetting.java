package me.x150.sipprivate.feature.config;

import java.lang.reflect.Modifier;
import java.util.Arrays;

public class EnumSetting<T extends Enum<?>> extends SettingBase<T> {
    private T[] values;

    @SuppressWarnings("unchecked")
    public EnumSetting(T defaultValue, String name, String description) {
        super(defaultValue, name, description);
        if (!Modifier.isPublic(defaultValue.getClass().getModifiers())) {
            throw new IllegalArgumentException("Enum has to be public!");
        }
        try {
            this.values = (T[]) defaultValue.getClass().getMethod("values").invoke(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public T parse(String value) {
        for (T t : values) {
            if (value.equalsIgnoreCase(t.toString())) {
                return t;
            }
        }
        return defaultValue;
    }

    public T[] getValues() {
        return values;
    }

    @Override
    public void setValue(T value) {
        if (Arrays.stream(values).noneMatch(t -> t.equals(value))) {
            return;
        }
        super.setValue(value);
    }

    public static class Builder<T extends Enum<?>> extends SettingBase.Builder<Builder<T>, T, EnumSetting<T>> {
        public Builder(T defaultValue) {
            super(defaultValue);
        }

        @Override
        public EnumSetting<T> get() {
            return new EnumSetting<>(defaultValue, name, description);
        }
    }
}
