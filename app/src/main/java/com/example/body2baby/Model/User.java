package com.example.body2baby.Model;

public class User {
    String name, trimester, id, email, idnumber, phonenumber,profilepictureurl,search,type,workout,workout1,workout2,workout3,description,workoutpictureurl;

    public User() {
    }


    public User(String name, String trimester, String id, String email, String idnumber, String phonenumber, String profilepictureurl, String search, String type, String workout, String workout1, String workout2, String workout3, String description, String workoutpictureurl) {
        this.name = name;
        this.trimester = trimester;
        this.id = id;
        this.email = email;
        this.idnumber = idnumber;
        this.phonenumber = phonenumber;
        this.profilepictureurl = profilepictureurl;
        this.search = search;
        this.type = type;
        this.workout = workout;
        this.workout1 = workout1;
        this.workout2 = workout2;
        this.workout3 = workout3;
        this.description = description;
        this.workoutpictureurl = workoutpictureurl;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTrimester() {
        return trimester;
    }

    public void setTrimester(String trimester) {
        this.trimester = trimester;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdnumber() {
        return idnumber;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getProfilepictureurl() {
        return profilepictureurl;
    }

    public void setProfilepictureurl(String profilepictureurl) {
        this.profilepictureurl = profilepictureurl;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWorkout() {
        return workout;
    }

    public void setWorkout(String workout) {
        this.workout = workout;
    }

    public String getWorkout1() {
        return workout1;
    }

    public void setWorkout1(String workout1) {
        this.workout1 = workout1;
    }

    public String getWorkout2() {
        return workout2;
    }

    public void setWorkout2(String workout2) {
        this.workout2 = workout2;
    }

    public String getWorkout3() {
        return workout3;
    }

    public void setWorkout3(String workout3) {
        this.workout3 = workout3;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWorkoutpictureurl() {
        return workoutpictureurl;
    }

    public void setWorkoutpictureurl(String workoutpictureurl) {
        this.workoutpictureurl = workoutpictureurl;
    }
}
