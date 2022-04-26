package hr.datastock.security;

public interface PasswordEncryptionService {
    String createMD5(String password);
}
