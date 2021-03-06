package com.mislead.ikanxue.app.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import com.mislead.ikanxue.app.base.BaseFragment;

/**
 * FragmentHelper
 *
 * @author Mislead
 *         DATE: 2015/7/8
 *         DESC:
 **/
public class FragmentHelper {

  private static String TAG = "FragmentHelper";

  private static FragmentManager mFragmentManager;

  private static Fragment currentFragment;

  public static void init(FragmentManager fragmentManager) {

    if (mFragmentManager != null) return;

    mFragmentManager = fragmentManager;
  }

  public static void addFragment(Fragment fragment, int containerID) {
    FragmentTransaction transaction = mFragmentManager.beginTransaction();
    transaction.add(containerID, fragment, getFragmentTag(fragment));
    transaction.commit();
  }

  public static void showFragment(Fragment fragment, int containerID) {
    String tag = getFragmentTag(fragment);

    Fragment fg = mFragmentManager.findFragmentByTag(tag);

    if (fg == null) {
      addFragment(fragment, containerID);
      fg = fragment;
    }

    FragmentTransaction transaction = mFragmentManager.beginTransaction();
    transaction.show(fg);
    transaction.commit();
    mFragmentManager.executePendingTransactions();
    currentFragment = fg;
  }

  public static Fragment getCurrentFragment() {
    return currentFragment;
  }

  public static void hideFragment(Fragment fragment) {
    String tag = getFragmentTag(fragment);

    Fragment fg = mFragmentManager.findFragmentByTag(tag);

    if (fg == null) {
      return;
    }

    FragmentTransaction transaction = mFragmentManager.beginTransaction();
    transaction.hide(fragment);
    transaction.commit();
  }

  public static void removeFragment(Fragment fragment) {
    FragmentTransaction transaction = mFragmentManager.beginTransaction();
    transaction.remove(fragment);
    transaction.commit();
  }

  public static void executePendingTransactions() {
    mFragmentManager.executePendingTransactions();
  }

  public static boolean hasAdded(Fragment fragment) {
    String tag = getFragmentTag(fragment);

    Fragment fg = mFragmentManager.findFragmentByTag(tag);

    return fg != null;
  }

  public static String getFragmentTag(Fragment fragment) {
    if (fragment instanceof BaseFragment) {
      return TAG + fragment.getClass().getName() + ((BaseFragment) fragment).titleID;
    } else {
      return TAG + fragment.getClass().getName();
    }
  }

  public static void destroy() {
    mFragmentManager = null;
    currentFragment = null;
  }
}
