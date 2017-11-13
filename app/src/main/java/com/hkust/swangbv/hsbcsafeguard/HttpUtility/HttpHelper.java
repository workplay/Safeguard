package com.hkust.swangbv.hsbcsafeguard.HttpUtility;



import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by admin on 6/6/2017.
 */
public class HttpHelper {

    private static String domain = "10.0.2.2:10007";


    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void example() throws IOException {


        //1. Personal Profile Management API
        createAccount("A123456(7)", "Chris", "34062801", "chris@astri.org", "Hong Kong", "Hong Kong", "Engineer");
        getAccount("A123456(7)");
        updateAccount("A123456(7)", "Chris Wong", "34062802", "", "Hong Kong", "Hong Kong", "");
        getAccounts();

        getAttachment("A123456(7)");

        removeAccount("A123456(7)");


        //2. Company Profile Management API
        createCompanyContact("ASTRI", "Hong Kong", "corporate@astri.org", "34062801", "34062802");
        updateCompanyContact("ASTRI", "", "corporate@astri.org", "34062801", "34062803");

        addContactPerson("ASTRI", "Chris", "chris@astri.org", "34062801", "34062802");
        addContactPerson("ASTRI", "Mary", "mary@astri.org", "34062801", "34062802");
        updateContactPerson("ASTRI", "Chris Wong", "chris@astri.org", "34062801", "34062802");
        removeContactPerson("ASTRI","chris@astri.org");

        getCompanies();
        getCompany("ASTRI");

        LocalDateTime approachTime = LocalDateTime.of(2017, 7, 2, 9, 30, 2);
        LocalDateTime replyTime = LocalDateTime.of(2017, 7, 2, 10, 30, 5);
        addApproachInfo("ASTRI", "chris@astri.org", "phone", approachTime, replyTime, false);
        getApproachInfo("ASTRI", "chris@astri.org");

        removeCompanyContact("ASTRI");
    }

    private static boolean equal(byte[] data1, byte[] data2) {
        if (data1.length != data2.length) {
            return false;
        }

        for (int i = 0; i < data1.length; i++) {
            if (data1[i] != data2[i]) {
                return false;
            }
        }

        return true;
    }

    private static void me() throws IOException {
        GetUtility get = new GetUtility("http://" + domain + "/api/account/me");
        System.out.println(get.getResponse());
    }

    private static void peers() throws IOException {
        GetUtility get = new GetUtility("http://" + domain + "/api/account/peers");
        System.out.println(get.getResponse());
    }

    private static void createAccount(String id, String name, String phoneNumber, String email,
                                      String residentialAddress, String correspondenceAddress, String job)
            throws IOException {
        PutUtility put = new PutUtility("http://" + domain + "/api/account/createAccount");
        String requestBody = "{"
                + "\"id\" : \"" + id + "\", "
                + "\"name\" : \"" + name + "\", "
                + "\"phoneNumber\" : \"" + phoneNumber + "\", "
                + "\"email\" : \"" + email + "\", "
                + "\"residentialAddress\" : \"" + residentialAddress + "\", "
                + "\"correspondenceAddress\" : \"" + correspondenceAddress + "\", "
                + "\"job\" : \"" + job + "\""
                + "}";

        System.out.println(requestBody);
        put.setContentType("application/json;charset=UTF-8");
        put.setRequestBody(requestBody);
        System.out.println(put.getResponse());
    }

    public static String updateAccount(String id, String name, String phoneNumber, String email,
                                      String residentialAddress, String correspondenceAddress, String job)
            throws IOException {
        PutUtility put = new PutUtility("http://" + domain + "/api/account/updateAccount");
        String requestBody = "{"
                + "\"id\" : \"" + id + "\"";
        if (!nullOrEmpty(name)) {
            requestBody += ", \"name\" : \"" + name + "\"";
        }

        if (!nullOrEmpty(phoneNumber)) {
            requestBody += ", \"phoneNumber\" : \"" + phoneNumber + "\"";
        }

        if (!nullOrEmpty(email)) {
            requestBody += ", \"email\" : \"" + email + "\"";
        }

        if (!nullOrEmpty(residentialAddress)) {
            requestBody += ", \"residentialAddress\" : \"" + residentialAddress + "\"";
        }

        if (!nullOrEmpty(correspondenceAddress)) {
            requestBody += ", \"correspondenceAddress\" : \"" + correspondenceAddress + "\"";
        }

        if (!nullOrEmpty(job)) {
            requestBody += ", \"job\" : \"" + job + "\"";
        }

        requestBody += "}";

        System.out.println(requestBody);
        put.setContentType("application/json;charset=UTF-8");
        put.setRequestBody(requestBody);
        return put.getResponse();
    }

    private static void removeAccount(String id) throws IOException {
        PutUtility put = new PutUtility("http://" + domain + "/api/account/removeAccount");
        String requestBody = "{\"id\" : \"" + id + "\"}";

        put.setContentType("application/json;charset=UTF-8");
        put.setRequestBody(requestBody);
        System.out.println(put.getResponse());
    }

    public static String getAccounts() throws IOException {
        GetUtility get = new GetUtility("http://" + domain + "/api/account/getAccounts");
        return get.getResponse();
    }

    public static String getAccount(String id) throws IOException {
        PutUtility put = new PutUtility("http://" + domain + "/api/account/getAccount");

        String requestBody = "{\"id\" : \"" + id + "\"}";
        ;
        put.setContentType("application/json;charset=UTF-8");
        put.setRequestBody(requestBody);
        return put.getResponse();
    }

    private static void uploadAttachment(String id, String filePath) throws IOException {
        PostMultipartUtility utility = new PostMultipartUtility("http://" + domain + "/api/account/uploadAttachment");
        utility.addFormField("id", id);
        utility.addFile("file", new File(filePath));

        System.out.println(utility.getResponse());
    }

    private static void getAttachment(String id) throws IOException {
        PutUtility put = new PutUtility("http://" + domain + "/api/account/getAttachment");

        String requestBody = "{\"id\" : \"" + id + "\"}";
        ;
        put.setContentType("application/json;charset=UTF-8");
        put.setRequestBody(requestBody);
        put.setAccept("image/webp");

        //System.out.println(put.getResponse());
        byte[] data = put.getBinaryResponse();
        FileOutputStream fos = new FileOutputStream("./attachment.jpg");
        fos.write(data);
        fos.close();
    }

    private static void createCompanyContact(String companyName, String address, String email,
                                             String phoneNumber, String faxNumber) throws IOException {
        PutUtility put = new PutUtility("http://" + domain + "/api/company/createCompanyContact");


        String requestBody = "{"
                + "\"companyName\" : \"" + companyName + "\", "
                + "\"address\" : \"" + address + "\", "
                + "\"email\" : \"" + email + "\","
                + "\"phoneNumber\" : \"" + phoneNumber + "\"";


        if (!nullOrEmpty(faxNumber)) {
            requestBody += ", \"faxNumber\" : \"" + faxNumber + "\"";
        }

        requestBody += "}";

        System.out.println(requestBody);

        put.setContentType("application/json;charset=UTF-8");
        put.setRequestBody(requestBody);

        System.out.println(put.getResponse());
    }

    private static void updateCompanyContact(String companyName, String address, String email,
                                             String phoneNumber, String faxNumber) throws IOException {
        PutUtility put = new PutUtility("http://" + domain + "/api/company/updateCompanyContact");

        String requestBody = "{"
                + "\"companyName\" : \"" + companyName + "\"";

        if (!nullOrEmpty(address)) {
            requestBody += ", \"address\" : \"" + address + "\"";
        }

        if (!nullOrEmpty(email)) {
            requestBody += ", \"email\" : \"" + email + "\"";
        }

        if (!nullOrEmpty(phoneNumber)) {
            requestBody += ", \"phoneNumber\" : \"" + phoneNumber + "\"";
        }

        if (!nullOrEmpty(faxNumber)) {
            requestBody += ", \"faxNumber\" : \"" + faxNumber + "\"";
        }

        requestBody += "}";

        System.out.println(requestBody);

        put.setContentType("application/json;charset=UTF-8");
        put.setRequestBody(requestBody);

        System.out.println(put.getResponse());
    }

    private static void removeCompanyContact(String companyName) throws IOException {
        PutUtility put = new PutUtility("http://" + domain + "/api/company/removeCompanyContact");

        String requestBody = "{"
                + "\"companyName\" : \"" + companyName + "\""
                + "}";

        System.out.println(requestBody);

        put.setContentType("application/json;charset=UTF-8");
        put.setRequestBody(requestBody);

        System.out.println(put.getResponse());
    }

    private static void addContactPerson(String companyName, String contactPersonName, String email, String
            phoneNumber, String faxNumber) throws IOException {
        PutUtility put = new PutUtility("http://" + domain + "/api/company/addContactPerson");
        String requestBody = "{"
                + "\"companyName\" : \"" + companyName + "\", "
                + "\"contactPersonName\" : \"" + contactPersonName + "\", "
                + "\"email\" : \"" + email + "\", "
                + "\"phoneNumber\" : \"" + phoneNumber + "\"";

        if (faxNumber != null && !faxNumber.isEmpty()) {
            requestBody += ", \"faxNumber\" : \"" + faxNumber + "\"";
        }
        requestBody += "}";

        System.out.println(requestBody);

        put.setContentType("application/json;charset=UTF-8");
        put.setRequestBody(requestBody);

        System.out.println(put.getResponse());
    }

    private static void updateContactPerson(String companyName, String contactPersonName, String email, String
            phoneNumber, String faxNumber) throws IOException {
        PutUtility put = new PutUtility("http://" + domain + "/api/company/updateContactPerson");
        String requestBody = "{"
                + "\"companyName\" : \"" + companyName + "\",";

        if (contactPersonName != null && !contactPersonName.isEmpty()) {
            requestBody += "\"contactPersonName\" : \"" + contactPersonName + "\",";
        }

        requestBody += "\"email\" : \"" + email + "\"";
        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            requestBody += ",\"phoneNumber\" : \"" + phoneNumber + "\"";
        }

        if (faxNumber != null && !faxNumber.isEmpty()) {
            requestBody += ", \"faxNumber\" : \"" + faxNumber + "\"";
        }
        requestBody += "}";

        System.out.println(requestBody);

        put.setContentType("application/json;charset=UTF-8");
        put.setRequestBody(requestBody);

        System.out.println(put.getResponse());
    }

    private static void removeContactPerson(String companyName, String email) throws IOException {
        PutUtility put = new PutUtility("http://" + domain + "/api/company/removeContactPerson");

        String requestBody = "{"
                + "\"companyName\" : \"" + companyName + "\","
                + "\"email\" : \"" + email + "\""
                + "}";

        System.out.println(requestBody);

        put.setContentType("application/json;charset=UTF-8");
        put.setRequestBody(requestBody);

        System.out.println(put.getResponse());
    }

    private static void getCompanies() throws IOException {
        GetUtility get = new GetUtility("http://" + domain + "/api/company/getCompanies");
        System.out.println(get.getResponse());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static void addApproachInfo(String companyName, String contactPersonEmail,
                                        String contactMethod, LocalDateTime approachTime,
                                        LocalDateTime replyTime, boolean isSuccessful) throws IOException {
        PutUtility put = new PutUtility("http://" + domain + "/api/company/addApproachInfo");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String requestBody = "{"
                + "\"companyName\" : \"" + companyName + "\", "
                + "\"contactPersonEmail\" : \"" + contactPersonEmail + "\", "
                + "\"contactMethod\" : \"" + contactMethod + "\", "
                + "\"approachTime\" : \"" + approachTime.format(formatter) + "\", ";

        if (replyTime != null) {
            requestBody += "\"replyTime\" : \"" + replyTime.format(formatter) + "\", ";
        }

        requestBody += "\"isSuccessful\" : \"" + isSuccessful + "\""
                + "}";

        System.out.println(requestBody);

        put.setContentType("application/json;charset=UTF-8");
        put.setRequestBody(requestBody);

        System.out.println(put.getResponse());
    }

    private static void getApproachInfo(String companyName) throws IOException {
        getApproachInfo(companyName, null);
    }

    private static void getApproachInfo(String companyName, String contactPersonEmail) throws IOException {
        PutUtility put = new PutUtility("http://" + domain + "/api/company/getApproachInfo");
        String requestBody;
        if (nullOrEmpty(contactPersonEmail)) {
            requestBody = "{\"companyName\" : \"" + companyName + "\"}";
        } else {
            requestBody = "{\"companyName\" : \"" + companyName + "\","
                    + " \"contactPersonEmail\" : \"" + contactPersonEmail + "\""
                    + "}";
        }

        put.setContentType("application/json;charset=UTF-8");
        put.setRequestBody(requestBody);

        System.out.println(put.getResponse());
    }

    private static void getCompany(String companyName) throws IOException {
        PutUtility put = new PutUtility("http://" + domain + "/api/company/getCompany");
        put.setContentType("application/json;charset=UTF-8");

        String requestBody = "{\"companyName\" : \"" + companyName + "\"}";
        put.setRequestBody(requestBody);
        System.out.println(put.getResponse());
    }

    private static void test() throws IOException {
        PutUtility put = new PutUtility("http://" + domain + "/api/company/test");
        put.setContentType("application/json;charset=UTF-8");
        put.setRequestBody("{\"company\":\"abc\"}");
        System.out.println(put.getResponse());
    }

    private static boolean nullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }
}
