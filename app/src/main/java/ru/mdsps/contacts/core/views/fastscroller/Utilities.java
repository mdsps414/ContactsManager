package ru.mdsps.contacts.core.views.fastscroller;

import android.content.res.Resources;
import android.os.Build;
import android.view.View;

final class Utilities {

  static boolean isRtl(Resources res) {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 &&
        res.getConfiguration().getLayoutDirection() == View.LAYOUT_DIRECTION_RTL;
  }

}
