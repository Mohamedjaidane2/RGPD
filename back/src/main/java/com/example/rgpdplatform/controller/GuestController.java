package com.example.rgpdplatform.controller;

import com.example.rgpdplatform.dto.requests.PaginationDto;
import com.example.rgpdplatform.dto.requests.create.GuestNewDto;
import com.example.rgpdplatform.dto.GuestDto;
import com.example.rgpdplatform.dto.response.GuestPaginationResultDto;
import com.example.rgpdplatform.dto.response.SuccessDto;
import com.example.rgpdplatform.dto.response.TestPaginationResultDto;
import com.example.rgpdplatform.service.IGuestService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@Api("/guest")
@RequestMapping(path = "/api/guest")
public class GuestController {
   private final IGuestService iGuestService;

    public GuestController(@Qualifier("guestServiceImpl") IGuestService iGuestService) {
        this.iGuestService = iGuestService;
    }

    @PostMapping(path = "/add")
    public ResponseEntity<SuccessDto> addGuest(@RequestBody @Valid GuestNewDto guestNewDto) {
        return ResponseEntity.ok(iGuestService.addGuest(guestNewDto));
    }

    @GetMapping(path = "/{guestId}")
    public ResponseEntity<GuestDto> getGuest(
            @PathVariable(name = "guestId") String guestId) {
        GuestDto guest = iGuestService.getGuestById(guestId);
        return ResponseEntity.ok(guest);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "/all")
    public ResponseEntity<List<GuestDto>> getGuest() {
        List<GuestDto> guests = iGuestService.getAllGuests();
        return ResponseEntity.ok(guests);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(path = "/all/pagination")
    public ResponseEntity<GuestPaginationResultDto> getGuestsByPagination(@RequestBody PaginationDto paginationDto) {
        return ResponseEntity.ok(iGuestService.getGuestsByPagination(paginationDto));
    }
    @GetMapping(path = "/guests/{Organisation_name}")
    public ResponseEntity<List<GuestDto>> getGuestByOrganisationName(
            @PathVariable(name = "Organisation_name") String Organisation_name) {
        List<GuestDto> guestList = iGuestService.getGuestsByOrganisation(Organisation_name);
        return ResponseEntity.ok(guestList);
    }
}
