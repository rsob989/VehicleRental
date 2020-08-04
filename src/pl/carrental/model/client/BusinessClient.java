package pl.carrental.model.client;

import java.util.Objects;

public class BusinessClient extends Client {
    public static final String TYPE = "Business customer";

    private String nip;

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public BusinessClient(String firstName, String lastName, String nip) {
        super(firstName, lastName);
        this.nip = nip;
    }

    @Override
    public String toString() {
        return super.toString() + " " + nip;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BusinessClient that = (BusinessClient) o;
        return Objects.equals(nip, that.nip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), nip);
    }

    @Override
    public String toCsv() {
        return (TYPE + ";") + getFirstName() + ";" + getLastName() + ";" + getNip();
    }
}
