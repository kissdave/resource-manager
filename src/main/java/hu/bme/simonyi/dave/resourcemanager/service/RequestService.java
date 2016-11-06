package hu.bme.simonyi.dave.resourcemanager.service;

import hu.bme.simonyi.dave.resourcemanager.model.Request;
import hu.bme.simonyi.dave.resourcemanager.model.RequestStatus;
import hu.bme.simonyi.dave.resourcemanager.model.Resource;
import hu.bme.simonyi.dave.resourcemanager.model.User;
import hu.bme.simonyi.dave.resourcemanager.repository.RequestRepository;
import hu.bme.simonyi.dave.resourcemanager.repository.RequestStatusRepository;
import hu.bme.simonyi.dave.resourcemanager.repository.ResourceRepository;
import hu.bme.simonyi.dave.resourcemanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static hu.bme.simonyi.dave.resourcemanager.utils.Utils.getCurrentTimeInString;

/**
 * Created by dkiss on 2016. 10. 29..
 */
@Service
public class RequestService {

    @PersistenceContext
    EntityManager em;

    @Autowired
    ResourceRepository resourceRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RequestRepository requestRepository;

    @Autowired
    RequestStatusRepository requestStatusRepository;

    @Transactional
    public void createRequest(Request request, Integer resourceID, Integer userID) {
        Resource resource = resourceRepository.findOne(resourceID);
        User user = userRepository.findOne(userID);
        RequestStatus requestStatus = requestStatusRepository.findAll().stream().filter(x -> x.getName().equals(RequestStatus.NEW)).findFirst().get();
        resource.addRequest(request);
        user.addRequest(request);
        requestStatus.addRequest(request);
        em.persist(request);
    }

    @Transactional
    public void answerRequest(Integer requestId, String comment, boolean isApproved) {
        Request request = requestRepository.findOne(requestId);
        request.setComment(request.getComment() + "\n" + getCurrentTimeInString() + ": " + comment);
        request.getRequestStatus().removeRequest(request);
        String nextStatus;
        if(isApproved) {
            nextStatus = RequestStatus.APPROVED;
        } else {
            nextStatus = RequestStatus.DECLINED;
        }

        RequestStatus requestStatus = requestStatusRepository.findAll().stream().filter(x -> x.getName().equals(nextStatus)).findFirst().get();
        requestStatus.addRequest(request);
    }

    @Transactional
    public void updateRequest(Request request, Integer resourceID, Integer userID) {
        Request oldRequest = requestRepository.findOne(request.getRequestID());
        oldRequest.updateFields(request);

        Resource resource = resourceRepository.findOne(resourceID);
        oldRequest.getResource().removeRequest(oldRequest);
        resource.addRequest(oldRequest);

        oldRequest.getRequestStatus().removeRequest(oldRequest);
        RequestStatus requestStatus = requestStatusRepository.findAll().stream().filter(x -> x.getName().equals(RequestStatus.UPDATED)).findFirst().get();
        requestStatus.addRequest(oldRequest);

        em.merge(oldRequest);
    }

    @Transactional
    public void deleteRequest() {
        // TODO Changes the status of the request to deleted.
    }




}
