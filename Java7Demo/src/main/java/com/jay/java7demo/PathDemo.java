package com.jay.java7demo;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;

/**
 * Created by jay on 16/5/2.
 */
public class PathDemo {
    public static void main(String[] args) {
//        Path listing = Paths.get("/usr/bin/zip");
//        System.out.println("File Name [" + listing.getFileName() + "]");
//        System.out.println("Number of Name Elements in the Path [" + listing.getNameCount() + "]");
//        System.out.println("Parent Path [" + listing.getParent() + "]");
//        System.out.println("Root of Path [" + listing.getRoot() + "]");
//        System.out.println("Subpath from Root, 2 elements deep [" + listing.subpath(0, 2) + "]");

//        for (Method m : Integer.class.getMethods())
//            if (m.getName().equals("compareTo"))
//            System.out.println(m.toGenericString());

        testResolve();

        testRelativize();
    }

    private static void testRelativize() {
        Path hadoop = Paths.get("/Users/jay/conf/hadoop");
        Path conf = Paths.get("/Users/jay/conf/youku_hadoop_conf");
        System.out.println(hadoop.relativize(conf));
    }

    private static void testResolve() {
        Path prefix = Paths.get("/Users/jay/conf");
        Path completePath = prefix.resolve("hadoop/hadoop-local.xml");
        System.out.println(completePath);
    }

    public static <T extends Comparable<T>> T max(Collection<T> coll) {
        Iterator<T> it = coll.iterator();
        T candidate = it.next();
        while (it.hasNext()) {
            T elt = it.next();
            if (candidate.compareTo(elt) < 0) candidate = elt; }
        return candidate;
    }

    public static <T extends Comparable<? super T>> T max2(Collection<? extends T> coll) {
        Iterator<? extends T> it = coll.iterator();
        T candidate = it.next();
        while (it.hasNext()) {
            T elt = it.next();
            if (candidate.compareTo(elt) < 0) candidate = elt; }
        return candidate;
    }

    public static <T extends Comparable<? super T>> Comparator<T> naturalOrder() {
        return new Comparator<T>() {
            public int compare (T o1, T o2){
                return o1.compareTo(o2);
            }
        };
    }
}
