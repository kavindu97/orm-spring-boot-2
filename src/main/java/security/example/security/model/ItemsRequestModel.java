package security.example.security.model;

import lombok.Getter;
import lombok.Setter;
import security.example.security.entity.CartEntity;

public class ItemsRequestModel {

    private String itemName;
    private String cartName;
    private  Integer empId;

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getCartName() {
        return cartName;
    }

    public void setCartName(String cartName) {
        this.cartName = cartName;
    }
}
