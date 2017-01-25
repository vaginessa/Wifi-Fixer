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

package org.wahtod.wififixer;

import android.net.wifi.SupplicantState;

import org.wahtod.wififixer.utility.SupplicantPattern;

import java.util.Arrays;
import java.util.List;

/*
 * Lists of patterns which indicate high confidence bugged supplicant state
 */
public class SupplicantPatterns {
    public static final SupplicantPattern SCAN_BOUNCE_1 = new SupplicantPattern(Arrays
            .asList(SupplicantState.DISCONNECTED,
                    SupplicantState.SCANNING, SupplicantState.FOUR_WAY_HANDSHAKE,
                    SupplicantState.SCANNING, SupplicantState.DISCONNECTED,
                    SupplicantState.FOUR_WAY_HANDSHAKE, SupplicantState.DISCONNECTED,
                    SupplicantState.SCANNING));

    public static final SupplicantPattern SCAN_BOUNCE_2 = new SupplicantPattern(Arrays
            .asList(SupplicantState.DISCONNECTED,
                    SupplicantState.INACTIVE, SupplicantState.SCANNING,
                    SupplicantState.DISCONNECTED, SupplicantState.INACTIVE,
                    SupplicantState.SCANNING));

    public static final SupplicantPattern CONNECT_FAIL_1 = new SupplicantPattern(
            Arrays.asList(SupplicantState.ASSOCIATED,
                    SupplicantState.FOUR_WAY_HANDSHAKE,
                    SupplicantState.DISCONNECTED, SupplicantState.ASSOCIATED,
                    SupplicantState.FOUR_WAY_HANDSHAKE,
                    SupplicantState.DISCONNECTED));

    public static List<SupplicantPattern> getSupplicantPatterns() {
        return Arrays
                .asList(SCAN_BOUNCE_1, SCAN_BOUNCE_2, CONNECT_FAIL_1);
    }
}
