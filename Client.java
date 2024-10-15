class Client {
    private int id;
    private String name;
    private String surname;
    private String patronymic;
    private int total_survices;
    private String phone;
    private String email;
    private String gender;

    public Client(int id, String name, String surname, String patronymic, int total_survices, String phone, String email, String gender) {
        this.setId(id);
        this.setName(name);
        this.setSurname(surname);
        this.setPatronymic(patronymic);
        this.setSurvices(total_survices);
        this.setPhone(phone);
        this.setEmail(email);
        this.setGender(gender);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public int getSurvices() {
        return total_survices;
    }

    public void setSurvices(int total_survices) {
        this.total_survices = total_survices;
    }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", total_services='" + total_survices + '\'' +
                '}';
    }
}
