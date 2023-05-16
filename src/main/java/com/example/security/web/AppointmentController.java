package com.example.security.web;

import com.example.security.model.AppointmentDetailDto;
import com.example.security.model.AppointmentDto;
import com.example.security.model.AppointmentRequest;
import com.example.security.model.UserData;
import com.example.security.service.AppointmentService;
import com.example.security.service.UserDataService;
import com.example.security.service.DateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AppointmentController {
    private final DateService webService;
    private final AppointmentService appointmentService;
    public final UserDataService userDataService;

    @GetMapping("/my-appointments/{role}/{userId}/{week}")
    public String myAppointmentPage(@PathVariable Integer week,
                                    @PathVariable String role,
                                    @PathVariable Long userId,
                                    Model model){

        List<Integer> daysOfWeek = webService.getListOfDates(week);
        List<AppointmentDto> appointmentList = appointmentService.getUserWeekAppointments(week, role, userId);
        log.info("Appointment list {}", appointmentList);

        model.addAttribute("daysOfWeek", daysOfWeek);
        model.addAttribute("appointmentList", appointmentList);
        model.addAttribute("week", week);
        model.addAttribute("weekNr", webService.getMonthNumber(week));
        model.addAttribute("role", role);
        model.addAttribute("userId", userId);
        model.addAttribute("month", webService.getMonth(week));
        model.addAttribute("year", webService.getYear(week));
        return "my-appointments";
    }

    @GetMapping("/provider-appointments/{providerId}/{week}")
    public String seeAllProviderAppointments(@PathVariable Long providerId, @PathVariable Integer week, Model model){
        List<AppointmentDto> list = appointmentService.getUserWeekAppointments(week, "provider", providerId);
        model.addAttribute("daysOfWeek", webService.getListOfDates(week));
        model.addAttribute("appointmentList", list);
        model.addAttribute("week", week);
        model.addAttribute("providerId", providerId);
        model.addAttribute("month", webService.getMonth(week));
        model.addAttribute("year", webService.getYear(week));
        return "/provider-appointments";
    }


//    @GetMapping("/my-appointments")
//    public String myAppointmentPage(Model model){
//        List<Integer> daysOfWeek = webService.getListOfDates(new LocalDate().getWeekOfWeekyear());
//        List<AppointmentDto> appointmentList = restTemplate.getForObject("http://appointment-service/api/v1/appointments/week-appointments/"+new LocalDate().getWeekOfWeekyear(), List.class);
//        model.addAttribute("daysOfWeek", daysOfWeek);
//        model.addAttribute("month", new LocalDate().getWeekOfWeekyear());
//        model.addAttribute("year", new LocalDate().getWeekOfWeekyear());
//        return "my-appointments";
//    }

    @PostMapping("/collect-appointment-data")
    public String collectAppointmentData(String role, Long userId){
        Integer week = new LocalDate().getWeekOfWeekyear();
        log.info(String.valueOf(week));
        return "redirect:my-appointments/"+role+"/"+userId+"/"+week;
    }

    @PostMapping("/view-appointment")
    public String viewAppointmentPage(Long appointmentId){
        return "redirect:appointment/"+appointmentId;
    }

    @GetMapping("/appointment/{id}")
    public String displayAppointmentPage(@PathVariable Long id, Model model){

        AppointmentDto appointmentDto = appointmentService.getAppointmentById(id);
        log.info("Appointment viewed {}", appointmentDto);
        AppointmentDetailDto appointmentDetails = appointmentService.getAppointmentDetailById(id);

        UserData clientData = new UserData();
        UserData providerData = new UserData();
        assert appointmentDto != null;
        if(appointmentDto.getClientId() !=null) clientData = userDataService.getUserDataByUserId(appointmentDto.getClientId());
        if(appointmentDto.getProviderId() != null) providerData = userDataService.getUserDataByUserId(appointmentDto.getProviderId());

        model.addAttribute("appointment", appointmentDto);
        model.addAttribute("appointmentDetails", appointmentDetails);
        model.addAttribute("clientData", clientData);
        model.addAttribute("providerData", providerData);
        return "appointment";
    }

    @GetMapping("/new-appointment/{userId}")
    public String createNewAppointment(@PathVariable Long userId, Model model){
        model.addAttribute("userId", userId);
        return "new-appointment";
    }

    @PostMapping("/create-appointment")
    public String createAppointment(Long userId, String startDate, Integer startHour, String appointmentType){

        AppointmentDto appointmentDto = appointmentService.createAppointment(
                AppointmentRequest.builder()
                        .appointmentType(appointmentType)
                        .providerId(userId)
                        .startDate(startDate)
                        .startHour(startHour.toString())
                        .build()
        );
        log.info("appointment created: {}", appointmentDto);
        return "redirect:new-appointment/"+userId;
    }

    @PostMapping("/see-provider-appointments")
    public String redirectToProviderAppointments(Long providerId){
        return "redirect:/provider-appointments/"+providerId+"/"+ LocalDate.now().getWeekOfWeekyear();
    }



    @PostMapping("/delete-appointment/{id}")
    public String deleteAppointment(@PathVariable Long id, String role, Long userId, Integer week){

        if(role.equals("provider")) appointmentService.deleteAppointment(id);
        if(role.equals("client")) appointmentService.cancelAppointment(id);

        log.info(" Appointment with id {} deleted", id);
        return "redirect:/my-appointments/"+role+"/"+userId+"/"+week;
    }

    @PostMapping ("/change-app-status/{id}")
    public String changAppointmentStatus(@PathVariable Long id){
        appointmentService.changeAppointmentStatus(id);
        return "redirect:/appointment/"+id;
    }

    @PostMapping("/change-app-type/{id}")
    public String changAppointmentType(@PathVariable Long id, String appointmentType){
        appointmentService.changeAppointmentType(appointmentType, id);
        return "redirect:/appointment/"+id;
    }

    @PostMapping("/book-appointment/{appointmentId}")
    public String bookAnAppointment(@PathVariable Long appointmentId, Long clientId, String details, Long providerId, String week){
        log.info("Client class {}", clientId);
        log.info("Details {}", details);
        log.info("App id {}", appointmentId);
        appointmentService.bookAnAppointment(clientId, appointmentId, details);
        return "redirect:/provider-appointments/"+providerId+"/"+week;
    }

    @PostMapping("/test")
    public String seeSomethingFromScript(String string){
        log.info("String is {}", string);
        return "redirect:/provider-appointments/7/14";
    }







}
