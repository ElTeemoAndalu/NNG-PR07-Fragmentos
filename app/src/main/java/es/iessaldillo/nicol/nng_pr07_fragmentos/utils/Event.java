package es.iessaldillo.nicol.nng_pr07_fragmentos.utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Event<T> {

    private T content;
    private boolean handled;

    public Event(@NonNull T content) {
        this.content = content;
    }

    public boolean hasBeenHandled() {
        return handled;
    }

    @Nullable
    public T getContentIfNotHandled() {
        if (handled) {
            return null;
        } else {
            handled = true;
            return content;
        }
    }

    @NonNull
    public T peekContent() {
        return content;
    }

}
