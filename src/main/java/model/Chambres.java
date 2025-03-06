package model;

public class Chambres {
    private int id;
    private String num;
    private String type;
    private int capacite;
    private String status;
    private int litOccupe;

    public Chambres(int id, String num,String type, int capacite, String status, int litOccupe) {
        this.id = id;
        this.num = num;
        this.type = type;
        this.capacite = capacite;
        this.status = status;
        this.litOccupe = litOccupe;
    }

    public Chambres(String num, String type, int capacite, String status, int litOccupe) {
        this.num = num;
        this.type = type;
        this.capacite = capacite;
        this.status = status;
        this.litOccupe = litOccupe;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCapacite() {
        return capacite;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getLitOccupe() {
        return litOccupe;
    }

    public void setLitOccupe(int litOccupe) {
        this.litOccupe = litOccupe;
    }

    @Override
    public String toString() {
        return "Chambres{" +
                "id=" + id +
                ", num='" + num + '\'' +
                ", type='" + type + '\'' +
                ", capacite=" + capacite +
                ", status='" + status + '\'' +
                ", litOccupe=" + litOccupe +
                '}';
    }
}