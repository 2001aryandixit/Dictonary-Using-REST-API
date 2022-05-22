package com.example.dictonaryapi;

import com.example.dictonaryapi.Models.APIResponse;

public interface OnFetchDataListener {
    void OnFetchData(APIResponse apiResponse,String message);
    void OnError(String message);
}
