package com.binance.dex.api.client.encoding.amino;

import javax.annotation.Nullable;
import java.util.ArrayList;

/**
 * @author Fitz.Lu
 **/
public class AminoField<T> {

    public static AminoFieldsBuilder newFieldsBuilder(){
        return new AminoFieldsBuilder();
    }

    private Class<T> clazz;

    private T t;

    private boolean skipWhenEncode = false;

    public AminoField(Class<T> clazz, @Nullable T t, boolean skipWhenEncode) {
        this.clazz = clazz;
        this.t = t;
        this.skipWhenEncode = skipWhenEncode;
    }

    public Class<T> getClazz() {
        return clazz;
    }

    public void setClazz(Class<T> clazz) {
        this.clazz = clazz;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    public boolean isSkipWhenEncode() {
        return skipWhenEncode;
    }

    public void setSkipWhenEncode(boolean skipWhenEncode) {
        this.skipWhenEncode = skipWhenEncode;
    }

    public static class AminoFieldsBuilder {

        private final ArrayList<AminoField<?>> fields;

        public AminoFieldsBuilder(){
            fields = new ArrayList<>();
        }

        public <T2> AminoFieldsBuilder addField(Class<T2> clazz, T2 value, boolean skipWhenEncode){
            fields.add(new AminoField<>(clazz, value, skipWhenEncode));
            return this;
        }

        public ArrayList<AminoField<?>> build(){
            return fields;
        }

    }

}
