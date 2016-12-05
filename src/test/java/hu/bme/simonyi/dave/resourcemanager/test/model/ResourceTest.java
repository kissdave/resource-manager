package hu.bme.simonyi.dave.resourcemanager.test.model;

import hu.bme.simonyi.dave.resourcemanager.model.Request;
import hu.bme.simonyi.dave.resourcemanager.model.Resource;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

/**
 * Created by dkiss on 2016. 11. 20..
 */
public class ResourceTest {
    public ResourceTest() {
    }

    @Test
    public void testAddRequest() {
        Request request = new Request("TestEvent", "testEvent", null, null, null, null, "some comment", null, null, null);
        Resource resource = new Resource();
        resource.setResourceName("Resource1");
        resource.setRequests(null);

        resource.addRequest(request);

        Assert.assertEquals(resource.getResourceName(), request.getResource().getResourceName());
        Boolean requestIsInList = false;
        for(Request req : resource.getRequests()) {
            if(req.getEventName().equals(request.getEventName())) {
                requestIsInList = true;
            }
        }
        Assert.assertTrue(requestIsInList);
    }

    @Test
    public void testRemoveRequest() {
        Request request = new Request("TestEvent", "testEvent", null, null, null, null, "some comment", null, null, null);
        Resource resource = new Resource();
        resource.setResourceName("Resource1");
        resource.setRequests(null);

        resource.addRequest(request);
        Assert.assertEquals(resource.getResourceName(), request.getResource().getResourceName());

        resource.removeRequest(request);
        Assert.assertNull(request.getResource());

        Boolean requestIsInList = false;
        for(Request req : resource.getRequests()) {
            if(req.getEventName().equals(request.getEventName())) {
                requestIsInList = true;
            }
        }
        Assert.assertFalse(requestIsInList);
    }

}
