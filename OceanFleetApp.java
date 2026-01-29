import java.util.ArrayList;
import java.util.List;

class Vessel {
    private String vesselId;
    private String vesselName;
    private double averageSpeed;
    private String vesselType;

    public Vessel() {
    }

    public Vessel(String vesselId, String vesselName, double averageSpeed, String vesselType) {
        this.vesselId = vesselId;
        this.vesselName = vesselName;
        this.averageSpeed = averageSpeed;

        this.vesselType = vesselType;

    }

    public String getVesselId() {
        return vesselId;
    }

    public void setVesselId(String vesselId) {
        this.vesselId = vesselId;
    }

    public String getVesselName() {
        return vesselName;

    }

    public void setVesselName(String vesselName) {
        this.vesselName = vesselName;

    }

    public double getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(double averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public String getVesselType() {
        return vesselType;
    }

    public void setVesselType(String vesselType) {
        this.vesselType = vesselType;
    }
}

class VesselUtil {
    private List<Vessel> vesselList = new ArrayList<>();

    public List<Vessel> getVesselList() {
        return vesselList;
    }

    public void setVesselList(List<Vessel> vesselList) {
        this.vesselList = vesselList;
    }

    public void addVesselPerformance(Vessel vessel) {
        vesselList.add(vessel);
    }

    public Vessel getVesselById(String vesselId) {
        for (Vessel v : vesselList) {
            if (v.getVesselId().equals(vesselId)) {
                return v;
            }
        }
        return null;
    }

    public List<Vessel> getHighPerformanceVessels() {
        List<Vessel> result = new ArrayList<>();
        double maxSpeed = vesselList.get(0).getAverageSpeed();

        for (Vessel v : vesselList) {
            if (v.getAverageSpeed() > maxSpeed) {
                maxSpeed = v.getAverageSpeed();
            }
        }

        for (Vessel v : vesselList) {
            if (v.getAverageSpeed() == maxSpeed) {
                result.add(v);
            }
        }

        return result;
    }
}

public class OceanFleetApp {
    public static void main(String[] args) {

        VesselUtil util = new VesselUtil();

        Vessel v1 = new Vessel("V001", "Sea King", 25.5, "Cargo");
        Vessel v2 = new Vessel("V002", "Ocean Star", 18.0, "Tanker");
        Vessel v3 = new Vessel("V003", "Wave Rider", 22.3, "Cruise");

        util.addVesselPerformance(v1);
        util.addVesselPerformance(v2);
        util.addVesselPerformance(v3);

        String searchId = "V001";
        Vessel found = util.getVesselById(searchId);

        if (found != null) {
            System.out.println(found.getVesselId() + " | " +
                    found.getVesselName() + " | " +
                    found.getVesselType() + " | " +
                    found.getAverageSpeed() + " knots");

        }

        System.out.println("High performance vessels are");
        List<Vessel> highList = util.getHighPerformanceVessels();

        for (Vessel v : highList) {
            System.out.println(v.getVesselId() + " | " +
                    v.getVesselName() + " | " +
                    v.getVesselType() + " | " +
                    v.getAverageSpeed() + " knots");
        }
    }
}