package ir.corestockexchanging.model.domain;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;

/**
 * Created by Ali on 03/27/2016.
 */
public class DepositRequest extends Entity {



    @NotEmpty(message = "����� ? ����� ��? ����� ���? ����.")
    private long userId;

    @Min(value = 0, message = "���� ��? ����� �� ��� ���� ����.")
    private long amount;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
}
