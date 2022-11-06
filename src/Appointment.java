import java.util.ArrayList;

public class Appointment {
    private Seller seller;
    private ArrayList<String> buyerRequests;
    private Buyer confirmedBuyer;
    private String time;

    public Appointment(Seller seller, ArrayList<String> buyerRequests, Buyer confirmedBuyers) {
        this.seller = seller;
        this.buyerRequests = buyerRequests;
        this.confirmedBuyer = confirmedBuyers
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public ArrayList<String> getBuyerRequests() {
        return buyerRequests;
    }

    public void setBuyerRequests(ArrayList<String> buyerRequests) {
        this.buyerRequests = buyerRequests;
    }

    public Buyer getConfirmedBuyer() {
        return confirmedBuyer;
    }

    public void setConfirmedBuyer(Buyer confirmedBuyer) {
        this.confirmedBuyer = confirmedBuyer;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
