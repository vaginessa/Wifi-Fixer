/*Copyright [2010-2011] [David Van de Ven]

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

 */

package org.wahtod.wififixer.ui;

import org.wahtod.wififixer.R;
import org.wahtod.wififixer.SharedPrefs.PrefUtil;
import org.wahtod.wififixer.SharedPrefs.PrefConstants.Pref;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

public class ServiceFragment extends Fragment {
    
    private TextView version;
    private ImageButton servicebutton;
    
    @Override
    public void onResume() {
	super.onResume();
	setIcon();
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	    Bundle savedInstanceState) {
	View v = inflater.inflate(R.layout.service, null);
	// Set layout version code
	version = (TextView) v.findViewById(R.id.version);
	servicebutton = (ImageButton) v.findViewById(R.id.ImageButton01);
	setText();
	setIcon();
	return v;
    }
    
    private Context getContext() {
	return getActivity().getApplicationContext();
    }
    
    void setIcon() {
	DisplayMetrics metrics = new DisplayMetrics();
	getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
	int scale = metrics.densityDpi / 3;
	/*
	 * Set Bounds
	 */
	if (scale > 64)
	    scale = 64;
	
	servicebutton.setAdjustViewBounds(true);
	servicebutton.setMaxHeight(scale);
	servicebutton.setMaxWidth(scale);
	servicebutton.setClickable(false);
	servicebutton.setFocusable(false);
	servicebutton.setFocusableInTouchMode(false);
	if (PrefUtil.readBoolean(getContext(), Pref.DISABLE_KEY.key())) {
	    servicebutton.setImageResource(R.drawable.service_inactive);
	} else {
	    servicebutton.setImageResource(R.drawable.service_active);
	}
    }
    
    void setText() {
	PackageManager pm = getContext().getPackageManager();
	String vers = "";
	try {
	    /*
	     * Get PackageInfo object
	     */
	    PackageInfo pi = pm.getPackageInfo(getContext().getPackageName(), 0);
	    /*
	     * get version code string
	     */
	    vers = pi.versionName;
	} catch (NameNotFoundException e) {
	    /*
	     * shouldn't ever be not found
	     */
	    e.printStackTrace();
	}

	version.setText(vers.toCharArray(), 0, vers.length());
    }
}
