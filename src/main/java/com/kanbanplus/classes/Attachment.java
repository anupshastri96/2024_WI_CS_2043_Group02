public class Attachment {
    private String attachmentId;
    private String fileName;
    private String filePath;

    public Attachment(String attachmentId, String fileName, String filePath) {
        this.attachmentId = attachmentId;
        this.fileName = fileName;
        this.filePath = filePath;
    }

    public String getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(String attachmentId) {
        this.attachmentId = attachmentId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}