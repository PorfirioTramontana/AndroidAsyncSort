package mimecast.com.recruitment.model;

/**
 * Created by Michael Aubert on 27/02/2015.
 */

public class EmailHeaderModel {

    private String iSender;
    private String iSubject;
    private long iReceivedTime;

    public EmailHeaderModel(String aSender, String aSubject, long aReceivedTime) {
        iSender = aSender;
        iSubject = aSubject;
        iReceivedTime = aReceivedTime;
    }

    public String getSender() {
        return iSender;
    }

    public String getSubject() {
        return iSubject;
    }

    public long getReceivedTime() {
        return iReceivedTime;
    }

}
