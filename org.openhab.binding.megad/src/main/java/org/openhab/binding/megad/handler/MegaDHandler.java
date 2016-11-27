/**
 * Copyright (c) 2014-2015 openHAB UG (haftungsbeschraenkt) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.megad.handler;

import static org.openhab.binding.megad.MegaDBindingConstants.*;

import java.math.BigDecimal;
import java.util.Map;

import org.eclipse.smarthome.core.library.types.OnOffType;
import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingStatus;
import org.eclipse.smarthome.core.thing.binding.BaseThingHandler;
import org.eclipse.smarthome.core.types.Command;
import org.eclipse.smarthome.core.types.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link MegaDHandler} is responsible for handling commands, which are
 * sent to one of the channels.
 *
 * @author Timofey Fedyanin - Initial contribution
 */
public class MegaDHandler extends BaseThingHandler {
    private Logger logger = LoggerFactory.getLogger(MegaDHandler.class);
    // private MegaD megad;

    public MegaDHandler(Thing thing) {
        super(thing);
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
        try {
            Integer channel = CHANNEL.get(channelUID.getId());
            if (channel != null) {
                if (command.equals(OnOffType.ON)) {
                    // megad.turnOn(channel);
                }
                if (command.equals(OnOffType.OFF)) {
                    // megad.turnOff(channel);
                }
            }
        } catch (Throwable ignored) {
        }
    }

    @Override
    public void initialize() {
        // TODO: Initialize the thing. If done set status to ONLINE to indicate proper working.
        // Long running initialization should be done asynchronously in background.
        int port = ((BigDecimal) getThing().getConfiguration().get(P_PORT)).intValue();
        String host = getThing().getConfiguration().get(P_ADDRESS).toString();
        String pass = getThing().getConfiguration().get(P_PASSWORD).toString();
        int timeout = ((BigDecimal) getThing().getConfiguration().get(P_TIMEOUT)).intValue();

        // megad = new MegaD(host, pass, port, timeout);
        updateStatus(ThingStatus.ONLINE);

        // Note: When initialization can NOT be done set the status with more details for further
        // analysis. See also class ThingStatusDetail for all available status details.
        // Add a description to give user information to understand why thing does not work
        // as expected. E.g.
        // updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR,
        // "Can not access device as username and/or password are invalid");
    }

    @Override
    public void handleConfigurationUpdate(Map<String, Object> configurationParameters) {
        // TODO Auto-generated method stub
        super.handleConfigurationUpdate(configurationParameters);
    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub
        super.dispose();
    }

    @Override
    public void handleUpdate(ChannelUID channelUID, State newState) {
        // TODO Auto-generated method stub
        super.handleUpdate(channelUID, newState);
    }

    @Override
    public void thingUpdated(Thing thing) {
        // TODO Auto-generated method stub
        super.thingUpdated(thing);
    }

    @Override
    public void channelLinked(ChannelUID channelUID) {
        // TODO Auto-generated method stub
        super.channelLinked(channelUID);
    }

    @Override
    public void channelUnlinked(ChannelUID channelUID) {
        // TODO Auto-generated method stub
        super.channelUnlinked(channelUID);
    }

}
