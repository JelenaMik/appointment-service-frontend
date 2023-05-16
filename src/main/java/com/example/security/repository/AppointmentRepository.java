package com.example.security.repository;


import com.example.security.model.AppointmentDetailDto;
import com.example.security.model.AppointmentDto;
import com.example.security.model.AppointmentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AppointmentRepository {

    private static final String token= "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyNUBtYWlsLmNvbSIsImlhdCI6MTY4NDE1Mzg4MSwiZXhwIjoxNjg0Mjk3ODgxfQ.YeCybJzp0feaA97MI811uy6BKCQjt9L6HNL7JmYku2I";

    HttpEntity<Object> entity =  new HttpEntity<>(authenticationHeader(this.token));

    public HttpHeaders authenticationHeader(String token){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer "+ token);
        return headers;
    }
    private static final String APPOINTMENT_SERVICE_BASE_URL= "http://localhost:8103/api/v1/appointments/";
    private static final String APPOINTMENT_DETAIL_SERVICE_BASE_URL= "http://localhost:8103/api/v1/appointment-details/";

    @Autowired
    private RestTemplate restTemplate;


    public List<AppointmentDto> getUsersWeekAppointment(Integer week, String role, Long userId){
        return restTemplate.exchange(APPOINTMENT_SERVICE_BASE_URL +"week-appointments/"+week+"?role="+role+"&userId="+userId, HttpMethod.GET, this.entity, List.class).getBody();
    }

    public Optional<AppointmentDto> getAppointmentById(Long id){
        return Optional.ofNullable(restTemplate.exchange(APPOINTMENT_SERVICE_BASE_URL+id, HttpMethod.GET, this.entity, AppointmentDto .class).getBody());
    }

    public Optional<AppointmentDetailDto> getAppointmentDetailByAppId(Long id){
        return Optional.ofNullable(restTemplate.exchange(APPOINTMENT_DETAIL_SERVICE_BASE_URL+id, HttpMethod.GET, this.entity, AppointmentDetailDto .class).getBody());
    }

    public Optional<AppointmentDto> createAppointment(AppointmentRequest appointmentRequest){
        HttpEntity<Object> entity1 =  new HttpEntity<>(appointmentRequest, authenticationHeader(this.token));
        return Optional.ofNullable(restTemplate.exchange(APPOINTMENT_SERVICE_BASE_URL+"create-appointment", HttpMethod.POST, entity1, AppointmentDto.class).getBody());
    }

    public void deleteAppointment(Long id){
        restTemplate.exchange(APPOINTMENT_SERVICE_BASE_URL+"delete?appointmentId="+id, HttpMethod.GET, this.entity, ResponseEntity.class);
    }

    public void cancelAppointment(Long id){
        restTemplate.exchange(APPOINTMENT_SERVICE_BASE_URL+"cancel?appointmentId="+id, HttpMethod.PUT, this.entity, AppointmentDto.class);
    }

    public void changeAppointmentStatus(Long id){
        restTemplate.exchange(APPOINTMENT_DETAIL_SERVICE_BASE_URL+"change-status/"+id, HttpMethod.PUT, this.entity, HttpStatus.class);
    }

    public void changeAppointmentType(String appointmentType, Long id){
        restTemplate.exchange(APPOINTMENT_SERVICE_BASE_URL+"/change-type?type="+appointmentType+"&appointmentId="+id, HttpMethod.PUT, this.entity, AppointmentDto.class);
    }

    public void bookAppointment(Long clientId, Long appointmentId, String details){
        restTemplate.exchange(APPOINTMENT_SERVICE_BASE_URL+"book-appointment?clientId="+ clientId+ "&appointmentId=" + appointmentId + "&details="+ details, HttpMethod.PUT, this.entity, AppointmentDto.class);
    }
}
