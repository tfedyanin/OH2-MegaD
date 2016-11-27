package ru.ittim.oh2.megad.core;

/**
 * Created by Timofey on 23.10.2016.
 */
interface MegaListener {
    void change(int channel, PortSwitchStatus status);
}
