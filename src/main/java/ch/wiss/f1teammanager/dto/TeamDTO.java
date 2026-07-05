package ch.wiss.f1teammanager.dto;

public class TeamDTO {
    private Long id;
    private String name;
    private String base;
    private List<DriverDTO> drivers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }
}
