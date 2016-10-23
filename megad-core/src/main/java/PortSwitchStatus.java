/**
 * Created by Timofey on 18.10.2016.
 */
public enum PortSwitchStatus {
    ON("on"),
    OFF("off"),
    UNKNOWN(""),;

    private String value;
    PortSwitchStatus(String s) {
        this.value = s;
    }


    public PortSwitchStatus getStatus(String status) {
        for (PortSwitchStatus portSwitchStatus : values()) {
            if (portSwitchStatus.value.equals(status)) {
                return portSwitchStatus;
            }
        }
        return UNKNOWN;
    }


    @Override
    public String toString() {
        return "Port status: " + super.toString();
    }
}
