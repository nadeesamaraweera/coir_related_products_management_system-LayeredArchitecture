package lk.ijse.coir.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
/*@Getter
@Setter
@ToString*/
public class CustomerTm {
    private String customerId;
    private String customerName;
    private String address;
    private String tel;

    @Override
    public String toString() {
        return "CustomerTm{" +
                "id='" + customerId + '\'' +
                ", name='" + customerName + '\'' +
                ", address='" + address + '\'' +
                ", tel='" + tel + '\'' +
                '}';
    }
}
