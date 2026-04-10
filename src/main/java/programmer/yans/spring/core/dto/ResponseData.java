package programmer.yans.spring.core.dto;

import java.util.ArrayList;
import java.util.List;

public class ResponseData<T> {
    private Boolean status;
    private List<String> messages = new ArrayList<>();
    private T payload;

    

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }
}
