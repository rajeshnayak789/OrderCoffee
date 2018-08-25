package java.com.vawsum.activities.credentials;

public class CredentialInformation
{
    String testType;
    String userName;
    String password;

    public CredentialInformation (String testType, String userName, String password)
    {
        this.testType = testType;
        this.userName = userName;
        this.password = password;
    }

    public String getTestType()
    {
        return testType;
    }

    public String getUserName()
    {
        return userName;
    }

    public String getPassword()
    {
        return password;
    }
}
