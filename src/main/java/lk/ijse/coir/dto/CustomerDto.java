package lk.ijse.coir.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerDto implements Serializable {
    private String customerId;
    private String customerName;
    private String address;
    private String tel;

    @Override
    public String toString() {
        return "CustomerDto{" +
                "id='" + customerId + '\'' +
                ", name='" + customerName + '\'' +
                ", address='" + address + '\'' +
                ", tel='" + tel + '\'' +
                '}';
    }

}
