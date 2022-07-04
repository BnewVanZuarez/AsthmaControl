package xyz.ibnuraffi.asthmacontrol.utils;

public class SpinnerModel {
    private String id;
    private String nama;

    public SpinnerModel(String id, String nama){
        this.id = id;
        this.nama = nama;
    }

    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    @Override
    public String toString() {
        return nama;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof SpinnerModel){
            SpinnerModel c = (SpinnerModel) obj;
            if(c.getId().equals(id) && c.getNama().equals(nama)) return true;
        }

        return false;
    }
}
