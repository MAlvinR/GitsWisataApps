package id.co.horveno.testgits.view;

import java.util.List;

import id.co.horveno.testgits.model.Register;

public interface RegisterView {
    void onSuccessResponse(String message);
    void onErrorResponse(String message);
}
