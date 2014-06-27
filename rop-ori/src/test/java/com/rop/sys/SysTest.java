package com.rop.sys;

import com.rop.client.CompositeResponse;
import com.rop.client.DefaultRopClient;
import com.rop.params.request.LogonRequest;
import com.rop.params.response.LogonResponse;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-06-27 16:36
 * To change this template use File | Settings | File Templates.
 */
public class SysTest {
    public static final String SERVER_URL = "http://localhost:8080/router";
    public static final String APP_KEY = "00001";
    public static final String APP_SECRET = "123";
    private DefaultRopClient ropClient = new DefaultRopClient(SERVER_URL, APP_KEY, APP_SECRET);

    @Test
    public void createSession() {
        LogonRequest ropRequest = new LogonRequest();
        ropRequest.setUserName("tomson");
        ropRequest.setPassword("123");
        CompositeResponse response = ropClient.buildClientRequest()
                .get(ropRequest, LogonResponse.class, "user.getSession", "1.0");
        assertNotNull(response);
        assertTrue(response.isSuccessful());
        assertNotNull(response.getSuccessResponse());
        assertTrue(response.getSuccessResponse() instanceof LogonResponse);
        assertEquals(((LogonResponse) response.getSuccessResponse()).getSessionId(), "mockSessionId1");
        ropClient.setSessionId(((LogonResponse) response.getSuccessResponse()).getSessionId());
    }
}
