package com.jenshen.tovisit.api;

import java.util.ArrayList;
import java.util.Iterator;

public class QueryList<Type> extends ArrayList<Type> {

    @Override
    public String toString() {
        Iterator<Type> it = iterator();
        if (! it.hasNext())
            return null;

        StringBuilder sb = new StringBuilder();
        for (;;) {
            Type next = it.next();
            sb.append(next.toString());
            if (! it.hasNext())
                return sb.append(']').toString();
            sb.append(',').append(' ');
        }
    }
}
