/**
 * Copyright (c) 2014-2015 openHAB UG (haftungsbeschraenkt) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.megad;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.smarthome.core.thing.ThingTypeUID;

/**
 * The {@link MegaDBinding} class defines common constants, which are
 * used across the whole binding.
 *
 * @author Timofey Fedyanin - Initial contribution
 */
public class MegaDBindingConstants {

    public static final String BINDING_ID = "megad";

    // List of all Thing Type UIDs
    public final static ThingTypeUID THING_TYPE_MEGAD = new ThingTypeUID(BINDING_ID, "megad-thing");

    // List of all Channel ids

    public final static String P01_IN = "P01-IN";
    public final static String P02_IN = "P02-IN";
    public final static String P03_IN = "P03-IN";
    public final static String P04_IN = "P04-IN";
    public final static String P05_IN = "P05-IN";
    public final static String P06_IN = "P06-IN";
    public final static String P07_OUT = "P07-OUT";
    public final static String P08_OUT = "P08-OUT";
    public final static String P09_OUT = "P09-OUT";
    public final static String P10_OUT = "P10-OUT";
    public final static String P11_OUT = "P11-OUT";
    public final static String P12_OUT = "P12-OUT";
    public final static String P13_OUT = "P13-OUT";

    public final static String P_PORT = "port";
    public final static String P_ADDRESS = "address";
    public final static String P_PASSWORD = "password";
    public final static String P_TIMEOUT = "timeout";

    public final static Map<String, Integer> CHANNEL = new HashMap<String, Integer>() {
        {
            put(P01_IN, 1);
            put(P02_IN, 2);
            put(P03_IN, 3);
            put(P04_IN, 4);
            put(P05_IN, 5);
            put(P06_IN, 6);
            put(P07_OUT, 7);
            put(P08_OUT, 8);
            put(P09_OUT, 9);
            put(P10_OUT, 10);
            put(P11_OUT, 11);
            put(P12_OUT, 12);
            put(P13_OUT, 13);
        }
    };
}
