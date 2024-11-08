package hr.datastore.security;

public interface PasswordEncryptionService {
    String createMD5(String password);
}
