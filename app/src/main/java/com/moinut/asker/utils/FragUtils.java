package com.moinut.asker.utils;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class FragUtils {

    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                             @NonNull Fragment fragment, int frameId) {

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment, fragment.getClass().getName());
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        transaction.commit();
    }

    public static void addFragmentToActivityToBackStack(@NonNull FragmentManager fragmentManager,
                                             @NonNull Fragment fragment, int frameId) {

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment, fragment.getClass().getName());
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        transaction.addToBackStack(fragment.getClass().getName());
        transaction.commit();
    }

    public static void startAnotherFragment(@NonNull FragmentManager fragmentManager,
                                            @NonNull Fragment fromFragment,
                                            @NonNull Fragment toFragment, int frameId) {

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(frameId, toFragment, toFragment.getClass().getName());
        transaction.hide(fromFragment);
        transaction.commit();
    }

    public static void startAnotherFragmentToBackStack(@NonNull FragmentManager fragmentManager,
                                            @NonNull Fragment fromFragment,
                                            @NonNull Fragment toFragment, int frameId) {

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(frameId, toFragment, toFragment.getClass().getName());
        transaction.hide(fromFragment);
        transaction.addToBackStack(toFragment.getClass().getName());
        transaction.commit();
    }

    private FragUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

}
