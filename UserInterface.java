import java.util.Scanner;

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
        float typeRate = timberType.equalsIgnoreCase("Premium") ? 0.25f : 0.15f;

        double volume = 3.147 * timberRadius * timberRadius * timberLength;
        float price = (float)(volume * timberPrice * typeRate);
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
        String[] arr = input.split(":");

        String id = arr[0];
        String date = arr[1];
        int rating = Integer.parseInt(arr[2]);
        String type = arr[3];

        if (type.equalsIgnoreCase("BrickTransport")) {
            float brickSize = Float.parseFloat(arr[4]);
            int brickQty = Integer.parseInt(arr[5]);
            float brickPrice = Float.parseFloat(arr[6]);
            return new BrickTransport(id, date, rating, brickSize, brickQty, brickPrice);
        } else {
            float length = Float.parseFloat(arr[4]);
            float radius = Float.parseFloat(arr[5]);
            String timberType = arr[6];
            float timberPrice = Float.parseFloat(arr[7]);
            return new TimberTransport(id, date, rating, length, radius, timberType, timberPrice);
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
        String[] data = input.split(":");
        String transportId = data[0];

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