/*
 * Wifi Fixer for Android
 *        Copyright (C) 2010-2016  David Van de Ven
 *
 *        This program is free software: you can redistribute it and/or modify
 *        it under the terms of the GNU General Public License as published by
 *        the Free Software Foundation, either version 3 of the License, or
 *        (at your option) any later version.
 *
 *        This program is distributed in the hope that it will be useful,
 *        but WITHOUT ANY WARRANTY; without even the implied warranty of
 *        MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *        GNU General Public License for more details.
 *
 *        You should have received a copy of the GNU General Public License
 *        along with this program.  If not, see http://www.gnu.org/licenses
 */

package org.wahtod.wififixer.utility;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class StatusMessage {
    public static final String SSID_KEY = "SSID";
    public static final String STATUS_KEY = "STATUS";
    public static final String SIGNAL_KEY = "SIGNAL";
    public static final String SHOW_KEY = "SHOW";
    public static final String LINK_KEY = "LINKSPEED";
    public static final String EMPTY = "Not Connected";
    public static final String MB = "mb";
    public Bundle status;

    public StatusMessage(String ssid, String smessage,
                         int si, int sh) {
        status = new Bundle();
        this.setSSID(StringUtil.removeQuotes(ssid));
        this.setStatus(smessage);
        this.setSignal(si);
        this.setShow(sh);
    }

    public StatusMessage() {
        status = new Bundle();
    }

    @NonNull
    public static StatusMessage getNew() {
        return new StatusMessage();
    }

    @NonNull
    public static StatusMessage fromMessage(@NonNull Message m) {
        StatusMessage s = new StatusMessage();
        s.status.putAll(m.getData());
        return s;
    }

    @NonNull
    public static StatusMessage fromIntent(@NonNull Intent i) {
        StatusMessage s = new StatusMessage();
        s.status = i.getBundleExtra(StatusDispatcher.STATUS_DATA_KEY);
        return s;
    }

    public static void updateFromMessage(@NonNull StatusMessage s, @NonNull Message m) {
        Bundle b = m.getData();
        if (b.containsKey(SSID_KEY) && b.getString(SSID_KEY) != null
                && b.getString(SSID_KEY).length() > 1)
            if (!b.equals(s.getSSID()))
                s.setSSID(b.getString(SSID_KEY));
        if (b.containsKey(STATUS_KEY) && b.getString(STATUS_KEY) != null
                && b.getString(STATUS_KEY).length() > 1)
            if (!b.equals(s.getStatus()))
                s.setStatus(b.getString(STATUS_KEY));
        if (b.containsKey(SIGNAL_KEY) && b.getInt(SIGNAL_KEY) != s.getSignal())
            s.setSignal(b.getInt(SIGNAL_KEY));
        if (b.containsKey(SHOW_KEY) && b.getInt(SHOW_KEY) != 0)
            if (b.getInt(SHOW_KEY) != s.getShow())
                s.setShow(b.getInt(SHOW_KEY));
        if (b.containsKey(LINK_KEY)) {
            String ls = b.getString(LINK_KEY);
            if (ls != null && !ls.contains(MB))
                s.setLinkSpeed(ls.concat(MB));
        }
    }

    public static void send(Context context, @NonNull StatusMessage tosend) {
        if (ScreenStateDetector.getScreenState(context)) {
            Intent i = new Intent(StatusDispatcher.REFRESH_INTENT);
            i.putExtras(tosend.status);
            BroadcastHelper.sendBroadcast(context, i, true);
        }
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder out = new StringBuilder(getSSID());
        out.append(getStatus());
        out.append(getSignal());
        out.append(getShow());
        out.append(getLinkSpeed());
        return out.toString();
    }

    @Nullable
    public String getLinkSpeed() {
        return status.getString(LINK_KEY);
    }

    @NonNull
    public StatusMessage setLinkSpeed(String s) {
        status.putString(LINK_KEY, s);
        return this;
    }

    @Nullable
    public String getSSID() {
        return status.getString(SSID_KEY);
    }

    @NonNull
    public StatusMessage setSSID(@Nullable String s) {
        if (s == null)
            s = EMPTY;
        status.putString(SSID_KEY, StringUtil.removeQuotes(s));
        return this;
    }

    @Nullable
    public String getStatus() {
        return status.getString(STATUS_KEY);
    }

    @NonNull
    public StatusMessage setStatus(@Nullable String s) {
        if (s == null)
            s = EMPTY;
        status.putString(STATUS_KEY, s);
        return this;
    }

    @NonNull
    public StatusMessage setStatus(@NonNull Context c, int s) {
        status.putString(STATUS_KEY, c.getString(s));
        return this;
    }

    public int getSignal() {
        return status.getInt(SIGNAL_KEY);
    }

    @NonNull
    public StatusMessage setSignal(int i) {
        status.putInt(SIGNAL_KEY, i);
        return this;
    }

    public int getShow() {
        return status.getInt(SHOW_KEY);
    }

    @NonNull
    public StatusMessage setShow(int sh) {
        status.putInt(SHOW_KEY, sh);
        return this;
    }
}