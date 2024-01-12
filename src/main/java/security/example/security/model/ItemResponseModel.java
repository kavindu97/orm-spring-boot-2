package security.example.security.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ItemResponseModel {
    @JsonProperty("item_id")
    private Long itemId;
    @JsonProperty("item_name")
    private String itemName;

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
