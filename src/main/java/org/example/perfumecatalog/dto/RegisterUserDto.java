package org.example.perfumecatalog.dto;

import java.util.Objects;

public class RegisterUserDto {
    private String email;
    private String password;
    private String passwordConfirmation;

    public RegisterUserDto() {
    }

    public RegisterUserDto(String email, String password, String passwordConfirmation) {
        this.email = email;
        this.password = password;
        this.passwordConfirmation = passwordConfirmation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegisterUserDto that = (RegisterUserDto) o;
        return Objects.equals(email, that.email) && Objects.equals(password, that.password) && Objects.equals(passwordConfirmation, that.passwordConfirmation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password, passwordConfirmation);
    }
}
