// Generated code from Butter Knife. Do not modify!
package ltd.vblago.prealpha.ui;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import ltd.vblago.prealpha.R;

public class MainActivity_ViewBinding implements Unbinder {
  private MainActivity target;

  private View view2131165286;

  @UiThread
  public MainActivity_ViewBinding(MainActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MainActivity_ViewBinding(final MainActivity target, View source) {
    this.target = target;

    View view;
    target.savedLocationTv = Utils.findRequiredViewAsType(source, R.id.savedLocation, "field 'savedLocationTv'", TextView.class);
    target.currentLocationTv = Utils.findRequiredViewAsType(source, R.id.currentLocation, "field 'currentLocationTv'", TextView.class);
    target.distanceTv = Utils.findRequiredViewAsType(source, R.id.distance, "field 'distanceTv'", TextView.class);
    target.requiredBeamingTv = Utils.findRequiredViewAsType(source, R.id.requiredBeaming, "field 'requiredBeamingTv'", TextView.class);
    target.currentBearingTv = Utils.findRequiredViewAsType(source, R.id.currentBeaming, "field 'currentBearingTv'", TextView.class);
    target.noticeBackgroundTv = Utils.findRequiredViewAsType(source, R.id.notice_background, "field 'noticeBackgroundTv'", RelativeLayout.class);
    view = Utils.findRequiredView(source, R.id.saveBtn, "method 'saveLocation'");
    view2131165286 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.saveLocation();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    MainActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.savedLocationTv = null;
    target.currentLocationTv = null;
    target.distanceTv = null;
    target.requiredBeamingTv = null;
    target.currentBearingTv = null;
    target.noticeBackgroundTv = null;

    view2131165286.setOnClickListener(null);
    view2131165286 = null;
  }
}
