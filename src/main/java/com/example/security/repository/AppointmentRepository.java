package com.example.security.repository;


import com.example.security.model.AppointmentDetailDto;
import com.example.security.model.AppointmentDto;
import com.example.security.model.AppointmentRequest;
import com.example.security.service.JwtService;
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

//    private static final String token= "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyNUBtYWlsLmNvbSIsImlhdCI6MTY4NDMyNTAyMiwiZXhwIjoxNjg0OTI1MDIyfQ.3xx9fpWIJ43dhQBT2ydADBER7d3vXf7931F4UUsnc5s";
//
//    HttpEntity<Object> entity =  new HttpEntity<>(authenticationHeader(this.token));
//
//    public HttpHeaders authenticationHeader(String token){
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "Bearer "+ token);
//        return headers;
//    }

    private final JwtService jwtService;
    private static final String APPOINTMENT_SERVICE_BASE_URL= "http://localhost:8103/api/v1/appointments/";
    private static final String APPOINTMENT_DETAIL_SERVICE_BASE_URL= "http://localhost:8103/api/v1/appointment-details/";

    @Autowired
    private RestTemplate restTemplate;


    public List<AppointmentDto> getUsersWeekAppointment(Integer week, String role, Long userId){
        return restTemplate.exchange(
                APPOINTMENT_SERVICE_BASE_URL +"week-appointments/"+week+"?role="+role+"&userId="+userId,
                HttpMethod.GET,
                jwtService.httpEntityWithAuthHeader(),
                List.class).getBody();
    }

    public Optional<AppointmentDto> getAppointmentById(Long id){
        return Optional.ofNullable(restTemplate.exchange(
                APPOINTMENT_SERVICE_BASE_URL+id,
                HttpMethod.GET,
                jwtService.httpEntityWithAuthHeader(),
                AppointmentDto .class).getBody());
    }

    public Optional<AppointmentDetailDto> getAppointmentDetailByAppId(Long id){
        return Optional.ofNullable(restTemplate.exchange(
                APPOINTMENT_DETAIL_SERVICE_BASE_URL+id,
                HttpMethod.GET,
                jwtService.httpEntityWithAuthHeader(),
                AppointmentDetailDto .class).getBody());
    }

    public Optional<AppointmentDto> createAppointment(AppointmentRequest appointmentRequest){
        return Optional.ofNullable(restTemplate.exchange(
                APPOINTMENT_SERVICE_BASE_URL+"create-appointment",
                HttpMethod.POST,
                jwtService.httpEntityWithAuthHeaderAndRequestBody(appointmentRequest),
                AppointmentDto.class).getBody());
    }

    public void deleteAppointment(Long id){
        restTemplate.exchange(
                APPOINTMENT_SERVICE_BASE_URL+"delete?appointmentId="+id,
                HttpMethod.GET,
                jwtService.httpEntityWithAuthHeader(),
                ResponseEntity.class);
    }

    public void cancelAppointment(Long id){
        restTemplate.exchange(
                APPOINTMENT_SERVICE_BASE_URL+"cancel?appointmentId="+id,
                HttpMethod.PUT,
                jwtService.httpEntityWithAuthHeader(),
                AppointmentDto.class);
    }

    public void changeAppointmentStatus(Long id){
        restTemplate.exchange(
                APPOINTMENT_DETAIL_SERVICE_BASE_URL+"change-status/"+id,
                HttpMethod.PUT,
                jwtService.httpEntityWithAuthHeader(),
                HttpStatus.class);
    }

    public void changeAppointmentType(String appointmentType, Long id){
        restTemplate.exchange(
                APPOINTMENT_SERVICE_BASE_URL+"/change-type?type="+appointmentType+"&appointmentId="+id,
                HttpMethod.PUT,
                jwtService.httpEntityWithAuthHeader(),
                AppointmentDto.class);
    }

    public void bookAppointment(Long clientId, Long appointmentId, String details){
        restTemplate.exchange(
                APPOINTMENT_SERVICE_BASE_URL+"book-appointment?clientId="+ clientId+ "&appointmentId=" + appointmentId + "&details="+ details,
                HttpMethod.PUT,
                jwtService.httpEntityWithAuthHeader(),
                AppointmentDto.class);
    }

    public void authenticate() {
        restTemplate.exchange(
                "http://localhost:8103/authenticate",
                HttpMethod.GET,
                jwtService.httpEntityWithAuthHeader(),
                HttpStatus.class
        ).getBody();
    }
}
