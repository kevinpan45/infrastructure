package io.kp45.consul.kv;

import org.springframework.lang.Nullable;

public interface ConsulKVOperations {

    @Nullable
    void write(String var1, @Nullable Object var2);

    void delete(String var1);

    @Nullable
    Object read(String var1);

    @Nullable
    <T> Object read(String var1, Class<T> var2);
}
