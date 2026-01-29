import java.util.List;
import java.util.Scanner;

public class UserInterface {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        VesselUtil vesselUtil = new VesselUtil();

        // Read number of vessels
        int n = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        // Add vessel performance details
        for (int i = 0; i < n; i++) {
            String line = scanner.nextLine();
            String[] parts = line.split(":");
            String vesselId = parts[0];
            String vesselName = parts[1];
            double averageSpeed = Double.parseDouble(parts[2]);
            String vesselType = parts[3];

            Vessel vessel = new Vessel(vesselId, vesselName, averageSpeed, vesselType);
            vesselUtil.addVesselPerformance(vessel);
        }

        // Read vessel id to search
        String vesselIdToSearch = scanner.nextLine();

        // Retrieve vessel by id
        Vessel vessel = vesselUtil.getVesselById(vesselIdToSearch);
        if (vessel != null) {
            System.out.println(vessel.getVesselId() + " | " + vessel.getVesselName() + " | " +
                    vessel.getVesselType() + " | " + vessel.getAverageSpeed() + " knots");
        }

        // Retrieve high performance vessels
        System.out.println("High performance vessels are");
        List<Vessel> highPerformanceVessels = vesselUtil.getHighPerformanceVessels();
        for (Vessel v : highPerformanceVessels) {
            System.out.println(v.getVesselId() + " | " + v.getVesselName() + " | " +
                    v.getVesselType() + " | " + v.getAverageSpeed() + " knots");
        }

        scanner.close();
    }
}

// DO NOT EDIT BELOW THIS LINE - OLD CODE
abstract class GoodsTransport {
    protected String transportId;
    protected String transportDate;
    protected int transportRating;

    public GoodsTransport(String transportId, String transportDate, int transportRating) {
        this.transportId = transportId;
        this.transportDate = transportDate;
        this.transportRating = transportRating;

    }

    public String getTransportId() {
        return transportId;
    }

    public void setTransportId(String transportId) {
        this.transportId = transportId;
    }

    public String getTransportDate() {
        return transportDate;
    }

    public void setTransportDate(String transportDate) {
        this.transportDate = transportDate;
    }

    public int getTransportRating() {
        return transportRating;
    }

    public void setTransportRating(int transportRating) {
        this.transportRating = transportRating;
    }

    abstract public String vehicleSelection();

    abstract public float calculateTotalCharge();
}

class BrickTransport extends GoodsTransport {
    private float brickSize;
    private int brickQuantity;
    private float brickPrice;

    public BrickTransport(String transportId, String transportDate, int transportRating,
            float brickSize, int brickQuantity, float brickPrice) {
        super(transportId, transportDate, transportRating);
        this.brickSize = brickSize;
        this.brickQuantity = brickQuantity;
        this.brickPrice = brickPrice;
    }

    public String vehicleSelection() {
        if (brickQuantity < 300)
            return "Truck";
        else if (brickQuantity <= 500)
            return "Lorry";
        else
            return "MonsterLorry";
    }

    public float calculateTotalCharge() {
        float price = brickPrice * brickQuantity;
        float tax = price * 0.3f;
        float discount = 0;

        if (transportRating == 5)
            discount = price * 0.2f;
        else if (transportRating == 3 || transportRating == 4)
            discount = price * 0.1f;

        float vehiclePrice = 0;
        String vehicle = vehicleSelection();
        if (vehicle.equals("Truck"))
            vehiclePrice = 1000;
        else if (vehicle.equals("Lorry"))
            vehiclePrice = 1700;
        else
            vehiclePrice = 3000;

        return (price + vehiclePrice + tax) - discount;
    }

    public int getBrickQuantity() {
        return brickQuantity;
    }

    public float getBrickPrice() {
        return brickPrice;
    }
}

class TimberTransport extends GoodsTransport {
    private float timberLength;
    private float timberRadius;
    private String timberType;
    private float timberPrice;

    public TimberTransport(String transportId, String transportDate, int transportRating,
            float timberLength, float timberRadius,
            String timberType, float timberPrice) {
        super(transportId, transportDate, transportRating);
        this.timberLength = timberLength;
        this.timberRadius = timberRadius;
        this.timberType = timberType;
        this.timberPrice = timberPrice;
    }

    public String vehicleSelection() {
        double area = 2 * 3.147 * timberRadius * timberLength;
        if (area < 250)
            return "Truck";
        else if (area <= 400)
            return "Lorry";
        else
            return "MonsterLorry";
    }

    public float calculateTotalCharge() {
        double volume = 3.147 * timberRadius * timberRadius * timberLength;
        float rate = timberType.equalsIgnoreCase("Premium") ? 0.25f : 0.15f;
        float price = (float) (volume * timberPrice * rate);
        float tax = price * 0.3f;
        float discount = 0;

        if (transportRating == 5)
            discount = price * 0.2f;
        else if (transportRating == 3 || transportRating == 4)
            discount = price * 0.1f;

        float vehiclePrice = 0;
        String vehicle = vehicleSelection();
        if (vehicle.equals("Truck"))
            vehiclePrice = 1000;
        else if (vehicle.equals("Lorry"))
            vehiclePrice = 1700;
        else
            vehiclePrice = 3000;

        return (price + vehiclePrice + tax) - discount;
    }

    public String getTimberType() {
        return timberType;
    }

    public float getTimberPrice() {
        return timberPrice;
    }
}

class Utility {
    public GoodsTransport parseDetails(String input) {
        String[] data = input.split(":");
        String id = data[0];
        String date = data[1];
        int rating = Integer.parseInt(data[2]);
        String type = data[3];

        if (type.equalsIgnoreCase("BrickTransport")) {
            float brickSize = Float.parseFloat(data[4]);
            int brickQty = Integer.parseInt(data[5]);
            float brickPrice = Float.parseFloat(data[6]);
            return new BrickTransport(id, date, rating, brickSize, brickQty, brickPrice);
        } else {
            float timberLength = Float.parseFloat(data[4]);
            float timberRadius = Float.parseFloat(data[5]);
            String timberType = data[6];
            float timberPrice = Float.parseFloat(data[7]);
            return new TimberTransport(id, date, rating, timberLength, timberRadius, timberType, timberPrice);
        }
    }

    public boolean validateTransportId(String transportId) {
        return transportId.matches("RTS\\d{3}[A-Z]");
    }

    public String findObjectType(GoodsTransport goodsTransport) {
        if (goodsTransport instanceof BrickTransport)
            return "BrickTransport";
        else
            return "TimberTransport";
    }
}

public class UserInterface {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Utility util = new Utility();

        String input = sc.nextLine();
        String[] parts = input.split(":");
        String transportId = parts[0];

        if (!util.validateTransportId(transportId)) {
            System.out.println("Transport id " + transportId + " is invalid");
            System.out.println("Please provide a valid record");
            return;
        }

        GoodsTransport gt = util.parseDetails(input);

        String vehicle = gt.vehicleSelection();
        float totalCharge = gt.calculateTotalCharge();

        if (gt instanceof BrickTransport) {
            BrickTransport bt = (BrickTransport) gt;
            System.out.println("Transporter id : " + bt.getTransportId() +
                    " | Date of transport : " + bt.getTransportDate() +
                    " | Rating of the transport : " + bt.getTransportRating() +
                    " | Quantity of bricks : " + bt.getBrickQuantity() +
                    " | Brick price : " + bt.getBrickPrice() +
                    " | Vehicle for transport : " + vehicle +
                    " | Total charge : " + totalCharge);
        } else {
            TimberTransport tt = (TimberTransport) gt;
            System.out.println("Transporter id : " + tt.getTransportId() +
                    " | Date of transport : " + tt.getTransportDate() +
                    " | Rating of the transport : " + tt.getTransportRating() +
                    " | Type of the timber : " + tt.getTimberType() +
                    " | Timber price per kilo : " + tt.getTimberPrice() +
                    " | Vehicle for transport : " + vehicle +
                    " | Total charge : " + totalCharge);
        }
    }
}