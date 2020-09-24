package com.hints.serverthree.service;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

public interface UserService {
    public OutputStream queryUser(String id, HttpServletResponse response)throws Exception;
}
