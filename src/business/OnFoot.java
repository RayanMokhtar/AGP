package business;

public class OnFoot extends Transport {
    public OnFoot() {
        super(5.0, 0.0); // 5 km/h, gratuit
    }
    @Override
    public String getTransportType() {
        return "ON_FOOT";
    }
}